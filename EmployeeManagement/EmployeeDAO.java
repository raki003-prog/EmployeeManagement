import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public void addEmployee(Employee emp) {
    String check = "SELECT name FROM employees WHERE id = ?";
    String query = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement checkStmt = conn.prepareStatement(check);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        checkStmt.setInt(1, emp.getId());
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            String existingName = rs.getString("name");
            System.out.println("Employee ID already exists for user: " + existingName);
            return;
        }

        stmt.setInt(1, emp.getId());
        stmt.setString(2, emp.getName());
        stmt.setString(3, emp.getDepartment());
        stmt.setDouble(4, emp.getSalary());
        stmt.executeUpdate();
        System.out.println("✅ Employee added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding employee: " + e.getMessage());
    }
}


    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employees: " + e.getMessage());
        }
        return list;
    }

    public void searchEmployeeById(int id) {
    String query = "SELECT * FROM employees WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Employee emp = new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("department"),
                rs.getDouble("salary")
            );
            System.out.println("Employee Found: " + emp);
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    } catch (SQLException e) {
        System.out.println("Error searching employee: " + e.getMessage());
    }
}


    public void updateSalary(int id, double newSalary) {
        String query = "UPDATE employees SET salary = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newSalary);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Salary updated!" : "Employee not found!");
        } catch (SQLException e) {
            System.out.println("Error updating salary: " + e.getMessage());
        }
    }

    public void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee deleted!" : "Employee not found!");
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}

