//package Model;
//
//public class Worker {
//    private Log log;
//
//    public Worker(Log log) {
//        this.log = log;
//    }
//
//    public void processParcel(Customer customer, ParcelMap parcelMap) {
//        Parcel parcel = parcelMap.getParcelById(customer.getParcelId());
//        if (parcel != null) {
//            double fee = parcel.getFee();
//            log.addLog("Processed " + customer.getName() + " for parcel " + customer.getParcelId() + ". Fee: " + fee);
//            parcelMap.addParcel(new Parcel(customer.getParcelId(), parcel.getLength(), parcel.getWidth(), parcel.getHeight(), parcel.getWeight(), 0));  // Mark as collected
//        }
//    }
//}