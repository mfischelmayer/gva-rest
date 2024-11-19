package at.fischelmayer.gvarest;

import java.time.LocalDate;

public record Pickup(LocalDate date, int daysUntilPickup, Type type) {
    public enum Type {
        RESTMUELL, WINDEL, PAPIER, BIO, GELBE_TONNE
    }
}
