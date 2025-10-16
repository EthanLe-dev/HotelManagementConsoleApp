
import java.io.*;
import java.util.*;

public class quanlyhoadon {
   
    static class HoaDon {
        
        private String maHD;
        private String tenKH;
        private String ngayHD;
        private double tongTien;

        public HoaDon(String maHD, String tenKH, String ngayHD, double tongTien) {
            
            this.maHD = maHD;
            this.tenKH = tenKH;
            this.ngayHD = ngayHD;
            this.tongTien = tongTien;
        }

        public String getTenKH() {
            return tenKH;
        }

        public double getTongTien() {
            return tongTien;
        }

        @Override
        public String toString() {
            return maHD + " | " + tenKH + " | " + ngayHD + " | " + tongTien + " VND";
        }
    }

    static ArrayList<HoaDon> danhSachHD = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // Thêm hóa đơn mới
    public static void themHoaDon() {
        
        System.out.print("Nhập mã hóa đơn: ");
        String maHD = sc.nextLine();
        System.out.print("Nhập tên khách hàng: ");
        String tenKH = sc.nextLine();
        System.out.print("Nhập ngày hóa đơn (dd/mm/yyyy): ");
        String ngayHD = sc.nextLine();
        System.out.print("Nhập tổng tiền: ");
        double tongTien = Double.parseDouble(sc.nextLine());

        danhSachHD.add(new HoaDon(maHD, tenKH, ngayHD, tongTien));
        System.out.println(" Thêm hóa đơn thành công!\n");
    }

    // Hiển thị danh sách hóa đơn
    public static void hienThiHoaDon() {
        if (danhSachHD.isEmpty()) {
            System.out.println(" Chưa có hóa đơn nào.\n");
            return;
        }
        System.out.println("===== DANH SÁCH HÓA ĐƠN =====");
        for (HoaDon hd : danhSachHD) {
            System.out.println(hd);
        }
        System.out.println();
    }

    // Tìm hóa đơn theo tên khách hàng
    public static void timHoaDonTheoTen() {
        System.out.print("Nhập tên khách hàng cần tìm: ");
        String ten = sc.nextLine().toLowerCase();
        boolean found = false;
        for (HoaDon hd : danhSachHD) {
            if (hd.getTenKH().toLowerCase().contains(ten)) {
                System.out.println(hd);
                found = true;
            }
        }
        if (!found) System.out.println(" Không tìm thấy hóa đơn của khách hàng này.\n");
    }

    // Tính tổng doanh thu
    public static void tinhTongDoanhThu() {
        double tong = 0;
        for (HoaDon hd : danhSachHD) {
            tong += hd.getTongTien();
        }
        System.out.println(" Tổng doanh thu: " + tong + " VND\n");
    }

    // Ghi danh sách ra file
    public static void ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("HotelData.txt"))) {
            for (HoaDon hd : danhSachHD) {
                bw.write(hd.toString());
                bw.newLine();
            }
            System.out.println(" Đã ghi dữ liệu vào file HotelData.txt\n");
        } catch (IOException e) {
            System.out.println(" Lỗi ghi file: " + e.getMessage());
        }
    }

    // Đọc danh sách từ file
    public static void docFile() {
        danhSachHD.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("HotelData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String maHD = parts[0].trim();
                    String tenKH = parts[1].trim();
                    String ngayHD = parts[2].trim();
                    double tongTien = Double.parseDouble(parts[3].replace("VND", "").trim());
                    danhSachHD.add(new HoaDon(maHD, tenKH, ngayHD, tongTien));
                }
            }
            System.out.println(" Đã đọc dữ liệu từ file HotelData.txt\n");
        } catch (IOException e) {
            System.out.println(" Lỗi đọc file hoặc file chưa tồn tại.");
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("========= QUẢN LÝ HÓA ĐƠN KHÁCH SẠN =========");
            System.out.println("1. Thêm hóa đơn");
            System.out.println("2. Hiển thị danh sách hóa đơn");
            System.out.println("3. Tìm hóa đơn theo tên khách hàng");
            System.out.println("4. Tính tổng doanh thu");
            System.out.println("5. Ghi dữ liệu ra file");
            System.out.println("6. Đọc dữ liệu từ file");
            System.out.println("0. Thoát");
            System.out.print("👉 Chọn chức năng: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    themHoaDon();
                    break;
                case "2":
                    hienThiHoaDon();
                    break;
                case "3":
                    timHoaDonTheoTen();
                    break;
                case "4":
                    tinhTongDoanhThu();
                    break;
                case "5":
                    ghiFile();
                    break;
                case "6":
                    docFile();
                    break;
                case "0":
                    System.out.println(" Thoát chương trình. Tạm biệt!");
                    return;
                default:
                    System.out.println(" Lựa chọn không hợp lệ. Vui lòng chọn lại!\n");
            }
        }
    }
}


