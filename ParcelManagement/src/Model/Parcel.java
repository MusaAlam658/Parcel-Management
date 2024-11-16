package Model;

public class Parcel {
    private String id;
    private int daysInDepot;
    private double weight;
    private double dimension1;
    private double dimension2;
    private double dimension3;

    public Parcel(String id, int daysInDepot, double weight, double dimension1, double dimension2, double dimension3) {
        this.id = id;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
        this.dimension3 = dimension3;
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

    public double getDimension1() {
        return dimension1;
    }

    public double getDimension2() {
        return dimension2;
    }

    public double getDimension3() {
        return dimension3;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "id='" + id + '\'' +
                ", daysInDepot=" + daysInDepot +
                ", weight=" + weight +
                ", dimensions=(" + dimension1 + ", " + dimension2 + ", " + dimension3 + ")" +
                '}';
    }
}
