## Full Introduction of Structural Design Patterns

### What are Structural Design Patterns?

**Structural Design Patterns** are a category of design patterns that focus on **how classes and objects are composed to form larger, more flexible, and efficient structures**. They simplify the design by identifying common ways to realize relationships between entities and manage their organization. The primary goal is to ensure that if one part of a system changes, the entire system doesn't need to change with it, or to allow parts of a system to work together even if they were not initially designed to do so.

These patterns explain how to assemble objects and classes into larger structures while keeping these structures adaptable and robust. They deal with relationships between entities, making it easier for these entities to collaborate and evolve independently.

### Why Do We Need Them? The Challenges of System Structure

As software systems grow in size and complexity, managing their internal structure and the relationships between their components becomes a significant challenge. Without careful design, systems can become rigid, difficult to maintain, and hard to extend. Common problems include:

1.  **Incompatible Interfaces:** Often, you need to integrate existing classes or third-party libraries whose interfaces don't match the interface your client code expects. Directly modifying these classes might be impossible or undesirable.
2.  **Extending Functionality Statically:** While inheritance is a powerful tool for extending functionality, it's a static relationship defined at compile time. If you need to add multiple, independent responsibilities to objects, or add them dynamically at runtime, inheritance can lead to a combinatorial explosion of subclasses or become too inflexible.
3.  **Complex Subsystems and Tight Coupling:** Large systems are often divided into subsystems. However, clients might still need to interact with many different classes within a subsystem, leading to tight coupling between the client and the subsystem's internal details. This makes the client complex and vulnerable to changes within the subsystem.
4.  **Managing Part-Whole Hierarchies:** Systems often need to represent objects that are compositions of other objects (e.g., a graphical user interface made of panels, buttons, and text fields). Clients should ideally be able to treat individual objects and compositions of objects uniformly.
5.  **Controlling Object Access and Creation:** Sometimes, you need to control how an object is accessed. This could be for security, to manage resource-intensive objects (e.g., creating them only when needed), or to represent objects in different address spaces (remote objects).
6.  **Balancing Abstraction and Multiple Implementations:** An abstraction (e.g., a `Window`) might have several different implementations (e.g., `XWindowImp`, `PMWindowImp`). If you use inheritance to connect the abstraction directly to its implementations, the hierarchy can become unwieldy, especially if the abstraction itself needs to be subclassed (e.g., `IconWindow`, `DialogWindow`).
7.  **Inefficiency with Numerous Fine-Grained Objects:** In some applications, you might need a very large number of small objects. Creating and managing each as a distinct entity can be highly inefficient in terms of memory and performance.

### How Structural Patterns Help

Structural patterns offer elegant and well-tested solutions to these common structural problems:

1.  **Adapting Interfaces (Adapter):** They provide a way to make incompatible interfaces work together without modifying the original source code of the involved classes. This promotes reuse of existing components.
2.  **Dynamic and Transparent Extension (Decorator):** They allow functionality to be added to objects dynamically at runtime by "wrapping" them. This offers a flexible alternative to subclassing and keeps the core object's interface unchanged.
3.  **Simplifying Complex Interfaces (Facade):** They provide a simplified, unified interface to a complex subsystem, making the subsystem easier to use and reducing coupling between clients and the subsystem's internal components.
4.  **Uniform Treatment of Objects and Compositions (Composite):** They allow clients to treat individual objects and compositions of objects uniformly through a shared interface. This simplifies client code when dealing with tree-like structures.
5.  **Controlled Access and Management (Proxy):** They provide a surrogate or placeholder for another object to control access to it, manage its lifecycle (e.g., lazy initialization for expensive objects), or add other pre/post processing behaviors.
6.  **Decoupling Abstraction from Implementation (Bridge):** They decouple an abstraction from its implementation so that the two can vary independently. This avoids a permanent binding between an abstraction and its implementation, which is particularly useful when both need to evolve through subclassing.
7.  **Efficient Sharing of Fine-Grained Objects (Flyweight):** They facilitate the efficient sharing of objects to support large numbers of fine-grained instances by separating intrinsic (sharable) state from extrinsic (context-dependent) state.

### General Characteristics:

*   **Focus on Composition:** They are primarily concerned with how classes and objects are composed to form larger, more useful structures.
*   **Class vs. Object Patterns:**
    *   **Class Structural Patterns:** Use inheritance to compose interfaces or implementations (e.g., Adapter using class inheritance).
    *   **Object Structural Patterns:** Primarily use object composition (aggregation or delegation) to combine objects and realize new functionalities at runtime (e.g., Decorator, Proxy, Composite, Bridge, Flyweight, and Adapter using object composition). Object patterns are generally more flexible.
*   **Increased Flexibility:** The resulting structures are typically more flexible and dynamic, allowing for easier modification and extension of the system.
*   **Improved Maintainability:** By clearly defining relationships and responsibilities, they contribute to more understandable and maintainable code.

