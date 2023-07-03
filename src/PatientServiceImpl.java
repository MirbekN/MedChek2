import exception.MyException;

import java.util.*;

public class PatientServiceImpl implements PatientService,GenericService{
private Database database;

    public PatientServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        boolean isTrue = true;
        for (Hospital d: Database.database.getHospitals()) {
            if (d.getId()==id){
                isTrue = true;
                d.getPatients().add((Patient) patients);
                return "Patient successfully added patient: "+patients;
            }else {
                isTrue = false;
            }
        }
        try{
            if (!isTrue){
                throw new MyException("Not found hospital");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public Patient getPatientById(Long id) {
        List<Hospital> hospitals = database.getHospitals();
        for (Hospital hospital : hospitals) {
            List<Patient> patients = hospital.getPatients();
            for (Patient patient : patients) {
                if (patient.getId().equals(id)) {
                    return patient;
                }
            }
        }
        return null; // Пациент не найден
    }

    @Override
    public Map<Integer, Patient> getPatientByAge() {
        Map<Integer, Patient> patientsByAge = new HashMap<>();
        List<Hospital> hospitals = database.getHospitals();
        for (Hospital hospital : hospitals) {
            List<Patient> patients = hospital.getPatients();
            for (Patient patient : patients) {
                patientsByAge.put(patient.getAge(), patient);
            }
        }
        return patientsByAge;
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        List<Patient> allPatients = new ArrayList<>();
        List<Hospital> hospitals = database.getHospitals();
        for (Hospital hospital : hospitals) {
            List<Patient> patients = hospital.getPatients();
            allPatients.addAll(patients);
        }

        if (ascOrDesc.equalsIgnoreCase("asc")) {
            allPatients.sort(Comparator.comparingInt(Patient::getAge));
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            allPatients.sort(Comparator.comparingInt(Patient::getAge).reversed());
        }

        return allPatients;
    }
    @Override
    public String add(Long hospitalId, Object o) {
        List<Hospital> hospitals = database.getHospitals();
        for (Hospital hospital : hospitals) {
            if (hospital.getId().equals(hospitalId)) {
                if (o instanceof Department) {
                    Department department = (Department) o;
                    hospital.getDepartments().add(department);
                    return "Department added successfully to the hospital.";
                } else if (o instanceof Doctor) {
                    Doctor doctor = (Doctor) o;
                    hospital.getDoctors().add(doctor);
                    return "Doctor added successfully to the hospital.";
                } else if (o instanceof Patient) {
                    Patient patient = (Patient) o;
                    hospital.getPatients().add(patient);
                    return "Patient added successfully to the hospital.";
                }
            }
        }
        return "Hospital not found.";
    }

    @Override
    public void removeById(Long id) {
        List<Hospital> hospitals = database.getHospitals();
        for (Hospital hospital : hospitals) {
            if (hospital.getId().equals(id)) {
                hospitals.remove(hospital);
                break;
            }
        }
    }

    @Override
    public String updateById(Long id, Object o) {
        List<Hospital> hospitals = database.getHospitals();
        for (Hospital hospital : hospitals) {
            if (hospital.getId().equals(id)) {
                if (o instanceof Department) {
                    Department updatedDepartment = (Department) o;
                    List<Department> departments = hospital.getDepartments();
                    for (int i = 0; i < departments.size(); i++) {
                        if (departments.get(i).getId().equals(updatedDepartment.getId())) {
                            departments.set(i, updatedDepartment);
                            return "Department updated successfully.";
                        }
                    }
                } else if (o instanceof Doctor) {
                    Doctor updatedDoctor = (Doctor) o;
                    List<Doctor> doctors = hospital.getDoctors();
                    for (int i = 0; i < doctors.size(); i++) {
                        if (doctors.get(i).getId().equals(updatedDoctor.getId())) {
                            doctors.set(i, updatedDoctor);
                            return "Doctor updated successfully.";
                        }
                    }
                } else if (o instanceof Patient) {
                    Patient updatedPatient = (Patient) o;
                    List<Patient> patients = hospital.getPatients();
                    for (int i = 0; i < patients.size(); i++) {
                        if (patients.get(i).getId().equals(updatedPatient.getId())) {
                            patients.set(i, updatedPatient);
                            return "Patient updated successfully.";
                        }
                    }
                }
            }
        }
        return "Hospital not found.";
    }

}
