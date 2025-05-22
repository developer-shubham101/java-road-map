package ImmutabilityClass;

public class RecordDemo {
    public static void main(String[] args) {

        TraditionalPoint traditionalImmutabilityClass = new TraditionalPoint(10, 20);

        // 1. Instantiation using the canonical constructor
        PointRecord p1 = new PointRecord(10, 20);
        PointRecord p2 = new PointRecord(10, 20);
        PointRecord p3 = new PointRecord(5, 15);

        // 2. Accessing components (transparency)
        System.out.println("p1.x: " + p1.x()); // Accessor method
        System.out.println("p1.y: " + p1.y());

        // 3. Immutability - cannot change state
        // p1.x = 30; // COMPILE ERROR: x is final
        // p1.setX(30); // COMPILE ERROR: no setX() method

        // 4. Transparent toString()
        System.out.println("p1: " + p1); // Output: ImmutabilityClass.PointRecord[x=10, y=20]
        System.out.println("p3: " + p3); // Output: ImmutabilityClass.PointRecord[x=5, y=15]

        // 5. Transparent equals()
        System.out.println("p1.equals(p2): " + p1.equals(p2)); // Output: true
        System.out.println("p1.equals(p3): " + p1.equals(p3)); // Output: false

        // 6. Transparent hashCode()
        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode()); // Same as p1
        System.out.println("p3.hashCode(): " + p3.hashCode()); // Different from p1/p2



        UserRecord user1 = new UserRecord(1, "Alice", "Alice@Example.COM");
        System.out.println(user1); // UserRecord[id=1, username=Alice, email=alice@example.com] (email normalized)
        System.out.println(user1.getDisplayInfo());

        UserRecord guest = UserRecord.createGuest(99);
        System.out.println(guest); // UserRecord[id=99, username=guest-99, email=null]
    }
}