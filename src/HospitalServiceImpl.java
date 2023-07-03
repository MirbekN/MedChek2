import exception.MyException;

import java.util.*;

public class HospitalServiceImpl implements HospitalService {
    public HospitalServiceImpl(Database database) {
        this.database = new Database();
    }

    private Database database;
    @Override
    public String addHospital(Hospital hospital) {
        Database.database.getHospitals().add(hospital);
        return "has joined!!!";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        boolean isTrue = true;
        for (Hospital d : database.getHospitals()) {
            if (Objects.equals(d.getId(), id)) {
                isTrue = true;
                return d;
            } else {
                isTrue = false;
            }
        }
        try {
            if (!isTrue) {
                throw new MyException("Hospital with id: " + id + " not found!");
            }
        }catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospital() {
        return database.getHospitals();
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        boolean isTrue = true;
        for (Hospital d : database.getHospitals()) {
            for (Patient p : d.getPatients()) {
                if (Objects.equals(d.getId(), id)) {
                    isTrue = true;
                    return d.getPatients();
                } else {
                    isTrue = false;
                }
            }
            try {
                if (!isTrue) {
                    throw new MyException("Hospital with id: " + id + " not found!");
                }
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String deleteHospitalById(Long id) {
        boolean isTrue = true;
        synchronized (Database.database.getHospitals()) {
            Iterator<Hospital> iterator = database.getHospitals().iterator();
            while (iterator.hasNext()) {
                Hospital h = iterator.next();
                if (h.getId() == id) {
                    isTrue = true;
                    iterator.remove();
                    return "successfully fired!!!";

                } else {
                    isTrue = false;
                }
            }
        }
        try {
            if (!isTrue) {
                throw new MyException("Hospital with id: " + id + " not found!");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        Map<String, Hospital> map = new HashMap<>();
        boolean isFound = false;
        for (Hospital hospital : database.getHospitals()) {
            if (hospital.getAddress().equalsIgnoreCase(address)) {
                map.put(hospital.getAddress(), hospital);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Hospital with address: " + address + " not found!");
        }
        return map;
    }


}