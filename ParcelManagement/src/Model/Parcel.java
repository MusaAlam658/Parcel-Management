package Model;

public class Parcel {
    private String id;
    private int daysInDepot;
    private double weight;
    private double length;
    private double width;
    private double height;
    private String status;
    private double fee;
    private double discount;
    private double totalPrice;

    //attributes of a parcel
    public Parcel(String id, int daysInDepot, double weight, double length, double width, double height) {
        this.id = id;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.status = "COLLECTING";
        this.fee = 0.0;
        this.discount = 0.0;
        this.totalPrice = 0.0;
    }

    // Getters and setters
    public String getId() { return id; }
    public int getDaysInDepot() { return daysInDepot; }
    public double getWeight() { return weight; }
    public double getLength() { return length; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
//    public void setStatus(String status) { this.status = status; }  // setter for status
    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    // Ensure getStatus is defined
    //Ensures that the status can be updated
    public String getStatus() { return status; }

    public void setStatus(String status) { 
        this.status = status; 
    }
    
    @Override
    public String toString() {
        return "daysInDepot=" + daysInDepot +
               ", weight=" + weight +
               ", length=" + length +
               ", width=" + width +
               ", height=" + height +
               ", status=" + status +
               ", discount=" + discount +
               ", Fee: " + fee +
               ", totalPrice=" + totalPrice;
    }

}
