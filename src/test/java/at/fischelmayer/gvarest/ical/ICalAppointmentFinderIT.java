package at.fischelmayer.gvarest.ical;

import at.fischelmayer.gvarest.Pickup;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;

class ICalAppointmentFinderIT {

    @Test
    void upcomingPickupDates() {
        String url = "https://calendar.google.com/calendar/ical/1041iirn1jefq6nfin6k5phkdo%40group.calendar.google.com/public/basic.ics";
        ICalAppointmentFinder iCalAppointmentFinder = new ICalAppointmentFinder(Clock.systemDefaultZone());
        iCalAppointmentFinder.setiCalUrl(url);
        List<Pickup> pickups = iCalAppointmentFinder.upcomingPickupDates();
        System.out.println(pickups);
    }

    @Test
    void upcomingPickupDates_nextFiveDays() {
        String url = "https://calendar.google.com/calendar/ical/1041iirn1jefq6nfin6k5phkdo%40group.calendar.google.com/public/basic.ics";
        ICalAppointmentFinder iCalAppointmentFinder = new ICalAppointmentFinder(Clock.systemDefaultZone());
        iCalAppointmentFinder.setiCalUrl(url);
        List<Pickup> pickups = iCalAppointmentFinder.findForUpcomingDays(5);
        System.out.println(pickups);
    }

}