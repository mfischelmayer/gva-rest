package at.fischelmayer.gvarest;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
class PickupCodeServiceTest {
    @Test
    void name() {
        PickupCodeService pickupCodeService = new PickupCodeService();
        List<Pickup> pickups = List.of(
                new Pickup(LocalDate.parse("2021-01-04"), 0, Pickup.Type.RESTMUELL),
                new Pickup(LocalDate.parse("2021-01-04"), 0, Pickup.Type.WINDEL),
                new Pickup(LocalDate.parse("2021-01-06"), 2, Pickup.Type.PAPIER),
                new Pickup(LocalDate.parse("2021-01-06"), 2, Pickup.Type.BIO),
                new Pickup(LocalDate.parse("2021-01-09"), 5, Pickup.Type.GELBE_TONNE)
        );
        String abholterminSummery = pickupCodeService.getPickupSummery(pickups);
        System.out.println(abholterminSummery);
    }

    //
//    //    InMemAbholterminRepository inMemAbholterminRepository = Mockito.mock(InMemAbholterminRepository.class);
//    AbholterminFinder abholterminFinder = Mockito.mock(AbholterminFinder.class);
//    AbholcodeService abholcodeService = new AbholcodeService(abholterminFinder);
//
//    @Test
//    void berechneAbholCode_alleMuellarten() {
//
//        when(abholterminFinder.findForUpcomingDays(1)).thenReturn(List.of(
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.PAPIER),
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.BIO),
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.GELBE_TONNE),
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.RESTMUELL),
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.WINDEL)
//        ));
//
//        List<Abholtermin> abholtermine = abholterminFinder.findForUpcomingDays(1);
//        int code = abholcodeService.berechneAbholCode(LocalDate.parse("2021-01-04"), abholtermine);
//        Assertions.assertThat(code).isEqualTo(31);  // 31 = 1 + 2 + 4 + 8 + 16 = Windel + Restmüll + Papier + Bio + Gelbe Tonne (alles)
//    }
//
//    @Test
//    void berechneAbholCode_windelUndRestmuell() {
//
//        when(abholterminFinder.findForUpcomingDays(1)).thenReturn(List.of(
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.RESTMUELL),
//                new Abholtermin(LocalDate.parse("2021-01-04"), Abholtermin.Type.WINDEL)
//        ));
//
//        List<Abholtermin> abholtermine = abholcodeService.afindForUpcomingDays(LocalDate.parse("2021-01-04"));
//        int code = abholcodeService.berechneAbholCode(LocalDate.parse("2021-01-04"), abholtermine);
//        Assertions.assertThat(code).isEqualTo(3);   // 3 = 1 + 2 = Windel + Restmüll
//    }
////
////    @Test
////    void name() {
////        LocalDate now = LocalDate.now();
////        when(abholterminFinder.findForDate(now))
////                .thenReturn(List.of(
////                        new Abholtermin(now, Abholtermin.Type.RESTMUELL),
////                        new Abholtermin(now, Abholtermin.Type.WINDEL),
////                        new Abholtermin(now.plusDays(2), Abholtermin.Type.PAPIER),
////                        new Abholtermin(now.plusDays(2), Abholtermin.Type.BIO),
////                        new Abholtermin(now.plusDays(5), Abholtermin.Type.GELBE_TONNE)
////                ));
////        List<AbholterminSummery> abholterminSummery = abholcodeService.getAbholterminSummery();
////        System.out.println(abholterminSummery);
////    }
}