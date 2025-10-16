import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Interface FileHandler
interface FileHandler {
    void readFromFile();
    void saveToFile();
}

// Lớp Service - chứa các thuộc tính,settter và getter,constructor và các phương thức về xử lý file,string
class Service implements FileHandler {
    private String id;
    private String name;
    private double price;
    
    // Biến static chung cho tất cả instance
    private static final String FILE_NAME = "data/Service";
    private static ArrayList<Service> services = new ArrayList<>();
    
    // Constructor
    public Service(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    // Lấy danh sách services (static)
    public static ArrayList<Service> getServices() {
        return services;
    }
    
    @Override
    public String toString() {
        return String.format("%-5s %-15s %,.0f VND", id, name, price);
    }
    
    // Implement FileHandler - Đọc từ file
    @Override
    public void readFromFile() {
        services.clear(); // Xóa dữ liệu cũ
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    services.add(new Service(id, name, price));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File không tồn tại!");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
    
    // Implement FileHandler - Lưu vào file
    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Service s : services) {
                writer.println(s.getId() + "," + s.getName() + "," + s.getPrice());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}
//--------------------------------------------------------------------------------------------------------------------------------------
// Lớp ServiceManagement - gồm các phương thức về xử lý chức năng
public class ServiceManagement {
    private Service serviceHandler; // Để sử dụng FileHandler
    private ArrayList<Service> services; // Biến lưu danh sách dịch vụ
    
    public ServiceManagement() {
        serviceHandler = new Service("", "", 0); // Khởi tạo để sử dụng FileHandler
        serviceHandler.readFromFile(); // Đọc dữ liệu từ file ngay khi khởi tạo
        services = Service.getServices(); // Gán 1 lần duy nhất
    }
    
    // Kiểm tra ID chỉ chứa số
    private boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return id.matches("\\d+"); // Chỉ chứa chữ số
    }
    
    // Kiểm tra tên: ký tự đầu phải in hoa, chỉ chứa chữ cái và khoảng trắng
    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra ký tự đầu phải in hoa
        if (!Character.isUpperCase(name.trim().charAt(0))) {
            return false;
        }
        // Kiểm tra chỉ chứa chữ cái, khoảng trắng và các ký tự tiếng Việt
        return name.matches("[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẤỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ ]*");
    }
    
    // Kiểm tra giá: phải là số dương
    private boolean isValidPrice(double price) {
        return price > 0;
    }
    
    // 1. Xem tất cả dịch vụ
    public void viewAllServices() {
        if (services.isEmpty()) {
            System.out.println("Không có dịch vụ nào!");
            return;
        }
        
        System.out.println("\n===== DANH SÁCH DỊCH VỤ =====");
        System.out.println("ID    Tên             Giá");
        System.out.println("----------------------------------");
        for (Service s : services) {
            System.out.println(s);
        }
        System.out.println("----------------------------------");
    }
    
    // 2. Thêm dịch vụ
    public void addService(String id, String name, double price) {
        // Validate ID
        if (!isValidId(id)) {
            System.out.println("ID không hợp lệ! ID chỉ được chứa số (VD: 001, 123)");
            return;
        }
        
        // Validate Name
        if (!isValidName(name)) {
            System.out.println("Tên không hợp lệ! Tên phải bắt đầu bằng chữ in hoa và chỉ chứa chữ cái (VD: Mì xào bò)");
            return;
        }
        
        // Validate Price
        if (!isValidPrice(price)) {
            System.out.println("Giá không hợp lệ! Giá phải là số dương (VD: 40000)");
            return;
        }
        
        // Kiểm tra ID đã tồn tại chưa
        for (Service s : services) {
            if (s.getId().equals(id)) {
                System.out.println("ID đã tồn tại! Vui lòng chọn ID khác.");
                return;
            }
        }
        
        // Thêm dịch vụ mới
        Service newService = new Service(id, name, price);
        services.add(newService);
        
        // Lưu vào file
        serviceHandler.saveToFile();
        System.out.println("Thêm dịch vụ thành công!");
    }
    
