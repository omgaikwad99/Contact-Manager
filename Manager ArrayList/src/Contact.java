import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String name;
    private String email;
    private String phoneNumber;
    private String addresses; // List to store multiple addresses

    public Contact(String name, String email, String phoneNumber, String addresses) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addresses = addresses;
    }

    // Getter and Setter methods for name, email, and phoneNumber

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }


}
