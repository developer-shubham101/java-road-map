Java has undergone significant changes from **Java 9** to **Java 22+**, with each version introducing new features, performance improvements, and API updates. Here’s a comprehensive overview of the **essential features** introduced from **Java 9** to **Java 22**.

### **Java 9 (Released September 2017)**

1. **Module System (Project Jigsaw)**:
   - Introduced the **Java Platform Module System (JPMS)**, which modularizes the JDK, allowing applications to be divided into smaller modules with defined dependencies.
   - Improves security and performance by allowing developers to use only the modules required by their application.
   - Example: `module-info.java` file in a module to declare dependencies.

2. **JShell (Interactive REPL)**:
   - Provides an interactive **Read-Eval-Print Loop (REPL)** tool for quickly testing snippets of code without the need for compiling and running entire classes.

3. **HTTP/2 Client (Incubator)**:
   - Introduced the HTTP/2 client for sending HTTP requests, replacing the old `HttpURLConnection` class.

4. **Private Methods in Interfaces**:
   - Allows **private methods in interfaces**, enabling code reuse between default methods.

5. **Enhanced @Deprecated Annotation**:
   - The `@Deprecated` annotation was enhanced with the ability to add reasons for deprecation and provide alternatives via `forRemoval` and `since`.

---

### **Java 10 (Released March 2018)**

1. **Local Variable Type Inference (`var`)**:
   - Introduced **type inference for local variables**, allowing developers to declare variables using `var`, which makes the code more concise.
   - Example: `var list = new ArrayList<String>();`

2. **Garbage Collection Enhancements**:
   - **Parallel Full GC for G1**: Improved G1 garbage collector by enabling full garbage collection to run in parallel.

3. **Application Class-Data Sharing (AppCDS)**:
   - Allows application classes to be placed in a **shared archive**, reducing startup time and memory footprint.

---

### **Java 11 (Released September 2018)** – **LTS Version**

1. **New HTTP Client API**:
   - Finalized the HTTP/2 client introduced in Java 9, with support for **WebSockets** and asynchronous requests.
   - Example: `HttpClient.newHttpClient().sendAsync(request, BodyHandlers.ofString())`

2. **String API Enhancements**:
   - Added methods such as `isBlank()`, `lines()`, `strip()`, `repeat()`, and `stripLeading()/stripTrailing()`.

3. **Local-Variable Syntax for Lambda Parameters**:
   - `var` can be used in **lambda expressions** to infer the type of parameters.

4. **Nest-Based Access Control**:
   - Enhanced JVM access rules, allowing classes that are logically nested within each other to access private members without needing synthetic bridge methods.

5. **Removal of Java EE and CORBA Modules**:
   - Java EE (e.g., JAX-WS, JAXB) and CORBA modules were removed from the standard JDK.

---

### **Java 12 (Released March 2019)**

1. **Switch Expressions (Preview)**:
   - Introduced **switch expressions** as a preview feature, allowing the `switch` statement to return values, thus making it more flexible and less error-prone.
   - Example:
     ```java
     var result = switch (day) {
         case MONDAY -> "Start of the week";
         case FRIDAY -> "End of the week";
         default -> "Middle of the week";
     };
     ```

2. **JVM Constants API**:
   - Introduced an API for modeling key class-file and runtime artifacts, making it easier to interact with **JVM constants**.

3. **Shenandoah Garbage Collector (Experimental)**:
   - Introduced the **Shenandoah GC**, a low-pause-time garbage collector optimized for responsiveness.

---

### **Java 13 (Released September 2019)**

1. **Text Blocks (Preview)**:
   - Introduced **text blocks** to simplify writing multiline strings, reducing the need for excessive escape sequences.
   - Example:
     ```java
     String json = """
         {
           "name": "John",
           "age": 30
         }
         """;
     ```

2. **Dynamic CDS Archives**:
   - Enabled **dynamic class data-sharing (CDS)** to improve startup performance by allowing the archive to be updated at runtime.

---

### **Java 14 (Released March 2020)**

1. **Switch Expressions (Standard Feature)**:
   - Finalized **switch expressions**, allowing them to return values and simplifying the `switch` syntax.

2. **Pattern Matching for `instanceof` (Preview)**:
   - Simplifies type-checking and casting in `instanceof` checks.
   - Example:
     ```java
     if (obj instanceof String s) {
         System.out.println(s.toUpperCase());
     }
     ```

3. **Records (Preview)**:
   - Introduced **Records**, a new type of class that is ideal for data carriers.
   - Example:
     ```java
     public record Point(int x, int y) {}
     ```

