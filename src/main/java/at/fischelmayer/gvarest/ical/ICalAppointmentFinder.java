package at.fischelmayer.gvarest.ical;

import at.fischelmayer.gvarest.Pickup;
import at.fischelmayer.gvarest.PickupDateFinder;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ICalAppointmentFinder implements PickupDateFinder {

    private static final Logger log = LoggerFactory.getLogger(ICalAppointmentFinder.class);

    @Value("${ical.url}")
    private String iCalUrl;

    private final Clock clock;

    public Clock getClock() {
        return clock;
    }

    public String getiCalUrl() {
        return iCalUrl;
    }

    public void setiCalUrl(String iCalUrl) {
        this.iCalUrl = iCalUrl;
    }

    public ICalAppointmentFinder(Clock clock) {
        this.clock = clock;
    }

    public List<Pickup> upcomingPickupDates() {
        // currently we load all future events and filter them in memory
        // maybe its more efficient to filter them before (--> check ical4j documentation)
        // not really a problem for now because there are just a few events and just for one year in forecast (~100 events a year)

        List<Pickup> abholtermine = new ArrayList<>();
        try {

            long currentTimeMillis = System.currentTimeMillis();

            URL url = new URL(iCalUrl);
            InputStream in = url.openStream();

            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(in);

            LocalDate today = LocalDate.now(clock);

            for (Object component : calendar.getComponents()) {
                if (component instanceof VEvent event) {

                    Temporal pickupDateTemporal = event.getDateTimeStart().orElseThrow().getDate();
                    LocalDate pickupDate = getLocalDateFromTemporal(pickupDateTemporal);

                    if (pickupDate.isAfter(today)) {
                        Optional<Summary> summary = event.getSummary();

                        summary.ifPresent(s -> {
                            Pickup.Type type = Pickup.Type.valueOf(s.getValue().toUpperCase());

                            // days between today and the pickup date
                            long days = today.until(pickupDate).getDays();

                            abholtermine.add(new Pickup(pickupDate, (int) days, type));
                        });
                    }
                }
            }
            log.debug("Found {} upcoming pickup dates", abholtermine.size());
            log.debug("Time taken: {}ms", System.currentTimeMillis() - currentTimeMillis);
        } catch (Exception e) {
            log.error("Error while fetching upcoming pickup dates", e);
        }
        return abholtermine;
    }

    public static LocalDate getLocalDateFromTemporal(Temporal temporal) {
        if (temporal instanceof LocalDate) {
            // Temporal is already a LocalDate
            return (LocalDate) temporal;
        } else if (temporal instanceof LocalDateTime) {
            // Temporal is LocalDateTime, so extract the date part
            return ((LocalDateTime) temporal).toLocalDate();
        } else if (temporal instanceof ZonedDateTime) {
            // Temporal is ZonedDateTime, so extract the date part
            return ((ZonedDateTime) temporal).toLocalDate();
        } else {
            throw new IllegalArgumentException("Unsupported Temporal type: " + temporal.getClass().getName());
        }
    }

    /**
     * @param givenDate THe date to check
     * @param days      The number of days to check
     * @return
     */
    private boolean isWithinNextDays(LocalDate givenDate, int days) {
        LocalDate today = LocalDate.now(clock);
        LocalDate threeDaysLater = today.plusDays(days);

        // Check if the given date is within today and three days later (inclusive)
        return (givenDate.isEqual(today) || givenDate.isAfter(today)) && givenDate.isBefore(threeDaysLater);
    }

    @Override
    public List<Pickup> findForUpcomingDays(int dayForecast) {
        List<Pickup> abholtermine = upcomingPickupDates();
        abholtermine.removeIf(abholtermin -> !isWithinNextDays(abholtermin.date(), dayForecast));
        return abholtermine;
    }
}
