import java.io.*;
import java.util.*;

// Lớp Promotion - chứa các thuộc tính và phương thức khởi tạo cho một chương trình khuyến mãi
class Promotion {
    private String id;
    private String name;
    private int discountPercent;
    
    public Promotion(String id, String name, int discountPercent) {
        this.id = id;
        this.name = name;
        this.discountPercent = discountPercent;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getDiscountPercent() {
        return discountPercent;
    }
    
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
    
    @Override
    public String toString() {
        return String.format("%-10s %-30s %d%%", id, name, discountPercent);
    }
}
//--------------------------------------------------------------------------------------------------------------------------------------
// Lớp PromotionManagement - quản lý các chương trình khuyến mãi và thực hiện các thao tác file
public class PromotionManagement implements FileHandler {
    private final Scanner sc;
    private final ClearScreen cleaner;
    private static ArrayList<Promotion> promotions;
    private final File f = new File("data/Promotion");
    
    public PromotionManagement(Scanner sc, ClearScreen cleaner) {
        this.sc = sc;
        this.cleaner = cleaner;
        promotions = new ArrayList<>();
    }
    
    // Kiểm tra ID hợp lệ
    private boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return id.matches("\\d+");
    }
    
    // Kiểm tra tên hợp lệ
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    // Kiểm tra phần trăm giảm giá hợp lệ
    private boolean isValidDiscount(int discount) {
        return discount >= 1 && discount <= 100;
    }
    
