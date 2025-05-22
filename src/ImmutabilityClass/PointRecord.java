package ImmutabilityClass;

// A Java Record - concise, transparent, and immutable by design.
public record PointRecord(int x, int y) {
    // That's it!
    // The compiler automatically generates:
    // 1. private final fields for x and y.
    // 2. A public canonical constructor: ImmutabilityClass.PointRecord(int x, int y)
    // 3. Public accessor methods: x() and y() (note: no 'get' prefix)
    // 4. Implementations of equals(), hashCode(), and toString() based on all components (x and y).
}