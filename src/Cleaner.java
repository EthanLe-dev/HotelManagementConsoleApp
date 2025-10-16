public class Cleaner extends Employee{
    private int cleanedRooms;

    // Constructor cho hàm tạo Cleaner mới
    public Cleaner(String employeeName, String phone, String positon) {
        super(employeeName, phone, positon);
        cleanedRooms = 0;
    }

    // Constructor cho hàm đọc/ghi file
    public Cleaner(String employeeName, String phone, String position, int cleanedRooms) {
        super(employeeName, phone, position);
        this.cleanedRooms = cleanedRooms;
    }

    public int getCleanedRooms() { return cleanedRooms;}

    @Override
    public void showInfo() {
        System.out.printf("ID: %03d | Tên: %-15s | SĐT: %-10s | Vị trí: %-15s\n",
                getEmployeeID(), getEmployeeName(), getPhone(), getPositon());
    }

    @Override
    public double calculateSalary() {
        double baseSalary = 7000000;
        return baseSalary + (cleanedRooms * 20000);
    }

    public int getWorkingCount() { return cleanedRooms;}

    public void increaseCleanedRooms() { cleanedRooms++;}
}
