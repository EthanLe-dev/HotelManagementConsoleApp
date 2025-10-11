import java.util.Scanner;

public class HotelManagementConsoleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClearScreen cleaner = new ClearScreen();
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
                    RoomManager rm = new RoomManager(sc, cleaner);
                    rm.readFromFile();
                    rm.showMenu();
                    rm.saveToFile();
                    break;
                case 2:
                    System.out.println("----Chức năng quản lý nhân viên----");
                    /*
                    Xem tất cả nhân viên
                    Xem nhân viên theo chức vụ (Receptionist,Chef,Cleaner)
                    Thêm nhân viên mới
                    Sửa thông tin nhân viên
                    Xóa nhân viên
                     */
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
                    System.out.println("----Chức năng quản lý dịch vụ----");
                    /*
                    Xem tất cả dịch vụ hiện có
                    Thêm dịch vụ
                    Sửa dịch vụ
                    Xóa dịch vụ
                    Tìm dịch vụ theo tên
                     */
                    break;
                case 7:
                    System.out.println("----Chức năng quản lý ưu đãi----");
                    /*
                    Xem tất cả chương trình KM hiện có
                    Thêm CTKM
                    Sửa CTKM
                    Xóa CTKM
                    Tìm CTKM theo tên
                     */
                    break;
                case 0:
                    System.out.println("Thoát chương trình...");
                    return;
                default:
                    System.out.println("Vui lòng nhập số từ 0-8");
            }
        }
    }
}