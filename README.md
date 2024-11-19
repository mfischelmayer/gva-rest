# Müllabfuhr API

Eine API mit welcher abgefragt werden kann welcher Art Müll an welchen Termin abgeholt wird.
Um die einzelnen Müllarten und deren Abholkombination zu identifizieren, wird ein 5-Bit-Integer verwendet.
Gelber Sack = 1, Biomüll = 2, Papiermüll = 4, Restmüll = 8, Windeltonne = 16.
Das wird in der Tabelle weiter unten erläutert (Bitmuster).

Diese Variante ist explizit gewählt, um die Weiterverarbeitung im Loxone Miniserver zu vereinfachen.
Dadurch wird kompliziertes Response Parsing im Miniserver
vermieden ([siehe Anmerkung Loxone Miniserver](#anmerkung-loxone-miniserver)).

## Tabelle

| Müllarten                                                   | Binäre Darstellung | Dezimalwert |
|-------------------------------------------------------------|--------------------|-------------|
| **Keine Abholung**                                          | 00000              | 0           |
| **Gelber Sack**                                             | 00001              | 1           |
| **Biomüll**                                                 | 00010              | 2           |
| Biomüll + Gelber Sack                                       | 00011              | 3           |
| **Papiermüll**                                              | 00100              | 4           |
| Papiermüll + Gelber Sack                                    | 00101              | 5           |
| Papiermüll + Biomüll                                        | 00110              | 6           |
| Papiermüll + Biomüll + Gelber Sack                          | 00111              | 7           |
| **Restmüll**                                                | 01000              | 8           |
| Restmüll + Gelber Sack                                      | 01001              | 9           |
| Restmüll + Biomüll                                          | 01010              | 10          |
| Restmüll + Biomüll + Gelber Sack                            | 01011              | 11          |
| Restmüll + Papiermüll                                       | 01100              | 12          |
| Restmüll + Papiermüll + Gelber Sack                         | 01101              | 13          |
| Restmüll + Papiermüll + Biomüll                             | 01110              | 14          |
| Restmüll + Papiermüll + Biomüll + Gelber Sack               | 01111              | 15          |
| **Windeltonne**                                             | 10000              | 16          |
| Windeltonne + Gelber Sack                                   | 10001              | 17          |
| Windeltonne + Biomüll                                       | 10010              | 18          |
| Windeltonne + Biomüll + Gelber Sack                         | 10011              | 19          |
| Windeltonne + Papiermüll                                    | 10100              | 20          |
| Windeltonne + Papiermüll + Gelber Sack                      | 10101              | 21          |
| Windeltonne + Papiermüll + Biomüll                          | 10110              | 22          |
| Windeltonne + Papiermüll + Biomüll + Gelber Sack            | 10111              | 23          |
| Windeltonne + Restmüll                                      | 11000              | 24          |
| Windeltonne + Restmüll + Gelber Sack                        | 11001              | 25          |
| Windeltonne + Restmüll + Biomüll                            | 11010              | 26          |
| Windeltonne + Restmüll + Biomüll + Gelber Sack              | 11011              | 27          |
| Windeltonne + Restmüll + Papiermüll                         | 11100              | 28          |
| Windeltonne + Restmüll + Papiermüll + Gelber Sack           | 11101              | 29          |
| Windeltonne + Restmüll + Papiermüll + Biomüll               | 11110              | 30          |
| Windeltonne + Restmüll + Papiermüll + Biomüll + Gelber Sack | 11111              | 31          |

## Daten

Die Daten werden von der GVA (Gemeindeverband für Abfallbeseitigung) Seite geholt.
Hier habe ich schon vor längerem einen Parser geschrieben, welcher die Daten von der Webseite ausliest und in ein CSV
File speichert. Das CSV File ist eigentlich für Google Kalender gedacht.
Hier nutze ich dasselbe CSV File, um die Daten in die API zu laden.

## Anmerkung Loxone Miniserver

Im Miniserver wird anhand der Dezimalwerte entschieden, welche `mood` (Stimmung) eines Lichtsteuerungselements aktiviert
wird.
Ein Lichtsteuerungselement hat theoretisch 99 mögliche Szenen.
Ich nutze also den Ganzzahlwert der API direkt als ID für die jeweilige Lichtstimmung.
Eine Beleuchtung über den Mülltonnen zeigt, welcher Müll an welchem Tag abgeholt wird.
Durch RGBW-Dimmer und RGBW-LED-Stripes wird die Beleuchtung realisiert.
Die Beleuchtung hinter den Mülltonnen leuchtet in der jeweiligen Farben der Müllart.
In Österreich ist das z.b. rot für Papier, gelb für Gelber Sack, braun für Biomüll und schwarz für Restmüll
(Schwarz leuchten ist natürlich schwierig 😅, hier wurde blau als passende Alternative gewählt).
In meiner aktuellen Situation ist die optionale Windeltonne unumgänglich. Hier wurde die Farbe Orange gewählt.

### Spiel und Zeug

Für den Youtube-Kanal [Spiel und Zeug](https://www.youtube.com/spielundzeug) wurde ein Video erstellt, welches die
Automation in Aktion zeigt.
Das Video ist [hier](https://www.youtube.com/watch?v=3J9J9Q6Z9ZQ) zu finden.
Außerdem gibt es einen Blogartikel
auf [fischelmayer.at](https://fischelmayer.at/2021/07/25/muellabfuhr-automatisierung-mit-loxone/),
welcher einen tieferen Einblick in das vollständige Projekt gibt.