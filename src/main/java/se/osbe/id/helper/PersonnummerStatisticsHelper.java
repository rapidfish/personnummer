package se.osbe.id.helper;

import se.osbe.id.Personnummer;
import se.osbe.id.enums.GenderType;
import se.osbe.id.exception.PersonnummerException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonnummerStatisticsHelper {
    private Map<String, Personnummer> _pnrMap;
    private List<Personnummer> _pnrList;
    private List<Personnummer> _pnrDuplicateStorageList;
    private int _amountMen;
    private int _amountWomen;
    private int _maxAge;
    private int _minAge;
    private double _avgAge;

    @SuppressWarnings("unused")
    private PersonnummerStatisticsHelper() {
    }

    public PersonnummerStatisticsHelper(List<Personnummer> pnrList) throws PersonnummerException {
        if (pnrList == null || pnrList.isEmpty()) {
            throw new PersonnummerException("pnrList is null or empty");
        }
        _pnrDuplicateStorageList = new LinkedList<Personnummer>();
        _pnrMap = new HashMap<String, Personnummer>();
        for (Personnummer pnr : pnrList) {
            String pnrStr = pnr.toString();
            if (_pnrMap.put(pnrStr, pnr) != null) {
                _pnrDuplicateStorageList.add(pnr);
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
        return _pnrList.stream().map(pnr -> pnr.getAgeNow()).max(Integer::compare).get();
    }

    private int resolveLowestAge() {
        return _pnrList.stream().map(pnr -> pnr.getAgeNow()).min(Integer::compare).get();
    }

    private double resolveAverageAge() {
        return _pnrList.stream().mapToDouble(pnr -> pnr.getAgeNow()).average().getAsDouble();
    }

    private void resolveGenders() {
        _amountMen = (int) _pnrList.stream().filter(pnr -> pnr.getGender().equals(GenderType.MALE)).count();
        _amountWomen = (int) _pnrList.stream().filter(pnr -> pnr.getGender().equals(GenderType.FEMALE)).count();
    }

    public List<Personnummer> getPnrDuplicateList() {
        return _pnrDuplicateStorageList;
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

    public List<Personnummer> extractAllSamordningsnummer() {
        return _pnrList.stream().filter(p -> p.isSamordningsnummer()).collect(Collectors.toList());
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

    public List<Personnummer> extractAllPnrWithAgeOver(int age) {
        return _pnrList.stream().filter(p -> p.getAgeNow() > age).collect(Collectors.toList());
    }

    public List<Personnummer> getAllPnrAverageAge(int diff) {
        double average = resolveAverageAge();
        return _pnrList.stream()
                .filter(p -> ((double)p.getAgeNow()) >=  (average - diff))
                .filter(p -> ((double)p.getAgeNow()) <=  (average + diff))
                .collect(Collectors.toList());
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