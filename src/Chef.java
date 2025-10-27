public class Chef extends Employee{
    private int cookedMeals;

    // Constructor cho hàm tạo Cleaner mới
    public Chef(String employeeName, String phone, String positon) {
        super(employeeName, phone, positon);
        cookedMeals = 0;
    }

    // Constructor cho hàm đọc/ghi file
    public Chef(String employeeName, String phone, String position, int cookedMeals) {
        super(employeeName, phone, position);
        this.cookedMeals = cookedMeals;
    }

    public int getCookedMeals() { return cookedMeals;}

    @Override
    public void showInfo() {
        System.out.printf("ID: %03d | Tên: %-15s | SĐT: %-10s | Vị trí: %-15s\n",
                getEmployeeID(), getEmployeeName(), getPhone(), getPositon());
    }

    @Override
    public double calculateSalary() {
        double baseSalary = 12000000;
        return baseSalary + (cookedMeals * 20000);
    }

    public int getWorkingCount() { return cookedMeals;}

    public void increaseCookedMeals() { cookedMeals++;}
}
