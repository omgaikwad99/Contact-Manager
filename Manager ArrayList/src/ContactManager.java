import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactManager {
    private static final Logger logger = LogManager.getLogger(ContactManager.class);

    private List<Contact> contacts;
    private Connection connection;
    private DBHelper dbHelper = new DBHelper();

    public ContactManager() {
        this.contacts = new ArrayList<>();
        this.connection = dbHelper.establishDBConnection();
        loadContacts();
    }

    public void addContact(Contact contact) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO contracts (name, email, phone_number) VALUES (?, ?, ?)");
           // PreparedStatement statement2 = connection.prepareStatement("INSERT INTO adresses (address_line, name) VALUES (?, (SELECT name FROM contracts WHERE name = ?))");
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getPhoneNumber());
            //statement2.setString(1, contact.getAddresses());
            //statement2.setString(2, contact.getName());
            statement.executeUpdate();
           // statement2.executeUpdate();
            contacts.add(contact);
            logger.info("Contact added: {}", contact.getName());
        } catch (SQLException e) {
            logger.error("Error occurred while adding contact to the database: {}", e.getMessage());
        }
    }

    public void addAddress(Contact contact){
        try{
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO adresses (address_line, name) VALUES (?, (SELECT name FROM contracts WHERE name = ?))");
            statement2.setString(1, contact.getAddresses());
            statement2.setString(2, contact.getName());
            statement2.executeUpdate();
            contacts.add(contact);
            logger.info("Address added: {}", contact.getName());
        }catch (SQLException e) {
            logger.error("Error occurred while adding contact to the database: {}", e.getMessage());
        }
    }

    public Contact getContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null;
    }

    public void updateContact(String name, String email, String phoneNumber, String address) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE contracts SET email = ?, phone_number = ? WHERE name = ?");
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO adresses (address_line, name) VALUES (?, (SELECT name FROM contracts WHERE name = ?))");
            statement.setString(1, email);
            statement.setString(2, phoneNumber);
            statement.setString(3, name);
            statement2.setString(1, address);
            statement2.setString(2, name);
            statement.executeUpdate();
            statement2.executeUpdate();

            for (Contact contact : contacts) {
                if (contact.getName().equals(name)) {
                    contact.setEmail(email);
                    contact.setPhoneNumber(phoneNumber);
                    contact.setAddresses(address);
                    break;
                }
            }
            logger.info("Contact updated: {}", name);
        } catch (SQLException e) {
            logger.error("Error occurred while updating contact in the database: {}", e.getMessage());
        }
    }

    public void deleteContact(String name) {
        try {
            PreparedStatement deleteAddressStatement = connection.prepareStatement("DELETE FROM adresses WHERE name = ?");
            deleteAddressStatement.setString(1, name);
            deleteAddressStatement.executeUpdate();

            PreparedStatement deleteContactStatement = connection.prepareStatement("DELETE FROM contracts WHERE name = ?");
            deleteContactStatement.setString(1, name);
            deleteContactStatement.executeUpdate();

            Contact contactToRemove = null;
            for (Contact contact : contacts) {
                if (contact.getName().equals(name)) {
                    contactToRemove = contact;
                    break;
                }
            }
            if (contactToRemove != null) {
                contacts.remove(contactToRemove);
                logger.info("Contact deleted: {}", name);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while deleting contact from the database: {}", e.getMessage());
        }
    }



    public List<Contact> getAllContacts() {
        return contacts;
    }

    private void loadContacts() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contracts");
            //ResultSet resultSet2 = statement.executeQuery("SELECT * FROM adresses");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                Contact contact = new Contact(name, email, phoneNumber, name);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while loading contacts from the database: " + e.getMessage());
        }
    }
    private List<String> loadAddresses(String name) {
        List<String> addresses = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT address_line FROM addresses WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String address = resultSet.getString("address_line");
                addresses.add(address);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while loading addresses for contact: {}", name);
        }
        return addresses;
    }
}
