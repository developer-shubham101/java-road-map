**Conceptual Questions (Transparent & Immutable Data Classes)**

1.  **What do you understand by an "immutable data class"? What are its key characteristics?**
    *   **Answer Focus:** State cannot change after creation, all fields are typically final, no mutator (setter) methods.

2.  **Why is immutability beneficial in software development? Can you list some advantages?**
    *   **Answer Focus:** Thread safety, predictability, easier reasoning, cacheability, usability as map keys/set elements, reduced need for defensive copies.

3.  **What does it mean for a data class to be "transparent"? How is transparency typically achieved?**
    *   **Answer Focus:** State is readily and clearly accessible. Achieved via public accessors (getters), and well-defined `equals()`, `hashCode()`, and `toString()` methods based on all state components.

4.  **Can a class be immutable but not transparent? Can it be transparent but not immutable? Explain with potential examples.**
    *   **Answer Focus:**
        *   *Immutable but not transparent:* Yes (e.g., an immutable class that doesn't expose its state via getters or has a poor `toString()`).
        *   *Transparent but not immutable:* Yes (e.g., a regular JavaBean with getters and setters, and proper `equals/hashCode/toString`).

5.  **How does the concept of "value objects" relate to transparent and immutable data classes?**
    *   **Answer Focus:** Value objects are often implemented as transparent and immutable data classes. Their equality is based on their state (value), not identity.

6.  **Before Java 16, how would you typically implement a transparent and immutable data class in Java? What boilerplate code was involved?**
    *   **Answer Focus:** Declaring fields `final`, writing a constructor, writing getters for each field, and manually implementing `equals()`, `hashCode()`, and `toString()`.

**Java `record` Specific Questions**

7.  **What is a Java `record`? What problem does it solve?**
    *   **Answer Focus:** Special concise class for creating transparent and immutable data carriers. Solves the boilerplate problem associated with traditional data classes.

8.  **When you declare a Java `record`, what members does the compiler automatically generate for you?**
    *   **Answer Focus:** Private final fields, public accessor methods (named after components), canonical constructor, `equals()`, `hashCode()`, and `toString()` implementations.

9.  **How do you define the components (fields) of a `record`?**
    *   **Answer Focus:** In the record header, within parentheses after the record name (e.g., `record Point(int x, int y) {}`).

10. **Are the fields of a Java `record` mutable or immutable by default? Explain.**
    *   **Answer Focus:** Immutable. The components declared in the header are implicitly `private` and `final`.

11. **What is the naming convention for the accessor methods generated for record components? How does it differ from traditional JavaBean getters?**
    *   **Answer Focus:** Accessor methods have the same name as the component (e.g., `x()` for component `x`), not `getX()`.

12. **Can a Java `record` extend another class? Can it implement interfaces?**
    *   **Answer Focus:** Cannot extend another class (implicitly extends `java.lang.Record`). Can implement interfaces.

13. **Can you add additional instance fields to a Java `record` beyond those declared in its header?**
    *   **Answer Focus:** No, you cannot declare additional *instance* fields. You can declare `static` fields.

14. **Can you add custom methods (static or instance) to a Java `record`?**
    *   **Answer Focus:** Yes.

15. **What is a "canonical constructor" in the context of a Java `record`? Can you override it?**
    *   **Answer Focus:** The constructor whose signature matches the record header. Yes, you can provide your own implementation, often for validation or normalization.

16. **What is a "compact constructor" for a Java `record`? What is its purpose and syntax?**
    *   **Answer Focus:** A constructor with no formal parameter list, declared within the record body (`public MyRecord { /* logic */ }`). Used for validation or normalization of constructor arguments before they are assigned to fields.

17. **If a record component is a mutable object type (e.g., `List<String>`), is the record still truly immutable? What precautions might be needed?**
    *   **Answer Focus:** The record itself (its fields) is immutable (references are final). However, if a component is a mutable type, the *object it refers to* can be changed. For deep immutability, defensive copies are needed in the constructor and accessor methods, or use immutable collections. Records don't automatically do this for mutable component types.

18. **When would you choose to use a Java `record` over a traditional class? When might a traditional class still be more appropriate?**
    *   **Answer Focus:**
        *   *Record:* Ideal for simple data aggregates, DTOs, value objects where conciseness and guaranteed immutability/transparency are key.
        *   *Traditional Class:* When you need mutable state, inheritance from a specific superclass, more complex encapsulation (e.g., some fields not directly part of the "data" representation), or more control over field access than simple getters.

19. **How do records interact with serialization? Are they `Serializable` by default?**
    *   **Answer Focus:** Records are implicitly serializable if all their component types are serializable. They have a well-defined serialized form. The `serialVersionUID` for a record is `0L` unless explicitly specified.

**Scenario-Based/Comparison Questions**

20. **You need to represent a 2D coordinate (x, y). Show how you would do this using a traditional immutable class and then using a Java `record`. Highlight the differences.**
    *   **Answer Focus:** Demonstrate the boilerplate reduction with records.

21. **Imagine you are designing a system that passes messages between services. These messages are simple data structures. Would a Java `record` be a good fit? Why or why not?**
    *   **Answer Focus:** Yes, excellent fit due to conciseness, immutability (good for data transfer), and clear structure.

22. **If you need to perform validation on the arguments passed to create a data object (e.g., an age must be positive), how would you achieve this with a Java `record`?**
    *   **Answer Focus:** Using a compact constructor or by providing a custom implementation of the canonical constructor.

These questions should give a good overview of a candidate's understanding of these important Java concepts.