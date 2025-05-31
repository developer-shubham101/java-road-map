In Java, **heap memory** and **stack memory** are two primary areas of memory management, each serving different purposes. Understanding the differences between them is key to efficient memory usage, performance optimization, and preventing memory-related issues.

### 1. Stack Memory
The **stack memory** in Java is used for:
- Storing **primitive data types** (like `int`, `double`, `boolean`, etc.).
- Storing **references to objects** in the heap (not the objects themselves).
- Managing **method execution** (local variables, method calls, and control flow).

#### Key Characteristics of Stack Memory
- **LIFO (Last In, First Out)** Structure: Stack operates in a LIFO manner, which helps in method calls and returns.
- **Size**: Limited in size and varies per thread. Each thread has its own stack memory.
- **Lifetime**: When a method is called, a new block is created on the stack for that method’s execution. After the method execution completes, its block is removed (popped) from the stack, releasing memory.
- **Fast Access**: Accessing stack memory is generally faster since it uses direct memory allocation.
- **Thread-Safe**: Each thread has its own stack memory, so it does not require synchronization.
- **Memory Limitations**: Due to its limited size, stack memory can throw a `StackOverflowError` if there are too many recursive calls or deeply nested methods.

#### Example Usage in Stack Memory
```java
public class StackExample {
    public static void main(String[] args) {
        int num = 10; // num is a local variable stored in stack
        printNum(num);
    }

    public static void printNum(int value) {
        int result = value * 2; // result is also stored in stack
        System.out.println(result);
    }
}
```

In this example:
- `num`, `value`, and `result` are local variables stored in stack memory.
- When `printNum` is called, a new stack frame is created, and `value` is stored within that frame.
- After `printNum` finishes execution, its stack frame is removed.

### 2. Heap Memory
The **heap memory** in Java is used for:
- Storing **all Java objects** created with `new`.
- Managing memory for the lifetime of the application.
- Enabling **dynamic memory allocation** for objects.

#### Key Characteristics of Heap Memory
- **Global Access**: Objects stored in the heap can be accessed from anywhere in the application.
- **Size**: Typically larger than stack memory and can be adjusted with JVM parameters (e.g., `-Xmx` to set max heap size).
- **Lifetime**: Objects in the heap are garbage-collected once they are no longer referenced.
- **Slower Access**: Accessing objects in the heap is slower compared to stack memory because it involves dynamic memory management.
- **Shared Among Threads**: Heap memory is shared across all threads, so synchronization is necessary when multiple threads access the same object.
- **OutOfMemoryError**: If the heap is full and no memory can be freed, Java throws an `OutOfMemoryError`.

#### Example Usage in Heap Memory
```java
public class HeapExample {
    public static void main(String[] args) {
        Person person = new Person("Alice"); // person object is stored in heap
        System.out.println(person.getName());
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

In this example:
- The `person` object is created on the heap, and a reference to it is stored in the `main` method’s stack frame.
- `name` (a `String`) is also an object, so it is stored in the heap memory.

### Major Differences Between Stack and Heap Memory

| Feature                   | Stack Memory                                        | Heap Memory                                           |
|---------------------------|-----------------------------------------------------|-------------------------------------------------------|
| **Usage**                 | Stores local variables and method call frames       | Stores all Java objects                               |
| **Access Speed**          | Faster, due to direct memory allocation             | Slower, involves dynamic allocation and deallocation  |
| **Memory Allocation**     | Fixed size per thread                               | Typically larger, can be controlled with JVM options  |
| **Scope**                 | Method-specific (LIFO for each method call)         | Global, shared across all threads                     |
| **Thread Safety**         | Each thread has its own stack                       | Shared, synchronization required for thread safety    |
| **Lifetime of Variables** | Managed automatically (pop on method completion)    | Managed by garbage collection                         |
| **Error Type**            | `StackOverflowError` when stack limit is exceeded   | `OutOfMemoryError` when heap is full                  |
| **Structure**             | LIFO structure for method calls and local variables | No specific structure, dynamic memory allocation      |

### Garbage Collection in Heap
Java uses **garbage collection** to manage heap memory. Objects are automatically cleaned up by the garbage collector when they are no longer referenced, which helps prevent memory leaks and reduces the risk of running out of memory.

### Example Showing Stack and Heap Together
Here’s a more comprehensive example to show how stack and heap interact:

```java
public class MemoryExample {
    public static void main(String[] args) {
        MemoryExample example = new MemoryExample();
        example.start();
    }

    public void start() {
        int value = 10;             // stored in stack
        Person person = new Person("Bob"); // person object is stored in heap
        process(person, value);
    }

    public void process(Person person, int value) {
        value = value * 2;          // new value stored in stack
        person.setName("Bob Jr.");  // updates the person object in heap
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

### Explanation
1. **Heap**: The `Person` object (`person`) is created in the heap when `new Person("Bob")` is called. Its reference is stored in the `start` method’s stack frame.
2. **Stack**:
    - The `start` method has a stack frame with local variables `value` and `person` (a reference to the `Person` object in the heap).
    - When `process` is called, a new stack frame is created, which contains `person` and `value` (method parameters).
3. **Method Calls**:
    - Each method call pushes a new frame onto the stack.
    - When the method completes, its stack frame is popped off, releasing the memory for its local variables.

### Summary
- **Stack memory** is limited, thread-safe, and faster for method execution and local variable storage. It is ideal for storing primitive values and references.
- **Heap memory** is larger, shared, and slower, suitable for dynamically created objects that persist beyond a single method call.

Both stack and heap memory management are critical in Java to maintain efficient performance, control memory usage, and prevent issues such as `OutOfMemoryError` and `StackOverflowError`.