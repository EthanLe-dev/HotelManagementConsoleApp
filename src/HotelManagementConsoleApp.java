import java.io.IOException;
import java.util.Scanner;

public class HotelManagementConsoleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClearScreen cleaner = new ClearScreen();
        int choice;

        while (true) {
            cleaner.clearScreen();
            System.out.println("------Ứng dụng quản lý khách sạn Nhóm 11------");
            System.out.println("1.Quản lý phòng");       // thêm,xóa,sửa,tìm số phòng, loại phòng
            System.out.println("2.Quản lý đặt phòng");   // thêm,xóa,sửa,tìm lịch đặt của khách
            System.out.println("3.Quản lý trả phòng và thanh toán");   // thêm,xóa,sửa,tìm lịch đặt của khách
            System.out.println("4.Quản lý khách hàng");  // thêm,xóa,sửa,tìm khách hàng và cấp vip
            System.out.println("5.Quản lý dịch vụ");     // thêm,xóa,sửa dịch vụ đồ ăn, thức uống
            System.out.println("6.Quản lý ưu đãi");      // thêm,xóa,sửa các chương trình ưu đãi
            System.out.println("7.Quản lý nhân viên");   // thêm,xóa,sửa,tìm lễ tân, dọn vs, nhân viên bếp,...
            System.out.println("8.Dữ liệu khách sạn");   // xem doanh thu, phòng trống/đã đặt, nhân viên
            System.out.println("0.Thoát");

            try {
                System.out.print("Nhập lựa chọn: ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số từ 0-8");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("----Chức năng quản lý phòng----");
                    break;
                case 2:
                    System.out.println("----Chức năng quản lý đặt phòng----");
                    break;
                case 3:
                    System.out.println("----Chức năng quản lý trả phòng và thanh toán----");
                    break;
                case 4:
                    System.out.println("----Chức năng quản lý khách hàng----");
                    break;
                case 5:
                    System.out.println("----Chức năng quản lý dịch vụ----");
                    break;
                case 6:
                    System.out.println("----Chức năng quản lý ưu đãi----");
                    break;
                case 7:
                    System.out.println("----Chức năng quản lý nhân viên----");
                    break;
                case 8:
                    System.out.println("----Chức năng quản lý dữ liệu khách sạn----");
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
