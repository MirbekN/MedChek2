import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartmentByHospital(Long id, Department department2);
    Department findDepartmentByName(String name);

}
