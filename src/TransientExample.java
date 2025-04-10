import java.io.*;

class User implements Serializable {
    private final String username;
    private final transient String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + password;
    }
}

public class TransientExample {
    public static void main(String[] args) {
        User user = new User("john_doe", "secret123");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.ser"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.ser"))) {

            oos.writeObject(user);

            User deserializedUser = (User) ois.readObject();
            System.out.println("Deserialized User: " + deserializedUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}