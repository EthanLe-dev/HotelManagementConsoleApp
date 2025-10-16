import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class RoomManager implements FileHandler{
    private final Scanner sc;
    private final ClearScreen cleaner;
    private static ArrayList<Room> roomList;

    public RoomManager(Scanner sc, ClearScreen cleaner) {
        this.sc = sc;
        this.cleaner = cleaner;
        roomList = new ArrayList<>();
    }

    // Hiển thị menu
    public void showMenu() {
        cleaner.clearScreen();
        int choice;
        while (true) {
            System.out.println("----Chức năng quản lý <PHÒNG>----");
            System.out.println("<<LƯU Ý: Nhấn 0 để lưu file dữ liệu trước khi tắt chương trình>>");
            System.out.println("1.Xem tất cả các phòng");
            System.out.println("2.Sửa giá phòng theo ID");
            System.out.println("3.Xóa phòng");
            System.out.println("4.Xem theo cấp phòng");
            System.out.println("5.Thêm phòng");
            System.out.println("0.Lưu file dữ liệu & Về menu chính");
            try {
                System.out.print("Nhập lựa chọn: ");
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\nVui lòng nhập số từ 0-5\n");
                continue;
            }

            switch (choice) {
                case 1:
                    showAllRooms();
                    break;
                case 2:
                    setRoomPriceByID();
                    break;
                case 3:
                    removeRoom();
                    break;
                case 4:
                    showRoomsByType();
                    break;
                case 5:
                    addRoom();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nVui lòng nhập các số từ 0-5\n");
            }
        }
    }

    // Xem tất cả phòng
    public void showAllRooms() {
        cleaner.clearScreen();
        System.out.println("----Bạn đang xem phòng----");
        if(!roomList.isEmpty()) {
            for (Room r : roomList) {
                r.showRoomInfo();
            }
            System.out.println("Tổng số phòng hiện tại: "+roomList.size()+"\n");
        }
        else
            System.out.println("Chưa set up phòng nào!\n");
    }

    // Xem phòng các cấp
    public void showRoomsByType() {
        System.out.print("Bạn muốn xem phòng loại nào(Standard/Suite): ");
        String type = sc.nextLine();

        if(type.equals("Standard") || type.equals("Suite")) {
            for (Room r : roomList) {
                if (r.getRoomType().equals(type))
                    r.showRoomInfo();
            }
            System.out.println("\n");
        }
        else
            System.out.println("Nhập lại Standard hoặc Suite nhé");
    }

    // Sửa giá phòng bằng ID
    public void setRoomPriceByID() {
        cleaner.clearScreen();
        showAllRooms();
        System.out.print("Nhập ID phòng cẩn sửa giá: ");
        int roomID = Integer.parseInt(sc.nextLine().trim());

        for(Room r : roomList) {
            if(r.getRoomID() == roomID) {
                System.out.println("Sửa giá: ");
                double newPrice = Double.parseDouble(sc.nextLine().trim());
                r.setRoomPrice(newPrice);
                System.out.println("Đã sửa giá phòng "+r.getRoomID()+"\n");
                r.showRoomInfo();
                return;
            }
        }
        System.out.println("Không tìm thấy phòng\n");
    }

    // Xóa phòng
    public void removeRoom() {
        cleaner.clearScreen();
        showAllRooms();
        System.out.println("Nhập ID phòng cần xóa: ");
        int roomID = Integer.parseInt(sc.nextLine().trim());

        boolean check = roomList.removeIf(r -> r.getRoomID() == roomID);
        if (check)
            System.out.println("Đã xóa phòng có ID: "+roomID);
        else
            System.out.println("Không tìm thấy phòng có ID: "+roomID);
    }

    //Thêm phòng
    public void addRoom() {
        if (Room.roomIsMax()) return;

        cleaner.clearScreen();
        System.out.println("Bạn đang thêm phòng:\n");

        System.out.print("Nhập ID phòng: ");
        int roomID = Integer.parseInt(sc.nextLine().trim());
        if(isCreated(roomID)) {
            System.out.println("ID phòng đã tồn tại-Chọn và nhập lại");
            return;
        }

        System.out.print("Nhập giá phòng: ");
        double roomPrice = Double.parseDouble(sc.nextLine().trim());
        String roomType = Room.checkRoomType(roomID, roomPrice);
        if(Objects.equals(roomType,"ID và giá phòng không hợp lệ")) {
            System.out.println(roomType);
            return;
        }

        Room room = new Room(roomID, roomType, roomPrice);
        roomList.add(room);
        System.out.println("Thêm phòng "+roomID+" thành công");
        room.showRoomInfo();
    }

    public static int getRoomListSize() {
        return roomList.size();
    }

    // Kiểm tra xem ID phòng có tồn tại không
    public boolean isCreated(int roomID) {
        for(Room r : roomList) {
            if(roomID == r.getRoomID())
                return true;
        }
        return false;
    }

    // Đọc dữ liệu phòng từ file RoomData
    public void readFromFile() {
        File f = new File("data/RoomData");
        roomList.clear();

        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu phòng");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                int roomID = Integer.parseInt(info[0]);
                String roomType = info[1];
                double price = Double.parseDouble(info[2]);
                boolean isAvaible = Boolean.parseBoolean(info[3]);

                Room room = new Room(roomID, roomType, price, isAvaible);
                roomList.add(room);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file RoomData");
        }
    }

    // Lưu dữ liệu phòng vào file RoomData
    public void saveToFile() {
        File f = new File("data/RoomData");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            String header = "ID Phòng | Loại phòng | Giá | Đã đặt hay chưa";
            bw.write(header);
            bw.newLine();
            for (Room r : roomList) {
                String line = String.format("%d,%s,%.0f,%b",
                        r.getRoomID(), r.getRoomType(), r.getRoomPrice(), r.getIsAvailable());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file RoomData");
        }
    }
}

