import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Interface FileHandler
interface FileHandler {
    void readFromFile();
    void saveToFile();
}

// Lớp Service - chỉ chứa các thuộc tính, constructor, getter, setter
class Service {
    private String id;
    private String name;
    private double price;
    
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
    
    @Override
    public String toString() {
        return String.format("%-5s %-15s %,.0f VND", id, name, price);
    }
}

//--------------------------------------------------------------------------------------------------------------------------------------
// Lớp ServiceManagement - quản lý dịch vụ và xử lý file
public class ServiceManagement implements FileHandler {
    private ArrayList<Service> services; // Danh sách dịch vụ
    private final File f = new File("data/Service");
    private Scanner sc;
    
    public ServiceManagement(Scanner sc) {
        this.sc = sc;
        services = new ArrayList<>();
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
        return name.matches("[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ ]*");
    }
    
    // Kiểm tra giá: phải là số dương
    private boolean isValidPrice(double price) {
        return price > 0;
    }
    
    // Hiển thị menu
    public void showMenu() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ DỊCH VỤ =====");
            System.out.println("<<LƯU Ý: Nhấn 0 để lưu file dữ liệu trước khi tắt chương trình>>");
            System.out.println("1. Xem tất cả dịch vụ");
            System.out.println("2. Thêm dịch vụ");
            System.out.println("3. Sửa dịch vụ");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("5. Tìm dịch vụ theo tên");
            System.out.println("0. Lưu file dữ liệu & Về menu chính");
            
            int choice;
            try {
                System.out.print("Chọn chức năng: ");
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\nVui lòng nhập số từ 0-5\n");
                continue;
            }
            
            switch (choice) {
                case 1:
                    viewAllServices();
                    break;
                case 2:
                    addService();
                    break;
                case 3:
                    updateService();
                    break;
                case 4:
                    deleteService();
                    break;
                case 5:
                    searchByName();
                    break;
                case 0:
                    return; // Thoát về menu chính
                default:
                    System.out.println("\nVui lòng nhập các số từ 0-5\n");
            }
        }
    }
    
    // 1. Xem tất cả dịch vụ
    public void viewAllServices() {
        if (services.isEmpty()) {
            System.out.println("\nKhông có dịch vụ nào!\n");
            return;
        }
        
        System.out.println("\n===== DANH SÁCH DỊCH VỤ =====");
        System.out.println("ID    Tên             Giá");
        System.out.println("----------------------------------");
        for (Service s : services) {
            System.out.println(s);
        }
        System.out.println("Tổng số dịch vụ: " + services.size());
        System.out.println("----------------------------------\n");
    }
    
    // 2. Thêm dịch vụ
    public void addService() {
        System.out.println("\n----Bạn đang thêm dịch vụ----");
        
        System.out.print("Nhập ID (chỉ số, VD: 001): ");
        String id = sc.nextLine().trim();
        
        // Validate ID
        if (!isValidId(id)) {
            System.out.println("ID không hợp lệ! ID chỉ được chứa số (VD: 001, 123)\n");
            return;
        }
        
        // Kiểm tra ID đã tồn tại chưa
        for (Service s : services) {
            if (s.getId().equals(id)) {
                System.out.println("ID đã tồn tại! Vui lòng chọn ID khác.\n");
                return;
            }
        }
        
        System.out.print("Nhập tên (chữ đầu viết hoa, VD: Mì xào bò): ");
        String name = sc.nextLine().trim();
        
        // Validate Name
        if (!isValidName(name)) {
            System.out.println("Tên không hợp lệ! Tên phải bắt đầu bằng chữ in hoa và chỉ chứa chữ cái (VD: Mì xào bò)\n");
            return;
        }
        
        System.out.print("Nhập giá (số dương, VD: 40000): ");
        double price;
        try {
            price = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Giá phải là số!\n");
            return;
        }
        
        // Validate Price
        if (!isValidPrice(price)) {
            System.out.println("Giá không hợp lệ! Giá phải là số dương (VD: 40000)\n");
            return;
        }
        
        // Thêm dịch vụ mới
        Service newService = new Service(id, name, price);
        services.add(newService);
        System.out.println("Thêm dịch vụ thành công!");
        System.out.println(newService);
        System.out.println();
    }
    
    // 3. Sửa dịch vụ
    public void updateService() {
        viewAllServices();
        
        System.out.print("Nhập ID cần sửa: ");
        String id = sc.nextLine().trim();
        
        boolean found = false;
        for (Service s : services) {
            if (s.getId().equals(id)) {
                System.out.print("Nhập tên mới (chữ đầu viết hoa, VD: Cơm chiên): ");
                String newName = sc.nextLine().trim();
                
                // Validate Name
                if (!isValidName(newName)) {
                    System.out.println("Tên không hợp lệ! Tên phải bắt đầu bằng chữ in hoa và chỉ chứa chữ cái\n");
                    return;
                }
                
                System.out.print("Nhập giá mới (số dương, VD: 45000): ");
                double newPrice;
                try {
                    newPrice = Double.parseDouble(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Giá phải là số!\n");
                    return;
                }
                
                // Validate Price
                if (!isValidPrice(newPrice)) {
                    System.out.println("Giá không hợp lệ! Giá phải là số dương\n");
                    return;
                }
                
                s.setName(newName);
                s.setPrice(newPrice);
                found = true;
                System.out.println("Cập nhật dịch vụ thành công!");
                System.out.println(s);
                System.out.println();
                break;
            }
        }
        
        if (!found) {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id + "\n");
        }
    }
    
    // 4. Xóa dịch vụ
    public void deleteService() {
        viewAllServices();
        
        System.out.print("Nhập ID cần xóa: ");
        String id = sc.nextLine().trim();
        
        boolean found = false;
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId().equals(id)) {
                services.remove(i);
                found = true;
                break;
            }
        }
        
        if (found) {
            System.out.println("Xóa dịch vụ thành công!\n");
        } else {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id + "\n");
        }
    }
    
    // 5. Tìm dịch vụ theo tên
    public void searchByName() {
        System.out.print("Nhập tên cần tìm: ");
        String keyword = sc.nextLine().trim();
        
        ArrayList<Service> results = new ArrayList<>();
        for (Service s : services) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(s);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy dịch vụ nào với từ khóa: " + keyword + "\n");
        } else {
            System.out.println("\n===== KẾT QUẢ TÌM KIẾM =====");
            System.out.println("ID    Tên             Giá");
            System.out.println("----------------------------------");
            for (Service s : results) {
                System.out.println(s);
            }
            System.out.println("----------------------------------\n");
        }
    }
    
    // Đọc dữ liệu từ file
    @Override
    public void readFromFile() {
        services.clear(); // Xóa dữ liệu cũ
        
        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu dịch vụ");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
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
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file Service");
        }
    }
    
    // Lưu dữ liệu vào file
    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(f))) {
            for (Service s : services) {
                writer.println(s.getId() + "," + s.getName() + "," + s.getPrice());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file Service");
        }
    }
}
