import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CustomerManager customerManager = new CustomerManager();
        RoomManager roomManager = new RoomManager();
        EmployeeManager employeeManager = new EmployeeManager();
        BillManager billManager = new BillManager(customerManager, roomManager, employeeManager);

        int choice;
        do {
            System.out.println("\n===== HOTEL MANAGEMENT MENU =====");
            System.out.println("1. Add new bill");
            System.out.println("2. Search bill by customer or room");
            System.out.println("3. Save statistics to file");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> billManager.addBill();
                case 2 -> billManager.findBillByNameOrRoom();
                case 3 -> billManager.saveStatisticToFile();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }
}
