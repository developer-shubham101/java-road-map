Java 8, released in March 2014, is one of the most significant updates in the history of Java, introducing major changes that revolutionized the way developers write code. These new features made Java more expressive, concise, and capable of handling modern programming paradigms like functional programming.

Here’s an overview of the key features introduced in **Java 8**:

### 1. **Lambda Expressions**
Lambda expressions are one of the most anticipated features in Java 8. They enable you to treat functionality as a method argument or treat code as data, providing a way to write more concise and flexible code, especially in scenarios involving functional programming.

#### **Syntax:**
```java
(parameters) -> expression
(parameters) -> { statements; }
```

#### **Example:**
```java
// Without Lambda (Anonymous class implementation)
Runnable runnable = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running without Lambda!");
    }
};

// With Lambda
Runnable lambdaRunnable = () -> System.out.println("Running with Lambda!");

lambdaRunnable.run();
```

### 2. **Functional Interfaces**
A functional interface is an interface with exactly **one abstract method**. Java 8 introduced a new package `java.util.function` that provides several built-in functional interfaces like `Predicate`, `Function`, `Consumer`, `Supplier`, etc.

#### **Example:**
```java
@FunctionalInterface
interface MyFunctionalInterface {
    void myMethod();
}

MyFunctionalInterface example = () -> System.out.println("Hello, Functional Interface!");
example.myMethod();
```

### 3. **Stream API**
The **Stream API** is one of the most powerful features of Java 8, enabling functional-style operations on collections and other data sources. It supports operations like filtering, mapping, and reducing, making it easier to work with large datasets in a declarative and parallelized manner.

#### **Example:**
```java
import java.util.Arrays;
import java.util.List;

public class StreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Using Stream API to filter even numbers and print them
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .forEach(System.out::println);
    }
}
```

### 4. **Default Methods**
Java 8 introduced **default methods** (also known as defender methods) in interfaces. This allows developers to add method implementations directly in the interface, maintaining backward compatibility with older versions of the interface.

#### **Example:**
```java
interface Vehicle {
    // Default method in interface
    default void start() {
        System.out.println("Vehicle is starting...");
    }

    void move();
}

class Car implements Vehicle {
    @Override
    public void move() {
        System.out.println("Car is moving...");
    }
}

public class DefaultMethodExample {
    public static void main(String[] args) {
        Car car = new Car();
        car.start(); // Calling default method
        car.move();  // Calling overridden method
    }
}
```

### 5. **Optional Class**
The `Optional` class is a container object introduced in Java 8 to handle the absence of values. It helps avoid null pointer exceptions by explicitly specifying that a value might be present or absent.

#### **Example:**
```java
import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        // Creating an Optional object
        Optional<String> optional = Optional.ofNullable(null);

        // Checking and retrieving the value
        optional.ifPresentOrElse(
            value -> System.out.println("Value: " + value),
            () -> System.out.println("No value present")
        );
    }
}
```

### 6. **New Date and Time API (java.time)**
Java 8 introduced a new, modern **Date and Time API** in the `java.time` package to address the flaws of the old `java.util.Date` and `java.util.Calendar`. This API is much more intuitive, immutable, and thread-safe.

#### **Example:**
```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeExample {
    public static void main(String[] args) {
        // Get current date and time
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();

        // Formatting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Current Date: " + date);
        System.out.println("Formatted Date: " + dateTime.format(formatter));
    }
}
```

### 7. **Method References**
Method references are a shorthand notation for calling methods using the `::` operator. They provide an easy way to pass method references in places where a lambda expression would normally be used.

#### **Example:**
```java
import java.util.Arrays;
import java.util.List;

public class MethodReferenceExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Doe", "Alice", "Bob");

        // Using method reference to print names
        names.forEach(System.out::println);
    }
}
```

### 8. **Collectors (for Stream API)**
The `Collectors` class provides a set of built-in **collector implementations** used in conjunction with the Stream API to accumulate elements into collections, compute summary statistics, etc.

#### **Example:**
```java
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class CollectorsExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Doe", "Alice", "Bob");

        // Collect names that start with 'A' into a List
        List<String> result = names.stream()
                                   .filter(name -> name.startsWith("A"))
                                   .collect(Collectors.toList());

        System.out.println(result); // Output: [Alice]
    }
}
```

### 9. **Parallel Streams**
Java 8 allows you to create **parallel streams** to execute stream operations in parallel, making it easier to perform parallel processing of large data sets.

#### **Example:**
```java
import java.util.stream.IntStream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        // Using parallel stream to calculate sum
        int sum = IntStream.range(1, 1000)
                           .parallel()
                           .sum();

        System.out.println("Sum: " + sum);
    }
}
```

### 10. **Static Methods in Interfaces**
Java 8 allows defining static methods within interfaces. These methods can be called independently of any instance of the implementing class.

#### **Example:**
```java
interface MathOperations {
    static int add(int a, int b) {
        return a + b;
    }
}

public class StaticMethodInterfaceExample {
    public static void main(String[] args) {
        int result = MathOperations.add(5, 3); // Call static method in interface
        System.out.println("Result: " + result);
    }
}
```

### Summary of Key Features in Java 8:
1. **Lambda Expressions**: Simplified syntax for functional programming.
2. **Functional Interfaces**: Interfaces with a single abstract method.
3. **Stream API**: For processing sequences of elements in a functional manner.
4. **Default Methods**: Interface methods with default implementations.
5. **Optional Class**: A container for handling null values more gracefully.
6. **New Date and Time API**: A more intuitive and powerful API for date/time handling.
7. **Method References**: Shorthand for calling methods in functional-style code.
8. **Collectors**: For collecting stream results into collections or other data types.
9. **Parallel Streams**: Easy parallelism for processing data streams.
10. **Static Methods in Interfaces**: Ability to define static methods in interfaces.

These Java 8 features brought functional programming capabilities to Java, modernized the APIs, and improved both developer productivity and application performance.