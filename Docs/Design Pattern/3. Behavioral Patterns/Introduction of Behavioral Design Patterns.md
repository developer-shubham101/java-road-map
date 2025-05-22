## Full Introduction of Behavioral Design Patterns

### What are Behavioral Design Patterns?

**Behavioral Design Patterns** are a category of design patterns that are specifically concerned with **algorithms and the assignment of responsibilities between objects**. They focus on how objects interact and communicate with each other to accomplish tasks. These patterns describe patterns of communication between objects, helping to organize and manage these interactions in a flexible and efficient manner.

Unlike creational patterns (which deal with object instantiation) and structural patterns (which deal with object composition), behavioral patterns characterize complex control flow that's difficult to follow at run-time. They shift your focus away from flow of control to let you concentrate on the way objects are interconnected.

### Why Do We Need Them? The Challenges of Object Interaction and Responsibility

As software systems evolve, managing the interactions, collaborations, and responsibilities among objects becomes increasingly complex. Without careful design, systems can suffer from:

1.  **Tight Coupling Between Objects:** Objects might become too dependent on each other's specific implementations or interfaces. Changes in one object can then ripple through many other objects, making the system brittle and hard to maintain.
2.  **Complex Communication Protocols:** The way objects send messages and data to each other can become convoluted and hard to understand or modify.
3.  **Scattered or Tangled Algorithms:** Logic for a particular behavior might be spread across multiple classes, or a single class might become bloated with too many varied responsibilities.
4.  **Inflexibility in Varying Algorithms:** If an algorithm needs to change or if multiple variations of an algorithm are required, hardcoding them directly into a class makes it difficult to switch or extend them without modifying the class.
5.  **Difficulty in Managing Object State:** Objects often have internal states that dictate their behavior. Managing state transitions and the associated behaviors can become complex.
6.  **Repetitive Boilerplate Code:** Common algorithmic structures or iteration logic might be repeated in many places, leading to code duplication.
7.  **Lack of Undo/Redo Functionality:** Implementing features like undo/redo or transaction management requires careful handling of operations and their inverse effects.
8.  **Inability to Traverse Collections Uniformly:** Different collection types might require different ways to access their elements, complicating client code.
9.  **Need for Operations on Object Structures:** Sometimes, you need to perform operations on all elements of a complex object structure (like a tree) without cluttering the classes of those elements with these operations.

### How Behavioral Patterns Help

Behavioral patterns provide elegant solutions to these challenges by:

1.  **Encapsulating Behavior:** They often encapsulate behavior in separate objects, making it easier to switch, extend, or reuse behaviors independently (e.g., Strategy, Command).
2.  **Defining Communication Protocols:** They establish clear protocols for how objects communicate, reducing direct dependencies and simplifying interactions (e.g., Mediator, Observer).
3.  **Assigning Responsibilities Clearly:** They help distribute responsibilities among objects in a well-defined way, leading to more cohesive classes (e.g., Chain of Responsibility).
4.  **Managing Algorithmic Variation:** They allow algorithms to vary independently from the clients that use them (e.g., Strategy, Template Method).
5.  **Handling State Transitions:** They provide structured ways to manage an object's state and the behavior associated with each state (e.g., State).
6.  **Simplifying Iteration:** They provide a uniform way to access elements in a collection without exposing its underlying representation (e.g., Iterator).
7.  **Supporting Operations on Structures:** They allow new operations to be added to a hierarchy of objects without modifying the classes of those objects (e.g., Visitor).
8.  **Facilitating Undoable Operations:** They help in representing operations as objects, which can support undo/redo, queuing, and logging (e.g., Command).
9.  **Capturing and Restoring State:** They allow an object's internal state to be saved and restored without violating encapsulation (e.g., Memento).
10. **Interpreting Languages:** They provide a way to define a grammar for a simple language and interpret sentences in that language (e.g., Interpreter).

### General Characteristics:

*   **Focus on Interactions and Responsibilities:** They are primarily concerned with how objects collaborate and distribute responsibilities.
*   **Increased Flexibility:** They make interactions more flexible, allowing changes in how objects are connected or how responsibilities are assigned without major system overhauls.
*   **Class vs. Object Patterns:**
    *   **Class Behavioral Patterns:** Use inheritance to distribute behavior between classes (e.g., Template Method).
    *   **Object Behavioral Patterns:** Use object composition or delegation to assign behaviors and manage interactions between objects. Most behavioral patterns are object patterns (e.g., Strategy, Observer, Command, State, Mediator, Iterator, Visitor, Chain of Responsibility, Memento).
*   **Promote Loose Coupling:** Many behavioral patterns aim to reduce coupling between objects.

### When to Consider Using Behavioral Patterns:

You should consider using Behavioral Design Patterns when:

