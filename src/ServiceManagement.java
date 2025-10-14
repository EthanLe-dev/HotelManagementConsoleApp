import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiceManagement {
    // Thuộc tính của một dịch vụ
    private String id;
    private String name;
    private double price;
    
    // Tên file dữ liệu
    private static final String FILE_NAME = "data/Service";
    
    // Constructor mặc định
    public ServiceManagement() {
        // Kiểm tra file có tồn tại không
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Chú ý: File " + FILE_NAME + " chưa tồn tại!");
            System.out.println("Vui lòng tạo file và thêm dữ liệu theo định dạng: ID,Tên,Giá");
        }
    }
    
    // Constructor với tham số (dùng khi đọc từ file)
    public ServiceManagement(String id, String name, double price) {
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
    
    // Đọc tất cả dịch vụ từ file
    private ArrayList<ServiceManagement> readFromFile() {
        ArrayList<ServiceManagement> services = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    services.add(new ServiceManagement(id, name, price));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File không tồn tại!");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        
        return services;
    }
    
    // Ghi tất cả dịch vụ vào file
    private void writeToFile(ArrayList<ServiceManagement> services) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (ServiceManagement s : services) {
                writer.println(s.getId() + "," + s.getName() + "," + s.getPrice());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    
    // 1. Xem tất cả dịch vụ
    public void viewAllServices() {
        ArrayList<ServiceManagement> services = readFromFile();
        
        if (services.isEmpty()) {
            System.out.println("Không có dịch vụ nào!");
            return;
        }
        
        System.out.println("\n===== DANH SÁCH DỊCH VỤ =====");
        System.out.println("ID    Tên             Giá");
        System.out.println("----------------------------------");
        for (ServiceManagement s : services) {
            System.out.println(s);
        }
        System.out.println("----------------------------------");
    }
    
    // 2. Thêm dịch vụ
    public void addService(String id, String name, double price) {
        ArrayList<ServiceManagement> services = readFromFile();
        
        // Kiểm tra ID đã tồn tại chưa
        for (ServiceManagement s : services) {
            if (s.getId().equals(id)) {
                System.out.println("ID đã tồn tại! Vui lòng chọn ID khác.");
                return;
            }
        }
        
        // Thêm dịch vụ mới
        ServiceManagement newService = new ServiceManagement(id, name, price);
        services.add(newService);
        
        // Ghi lại vào file
        writeToFile(services);
        System.out.println("Thêm dịch vụ thành công!");
    }
    
    // 3. Sửa dịch vụ
    public void updateService(String id, String newName, double newPrice) {
        ArrayList<ServiceManagement> services = readFromFile();
        boolean found = false;
        
        for (ServiceManagement s : services) {
            if (s.getId().equals(id)) {
                s.setName(newName);
                s.setPrice(newPrice);
                found = true;
                break;
            }
        }
        
        if (found) {
            // Ghi lại vào file
            writeToFile(services);
            System.out.println("Cập nhật dịch vụ thành công!");
        } else {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id);
        }
    }
    
    // 4. Xóa dịch vụ
    public void deleteService(String id) {
        ArrayList<ServiceManagement> services = readFromFile();
        boolean found = false;
        
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId().equals(id)) {
                services.remove(i);
                found = true;
                break;
            }
        }
        
        if (found) {
            // Ghi lại vào file
            writeToFile(services);
            System.out.println("Xóa dịch vụ thành công!");
        } else {
            System.out.println("Không tìm thấy dịch vụ có ID: " + id);
        }
    }
    
    // 5. Tìm dịch vụ theo tên
    public void searchByName(String keyword) {
        ArrayList<ServiceManagement> services = readFromFile();
        ArrayList<ServiceManagement> results = new ArrayList<>();
        
        for (ServiceManagement s : services) {
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
            for (ServiceManagement s : results) {
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
                    System.out.print("Nhập ID: ");
                    String id = sc.nextLine();
                    System.out.print("Nhập tên: ");
                    String name = sc.nextLine();
                    System.out.print("Nhập giá: ");
                    double price = sc.nextDouble();
                    addService(id, name, price);
                    break;
                    
                case 3:
                    System.out.print("Nhập ID cần sửa: ");
                    String updateId = sc.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String newName = sc.nextLine();
                    System.out.print("Nhập giá mới: ");
                    double newPrice = sc.nextDouble();
                    updateService(updateId, newName, newPrice);
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
}
