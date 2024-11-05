import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FindAverageSalaryApp {

    /**
     *
     * This method will take employees list as input and
     * would print the average salary of employees for every designation.
     *
     * @param employees
     */
    public void findAverageSalary(List<Employee> employees){

        Map<String, Map<String, Double>> averageSalaries = null;

        try {
            averageSalaries = calculateAverageSalaries(employees);
        } catch (ExecutionException e) {
            System.out.println("ExecutionException occurred while calling calculateAverageSalaries" + e );
        } catch (InterruptedException e) {
            System.out.println("InterruptedException occurred while calling calculateAverageSalaries" + e );
        }

        // iterate and print average salary for every designation.
        averageSalaries.forEach((location, designationMap) -> {
            designationMap.forEach((designation, avgSalary) -> {
                System.out.println("Office Location: " + location + "  Designation: " + designation + ", Average Salary: " + avgSalary);
            });
        });

    }

    /**
     *
     * method to first group the employees by office location and then calculate the average salary.
     *
     * @param employees
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private Map<String, Map<String, Double>> calculateAverageSalaries(List<Employee> employees)
            throws ExecutionException, InterruptedException {
        // initialize executor Service
        ExecutorService executorService = Executors.newCachedThreadPool();

        // group employees by location
        Map<String, List<Employee>> groupEmployeesByLocation = employees.stream().
                collect(Collectors.groupingBy(emp -> emp.getOfficeLocation()));

        // initialize map to store location and future map object of designation and average salary
        Map<String, Future<Map<String, Double>>> locationWiseFutureMap = new HashMap<>();

        // iterate for each office location and submit a task in new thread
        for (Map.Entry<String, List<Employee>> entry : groupEmployeesByLocation.entrySet()) {
            String officeLocation = entry.getKey();
            List<Employee> officeEmployees = entry.getValue();

            // Submit tasks to calculate averages for each office location
            Future<Map<String, Double>> futureMap = executorService.submit(() -> {
                return officeEmployees.stream()
                        .collect(Collectors.groupingBy(emp -> emp.getDesignation(),
                                Collectors.averagingDouble(emp -> emp.getSalary())));
            });
            locationWiseFutureMap.put(officeLocation, futureMap);
        }

        // iterate locationWiseFutureMap, fetch the details from futureMap and put them into resultMap
        Map<String, Map<String, Double>> resultMap = new HashMap<>();
        for (Map.Entry<String, Future<Map<String, Double>>> entry : locationWiseFutureMap.entrySet()) {
            String officeLocation = entry.getKey();
            // Call get() method on Future object to fetch the values
            Map<String, Double> designationMap = designationMap = entry.getValue().get();
            resultMap.put(officeLocation, designationMap);
        }
        // gracefully shutdown the executor service.
        executorService.shutdown();
        return resultMap;
    }
}
