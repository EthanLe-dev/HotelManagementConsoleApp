public class Customer {
    private int customerID;
    private String name;
    private String phone;
    private int usageCount;
    private String memberRank;

    public Customer(int customerID, String name, String phone, int usageCount, String memberRank) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.usageCount = usageCount;
        this.memberRank = memberRank;
    }

    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getUsageCount() { return usageCount; }
    public void setUsageCount(int usageCount) { this.usageCount = usageCount; }

    public String getMemberRank() { return memberRank; }
    public void setMemberRank(String memberRank) { this.memberRank = memberRank; }

    public void updateMemberRank() {
        if (usageCount >= 10) memberRank = "Gold";
        else if (usageCount >= 5) memberRank = "Silver";
        else memberRank = "Normal";
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-15s | %-12s | %-5d | %-7s",
                customerID, name, phone, usageCount, memberRank);
    }
}
