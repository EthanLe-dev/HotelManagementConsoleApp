import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManager implements FileHandler{
    private final Scanner sc;
    private final ClearConsoleScreen cleaner;
    private static ArrayList<Employee> employeeList;
    private final File f = new File("data/EmployeeData");

    public EmployeeManager(Scanner sc, ClearConsoleScreen cleaner) {
        this.sc = sc;
        this.cleaner = cleaner;
        employeeList = new ArrayList<>();
    }

    // Hiển thị menu
    public void showMenu() {
        cleaner.clearScreen();
        int choice;
        while (true) {
            System.out.println("----Chức năng quản lý <Nhân viên>----");
            System.out.println("<<LƯU Ý: Nhấn 0 để lưu file dữ liệu trước khi tắt chương trình>>");
            System.out.println("1.Xem tất cả nhân viên");
            System.out.println("2.Sửa thông tin nhân viên");
            System.out.println("3.Xóa nhân viên");
            System.out.println("4.Xem nhân viên theo chức vụ");
            System.out.println("5.Thêm nhân viên");
            System.out.println("6.Xem lương nhân viên");
            System.out.println("0.Lưu file dữ liệu & Về menu chính");
            try {
                System.out.print("Nhập lựa chọn: ");
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\nVui lòng nhập số từ 0-6\n");
                continue;
            }

            switch (choice) {
                case 1:
                    showAllEmployees();
                    break;
                case 2:
                    setEmployeeInfo();
                    break;
                case 3:
                    removeEmployee();
                    break;
                case 4:
                    searchByPosition();
                    break;
                case 5:
                    addEmployee();
                    break;
                case 6:
                    showSalary();
                    break;
                case 0:
                    saveToFile();
                    return;
                default:
                    System.out.println("\nVui lòng nhập các số từ 0-6\n");
            }
        }
    }

    // Xem tất cả nhân viên
    public void showAllEmployees() {
        cleaner.clearScreen();
        System.out.println("----Bạn đang xem tất cả nhân viên----");
        if(!employeeList.isEmpty()) {
            for (Employee e : employeeList) {
                e.showInfo();
            }
            System.out.println("Tổng số nhân viên hiện tại: "+employeeList.size()+"\n");
        }
        else
            System.out.println("Không tìm thấy nhân viên nào!\n");
    }

    // Sửa thông tin nhân viên
    public void setEmployeeInfo() {
        System.out.println("Chọn phương án tìm:");
        System.out.println("1.Tìm theo tên");
        System.out.println("2.Tìm theo SĐT");

        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice == 1) {
                Employee employee = searchEmployeeByName();
                if (employee == null)
                    return;

                typeNewInfo(employee);
            } else if (choice == 2) {
                Employee employee = searchEmployeeByPhone();
                if (employee == null)
                    return;

                typeNewInfo(employee);
            } else
                System.out.println("Nhập số 1 hoặc 2 nhé\n");
        } catch (NumberFormatException e) {
            System.out.println("Nhập số 1 hoặc 2 nhé\n");
        }
    }

    // Xóa nhân viên
    public void removeEmployee() {
        System.out.println("Chọn phương án tìm:");
        System.out.println("1.Tìm theo tên");
        System.out.println("2.Tìm theo SĐT");

        try {
            int choice = Integer.parseInt(sc.nextLine().trim());
            if (choice == 1) {
                Employee employee = searchEmployeeByName();
                if (employee == null)
                    return;

                employeeList.remove(employee);
                System.out.println("Xóa nhân viên " + employee.getEmployeeName() + " thành công");
            } else if (choice == 2) {
                Employee employee = searchEmployeeByPhone();
                if (employee == null)
                    return;

                employeeList.remove(employee);
                System.out.println("Xóa nhân viên " + employee.getEmployeeName() + " thành công");
            } else
                System.out.println("Nhập số 1 hoặc 2 nhé\n");
        } catch (NumberFormatException e) {
            System.out.println("Nhập số 1 hoặc 2 nhé\n");
        }
    }

    // Xem nhân viên theo chức vụ
    public void searchByPosition() {
        System.out.print("Chọn chức vụ muốn tìm: Cleaner-Chef-Receptionist? -> ");
        String position = sc.nextLine().trim();
        String key = "cleaner-chef-receptionist";

        if (!key.contains(position.toLowerCase())) {
            System.out.println("Nhập lại đúng Cleaner-Chef-Receptionist nhé\n");
            return;
        }

        for (Employee e : employeeList) {
            if (e.getPositon().equalsIgnoreCase(position))
                e.showInfo();
        }
    }

    // Thêm nhân viên mới
    public void addEmployee() {
        System.out.print("Nhập tên nhân viên mới: ");
        String name = sc.nextLine();
        name = normalizeName(name);

        System.out.print("Nhập SĐT: ");
        String phone = sc.nextLine();

        if(!isValidPhone(phone)) {
            System.out.println("SĐT sai định dạng-Chọn và nhập lại nhé");
            return;
        }

        System.out.print("Nhập vị trí của nhân viên mới(Cleaner-Chef-Receptionist): ");
        String position = sc.nextLine().trim();
        String key = "cleaner-chef-receptionist";

        if (!key.contains(position.toLowerCase())) {
            System.out.println("Nhập lại đúng Cleaner-Chef-Receptionist nhé\n");
            return;
        }

        if (position.equalsIgnoreCase("Cleaner")) {
            Employee newEmployee = new Cleaner(name, phone, position);
            employeeList.add(newEmployee);
        }
        else if (position.equalsIgnoreCase("Chef")) {
            Employee newEmployee = new Chef(name, phone, position);
            employeeList.add(newEmployee);
        }
        else {
            Employee newEmployee = new Receptionist(name, phone, position);
            employeeList.add(newEmployee);
        }
        System.out.println("Thêm nhân viên "+name+" thành công\n");
    }

    // Xem lương nhân viên
    public void showSalary() {
        System.out.println("-----Lương tất cả nhân viên-----");
        for (Employee e : employeeList) {
            System.out.printf("Tên nhân viên: %-15s | Vị trí: %-15s | Số lần được thưởng: %03d | Lương: %.0f\n",
                    e.getEmployeeName(), e.getPositon(), e.getWorkingCount(), e.calculateSalary());
        }
        System.out.println("\n");
    }

    // Nhập thông tin muốn sửa
    public void typeNewInfo(Employee employee) {
        if (employee == null) {
            System.out.println("Chọn và nhập lại tên hoặc SĐT");
            return;
        }

        System.out.print("Nhập họ và tên mới: ");
        String newName = sc.nextLine();
        newName = normalizeName(newName);

        System.out.print("Nhập SĐT mới: ");
        String newPhone = sc.nextLine().trim();

        if (isValidPhone(newPhone)) {
            employee.setEmployeeName(newName);
            employee.setPhone(newPhone);
            System.out.println("Sửa thông tin thành công");
            employee.showInfo();
            System.out.println();
        } else {
            System.out.println("SĐT sai định dạng-Chọn và nhập lại");
        }
    }

    // Tìm nhân viên theo tên
    public Employee searchEmployeeByName() {
        System.out.print("Nhập tên nhân viên cần tìm: ");
        String name = sc.nextLine();
        name = normalizeName(name);

        for (Employee e : employeeList) {
            if (e.getEmployeeName().contains(name))
                return e;
        }
        System.out.println("Không tìm thấy nhân viên tên "+name);
        return null;
    }

    // Tìm nhân viên theo SĐT
    public Employee searchEmployeeByPhone() {
        System.out.print("Nhập SĐT nhân viên cần tìm: ");
        String phone = sc.nextLine().trim();

        if (isValidPhone(phone)) {
            for (Employee e : employeeList) {
                if (e.getPhone().equals(phone))
                    return e;
            }
            System.out.println("Không tìm thấy nhân viên có SĐT này");
            return null;
        }
        System.out.println("SĐT nhập vào không hợp lệ-Chọn và nhập lại");
        return null;
    }

    // Chuẩn hóa tên nhân viên
    public static String normalizeName(String name) {
        if (name == null || name.isBlank())
            return "Tên sai định dạng";

        StringBuilder newName = new StringBuilder();

        name = name.trim().toLowerCase();
        String[] wordArr = name.split("\\s+");

        for (String word : wordArr) {
            String firstChar = (word.substring(0,1)).toUpperCase();
            String newWord = firstChar + word.substring(1);
            newName.append(newWord);
            newName.append(" ");
        }
        return newName.toString().trim();
    }

    // Kiểm tra hợp lệ SĐT nhân viên
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;

        return phone.matches("^0\\d{9,10}$");
    }

    @Override
    public void readFromFile() {
        employeeList.clear();

        if (!f.exists()) {
            System.out.println("Không tìm thấy file dữ liệu phòng");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine();
            String line;

            while((line = br.readLine()) != null) {
                String[] info = line.split(",");
                String name = info[1].trim();
                String phone = info[2];
                String position = info[3].trim();
                int workingCount = Integer.parseInt(info[5]);

                if (position.equals("Cleaner")) {
                    Employee newEmployee = new Cleaner(name, phone, position, workingCount);
                    employeeList.add(newEmployee);
                }
                else if (position.equals("Chef")) {
                    Employee newEmployee = new Chef(name, phone, position, workingCount);
                    employeeList.add(newEmployee);
                }
                else {
                    Employee newEmployee = new Receptionist(name, phone, position, workingCount);
                    employeeList.add(newEmployee);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu từ file EmployeeData");
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            String header = "ID nhân viên | Tên nhân viên | SĐT | Chức vụ | Số lần nhận thưởng";
            bw.write(header);
            bw.newLine();

            for (Employee e : employeeList) {
                String line = String.format("%03d,%-15s,%-10s,%-15s,%.0f,%03d",
                        e.getEmployeeID(), e.getEmployeeName(), e.getPhone(), e.getPositon(), e.calculateSalary(), e.getWorkingCount());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu dữ liệu vào file EmployeeData");
        }
    }
}