4. **Helpful NullPointerExceptions**:
   - JVM now provides **detailed messages for NullPointerExceptions**, making it easier to debug issues related to null values.

---

### **Java 15 (Released September 2020)**

1. **Text Blocks (Standard Feature)**:
   - Finalized the **text blocks** feature, providing a simpler syntax for multiline strings.

2. **Sealed Classes (Preview)**:
   - Introduced **sealed classes**, allowing developers to define which other classes or interfaces may extend or implement them.
   - Example:
     ```java
     public sealed class Shape permits Circle, Rectangle {}
     ```

3. **Hidden Classes**:
   - Allows the JVM to define **hidden classes**, which are not discoverable by other classes, useful for frameworks and proxies.

---

### **Java 16 (Released March 2021)**

1. **Records (Standard Feature)**:
   - **Records** were finalized in Java 16, providing a compact syntax for creating data-holding classes.

2. **Pattern Matching for `instanceof` (Standard Feature)**:
   - Finalized pattern matching for `instanceof`, simplifying type casting.

3. **Vector API (Incubator)**:
   - Introduced a **Vector API** to allow developers to express vector computations that reliably compile to optimal vector hardware instructions.

---

### **Java 17 (Released September 2021)** – **LTS Version**

1. **Sealed Classes (Standard Feature)**:
   - Finalized **sealed classes**, allowing developers to restrict which classes can extend a particular class.

2. **Pattern Matching for `switch` (Preview)**:
   - Extended pattern matching to the `switch` statement, allowing more expressive and powerful control over complex conditions.

3. **Foreign Function & Memory API (Incubator)**:
   - Introduced a mechanism to access foreign memory and interact with native code safely and efficiently.

---

### **Java 18 (Released March 2022)**

1. **Simple Web Server**:
   - A built-in simple **HTTP file server** was introduced for testing and local development purposes.

2. **UTF-8 by Default**:
   - Made **UTF-8** the default character set for the Java platform, ensuring consistency across different environments.

3. **Code Snippets in Java API Documentation**:
   - Added support for including **code snippets** in Javadoc comments, making it easier to document and illustrate code behavior.

---

### **Java 19 (Released September 2022)**

1. **Virtual Threads (Preview)**:
   - Introduced **virtual threads** as part of Project Loom, aimed at making concurrency simpler and more scalable by decoupling thread management from the OS.

2. **Structured Concurrency (Incubator)**:
   - Introduced **structured concurrency**, which simplifies concurrent programming by managing multiple threads in a structured way.

---

### **Java 20 (Released March 2023)**

1. **Continued Virtual Threads and Structured Concurrency (Preview)**:
   - Continued improvements to **virtual threads** and **structured concurrency** as preview features.

2. **Pattern Matching for `switch` (Second Preview)**:
   - Continued enhancements to **pattern matching** for the `switch` statement.

---

### **Java 21 (Released September 2023)** – **LTS Version**

1. **Virtual Threads (Standard Feature)**:
   - Finalized **virtual threads**, simplifying concurrency by allowing millions of threads to be managed efficiently.

2. **Pattern Matching for `switch` (Standard Feature)**:
   - Finalized **pattern matching** for the `switch` statement.

3. **Record Patterns (Standard Feature)**:
   - Allows matching of record components directly within patterns.

4. **String Templates (Preview)**:
   - Introduced **String Templates**, allowing embedding expressions directly within strings for easier formatting.

---

### **Java 22+ (Upcoming)**

While specifics of **Java 22 and beyond** are still in development, further enhancements to features like **Project Loom** (virtual threads), **Pattern Matching**, and **Foreign Function** APIs are expected, along with other tools to simplify and improve performance in cloud-native and large-scale distributed applications.

---

### **Summary of Key Innovations from Java 9–22:**

1. **Modularity (Project Jigsaw)** – Java 9
2. **Lambda Improvements and Local Type Inference (var)** – Java 10
3. **New HTTP Client API** – Java 11
4. **Switch Expressions** – Java 12-

14
5. **Text Blocks** – Java 13
6. **Records** – Java 14-16
7. **Sealed Classes** – Java 15-17
8. **Virtual Threads (Project Loom)** – Java 19-21
9. **Pattern Matching for instanceof and switch** – Java 14-21
10. **Structured Concurrency** – Java 19-21

Java 9 to 22+ has enhanced the language to make it more concise, modular, and suitable for modern cloud-native and parallel computing architectures.