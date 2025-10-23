import java.util.*;

public class EmployeeManager {
    private List<Service> serviceList = new ArrayList<>();

    public EmployeeManager() {
        serviceList.add(new Service("Breakfast", 50));
        serviceList.add(new Service("Laundry", 30));
        serviceList.add(new Service("Spa", 100));
    }

    public Service searchServiceByName(String name) {
        for (Service s : serviceList) {
            if (s.getServiceName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    public void showAllServices() {
        for (Service s : serviceList) {
            System.out.println(s);
        }
    }
}
