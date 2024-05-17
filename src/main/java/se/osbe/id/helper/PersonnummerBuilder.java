package se.osbe.id.helper;

import se.osbe.id.enums.GenderType;
import se.osbe.id.enums.LocationType;
import se.osbe.id.exception.PersonnummerException;
import se.osbe.id.Personnummer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static se.osbe.id.helper.PersonnummerHelper.generateAllValidPnrForDate;
import static se.osbe.id.helper.PersonnummerHelper.parseToDate;


public class PersonnummerBuilder {

    private boolean _isCreateAsSamordningsnummer;
    private int _limit;
    private LocalDate _startDate;
    private LocalDate _endDate;
    private LocationType _location;
    private GenderType _gender;
    private Integer _checksum;

    public PersonnummerBuilder() {
    }

    /**
     * Constructor, automatically initializing a date span to generate a Personnummer
     * series, based on age given as parameter.
     *
     * @param age
     * @throws PersonnummerException if any error occur, a description will be sent with along
     *                               with the exception.
     */
    public PersonnummerBuilder setAge(Integer age) throws PersonnummerException {
        if (age == null) {
            throw new PersonnummerException("Age can not be set to null");
        }
        _startDate = LocalDate.now().minusYears(age).minusYears(1).plusDays(1);
        _endDate = _startDate.plusYears(1).minusDays(1);
        return this;
    }

    /**
     * Constructor, manually initializing a date span for generating Personnummer series
     * based on start- and end date given as arguments.
     * <p>
     * Warning: Since each day contains 1000 personnummer, any date span is multiplied by 1000 for each date.
     * (one week=7000, one month=30.000, one year=365.000, ten years=3.650.000)
     *
     * @param start start date of Personnummer series
     * @param end   end date of Personnummer series (inclusive)
     * @throws PersonnummerException if any error occur, a description will be sent with along
     *                               with the exception.
     */
    public PersonnummerBuilder setStartAndEndDate(LocalDate start, LocalDate end) throws PersonnummerException {
        _startDate = start;
        _endDate = end;
        if (start == null) {
            throw new PersonnummerException("start date can not be set to null");
        } else if (end == null) {
            throw new PersonnummerException("end date can not be set to null");
        } else if (_endDate.isAfter(LocalDate.now())) {
            throw new PersonnummerException("End date can not be set to a future date");
        }
        if (_startDate.isAfter(_endDate)) {
            LocalDate tmpDate;
            tmpDate = _startDate;
            _startDate = _endDate;
            _endDate = tmpDate;
        }
        return this;
    }

    /**
     * This method can only be set if startDate and endDate is between the years
     * 1947 and 1990
     *
     * @param location location from where the person was born
     * @return PnrBuilder
     */
    public PersonnummerBuilder setLocation(LocationType location) {
        _location = location;
        return this;
    }

    public PersonnummerBuilder setAsSamordningsnummer(boolean isSam) {
        _isCreateAsSamordningsnummer = isSam;
        return this;
    }

    /**
     * Biological gender (biological gender when born)
     * @param gender male or female
     * @return
     */
    public PersonnummerBuilder setGender(GenderType gender) {
        _gender = gender;
        return this;
    }

    public PersonnummerBuilder setChecksum(Integer checksum) {
        if (checksum == null) {
            return this;
        }
        _checksum = checksum;
        return this;
    }

    /**
     * Set upper limit to how many Personnummer can be generated when calling build()
     *
     * @param limit a number of how many Personnummer to be generated when calling build method.
     * @return PnrBuilder
     * @throws PersonnummerException
     */
    public PersonnummerBuilder setUpperLimitForGenerate(int limit) throws PersonnummerException {
        if (limit == 0) {
            throw new PersonnummerException("Builder can not generate 0 Personnummer");
        } else if (limit < 0) {
            throw new PersonnummerException("Builder can not generate a negative number of Personnummer");
        }
        _limit = limit;
        return this;
    }

    public List<Personnummer> build() throws PersonnummerException {
        return build(true);
    }

    /**
     * Generates the list with valid Personnummer based on parameters set up with the
     * setters of this builder class.
     *
     * @param useSort true makes the returning list sorted into ascending order
     * @return a list with generated and all valid Personnummer.
     * @throws PersonnummerException if any error occur, a description will be sent with along
     *                               with the exception.
     */
    public List<Personnummer> build(boolean useSort) throws PersonnummerException {
        List<Personnummer> tempList;
        List<Personnummer> hitList = new LinkedList<Personnummer>();
        List<Personnummer> resultList = new LinkedList<Personnummer>();
        if (_startDate == null) {
            throw new PersonnummerException("Can not build due to missing start date");
        } else if (_endDate == null) {
            throw new PersonnummerException("Can not build due to missing end date");
        }
        LocalDate dateWalk = _startDate;
        while (dateWalk.isBefore(_endDate) || dateWalk.isEqual(_endDate)) {
            StringBuilder regexBuilder = new StringBuilder();
            regexBuilder.append(resolveDatePattern(dateWalk));
            regexBuilder.append(resolveBirthplaceRegex(_location));
            regexBuilder.append(resolveGenderRegex(_gender));
            regexBuilder.append(resolveChecksum(_checksum));
            String masterRegex = regexBuilder.toString();
            // Search for hits and add those to tempList, searching one date at a time
            tempList = generateAllValidPnrForDate(parseToDate(dateWalk.toString().replaceAll("\\D", "")).get());
            hitList = PersonnummerHelper.findPersonnummerInList(masterRegex, tempList);
            resultList.addAll(hitList);
            if (_limit != 0 && resultList.size() >= _limit) {
                break;
            }
            dateWalk = dateWalk.plusDays(1);
        }

        if (useSort) {
            Collections.sort(resultList);
        }
        return (_limit != 0 && _limit <= resultList.size()) ? resultList.subList(0, _limit) : resultList;
    }

    private String resolveDatePattern(LocalDate date) {
        StringBuilder result = new StringBuilder();
        if (date == null) {
            result.append("\\d{6}");
        } else {
            result.append("(").append(date.toString().replaceAll("\\D", "")).append(")");
        }
        return result.toString();
    }

    private String resolveBirthplaceRegex(LocationType location) {
        StringBuilder result = new StringBuilder();
        if (location == null) {
            result.append("\\d{2}");
        } else {
            result.append("(");
            int high = location.getRangeHigh();
            for (int i = location.getRangeLow(); i <= high; i++) {
                result.append((i < 10) ? ("0" + i) : i);
                if (i < high) {
                    result.append("|");
                }
            }
            result.append(")");
        }
        return result.toString();
    }

    private String resolveGenderRegex(GenderType gender) {
        String result = "[0-9]";
        if (gender != null) {
            result = gender.equals(GenderType.FEMALE) ? "(0|2|4|6|8)" : "(1|3|5|7|9)";
        }
        return result;
    }

    private String resolveChecksum(Integer checksum) {
        StringBuilder result = new StringBuilder();
        if (checksum == null) {
            result.append("([0-9])");
        } else {
            result.append("([").append((checksum % 10)).append("])");
        }
        return result.toString();
    }
}