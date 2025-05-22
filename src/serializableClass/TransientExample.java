package serializableClass;

import java.io.*;

/**
 * Demonstrates the use of the 'transient' keyword in Java Serialization.
 * When an object is serialized, fields marked as {@code transient} are not
 * included in the byte stream. Upon deserialization, these fields are
 * initialized to their default Java values (e.g., {@code null} for objects).
 */
public class TransientExample {
    public static void main(String[] args) {
        // 1. Create a serializableClass.User object
        User user = new User("john_doe", "secret123");
        System.out.println("Original serializableClass.User: " + user);

        String filename = "user.ser"; // File to store the serialized object

        // 2. Serialize the serializableClass.User object
        // The try-with-resources statement ensures that the streams are closed automatically.
        System.out.println("\n--- Attempting Serialization ---");
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            System.out.println("Serializing user object to " + filename + "...");
            oos.writeObject(user); // The 'password' field (transient) will be skipped.
            System.out.println("serializableClass.User object serialized successfully.");

        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }

        // 3. Deserialize the serializableClass.User object
        User deserializedUser = null;
        System.out.println("\n--- Attempting Deserialization ---");
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            System.out.println("Deserializing user object from " + filename + "...");
            // The readObject() method reads the byte stream and reconstructs the object.
            // It requires a cast to the original object type.
            deserializedUser = (User) ois.readObject();
            System.out.println("serializableClass.User object deserialized successfully.");

        } catch (IOException | ClassNotFoundException e) {
            // ClassNotFoundException can occur if the serializableClass.User class is not available
            // in the classpath during deserialization, or if serialVersionUID mismatch occurs
            // with an incompatible class change.
            System.err.println("Error during deserialization: " + e.getMessage());
            e.printStackTrace();
        }

        // 4. Print the deserialized serializableClass.User object
        if (deserializedUser != null) {
            System.out.println("\nDeserialized serializableClass.User: " + deserializedUser);
            // You will observe that the 'password' field in the deserializedUser is null
            // because it was marked as transient.
            // System.out.println("Deserialized Username: " + deserializedUser.getUsername());
            // System.out.println("Deserialized Password (expected null or placeholder): " + deserializedUser.getPassword());
        } else {
            System.out.println("\nDeserialization failed, deserializedUser is null.");
        }

        // Optional: Clean up the serialized file
        // File file = new File(filename);
        // if (file.exists()) {
        //     file.delete();
        // }
    }
}