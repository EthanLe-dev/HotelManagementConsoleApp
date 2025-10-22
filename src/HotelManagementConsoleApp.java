import java.util.Scanner;

public class HotelManagementConsoleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClearScreen cleaner = new ClearScreen();

        RoomManager rm = new RoomManager(sc, cleaner);
        EmployeeManager em = new EmployeeManager(sc, cleaner);
        ServiceManagement sm = new ServiceManagement(sc, cleaner);
        PromotionManager pm = new PromotionManager();
        readAllData(rm, em, sm, pm);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Đang lưu dữ liệu trước khi thoát...");
            saveAllData(rm, em, sm ,pm);
        }));

        int choice;
        while (true) {
            cleaner.clearScreen();
            System.out.println("------Ứng dụng quản lý khách sạn Nhóm 11------");
            System.out.println("1.Quản lý phòng");          // Quý
            System.out.println("2.Quản lý nhân viên");      // Quý
            System.out.println("3.Quản lý khách hàng");     // Phú
            System.out.println("4.Quản lý đặt phòng");      // Phú
            System.out.println("5.Quản lý hóa đơn");        // Thịnh
            System.out.println("6.Quản lý dịch vụ");        // Thái
            System.out.println("7.Quản lý ưu đãi");         // Thái
            System.out.println("0.Thoát chương trình");

            try {
                System.out.print("Nhập lựa chọn: ");
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số từ 0-8");
                continue;
            }

            switch (choice) {
                case 1:
                    rm.showMenu();
                    break;
                case 2:
                    em.showMenu();
                    break;
                case 3:
                    System.out.println("----Chức năng quản lý khách hàng----");
                    /*
                    Xem tất cả khách hàng
                    Xem khách hàng theo cấp Member
                    Thêm khách hàng mới
                    Sửa thông tin khách
                    Xóa khách hàng theo SĐT
                     */
                    break;
                case 4:
                    System.out.println("----Chức năng quản lý đặt phòng----");
                    /*
                    Xem tất cả phòng đã đặt/chưa đặt
                    Tạo 1 lịch đặt
                    Sửa 1 lịch đặt
                    Xóa 1 lịch đặt
                    Xem lịch đặt theo tên khách
                     */
                    break;
                case 5:
                    System.out.println("----Chức năng quản lý hóa đơn----");
                    /*
                    Xem tất cả lịch đặt từ booking
                    Xử lý thanh toán -> ra hóa đơn
                    Xem tất cả hóa đơn đã thanh toán
                    Tìm hóa đơn theo tên khách
                    Xem tổng doanh thu/in ra file HotelData
                     */
                    break;
                case 6:
                    sm.showMenu();
                    break;
                case 7:

                    break;
                case 0:
                    System.out.println("Thoát chương trình...");
                    return;
                default:
                    System.out.println("Vui lòng nhập số từ 0-8");
            }
        }
    }

    public static void readAllData(RoomManager rm, EmployeeManager em, ServiceManagement sm, PromotionManager pm) {
        rm.readFromFile();
        em.readFromFile();
        sm.readFromFile();
        pm.readFromFile();
    }

    public static void saveAllData(RoomManager rm, EmployeeManager em, ServiceManagement sm, PromotionManager pm) {
        rm.saveToFile();
        em.saveToFile();
        sm.saveToFile();
        pm.saveToFile();
    }
}