*   You need to define how a group of objects cooperate to perform a task that no single object can carry out alone.
*   You want to make algorithms or parts of algorithms interchangeable or extensible.
*   You need to manage complex control flows or interactions between objects.
*   You want to avoid tight coupling between senders and receivers of requests.
*   You need to manage an object's behavior as its internal state changes.
*   You need to provide a standard way to traverse through the elements of a collection.
*   You want to encapsulate requests as objects.
*   You need to define a grammatical representation for a language and an interpreter to deal with this grammar.
*   You want to implement undo/redo or transactional capabilities.
*   You need to add new operations to an existing class hierarchy without changing the classes themselves.

---

## List of All Behavioral Design Patterns (GoF)

The "Gang of Four" (GoF) book, "Design Patterns: Elements of Reusable Object-Oriented Software," identifies the following primary behavioral patterns:

1.  **Chain of Responsibility Pattern**
    *   **Intent:** Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.
    *   **Use When:**
        *   More than one object may handle a request, and the handler isn't known beforehand. The handler should be ascertained automatically.
        *   You want to issue a request to one of several objects without specifying the receiver explicitly.
        *   The set of objects that can handle a request should be specified dynamically.

2.  **Command Pattern (Action, Transaction)**
    *   **Intent:** Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.
    *   **Use When:**
        *   You want to parameterize objects by an action to perform.
        *   You want to specify, queue, and execute requests at different times.
        *   You need to support undo, logging, or transactions.
        *   You want to structure a system around high-level operations built on primitive operations.

3.  **Interpreter Pattern**
    *   **Intent:** Given a language, define a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.
    *   **Use When:**
        *   The grammar is simple and well-defined.
        *   Efficiency is not a critical concern (interpreters are usually slower than directly executing code).
        *   You have many instances of similar problems that can be represented as sentences in a simple language.

4.  **Iterator Pattern (Cursor)**
    *   **Intent:** Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.
    *   **Use When:**
        *   You want to access an aggregate object's contents without exposing its internal structure.
        *   You need to support multiple traversals of aggregate objects.
        *   You want to provide a uniform interface for traversing different aggregate structures (polymorphic iteration).

5.  **Mediator Pattern**
    *   **Intent:** Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.
    *   **Use When:**
        *   A set of objects communicate in well-defined but complex ways. The resulting interdependencies are unstructured and difficult to understand.
        *   Reusing an object is difficult because it refers to and communicates with many other objects.
        *   A behavior that's distributed between several classes should be customizable without a lot of subclassing.

6.  **Memento Pattern (Token)**
    *   **Intent:** Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later.
    *   **Use When:**
        *   You need to save a snapshot of an object's state (or part of it) so that it can be restored later.
        *   A direct interface to obtaining the state would expose implementation details and break the object's encapsulation.

7.  **Observer Pattern (Dependents, Publish-Subscribe)**
    *   **Intent:** Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
    *   **Use When:**
        *   An abstraction has two aspects, one dependent on the other. Encapsulating these aspects in separate objects lets you vary and reuse them independently.
        *   A change to one object requires changing others, and you don't know how many objects need to be changed.
        *   An object should be able to notify other objects without making assumptions about who these objects are. In other words, you don't want these objects tightly coupled.

8.  **State Pattern (Objects for States)**
    *   **Intent:** Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.
    *   **Use When:**
        *   An object's behavior depends on its state, and it must change its behavior at run-time depending on that state.
        *   Operations have large, multipart conditional statements that depend on the object's state. This state is usually represented by one or more enumerated constants. Often, several operations will contain this same conditional structure. The State pattern puts each branch of the conditional in a separate class.

9.  **Strategy Pattern (Policy)**
    *   **Intent:** Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.
    *   **Use When:**
        *   Many related classes differ only in their behavior. Strategies provide a way to configure a class with one of many behaviors.
        *   You need different variants of an algorithm. For example, you might define algorithms reflecting different space/time trade-offs.
        *   An algorithm uses data that clients shouldn't know about. Use the Strategy pattern to avoid exposing complex, algorithm-specific data structures.
        *   A class defines many behaviors, and these appear as multiple conditional statements in its operations. Instead of many conditionals, move related conditional branches into their own Strategy class.

10. **Template Method Pattern**
    *   **Intent:** Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.
    *   **Use When:**
        *   You want to implement the invariant parts of an algorithm once and leave it up to subclasses to implement the behavior that can vary.
        *   You need to control subclass extensions. You can define a template method that calls "hook" operations at specific points, thereby permitting extensions only at those points.
        *   Common behavior among subclasses should be factored and localized in a common class to avoid code duplication.

11. **Visitor Pattern**
    *   **Intent:** Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.
    *   **Use When:**
        *   An object structure contains many classes of objects with differing interfaces, and you want to perform operations on these objects that depend on their concrete classes.
        *   You need to perform many distinct and unrelated operations on objects in an object structure, and you want to avoid "polluting" their classes with these operations. Visitor lets you keep related operations together by defining them in one class.
        *   The classes defining the object structure rarely change, but you often want to define new operations over the structure.

---

Behavioral patterns are crucial for creating flexible, maintainable, and well-organized systems by effectively managing object interactions and responsibilities. They provide a rich set of tools for handling complex control flows and collaborations in object-oriented design.