    // Hiển thị menu
    public void showMenu() {
        cleaner.clearScreen();
        int choice;
        while (true) {
            System.out.println("----Chức năng quản lý <Khuyến mãi>----");
            System.out.println("<<LƯU Ý: Nhấn 0 để lưu file dữ liệu trước khi tắt chương trình>>");
            System.out.println("1. Xem tất cả chương trình khuyến mãi");
            System.out.println("2. Thêm chương trình khuyến mãi");
            System.out.println("3. Sửa chương trình khuyến mãi");
            System.out.println("4. Xóa chương trình khuyến mãi");
            System.out.println("5. Tìm chương trình theo tên");
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
                    viewAllPromotions();
                    break;
                case 2:
                    addPromotion();
                    break;
                case 3:
                    updatePromotion();
                    break;
                case 4:
                    deletePromotion();
                    break;
                case 5:
                    searchPromotionByName();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nVui lòng nhập các số từ 0-5\n");
            }
        }
    }
    
    // 1. Xem tất cả chương trình khuyến mãi
    public void viewAllPromotions() {
        cleaner.clearScreen();
        System.out.println("----Bạn đang xem tất cả chương trình khuyến mãi----");
        if (!promotions.isEmpty()) {
            System.out.println(String.format("%-10s %-30s %s", "Mã CTKM", "Tên chương trình", "Giảm giá"));
            System.out.println("-------------------------------------------------------------");
            for (Promotion promo : promotions) {
                System.out.println(promo);
            }
            System.out.println("Tổng số chương trình khuyến mãi hiện tại: " + promotions.size() + "\n");
        } else {
            System.out.println("Không tìm thấy chương trình khuyến mãi nào!\n");
        }
    }
    
    // 2. Thêm chương trình khuyến mãi
    public void addPromotion() {
        System.out.print("Nhập mã CTKM (chỉ số, VD: 011): ");
        String id = sc.nextLine().trim();
        
        if (!isValidId(id)) {
            System.out.println("Mã CTKM sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        if (findPromotionById(id) != null) {
            System.out.println("Mã CTKM đã tồn tại! Vui lòng chọn mã khác.\n");
            return;
        }
        
        System.out.print("Nhập tên chương trình: ");
        String name = sc.nextLine().trim();
        
        if (!isValidName(name)) {
            System.out.println("Tên sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        System.out.print("Nhập phần trăm giảm giá (1-100): ");
        int discount;
        try {
            discount = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Phần trăm giảm giá sai định dạng-Chọn và nhập lại nhé\n");
            return;
        }
        
        if (!isValidDiscount(discount)) {
            System.out.println("Phần trăm giảm giá phải từ 1-100\n");
            return;
        }
        
        Promotion newPromotion = new Promotion(id, name, discount);
        promotions.add(newPromotion);
        System.out.println("Thêm chương trình khuyến mãi " + name + " thành công\n");
    }
    
    // 3. Sửa chương trình khuyến mãi
    public void updatePromotion() {
        System.out.print("Nhập mã CTKM cần sửa: ");
        String id = sc.nextLine().trim();
        
        Promotion promo = findPromotionById(id);
        if (promo == null) {
            System.out.println("Không tìm thấy chương trình có mã: " + id + "\n");
            return;
        }
        
        typeNewInfo(promo);
    }
    
    // 4. Xóa chương trình khuyến mãi
    public void deletePromotion() {
        System.out.print("Nhập mã CTKM cần xóa: ");
        String id = sc.nextLine().trim();
        
        Promotion promo = findPromotionById(id);
        if (promo == null) {
            System.out.println("Không tìm thấy chương trình có mã: " + id + "\n");
            return;
        }
        
        promotions.remove(promo);
        System.out.println("Xóa chương trình " + promo.getName() + " thành công\n");
    }
    
    // 5. Tìm chương trình theo tên
    public void searchPromotionByName() {
        System.out.print("Nhập tên cần tìm: ");
        String keyword = sc.nextLine().trim();
        
        ArrayList<Promotion> results = new ArrayList<>();
        for (Promotion promo : promotions) {
            if (promo.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(promo);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy chương trình nào với từ khóa: " + keyword + "\n");
        } else {
            System.out.println("\n===== KẾT QUẢ TÌM KIẾM =====");
            System.out.println(String.format("%-10s %-30s %s", "Mã CTKM", "Tên chương trình", "Giảm giá"));
            System.out.println("-------------------------------------------------------------");
            for (Promotion promo : results) {
                System.out.println(promo);
            }
            System.out.println("-------------------------------------------------------------\n");
        }
    }
    
    // Nhập thông tin muốn sửa
    public void typeNewInfo(Promotion promo) {
        if (promo == null) {
            System.out.println("Chọn và nhập lại mã CTKM");
            return;
        }
        
        System.out.print("Nhập tên mới: ");
        String newName = sc.nextLine().trim();
        
        System.out.print("Nhập phần trăm giảm giá mới (1-100): ");
        String discountStr = sc.nextLine().trim();
        
        if (isValidName(newName)) {
            try {
                int newDiscount = Integer.parseInt(discountStr);
                if (isValidDiscount(newDiscount)) {
                    promo.setName(newName);
                    promo.setDiscountPercent(newDiscount);
                    System.out.println("Sửa thông tin thành công");
                    System.out.println(promo);
                    System.out.println();
                } else {
                    System.out.println("Phần trăm giảm giá sai định dạng-Chọn và nhập lại");
                }
            } catch (NumberFormatException e) {
                System.out.println("Phần trăm giảm giá sai định dạng-Chọn và nhập lại");
            }
        } else {
            System.out.println("Tên sai định dạng-Chọn và nhập lại");
        }
    }
    
    // Tìm chương trình theo ID
    public Promotion findPromotionById(String id) {
        for (Promotion promo : promotions) {
            if (promo.getId().equals(id))
                return promo;
        }
        return null;
    }
    
    @Override
    public void readFromFile() {
        promotions.clear();
        
        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu khuyến mãi");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int discount = Integer.parseInt(parts[2].trim());
                    promotions.add(new Promotion(id, name, discount));
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file Promotion");
        }
    }
    
    @Override
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Promotion promo : promotions) {
                String line = promo.getId() + "," + promo.getName() + "," + promo.getDiscountPercent();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file Promotion");
        }
    }
}
