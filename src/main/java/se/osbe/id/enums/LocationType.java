package se.osbe.id.enums;

/**
 * WARNING! DO NOT ALTER THIS FILE
 * <p>
 * Följande nummerserier användes före 1990 för de olika länen i sverige
 * <p>
 * 00–13 Stockholms län
 * 14–15 Uppsala län
 * 16–18 Södermanlands län
 * 19–23 Östergötlands län
 * 24–26 Jönköpings län
 * 27–28 Kronobergs län
 * 29–31 Kalmar län
 * 32    Gotlands län
 * 33–34 Blekinge län
 * 35–38 Kristianstads län
 * 39–45 Malmöhus län
 * 46–47 Hallands län
 * 48–54 Göteborgs och Bohus län
 * 55–58 Älvsborgs län
 * 59–61 Skaraborgs län
 * 62–64 Värmlands län
 * 65    Födda utomlands
 * 66–68 Örebro län
 * 69–70 Västmanlands län
 * 71–73 Kopparbergs län
 * 75–77 Gävleborgs län
 * 78–81 Västernorrlands län
 * 82–84 Jämtlands län
 * 85–88 Västerbottens län
 * 89–92 Norrbottens län
 * 93–99 Födda utomlands eller utländska medborgare födda i Sverige
 * <p>
 * Numret 74 användes normalt inte förutom i undantagsfall där de vanliga numren inte räckte till.
 * Detta system används inte längre, och gäller endast för personer födda i, eller invandrade till, Sverige före 1990.
 * <p>
 * Källa: http://id-check.artega.biz/info-sv.php
 */
public enum LocationType {

    STOCKHOLM(00, 13, "Stockholms län"),
    UPPSALA(14, 15, "Uppsala län"),
    SODERMANLAND(16, 18, "Södermanlands län"),
    OSTERGOTLAND(19, 23, "Östergötlands län"),
    JONKOPING(24, 26, "Jönköpings län"),
    KRONOBERG(27, 28, "Kronobergs län"),
    KALMAR(29, 31, "Kalmar län"),
    GOTLAND(32, 32, "Gotlands län"),
    BLEKINGE(33, 34, "Blekinge län"),
    KRISTIANSTAD(35, 38, "Kristianstads län"),
    MALMOHUS(39, 45, "Malmöhus län"),
    HALLAND(46, 47, "Hallands län"),
    GOTEBORG_BOHUS(48, 54, "Göteborgs och Bohus län"),
    ALVSBORG(55, 58, "Älvsborgs län"),
    SKARABORG(59, 61, "Skaraborgs län"),
    VARMLAND(62, 64, "Värmlands län"),
    UTLANDSFODD1(65, 65, "Född utomlands"),
    OREBRO(66, 68, "Örebro län"),
    VASTMANLAND(69, 70, "Västmanlands län"),
    KOPPARBERG(71, 73, "Kopparbergs län"), // sedan 1997 Dalarnas län.
    UNUSED(74, 74, "Reservnummer"),
    GAVLEBORG(75, 77, "Gävleborgs län"),
    VASTERNORRLAND(78, 81, "Västernorrlands län"),
    JAMTLAND(82, 84, "Jämtlands län"),
    VASTERBOTTEN(85, 88, "Västerbottens län"),
    NORRBOTTEN(89, 92, "Norrbottens län"),
    UTLANDSFODD2(93, 99, "Utlandsfödd");

    private final int _rangeLow;
    private final int _rangeHigh;
    private final String _description;

    LocationType(int rangeLow, int rangeHigh, String description) {
        _rangeLow = rangeLow;
        _rangeHigh = rangeHigh;
        _description = description;
    }

    public String getDescription() {
        return _description;
    }

    public int getRangeLow() {
        return _rangeLow;
    }

    public int getRangeHigh() {
        return _rangeHigh;
    }
}