public abstract class Employee {
    private int employeeID;
    private String employeeName;
    private String phone;
    private String positon;
    private static int numberOfEmployees = 0;

    public Employee(String employeeName, String phone, String positon) {
        ++numberOfEmployees;
        this.employeeID = numberOfEmployees;
        this.employeeName = employeeName;
        this.phone = phone;
        this.positon = positon;
    }

    public int getEmployeeID() { return employeeID;}
    public String getEmployeeName() { return employeeName;}
    public String getPhone() { return phone;}
    public String getPositon() { return positon;}

    public void setEmployeeName(String employeeName) { this.employeeName = employeeName;}
    public void setPhone(String phone) { this.phone = phone;}

    public static int getNumberOfEmployees() { return numberOfEmployees;}
    public abstract void showInfo();
    public abstract double calculateSalary();
    public abstract int getWorkingCount();
}
