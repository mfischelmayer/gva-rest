package at.fischelmayer.gvarest;

import java.util.List;

public interface PickupDateFinder {
    List<Pickup> findForUpcomingDays(int dayForecast);
}
