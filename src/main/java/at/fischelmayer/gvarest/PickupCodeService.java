package at.fischelmayer.gvarest;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service zum Berechnen des Abholcodes
 * Der Abholcode ist ein Bitmuster, das sich aus den Abholterminen eines Tages ergibt.
 * Jedes Bit repräsentiert eine Müllart.
 * Die Bitpositionen sind wie folgt definiert:
 * Bit 1: Windel
 * Bit 2: Restmüll
 * Bit 3: Papier
 * Bit 4: Bio
 * Bit 5: Gelbe Tonne
 * <p>
 * Beispiel:
 * Wenn am 01.01.2021 Restmüll und Windel abgeholt werden, dann ist der Abholcode 3 (Bit 1 und Bit 2 sind gesetzt).
 * Wenn am 02.01.2021 Bio und Papier abgeholt werden, dann ist der Abholcode 12 (Bit 3 und Bit 4 sind gesetzt).
 * <p>
 * |---------------------------------------------------- <br>
 * | Muellart     | Binaere Darstellung | Dezimal      | <br>
 * |----------------------------------------------------   <br>
 * | Windel          | 00001               | 1         |  <br>
 * | Restmuell       | 00010               | 2         |  <br>
 * | Papier          | 00100               | 4         |  <br>
 * | Bio             | 01000               | 8         |  <br>
 * | Gelbe Tonne     | 10000               | 16        |  <br>
 * | Windel+Restmuel | 00011               | 3         |  <br>
 * | Windel+Papier   | 00101               | 5         |  <br>
 * | Windel+Bio      | 01001               | 9         |  <br>
 * | Windel+GelbeTon | 10001               | 17        |  <br>
 * usw.
 * |----------------------------------------------------
 */
@Service
public class PickupCodeService {

    /**
     * Erzeugt eine Zusammenfassung der Abholtermine in der Form:
     * <p>
     * days_utils:0;code:3 <br>
     * days_utils:2;code:12 <br>
     * days_utils:5;code:16 <br>
     * </p>
     *
     * @param termine
     * @return
     */
    public String getPickupSummery(List<Pickup> termine) {

        Map<Integer, List<Pickup>> termineByDay = termine
                .stream()
                .collect(Collectors.groupingBy(Pickup::daysUntilPickup));

        StringBuilder sb = new StringBuilder();

        termineByDay.forEach((days, termins) -> {
            sb.append("days_utils:").append(days).append(";code:").append(berechneAbholCode(termins.getFirst().date(), termins)).append("\n");
        });

        return sb.toString();
    }

    /**
     * @param date         das Datum, für das die Abholung geprüft wird
     * @param abholtermine
     * @return Dezimaldarstellung welcher der die Müllabholung an diesem Tag kodiert (siehe Tabelle - z.b. 3 für Windel+Restmüll)
     */
    private int berechneAbholCode(LocalDate date, List<Pickup> abholtermine) {

        int abholCode = 0;

        for (Pickup termin : abholtermine) {
            if (termin.date().equals(date)) {
                abholCode |= getBitMaskForType(termin.type());
            }
        }
        return abholCode;
    }

    /**
     * Gibt die Bitmaske für den jeweiligen Mülltyp zurück.
     *
     * @param type der Mülltyp
     * @return die Bitmaske für den Mülltyp
     */
    private int getBitMaskForType(Pickup.Type type) {
        return switch (type) {
            case WINDEL -> 1;        // Bit 1
            case RESTMUELL -> 2;     // Bit 2
            case PAPIER -> 4;        // Bit 3
            case BIO -> 8;           // Bit 4
            case GELBE_TONNE -> 16;  // Bit 5
        };
    }

}
