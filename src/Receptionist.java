public class Receptionist extends Employee{
    private int successfulBooking;

    // Constructor cho hàm tạo Cleaner mới
    public Receptionist(String employeeName, String phone, String positon) {
        super(employeeName, phone, positon);
        successfulBooking = 0;
    }

    // Constructor cho hàm đọc/ghi file
    public Receptionist(String employeeName, String phone, String position, int successfulBooking) {
        super(employeeName, phone, position);
        this.successfulBooking = successfulBooking;
    }

    @Override
    public void showInfo() {
        System.out.printf("ID: %03d | Tên: %-15s | SĐT: %-10s | Vị trí: %-15s\n",
                getEmployeeID(), getEmployeeName(), getPhone(), getPositon());
    }

    @Override
    public double calculateSalary() {
        double baseSalary = 9000000;
        return baseSalary + (successfulBooking * 20000);
    }

    public int getWorkingCount() { return successfulBooking;}

    public void increaseCookedMeals() { successfulBooking++;}
}
