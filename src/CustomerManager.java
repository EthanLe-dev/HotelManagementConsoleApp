import java.util.*;

public class CustomerManager {
    private List<Customer> customerList = new ArrayList<>();

    public CustomerManager() {
        // Dữ liệu mẫu
        customerList.add(new Customer("C01", "Thinh"));
        customerList.add(new Customer("C02", "Quan"));
    }

    public Customer searchByName(String name) {
        for (Customer c : customerList) {
            if (c.getName().equalsIgnoreCase(name)) return c;
        }
        return null;
    }

    public Customer addNewCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new customer name: ");
        String name = sc.nextLine();
        String id = "C" + (customerList.size() + 1);
        Customer c = new Customer(id, name);
        customerList.add(c);
        System.out.println("✅ Added new customer: " + c);
        return c;
    }

    public void showAllCustomers() {
        for (Customer c : customerList) {
            System.out.println(c);
        }
    }
}
