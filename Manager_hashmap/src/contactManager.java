import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class contactManager {
    private Map<String, contact> contacts;
    private String filePath;

    public contactManager(String filePath) {
        this.filePath = filePath;
        this.contacts = new HashMap<>();
        loadContacts();
    }


    public void addContact(contact Contact) {
        contacts.put(Contact.getName(), Contact);
        saveContacts();
    }

    public contact getContact(String name) {
        return contacts.get(name);
    }

    public void updateContact(String name, String email, String phoneNumber) {
        contact Contact = contacts.get(name);
        if (Contact != null) {
            Contact.setEmail(email);
            Contact.setPhoneNumber(phoneNumber);
            contacts.put(name, Contact);
            saveContacts();
        }
    }

    public void deleteContact(String name) {
        contacts.remove(name);
        saveContacts();
    }

    public List<contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contactData = line.split(",");
                String name = contactData[0];
                String email = contactData[1];
                String phoneNumber = contactData[2];
                contact Contact = new contact(name, email, phoneNumber);
                contacts.put(Contact.getName(), Contact);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while loading contacts: " + e.getMessage());
        }
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contacts.txt"))) {
            for (contact Contact : contacts.values()) {
                String line = Contact.getName() + "," + Contact.getEmail() + "," + Contact.getPhoneNumber();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving contacts: " + e.getMessage());
        }
    }
}

