package at.fischelmayer.gvarest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pickup-codes")
public class PickupCodeRestController {

    private final PickupCodeService pickupCodeService;
    private final PickupDateFinder pickupDateFinder;

    public PickupCodeRestController(PickupCodeService pickupCodeService,
                                    PickupDateFinder pickupDateFinder) {
        this.pickupCodeService = pickupCodeService;
        this.pickupDateFinder = pickupDateFinder;
    }

    /**
     * Returns the pickup codes for the upcoming days.
     * If no forecastDays parameter is provided, the default value is 3.
     *
     * Notice: The format was chosen to ensure easy parsing on the client side (Loxone Miniserver)
     *
     * @param forecastDays
     * @return
     */
    @GetMapping
    public String getAbholterminCodes(@RequestParam(required = false) Integer forecastDays) {

        if (forecastDays == null) {
            forecastDays = 3;
        }
        List<Pickup> forUpcomingDays = pickupDateFinder.findForUpcomingDays(forecastDays);
        return pickupCodeService.getPickupSummery(forUpcomingDays);
    }

}
