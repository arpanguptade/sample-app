import java.util.ArrayList;
import java.util.List;

public class SampleApp {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
		//Sample input for employees list as:
			employees.add(new Employee("Ashish", "A", "IT", "Pune", "Software Engineer", new Double(10000)));
			employees.add(new Employee("Amit", "R", "HR", "Pune", "Recruiter", new Double(12000)));
			employees.add(new Employee("Ramesh", "D", "HR", "Pune", "Senior Recruiter", new Double(14000)));
			employees.add(new Employee("Jaya", "S", "IT", "Pune", "Tech Lead", new Double(15000)));
			employees.add(new Employee("Smita", "M", "IT", "Bangalore", "Recruiter", new Double(16000)));
			employees.add(new Employee("Umesh", "A", "IT", "Bangalore", "Software Engineer", new Double(12000)));
			employees.add(new Employee("Pooja", "R", "HR", "Bangalore", "Software Engineer", new Double(12000)));
			employees.add(new Employee("Ramesh", "D", "HR", "Pune", "Recruiter", new Double(16000)));
			employees.add(new Employee("Bobby", "S", "IT", "Bangalore", "Tech Lead", new Double(20000)));
			employees.add(new Employee("Vipul", "M", "IT", "Bangalore", "Software Engineer", new Double(14000)));

			FindAverageSalaryApp app = new FindAverageSalaryApp();
			app.findAverageSalary(employees);

    }
}
