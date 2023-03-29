import java.io.Serializable;

public class passenger implements Serializable {

    private String firstName;
    private String secondName;
    private String vehicleNo;
    private int liters;

    //Declaring constructor, and getter setters for the attributes
    public passenger(String firstName, String secondName, String vehicleNo, int liters) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.vehicleNo = vehicleNo;
        this.liters = liters;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void getAll() {
        getFirstName();
        getSecondName();
        getVehicleNo();
        getLiters();
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public int getLiters() {
        return liters;
    }

    public void setLiters(int liters) {
        this.liters = liters;
    }

}
