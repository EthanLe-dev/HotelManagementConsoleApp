import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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

// Lớp ServiceManagement - quản lý dịch vụ và xử lý file
public class ServiceManagement implements FileHandler {
    private final Scanner sc;
    private final ClearScreen cleaner;
    private static ArrayList<Service> services;
    private final File f = new File("data/Service");
    
    public ServiceManagement(Scanner sc, ClearScreen cleaner) {
        this.sc = sc;
        this.cleaner = cleaner;
        services = new ArrayList<>();
    }
    
    // Kiểm tra ID chỉ chứa số
    private boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return id.matches("\\d+");
    }
    
    // Kiểm tra tên: ký tự đầu phải in hoa, chỉ chứa chữ cái và khoảng trắng
    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (!Character.isUpperCase(name.trim().charAt(0))) {
            return false;
        }
        return name.matches("[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ ]*");
    }
    
    // Kiểm tra giá: phải là số dương
    private boolean isValidPrice(double price) {
        return price > 0;
    }
    
    // Hiển thị menu
    public void showMenu() {
        cleaner.clearScreen();
        int choice;
        while (true) {
            System.out.println("----Chức năng quản lý <Dịch vụ>----");
            System.out.println("<<LƯU Ý: Nhấn 0 để lưu file dữ liệu trước khi tắt chương trình>>");
            System.out.println("1. Xem tất cả dịch vụ");
            System.out.println("2. Thêm dịch vụ");
            System.out.println("3. Sửa dịch vụ");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("5. Tìm dịch vụ theo tên");
            System.out.println("0. Lưu file dữ liệu & Về menu chính");
            try {
                System.out.print("Nhập lựa chọn: ");
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
                    saveToFile();
                    return;
                default:
                    System.out.println("\nVui lòng nhập các số từ 0-5\n");
            }
        }
    }
    
    // 1. Xem tất cả dịch vụ
    public void viewAllServices() {
        cleaner.clearScreen();
        System.out.println("----Bạn đang xem tất cả dịch vụ----");
        if (!services.isEmpty()) {
            System.out.println("ID    Tên             Giá");
            System.out.println("----------------------------------");
            for (Service s : services) {
                System.out.println(s);
            }
            System.out.println("Tổng số dịch vụ hiện tại: " + services.size() + "\n");
        } else {
            System.out.println("Không tìm thấy dịch vụ nào!\n");
        }
    }
    
    // 2. Thêm dịch vụ
    public void addService() {
        System.out.print("Nhập ID (chỉ số, VD: 001): ");
        String id = sc.nextLine().trim();
        
        if (!isValidId(id)) {
            System.out.println("ID sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        for (Service s : services) {
            if (s.getId().equals(id)) {
                System.out.println("ID đã tồn tại! Vui lòng chọn ID khác.\n");
                return;
            }
        }
        
        System.out.print("Nhập tên (chữ đầu viết hoa, VD: Mì xào bò): ");
        String name = sc.nextLine().trim();
        
        if (!isValidName(name)) {
            System.out.println("Tên sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        System.out.print("Nhập giá (số dương, VD: 40000): ");
        double price;
        try {
            price = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Giá sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        if (!isValidPrice(price)) {
            System.out.println("Giá sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        Service newService = new Service(id, name, price);
        services.add(newService);
        System.out.println("Thêm dịch vụ " + name + " thành công\n");
    }
    
    // 3. Sửa dịch vụ
    public void updateService() {
        System.out.print("Nhập ID cần sửa: ");
        String id = sc.nextLine().trim();
        
        Service service = searchServiceById(id);
        if (service == null) {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id + "\n");
            return;
        }
        
        typeNewInfo(service);
    }
    
    // 4. Xóa dịch vụ
    public void deleteService() {
        System.out.print("Nhập ID cần xóa: ");
        String id = sc.nextLine().trim();
        
        Service service = searchServiceById(id);
        if (service == null) {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id + "\n");
            return;
        }
        
        services.remove(service);
        System.out.println("Xóa dịch vụ " + service.getName() + " thành công\n");
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
    
    // Nhập thông tin muốn sửa
    public void typeNewInfo(Service service) {
        if (service == null) {
            System.out.println("Chọn và nhập lại ID");
            return;
        }
        
        System.out.print("Nhập tên mới: ");
        String newName = sc.nextLine().trim();
        
        System.out.print("Nhập giá mới: ");
        String priceStr = sc.nextLine().trim();
        
        if (isValidName(newName)) {
            try {
                double newPrice = Double.parseDouble(priceStr);
                if (isValidPrice(newPrice)) {
                    service.setName(newName);
                    service.setPrice(newPrice);
                    System.out.println("Sửa thông tin thành công");
                    System.out.println(service);
                    System.out.println();
                } else {
                    System.out.println("Giá sai định dạng-Chọn và nhập lại");
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá sai định dạng-Chọn và nhập lại");
            }
        } else {
            System.out.println("Tên sai định dạng-Chọn và nhập lại");
        }
    }
    
    // Tìm dịch vụ theo ID
    public Service searchServiceById(String id) {
        for (Service s : services) {
            if (s.getId().equals(id))
                return s;
        }
        return null;
    }
    
    @Override
    public void readFromFile() {
        services.clear();
        
        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu dịch vụ");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
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
    
    @Override
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Service s : services) {
                String line = s.getId() + "," + s.getName() + "," + s.getPrice();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file Service");
        }
    }
}