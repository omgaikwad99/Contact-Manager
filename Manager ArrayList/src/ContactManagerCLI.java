import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ContactManagerCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ContactManager contactManager = new ContactManager();
    private static final Logger logger = LogManager.getLogger(ContactManagerCLI.class);


    public static void main(String[] args) {
        boolean running = true;
        System.setProperty("log4j.configurationFile", "log4j2.properties");
        logger.info("Hello, logging!");
        while (running) {
            System.out.println("Welcome to Contact Manager");
            System.out.println("1. Add contact");
            System.out.println("2. Retrieve contact");
            System.out.println("3. Update contact");
            System.out.println("4. Delete contact");
            System.out.println("5. Display all contacts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    retrieveContact();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    displayAllContacts();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Contact Contact = new Contact(name, email, phoneNumber, address);
        if ((Syntax.isValidEmail(email)) && (Syntax.isValidMobileNumber(phoneNumber)) ) {
            System.out.println("Email and Phone Number are valid.");
            contactManager.addContact(Contact);
            contactManager.addAddress(Contact);
            System.out.println("Contact added successfully!");
        }
        else
        {
            System.out.println("Email or Phone Number is not valid.");
        }
    }

    private static void retrieveContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        Contact Contact = contactManager.getContact(name);
        if (Contact != null) {
            System.out.println("Contact details:");
            System.out.println("Name: " + Contact.getName());
            System.out.println("Email: " + Contact.getEmail());
            System.out.println("Phone number: " + Contact.getPhoneNumber());
            System.out.println(("Address Line: "+ Contact.getAddresses()));
        } else {
            System.out.println("Contact not found!");
        }
    }

    private static void updateContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        Contact Contact = contactManager.getContact(name);
        if (Contact != null) {
            System.out.print("Enter new email: ");
            String email = scanner.nextLine();
            System.out.print("Enter new phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter additional address: ");
            String address = scanner.nextLine();

            contactManager.updateContact(name, email, phoneNumber, address);
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Contact not found!");
        }
    }

    private static void deleteContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        Contact Contact = contactManager.getContact(name);
        if (Contact != null) {
            contactManager.deleteContact(name);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found!");
        }
    }

    private static void displayAllContacts() {
        List<Contact> Contacts = contactManager.getAllContacts();
        if (Contacts.isEmpty()) {
            System.out.println("No contacts found!");
        } else {
            System.out.println("All contacts:");
            for (Contact Contact : Contacts) {
                System.out.println("Name: " + Contact.getName());
                System.out.println("Email: " + Contact.getEmail());
                System.out.println("Phone number: " + Contact.getPhoneNumber());
                //System.out.println("Address Line: " + Contact.getAddresses());
                System.out.println();
            }
        }
    }
}
