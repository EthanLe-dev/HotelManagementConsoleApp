import java.io.*;
import java.util.*;

//------------------------------------------------------------------------------------------------------------------------------------------
// Lớp PromotionManager quản lý các chương trình khuyến mãi
public class PromotionManager implements FileHandler {
    private static final String FILE_PATH = "data/Promotion";
    private List<Promotion> promotions;
    private Scanner scanner;
    
    public PromotionManager() {
        promotions = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File chưa tồn tại. Vui lòng tạo file promotions.txt với dữ liệu ban đầu.");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            promotions.clear();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    promotions.add(new Promotion(
                        parts[0].trim(),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim())
                    ));
                }
            }
            System.out.println("Đã tải " + promotions.size() + " chương trình khuyến mãi từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
    
    @Override
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Promotion promo : promotions) {
                pw.println(promo.toFileFormat());
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    
    public void viewAllPromotions() {
        if (promotions.isEmpty()) {
            System.out.println("Không có chương trình khuyến mãi nào!");
            return;
        }
        
        System.out.println("\nDANH SÁCH CHƯƠNG TRÌNH KHUYẾN MÃI");
        System.out.println(String.format("%-10s %-30s %s", "Mã CTKM", "Tên chương trình", "Giảm giá"));
        System.out.println("-------------------------------------------------------------");
        
        for (Promotion promo : promotions) {
            System.out.println(promo);
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Tổng số: " + promotions.size() + " chương trình\n");
    }
    
    public void addPromotion() {
        System.out.println("\nTHÊM CHƯƠNG TRÌNH KHUYẾN MÃI MỚI");
        
        String id;
        while (true) {
            System.out.print("Nhập mã CTKM (VD: 011): ");
            id = scanner.nextLine().trim();
            
            if (id.isEmpty()) {
                System.out.println("Mã không được để trống!");
                continue;
            }
            
            if (findPromotionById(id) != null) {
                System.out.println("Mã CTKM đã tồn tại! Vui lòng nhập mã khác.");
                continue;
            }
            break;
        }
        
        System.out.print("Nhập tên chương trình: ");
        String name = scanner.nextLine().trim();
        
        int discount;
        while (true) {
            try {
                System.out.print("Nhập phần trăm giảm giá (1-100): ");
                discount = Integer.parseInt(scanner.nextLine().trim());
                if (discount < 1 || discount > 100) {
                    System.out.println("Phần trăm giảm giá phải từ 1-100!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
        
        promotions.add(new Promotion(id, name, discount));
        saveToFile();
        System.out.println("✓ Đã thêm chương trình khuyến mãi thành công!");
    }
    
    public void updatePromotion() {
        System.out.println("\nSỬA CHƯƠNG TRÌNH KHUYẾN MÃI");
        System.out.print("Nhập mã CTKM cần sửa: ");
        String id = scanner.nextLine().trim();
        
        Promotion promo = findPromotionById(id);
        if (promo == null) {
            System.out.println("✗ Không tìm thấy CTKM có mã: " + id);
            return;
        }
        
        System.out.println("\nThông tin hiện tại:");
        System.out.println(promo);
        
        System.out.print("\nNhập tên mới (Enter để giữ nguyên): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            promo.setName(newName);
        }
        
        System.out.print("Nhập phần trăm giảm giá mới (Enter để giữ nguyên): ");
        String discountStr = scanner.nextLine().trim();
        if (!discountStr.isEmpty()) {
            try {
                int newDiscount = Integer.parseInt(discountStr);
                if (newDiscount >= 1 && newDiscount <= 100) {
                    promo.setDiscountPercent(newDiscount);
                } else {
                    System.out.println("Phần trăm không hợp lệ, giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Định dạng không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }
        
        saveToFile();
        System.out.println("✓ Đã cập nhật thông tin CTKM thành công!");
    }
    
    public void deletePromotion() {
        System.out.println("\nXÓA CHƯƠNG TRÌNH KHUYẾN MÃI");
        System.out.print("Nhập mã CTKM cần xóa: ");
        String id = scanner.nextLine().trim();
        
        Promotion promo = findPromotionById(id);
        if (promo == null) {
            System.out.println("✗ Không tìm thấy CTKM có mã: " + id);
            return;
        }
        
        System.out.println("\nThông tin CTKM sẽ xóa:");
        System.out.println(promo);
        System.out.print("\nBạn có chắc chắn muốn xóa? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        
        if (confirm.equals("Y")) {
            promotions.remove(promo);
            saveToFile();
            System.out.println("✓ Đã xóa CTKM thành công!");
        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }
    
    public void searchPromotionByName() {
        System.out.println("\nTÌM KIẾM CHƯƠNG TRÌNH KHUYẾN MÃI");
        System.out.print("Nhập tên CTKM cần tìm: ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        
        List<Promotion> results = new ArrayList<>();
        for (Promotion promo : promotions) {
            if (promo.getName().toLowerCase().contains(keyword)) {
                results.add(promo);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("✗ Không tìm thấy CTKM nào với từ khóa: " + keyword);
        } else {
            System.out.println("\nKết quả tìm kiếm (" + results.size() + " kết quả):");
            System.out.println(String.format("%-10s %-30s %s", "Mã CTKM", "Tên chương trình", "Giảm giá"));
            System.out.println("-------------------------------------------------------------");
            for (Promotion promo : results) {
                System.out.println(promo);
            }
        }
    }
    
    public Promotion findPromotionById(String id) {
        for (Promotion promo : promotions) {
            if (promo.getId().equals(id)) {
                return promo;
            }
        }
        return null;
    }

    public Promotion findPromotionByName(String name) {
        for (Promotion p : promotions) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    public void showMenu() {
        while (true) {
            System.out.println("\n========================================================");
            System.out.println("       HỆ THỐNG QUẢN LÝ KHUYẾN MÃI KHÁCH SẠN");
            System.out.println("========================================================");
            System.out.println("  1. Xem tất cả chương trình khuyến mãi");
            System.out.println("  2. Thêm chương trình khuyến mãi");
            System.out.println("  3. Sửa chương trình khuyến mãi");
            System.out.println("  4. Xóa chương trình khuyến mãi");
            System.out.println("  5. Tìm kiếm chương trình theo tên");
            System.out.println("  0. Thoát");
            System.out.println("--------------------------------------------------------");
            System.out.print("Chọn chức năng: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    viewAllPromotions();
                    break;
                case "2":
                    addPromotion();
                    break;
                case "3":
                    updatePromotion();
                    break;
                case "4":
                    deletePromotion();
                    break;
                case "5":
                    searchPromotionByName();
                    break;
                case "0":
                    System.out.println("\n✓ Cảm ơn bạn đã sử dụng hệ thống!");
                    return;
                default:
                    System.out.println("✗ Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }
}
