// Lớp Promotion chứa các thuôc tính và phương thức khởi tạo cho một chương trình khuyến mãi
public class Promotion {
    private String id;
    private String name;
    private int discountPercent;

    public Promotion(String id, String name, int discountPercent) {
        this.id = id;
        this.name = name;
        this.discountPercent = discountPercent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-30s %d%%", id, name, discountPercent);
    }

    public String toFileFormat() {
        return id + "," + name + "," + discountPercent;
    }
}