    // 3. Sửa dịch vụ
    public void updateService(String id, String newName, double newPrice) {
        // Validate Name
        if (!isValidName(newName)) {
            System.out.println("Tên không hợp lệ! Tên phải bắt đầu bằng chữ in hoa và chỉ chứa chữ cái (VD: Mì xào bò)");
            return;
        }
        
        // Validate Price
        if (!isValidPrice(newPrice)) {
            System.out.println("Giá không hợp lệ! Giá phải là số dương (VD: 40000)");
            return;
        }
        
        boolean found = false;
        
        for (Service s : services) {
            if (s.getId().equals(id)) {
                s.setName(newName);
                s.setPrice(newPrice);
                found = true;
                break;
            }
        }
        
        if (found) {
            serviceHandler.saveToFile();
            System.out.println("Cập nhật dịch vụ thành công!");
        } else {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id);
        }
    }
    
    // 4. Xóa dịch vụ
    public void deleteService(String id) {
        boolean found = false;
        
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId().equals(id)) {
                services.remove(i);
                found = true;
                break;
            }
        }
        
        if (found) {
            serviceHandler.saveToFile();
            System.out.println("Xóa dịch vụ thành công!");
        } else {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id);
        }
    }
    
    // 5. Tìm dịch vụ theo tên
    public void searchByName(String keyword) {
        ArrayList<Service> results = new ArrayList<>();
        
        for (Service s : services) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(s);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy dịch vụ nào với từ khóa: " + keyword);
        } else {
            System.out.println("\n===== KẾT QUẢ TÌM KIẾM =====");
            System.out.println("ID    Tên             Giá");
            System.out.println("----------------------------------");
            for (Service s : results) {
                System.out.println(s);
            }
            System.out.println("----------------------------------");
        }
    }
    
    // Phương thức hiển thị menu và xử lý
    public void show_service() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n===== QUẢN LÝ DỊCH VỤ =====");
            System.out.println("1. Xem tất cả dịch vụ");
            System.out.println("2. Thêm dịch vụ");
            System.out.println("3. Sửa dịch vụ");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("5. Tìm dịch vụ theo tên");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // Đọc bỏ newline
            
            switch (choice) {
                case 1:
                    viewAllServices();
                    break;
                    
                case 2:
                    System.out.print("Nhập ID (chỉ số, VD: 001): ");
                    String id = sc.nextLine();
                    System.out.print("Nhập tên (chữ đầu viết hoa, VD: Mì xào bò): ");
                    String name = sc.nextLine();
                    System.out.print("Nhập giá (số dương, VD: 40000): ");
                    try {
                        double price = sc.nextDouble();
                        addService(id, name, price);
                    } catch (Exception e) {
                        System.out.println("Giá phải là số!");
                        sc.nextLine(); // Clear buffer
                    }
                    break;
                    
                case 3:
                    System.out.print("Nhập ID cần sửa: ");
                    String updateId = sc.nextLine();
                    System.out.print("Nhập tên mới (chữ đầu viết hoa, VD: Cơm chiên): ");
                    String newName = sc.nextLine();
                    System.out.print("Nhập giá mới (số dương, VD: 45000): ");
                    try {
                        double newPrice = sc.nextDouble();
                        updateService(updateId, newName, newPrice);
                    } catch (Exception e) {
                        System.out.println("Giá phải là số!");
                        sc.nextLine(); // Clear buffer
                    }
                    break;
                    
                case 4:
                    System.out.print("Nhập ID cần xóa: ");
                    String deleteId = sc.nextLine();
                    deleteService(deleteId);
                    break;
                    
                case 5:
                    System.out.print("Nhập tên cần tìm: ");
                    String keyword = sc.nextLine();
                    searchByName(keyword);
                    break;
                    
                case 0:
                    System.out.println("Tạm biệt!");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    public static void main(String[] args) {
        ServiceManagement sm = new ServiceManagement();
        sm.show_service();
    }
}