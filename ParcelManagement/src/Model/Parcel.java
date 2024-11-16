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

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // String representation of parcel details
    @Override
    public String toString() {
        return "Parcel{id='" + id + '\'' +
               ", daysInDepot=" + daysInDepot +
               ", weight=" + weight +
               ", dimensions=(" + "length=" + length + ", width=" + width + ", height=" + height + ")}";
    }
}
