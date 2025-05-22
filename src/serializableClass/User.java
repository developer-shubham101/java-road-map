package serializableClass;

import java.io.Serializable;

/**
 * Represents a user with a username and password.
 * This class implements {@link Serializable} to allow its state (instances)
 * to be converted into a byte stream and later restored.
 * <p>
 * The 'password' field is marked as {@code transient} to prevent it from
 * being included in the serialized form, which is a common practice for
 * sensitive information or fields that shouldn't persist.
 */
class User implements Serializable {

    /**
     * The serialVersionUID is a universal version identifier for a Serializable class.
     * During deserialization, the JVM checks this ID to ensure that the loaded class
     * for the object matches the serialized object's class version.
     * It's crucial for version control of serialized objects.
     * Best practice is to explicitly declare it.
     */
    private static final long serialVersionUID = 1L; // Or any unique long value

    private final String username; // This field will be part of the serialized state.

    /**
     * The 'password' field is marked as transient.
     * This means it will NOT be included in the serialized byte stream.
     * Upon deserialization, transient fields are initialized to their default
     * value (e.g., null for objects, 0 for int, false for boolean).
     * This is useful for:
     * 1. Sensitive data (like passwords, though encryption in writeObject/readObject is better for true security).
     * 2. Fields that can be recalculated or are environment-specific.
     * 3. Fields whose types are not serializable.
     */
    private final transient String password;

    /**
     * Constructs a new User.
     *
     * @param username The username.
     * @param password The password (will not be serialized).
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Provides a string representation of the User object.
     * Note how the password will appear before serialization and after deserialization.
     *
     * @return A string containing the username and password.
     */
    @Override
    public String toString() {
        // Demonstrates that 'password' is null after deserialization if not handled otherwise.
        return "Username: " + username + ", Password: " + (password == null ? "[TRANSIENT - NOT SERIALIZED]" : password);
    }

    // Optional: Getter for username for external access if needed
    public String getUsername() {
        return username;
    }

    // Optional: Getter for password (primarily for pre-serialization state or if re-populated)
    // Be cautious about exposing transient sensitive data directly.
    public String getPassword() {
        return password;
    }

    // --- Optional: Custom Serialization Logic (Advanced) ---
    // If you needed to encrypt the password or handle transient fields in a special way:
    /*
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Writes non-transient fields (username)
        // Example: Encrypt password and write it manually
        // String encryptedPassword = encrypt(this.password);
        // oos.writeObject(encryptedPassword);
        System.out.println("Custom writeObject called. Username written by default. Password was transient.");
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Reads non-transient fields (username)
        // Example: Read encrypted password and decrypt it
        // String encryptedPassword = (String) ois.readObject();
        // this.password = decrypt(encryptedPassword); // Need to make password non-final for this
        System.out.println("Custom readObject called. Username read by default. Password remains null (or handle here).");
        // Note: If 'password' field in User class was not final, you could assign to it here.
        // As it's final and transient, it will be null unless special handling (not shown for simplicity of transient demo).
    }
    */
}
