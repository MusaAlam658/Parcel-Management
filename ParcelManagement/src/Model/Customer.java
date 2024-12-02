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

    //Getter for Name
    public String getName() {
        return name;
    }
    
    //Getter for Parcel ID
    public String getId() {
        return id;
    }

    //Getter for Customer Sequence Number
    public int getSeqNo() {
        return seqNo;
    }

    //Setter for Customer Sequence Number
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ", Seq No: " + seqNo + ")";
    }
}