### When to Consider Using Structural Patterns:

You should consider using Structural Design Patterns when:

*   You need to combine existing objects or classes into larger, more flexible structures without altering their original code significantly.
*   You need to modify the "shape" or interface of objects or classes, often at runtime, to suit a new context.
*   You are working with complex systems or subsystems and need to simplify how clients interact with them.
*   You need to manage dependencies between objects or subsystems in a way that promotes loose coupling and independent evolution.
*   You want to add responsibilities to objects dynamically rather than through static inheritance.
*   You need to represent and manage hierarchical structures of objects.
*   You need to optimize the use of system resources (e.g., memory) by sharing objects or deferring their creation.
*   You are integrating legacy systems or third-party libraries with incompatible interfaces into a new system.

---

## List of All Structural Design Patterns (GoF)

The "Gang of Four" (GoF) book, "Design Patterns: Elements of Reusable Object-Oriented Software," identifies the following primary structural patterns:

1.  **Adapter Pattern (Wrapper)**
    *   **Intent:** Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.
    *   **Use When:**
        *   You want to use an existing class, and its interface does not match the one you need.
        *   You want to create a reusable class that cooperates with unrelated or unforeseen classes, including classes that don't necessarily have compatible interfaces (object adapter).
        *   (Class Adapter) You need to use several existing subclasses, but it's impractical to adapt their interface by subclassing every one. An object adapter can adapt the interface of its parent class.

2.  **Bridge Pattern**
    *   **Intent:** Decouple an abstraction from its implementation so that the two can vary independently.
    *   **Use When:**
        *   You want to avoid a permanent binding between an abstraction and its implementation. This might be the case, for example, when the implementation must be selected or switched at run-time.
        *   Both the abstractions and their implementations can have their own independent extension hierarchies.
        *   Changes in the implementation of an abstraction should have no impact on clients; that is, their code should not have to be recompiled.
        *   You want to share an implementation among multiple objects (perhaps using reference counting), and this fact should be hidden from the client.

3.  **Composite Pattern**
    *   **Intent:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.
    *   **Use When:**
        *   You want to represent part-whole hierarchies of objects.
        *   You want clients to be able to treat individual objects and compositions of objects uniformly.

4.  **Decorator Pattern (Wrapper)**
    *   **Intent:** Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.
    *   **Use When:**
        *   You want to add responsibilities to individual objects dynamically and transparently, i.e., without affecting other objects.
        *   For responsibilities that can be withdrawn.
        *   When extension by subclassing is impractical. Sometimes a large number of independent extensions are possible and would produce an explosion of subclasses to support every combination. Or a class definition may be hidden or otherwise unavailable for subclassing.

5.  **Facade Pattern**
    *   **Intent:** Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.
    *   **Use When:**
        *   You want to provide a simple interface to a complex subsystem. Subsystems often get more complex as they evolve. A facade can provide a simple default view of the subsystem that is good enough for most clients.
        *   There are many dependencies between clients and the implementation classes of an abstraction. Introduce a facade to decouple the subsystem from clients and other subsystems, thereby promoting subsystem independence and portability.
        *   You want to layer your subsystems. Use a facade to define an entry point to each subsystem level. If subsystems are dependent, you can simplify the dependencies between them by making them communicate with each other solely through their facades.

6.  **Flyweight Pattern**
    *   **Intent:** Use sharing to support large numbers of fine-grained objects efficiently.
    *   **Use When:** An application uses a large number of objects, and:
        *   Storage costs are high because of the sheer quantity of objects.
        *   Most object state can be made extrinsic (i.e., stored or computed by clients, rather than within the Flyweight object itself).
        *   Many groups of objects may be replaced by relatively few shared objects once extrinsic state is removed.
        *   The application doesn't depend on object identity. Since flyweight objects may be shared, identity tests will return true for conceptually distinct objects.

7.  **Proxy Pattern (Surrogate)**
    *   **Intent:** Provide a surrogate or placeholder for another object to control access to it.
    *   **Use When:** You need a more versatile or sophisticated reference to an object than a simple pointer or direct reference. Common situations include:
        *   **Remote Proxy:** Represents an object in a different address space (e.g., a network server).
        *   **Virtual Proxy:** Creates expensive objects on demand (lazy initialization).
        *   **Protection Proxy (Access Proxy):** Controls access to the original object, useful when objects should have different access rights.
        *   **Smart Reference (Smart Pointer):** Performs additional actions when an object is accessed, such as reference counting, loading a persistent object into memory, or locking the real object to ensure exclusive access.

---

By understanding and applying these structural patterns, developers can build more robust, flexible, and maintainable object-oriented systems. They provide a common vocabulary and proven solutions for managing the composition and relationships between classes and objects.