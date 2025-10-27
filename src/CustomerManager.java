import java.io.*;
import java.util.*;

public class CustomerManager {
    private final File f = new File("data/CustomerData");
    private List<Customer> customers;
    private final Scanner sc;

    public CustomerManager(Scanner sc) {
        this.sc = sc;
        customers = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    private int generateNewID() {
        return customers.stream().mapToInt(Customer::getCustomerID).max().orElse(0) + 1;
    }

    // ====== Thêm khách hàng ======
    public void addCustomer() {
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        System.out.print("Nhập SĐT: ");
        String phone = sc.nextLine();

        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(name) || c.getPhone().equals(phone)) {
                System.out.println("Khách hàng đã tồn tại: ");
                System.out.println(c);
                System.out.print("Bạn muốn (S)ửa / (X)óa / (B)ỏ qua: ");
                String opt = sc.nextLine().toUpperCase();
                switch (opt) {
                    case "S" -> updateCustomer();
                    case "X" -> deleteCustomer();
                    default -> System.out.println("Bỏ qua thao tác");
                }
                return;
            }
        }

        int id = generateNewID();
        Customer newCus = new Customer(id, name, phone, 0, "Normal");
        customers.add(newCus);
        System.out.println("Thêm khách hàng thành công!");
    }

    // ====== Sửa khách hàng ======
    public void updateCustomer() {
        System.out.print("Nhập SĐT khách cần sửa: ");
        String phone = sc.nextLine();
        for (Customer c : customers) {
            if (c.getPhone().equals(phone)) {
                System.out.print("Tên mới: ");
                c.setName(sc.nextLine());
                System.out.print("Số lần sử dụng mới: ");
                c.setUsageCount(Integer.parseInt(sc.nextLine()));
                c.updateMemberRank();
                System.out.println("Cập nhật thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy khách hàng!");
    }

    // ====== Xóa khách hàng ======
    public void deleteCustomer() {
        System.out.print("Nhập SĐT khách cần xóa: ");
        String phone = sc.nextLine();
        boolean removed = customers.removeIf(c -> c.getPhone().equals(phone));
        if (removed) System.out.println("Đã xóa thành công!");
        else System.out.println("Không tìm thấy khách hàng!");
    }

    // ====== Xem danh sách theo chữ cái ======
    public void viewSortedList() {
        customers.sort(Comparator.comparing(Customer::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.printf("%-5s | %-15s | %-12s | %-5s | %-7s\n", "ID", "Tên", "SĐT", "Lần", "Hạng");
        for (Customer c : customers) System.out.println(c);
    }

    // ====== Xem theo hạng ======
    public void viewByRank() {
        System.out.print("Nhập hạng (Normal/Silver/Gold): ");
        String rank = sc.nextLine();
        for (Customer c : customers) {
            if (c.getMemberRank().equalsIgnoreCase(rank)) System.out.println(c);
        }
    }

    public Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getName().equalsIgnoreCase(name)) return c;
        return null;
    }

    // ====== MENU ======
    public void showMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ KHÁCH HÀNG ---");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Sửa khách hàng");
            System.out.println("3. Xóa khách hàng theo SĐT");
            System.out.println("4. Xem danh sách theo tên");
            System.out.println("5. Xem danh sách theo hạng");
            System.out.println("0. Lưu & Thoát");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addCustomer();
                case "2" -> updateCustomer();
                case "3" -> deleteCustomer();
                case "4" -> viewSortedList();
                case "5" -> viewByRank();
                case "0" -> {
                    saveToFile();
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ====== Đọc file ======
    public void readFromFile() {
        customers.clear();

        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu khách hàng");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // bỏ dòng tiêu đề
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                int id = Integer.parseInt(info[0]);
                String name = info[1];
                String phone = info[2];
                int usage = Integer.parseInt(info[3]);
                String rank = info[4];
                customers.add(new Customer(id, name, phone, usage, rank));
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file CustomerData");
        }
    }

    // ====== Lưu file ======
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("ID,Name,Phone,Usage,Rank");
            bw.newLine();
            for (Customer c : customers) {
                String line = String.format("%d,%s,%s,%d,%s",
                        c.getCustomerID(), c.getName(), c.getPhone(), c.getUsageCount(), c.getMemberRank());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file CustomerData");
        }
    }
}