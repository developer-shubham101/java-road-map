package ImmutabilityClass;

import java.util.Objects;

// A traditional Java class designed to be a transparent and immutable data carrier for a Point.
public final class TraditionalPoint { // final to prevent subclassing which could break immutability

    private final int x; // final for immutability
    private final int y; // final for immutability

    // Constructor to initialize the immutable state
    public TraditionalPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Accessor (getter) for the x-coordinate - part of transparency
    public int getX() {
        return x;
    }

    // Accessor (getter) for the y-coordinate - part of transparency
    public int getY() {
        return y;
    }

    // equals() method based on state - crucial for transparency and value semantics
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraditionalPoint that = (TraditionalPoint) o;
        return x == that.x && y == that.y;
    }

    // hashCode() method based on state - crucial for transparency and use in collections
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    // toString() method for clear representation - part of transparency
    @Override
    public String toString() {
        return "TraditionalPoint[" +
               "x=" + x + ", " +
               "y=" + y + ']';
    }
}