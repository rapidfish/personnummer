package se.osbe.id.enums;

import java.util.Optional;

import static java.util.Arrays.stream;

public enum OrgGruppnummerType {
    DODSBON(1), // Dödsbon

    // Stat, regioner (f.d. landsting), kommuner, församlingar, trossamfund
    STAT_KOMMUN_LANDSTING(2),

    // Utländska företag som bedriver näringsverksamhet eller äger fastigheter i Sverige
    UTLÄNDSKA(3),

    // Aktiebolag, filialer, banker, försäkringsbolag och europabolag
    AKTIEBOLAG(5),

    // Bostadsrättsföreningar, ekonomiska föreningar, näringsdrivande ideella föreningar, bostadsföreningar, kooperativa hyresrättsföreningar, europakooperativ och Europeiska grupperingar för territoriellt samarbete
    EKONOMISK_FORENING(7),

    // Stiftelser, ideella föreningar, trossamfund
    STIFTELSE_IDEELL_FORENING(8),

    // Handelsbolag eller kommanditbolag
    HANDELSBOLAG_KOMMANDITBOLAG(9),

    // Statliga myndigheter (tilldelas av Statistikmyndigheten SCB)
    STATLIG_MYNDIGHET(20);

    private int _grupp;

    OrgGruppnummerType(int gruppnummer) {
        _grupp = gruppnummer;
    }

    public int getGruppnummer() {
        return _grupp;
    }

    public static Optional<OrgGruppnummerType> fromInt(int num) {
        return stream(values())
                .filter(o -> num == o._grupp)
                .findFirst();
    }

//    public static Optional<OrgGruppnummerType> fromString(String grupp) {
//        return isNotBlank(grupp) ? stream(values())
//                .filter(o -> grupp.equalsIgnoreCase(o.name()))
//                .findFirst() : empty();
//    }
}
