package Model;

public class Parcel {
    private String id;
    private int daysInDepot;
    private double weight;
    private double length;
    private double width;
    private double height;

    public Parcel(String id, int daysInDepot, double weight, double length, double width, double height) {
        this.id = id;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    public double getWeight() {
        return weight;
    }

    // Getter for length
    public double getLength() {
        return length;
    }

    // Getter for width
    public double getWidth() {
        return width;
    }

    // Getter for height
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        // Format the dimensions as requested: length=10.0, width:5.0, height:2.0
        return "Parcel{" +
                "id='" + id + '\'' +
                ", daysInDepot=" + daysInDepot +
                ", weight=" + weight +
                ", length=" + length + ", width:" + width + ", height:" + height +
                '}';
    }
}
