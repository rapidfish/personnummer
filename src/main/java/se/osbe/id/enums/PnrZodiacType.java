package se.osbe.id.enums;


public enum PnrZodiacType {

    CAPRICORNUS("01-01", "01-19", "Capricornus", "Stenbocken"),
    AQUARIUS("01-20", "02-18", "Aquarius", "Vattumannen"),
    PISCES("02-19", "03-20", "Pisces", "Fiskarna"),

    ARIES("03-21", "04-19", "Aries", "Väduren"),
    TAURUS("04-20", "05-21", "Taurus", "Oxen"),
    GEMINI("05-22", "06-21", "Gemini", "Tvillingarna"),

    CANCER("06-22", "07-22", "Cancer", "Kräftan"),
    LEO("07-23", "08-22", "Leo", "Lejonet"),
    VIRGO("08-23", "09-22", "Virgo", "Jungfrun"),

    LIBRA("09-23", "10-22", "Libra", "Vågen"),
    SCORPIO("10-23", "11-21", "Scorpio", "Skorpionen"),
    SAGITTARIUS("11-22", "12-31", "Sagittarius", "Skytten");

    PnrZodiacType(String startDate, String endDate, String latin, String swedish) {
        _start = startDate;
        _end = endDate;
        _latin = latin;
        _swedish = swedish;
    }

    private final String _start;
    private final String _end;
    private final String _latin;
    private final String _swedish;

    public String getSwedishName() {
        return _swedish;
    }

    public String getLatinName() {
        return _latin;
    }

    public String getStartDate() {
        return _start;
    }

    public String getEndDate() {
        return _end;
    }

    /**
     * toString returning the latin name of this zodiac sign
     */
    @Override
    public String toString() {
        return this.name();
    }
}