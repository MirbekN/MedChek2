import exception.MyException;

import java.util.List;
import java.util.Objects;

public class DoctorServiceImpl implements DoctorService,GenericService{

    private Database database;

    public DoctorServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public Doctor findDoctorById(Long id) {
        try {
            for (Hospital h : database.getHospitals()) {
                for (Doctor d : h.getDoctors()) {
                    if (d.getId().equals(id)) {
                        return d;
                    } else {
                        throw new MyException("ID not found! ");
                    }
                }
            }
        } catch (MyException m) {
            System.out.println(m.getMessage());

        }
        return null;
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        try {
            for (Hospital h : Database.database.getHospitals()) {
                for (Department d : h.getDepartments()) {
                    for (Doctor o : h.getDoctors()) {
                        if (d.getId().equals(departmentId)) {
                            doctorsId.add(o.getId());
                            return "Doctor id added successfully" + doctorsId;
                        } else {
                            throw new MyException("Doctor not found!");
                        }
                    }
                }
            }
        } catch (MyException m) {
            System.out.println(m.getMessage());
        }
        return "Invalid partition ID";
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        try {
            boolean isTrue = true;

            for (Hospital h : database.getHospitals()) {
                if (Objects.equals(h.getId(), id)) {
                    isTrue = true;
                    return h.getDoctors();
                } else {
                    isTrue = false;
                }
            }

            if (!isTrue) {
                throw new MyException("Hospital with id " + id + " does not exist.");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        try {
            for (Hospital h : database.getHospitals()) {
                for (Department d : h.getDepartments()) {
                    if (Objects.equals(d.getId(), id)) {
                        return d.getDoctors();
                    } else {
                        throw new MyException("ID not found!");
                    }
                }
            }
        } catch (MyException m) {
            System.out.println(m.getMessage());
        }
        return null;
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

