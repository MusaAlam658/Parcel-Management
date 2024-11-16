package Model;

public class Customer {
    private String name;
    private String id;
    private int seqNo;

    public Customer(String name, String id, int seqNo) {
        this.name = name;
        this.id = id;
        this.seqNo = seqNo;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getSeqNo() {
        return seqNo;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ", Seq No: " + seqNo + ")";
    }
}