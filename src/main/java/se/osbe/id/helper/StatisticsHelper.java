package se.osbe.id.helper;

import se.osbe.id.enums.GenderType;
import se.osbe.id.exception.PersonnummerException;
import se.osbe.id.Personnummer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StatisticsHelper {
    private Map<String, Personnummer> _pnrMap;
    private List<Personnummer> _pnrList;
    private List<Personnummer> _pnrDuplicatesList;
    private int _amountMen;
    private int _amountWomen;
    private int _maxAge;
    private int _minAge;
    private double _avgAge;

    @SuppressWarnings("unused")
    private StatisticsHelper() {
    }

    public StatisticsHelper(List<Personnummer> pnrList) throws PersonnummerException {
        if (pnrList == null || pnrList.isEmpty()) {
            throw new PersonnummerException("pnrList is null or empty");
        }
        _pnrDuplicatesList = new LinkedList<Personnummer>();
        _pnrMap = new HashMap<String, Personnummer>();
        for (Personnummer pnr : pnrList) {
            String pnrStr = pnr.toString();
            if (_pnrMap.put(pnrStr, pnr) != null) {
                _pnrDuplicatesList.add(pnr);
            }
        }
        _pnrList = new LinkedList<Personnummer>(_pnrMap.values());
        _pnrMap.clear(); // save memory
        _maxAge = resolveHighestAge();
        _minAge = resolveLowestAge();
        _avgAge = resolveAverageAge();
        resolveGenders();
    }

    private int resolveHighestAge() {
        int age = _pnrList.get(0).getAgeNow();
        int highestAge = 0;
        for (Personnummer pnr : _pnrList) {
            age = pnr.getAgeNow();
            highestAge = (age > highestAge) ? age : highestAge;
        }
        return highestAge;
    }

    private int resolveLowestAge() {
        int age = _pnrList.get(0).getAgeNow();
        int lowestAge = age;
        for (Personnummer pnr : _pnrList) {
            age = pnr.getAgeNow();
            lowestAge = (age < lowestAge) ? age : lowestAge;
        }
        return lowestAge;
    }

    private double resolveAverageAge() {
        double average = 0.0;
        double sum = 0.0;
        for (Personnummer pnr : _pnrList) {
            sum += pnr.getAgeNow();
        }
        average = sum / _pnrList.size();
        return average;
    }

    private void resolveGenders() {
        _amountMen = 0;
        _amountWomen = 0;
        for (Personnummer pnr : _pnrList) {
            if (pnr.getGender().equals(GenderType.MALE))
                _amountMen++;
            else if (pnr.getGender().equals(GenderType.FEMALE))
                _amountWomen++;
        }
    }

    public List<Personnummer> getPnrDuplicateList() {
        return _pnrDuplicatesList;
    }

    public List<Personnummer> getAllPnr() {
        return _pnrList;
    }

    public List<Personnummer> extractAllPnrWithAge(int age) {
        LinkedList<Personnummer> allWithAge = new LinkedList<Personnummer>();
        if (!_pnrList.isEmpty()) {
            for (Personnummer pnr : _pnrList) {
                if (pnr.getAgeNow() == age) {
                    allWithAge.add(pnr);
                }
            }
        }
        return allWithAge;
    }

    public LinkedList<Personnummer> extractAllPnrWithAgeUnder(int age) {
        LinkedList<Personnummer> allUnderAge = new LinkedList<Personnummer>();
        if (!_pnrList.isEmpty()) {
            for (Personnummer pnr : _pnrList) {
                if (pnr.getAgeNow() < age) {
                    allUnderAge.add(pnr);
                }
            }
        }
        return allUnderAge;
    }

    public LinkedList<Personnummer> extractAllPnrWithAgeOver(int age) {
        LinkedList<Personnummer> allOverAge = new LinkedList<Personnummer>();
        if (!_pnrList.isEmpty()) {
            for (Personnummer pnr : _pnrList) {
                if (pnr.getAgeNow() > age) {
                    allOverAge.add(pnr);
                }
            }
        }
        return allOverAge;
    }

    public LinkedList<Personnummer> getAllPnrAverageAge(int diff) {
        LinkedList<Personnummer> allAverage = new LinkedList<Personnummer>();
        Double average = resolveAverageAge();
        if (!_pnrList.isEmpty()) {
            for (Personnummer pnr : _pnrList) {
                double ageNow = pnr.getAgeNow();
                if (ageNow >= (average - diff) && ageNow <= (average + diff)) {
                    allAverage.add(pnr);
                }
            }
        }
        return allAverage;
    }

    public int getMaxAge() {
        return _maxAge;
    }

    public int getMinAge() {
        return _minAge;
    }

    public double getAvgAge() {
        return _avgAge;
    }

    public int getAmountMen() {
        return _amountMen;
    }

    public int getAmountWomen() {
        return _amountWomen;
    }
}