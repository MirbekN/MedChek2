import exception.MyException;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DepartmentServiceImpl implements DepartmentService,GenericService{
    public DepartmentServiceImpl(Database database) {
        this.database = database;
    }

    private Database database;

    @Override
    public List<Department> getAllDepartmentByHospital(Long id, Department department2) {
        boolean isTrue = true;
        for (Hospital h : Database.database.getHospitals()) {
            if (Objects.equals(h.getId(), id)) {
                isTrue = true;
                return h.getDepartments();
            } else {
                isTrue = false;
            }
        }
        try {
            if (!isTrue) {
                throw new MyException("Hospital with id " + id + " does not exist.");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public Department findDepartmentByName(String name) {
        for (Hospital ha : Database.database.getHospitals()) {
            for (Department department : ha.getDepartments()) {
                if (department.getDepartmentName().equals(name)) {
                    return department;
                }
            }
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
        boolean found = false;
        for (int i = hospitals.size() - 1; i >= 0; i--) {
            Hospital hospital = hospitals.get(i);
            List<Department> departments = hospital.getDepartments();
            Iterator<Department> iterator = departments.iterator();
            while (iterator.hasNext()) {
                Department department = iterator.next();
                if (department.getId().equals(id)) {
                    iterator.remove();
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (!found) {
            System.out.println("Department with ID " + id + " not found.");
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
