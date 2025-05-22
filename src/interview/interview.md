---

## Consolidated Technical Q&A: Java, Spring Boot & Microservices

---

### I. Core Java & OOP

**Q1: What are Java Class Loaders?**
**A1:** A Class Loader is part of the JRE responsible for dynamically loading Java classes into the JVM at runtime. It locates classes and makes them available. Key types include:
    *   **Bootstrap Class Loader:** Loads core Java libraries (`rt.jar`).
    *   **Extension Class Loader:** Loads classes from the JRE's extension directory.
    *   **System/Application Class Loader:** Loads classes from the application's classpath.

**Q2: Can we call non-static instance variables or methods from static methods? Why or why not?**
**A2:** No, not directly. Static methods belong to the class itself and don't require an object instance. Non-static members belong to specific instances (objects). To access non-static members from a static context, you need an explicit object reference.

**Q3: What is the use of static methods and variables?**
**A3:**
    *   **Memory Management:** Static members belong to the class, not individual objects. A single copy exists in memory, shared by all instances, saving memory (e.g., a common property for all objects).
    *   **Utility:** Can be called directly using the class name without creating an object, suitable for helper functions.

**Q4: Why is String immutable in Java?**
**A4:** Primarily for:
    *   **Thread Safety:** Multiple threads can access the same String object without risking inconsistent states due to modification.
    *   **Security:** Safer for sensitive data (credentials, paths) as values cannot be changed after creation.
    *   **Performance/Caching:** Enables the String Constant Pool (SCP), allowing duplicate literals to share the same object, saving memory. Hash codes are reliable for collections like HashMap.

**Q5: Why prefer using character arrays (`char[]`) over String to store passwords?**
**A5:** For security:
    *   **Mutability:** `char[]` is mutable. You can explicitly overwrite the array elements after use, clearing the sensitive data from memory sooner.
    *   **Memory Persistence:** Immutable Strings might persist in the String Pool or Heap longer, even after the reference is nulled, potentially exposed in memory dumps.

**Q6: What are the benefits of Object-Oriented Programming (OOP)?**
**A6:** OOP provides:
    *   **Modularity:** Breaks systems into smaller, self-contained objects, improving understanding and maintainability.
    *   **Reusability:** Concepts like inheritance reduce code redundancy.
    *   **Maintainability & Scalability:** Encapsulation allows modifications without affecting other parts.
    *   **Real-world Modeling:** Allows natural modeling of real-world entities.
    *   **Enhanced Structure:** Better code organization than procedural approaches.
    *   **(The Four Pillars):** Encapsulation, Inheritance, Polymorphism, Abstraction.

**Q7: What is the purpose of using `enum` in Java?**
**A7:** Enums define a fixed set of named constants, providing:
    *   **Type Safety:** Restricts variables to only the predefined constants, preventing errors.
    *   **Readability:** Uses meaningful names (e.g., `Color.RED`) instead of obscure integer/string constants.
    *   **Maintainability:** Centralizes constant definitions.

**Q8: What is the Diamond Problem in Java, and how is it addressed?**
**A8:**
    *   **Problem:** Arises when a class inherits from two classes (not allowed pre-Java 8 for classes) or implements two interfaces (Java 8+) that have methods with the same signature (especially default methods in interfaces). Ambiguity occurs if the inheriting/implementing class doesn't override the method.
    *   **Addressing (Java 8+):** Java requires the implementing class to explicitly resolve the ambiguity. The class *must* override the conflicting default method. Within the override, it can optionally choose to call a specific super-interface implementation using `InterfaceName.super.methodName();`.

**Q9: How does Java handle memory management and leaks?**
**A9:**
    *   **Garbage Collection (GC):** Java's primary automatic memory management. GC identifies and reclaims memory occupied by unreachable objects.
    *   **Memory Leak Definition:** Occurs when objects are no longer needed but are still referenced, preventing GC from reclaiming them, leading to potential `OutOfMemoryError`.
    *   **Identifying/Fixing Techniques:**
        *   **Profiling Tools:** Use memory profilers (e.g., JProfiler, YourKit, VisualVM, MAT) to analyze heap dumps, identify large objects, and find leak suspects.
        *   **Code Reviews:** Look for common patterns: unclosed resources (use try-with-resources), static collections holding references indefinitely, un-removed listeners.
        *   **Monitoring:** Track heap memory usage over time (JMX, Actuator, APM tools).
        *   **Heap Dumps:** Analyze heap dumps (`-XX:+HeapDumpOnOutOfMemoryError`).
        *   **Weak/Soft References:** Use `java.lang.ref.WeakReference` or `SoftReference` for caches where objects can be GC'd if memory is needed.

**Q10: What is Serialization? Why is it used? Challenges?**
**A10:**
    *   **Serialization:** Converting a Java object's state into a byte stream.
    *   **Deserialization:** Reconstructing the object from the byte stream.
    *   **Why Used:** Persistence (saving state), Communication (sending objects over network/messaging), Caching.
    *   **Implementation:** Implement `java.io.Serializable`, use `ObjectOutputStream`/`ObjectInputStream`.
    *   **Challenges:** Versioning (`serialVersionUID`), Security (deserialization vulnerabilities), Performance, `transient` fields, Compatibility.

**Q11: How to create a custom runtime exception?**
**A11:**
    1.  Create a class that `extends RuntimeException`.
    2.  Add constructors matching the superclass (optional).
    3.  `throw new MyCustomRuntimeException("Error details");` where needed. No mandatory `throws` declaration or `try-catch` needed (but possible).

---

### II. Java 8 & Streams

**Q12: What were the primary reasons for introducing Java 8?**
**A12:**
    *   **Embrace Functional Programming:** Introduce lambdas, method references for concise, declarative, potentially parallel code (especially for collections).
    *   **Improve Developer Productivity:** Reduce boilerplate with Streams API, Lambdas.
    *   **Address Pain Points:** Better null handling (`Optional`), improved date/time API (`java.time`).
    *   **Enhance Performance:** Leverage multi-core CPUs via parallel streams.

**Q13: What is a Functional Interface? Can it extend other interfaces?**
**A13:**
    *   An interface with exactly one abstract method (SAM). Can have multiple default/static methods.
    *   Target type for lambda expressions and method references.
    *   Yes, it can extend another interface, but the resulting interface must still have only one *total* abstract method.

**Q14: Difference between intermediate and terminal operations in Java Streams?**
**A14:**
    *   **Intermediate:** Transform/filter the stream (e.g., `filter()`, `map()`, `sorted()`). Lazy, return a new stream, chainable.
    *   **Terminal:** Produce a result or side-effect (e.g., `forEach()`, `collect()`, `reduce()`, `count()`). Trigger execution, don't return a stream.

**Q15: What's the use of the `reduce()` method in streams?**
**A15:** A terminal operation to combine stream elements into a single result using a reduction operation (e.g., sum, max, concatenation).

**Q16: How is a Lambda expression related to functional interfaces?**
**A16:** A lambda expression provides a concise implementation for the single abstract method of a functional interface. The interface is the target type; the lambda is effectively an instance of that interface.

**Q17: What enhancement was made to HashMap in Java 8 regarding collisions?**
**A17:** When the number of entries in a bucket exceeds a threshold (TREEIFY_THRESHOLD, default 8), the bucket's internal structure changes from a LinkedList to a balanced Red-Black Tree. This improves worst-case performance for operations like `get()` or `put()` in that bucket from O(n) to O(log n).

**Q18: When should you use a `Comparator`?**
**A18:** Use a `Comparator` to define custom sorting logic when:
    *   The class doesn't implement `Comparable` (e.g., third-party).
    *   You need multiple different sorting criteria for the same class.
    *   You want sorting logic separate from the class definition.

**Q19: Which collection avoids duplicates and maintains insertion order?**
**A19:** `LinkedHashSet`.

**Q20: Does `TreeSet` allow null values? Why or why not?**
**A20:** No (by default). `TreeSet` maintains sorted order, requiring elements to be compared using `compareTo()` (natural order) or a `Comparator`. Comparing with `null` throws a `NullPointerException`.

**Q21: In HashMap's `put(K key, V value)` operation, which method (`hashCode()` or `equals()`) executes first? Do both always execute?**
**A21:**
    *   `hashCode()` executes first to find the bucket index.
    *   `equals()` only executes if a potential collision occurs (the bucket is not empty and contains keys with the same hash code) to check if the key being inserted is truly identical to an existing key in that bucket.

**Q22: What causes a `ConcurrentModificationException`?**
**A22:** Modifying a collection (e.g., `ArrayList`, `HashMap`) structurally (add/remove elements) while iterating over it using its standard (fail-fast) iterator, *except* through the iterator's own `remove()` method.

**Q23: What is an `IdentityHashMap` and a `WeakHashMap`?**
**A23:**
    *   **`IdentityHashMap`:** Uses reference equality (`==`) instead of object equality (`equals()`) for comparing keys. Useful when distinct instances that are `equals()` need to be treated as separate keys.
    *   **`WeakHashMap`:** Entries use `WeakReference`s for keys. Allows keys to be garbage collected if they have no other strong references, and the corresponding entry is then removed from the map. Useful for caches or metadata storage that shouldn't prevent GC.

---

### III. Concurrency

**Q24: Difference between `Runnable` and `Callable`?**
**A24:** Both represent tasks for threads.
    *   **`Runnable`:** `void run()`; returns no result; cannot throw checked exceptions.
    *   **`Callable<V>`:** `V call() throws Exception`; returns a result of type `V`; can throw checked exceptions. Used with `ExecutorService`/`Future`.

**Q25: What happens if `start()` is called more than once on the same Thread object?**
**A25:** Throws `IllegalThreadStateException`. A thread can only be started once.

**Q26: How does `parallelStream()` enhance performance, and when use it cautiously?**
**A26:**
    *   **Enhancement:** Processes elements using multiple threads (common ForkJoinPool), potentially speeding up CPU-intensive operations on large datasets by utilizing multiple cores.
    *   **Use Cautiously:**
        *   **Small Datasets:** Overhead can make it slower than sequential.
        *   **Order-Dependent Operations:** Can be less efficient or require special handling (`forEachOrdered`).
        *   **Stateful Operations:** Lambdas should be stateless or use thread-safe mechanisms to avoid race conditions.
        *   **Blocking I/O:** Can exhaust the common pool; use dedicated executors for blocking tasks.
        *   **Splitting/Merging Cost:** Some data structures (LinkedList) split poorly. Reduction operations vary in merge cost.
        *   **Benchmarking:** Always measure performance for your specific use case.

**Q27: How to decide between parallel and sequential streams for processing a large collection?**
**A27:** Consider:
    1.  **Size of Data:** Benefits usually only for large datasets.
    2.  **Nature of Operation:** CPU-bound (good candidate) vs. I/O-bound (use dedicated executors).
    3.  **Statelessness:** Ensure operations are stateless or handle state safely.
    4.  **Splitting/Merging Cost:** Data structure (ArrayList good, LinkedList bad) and reduction cost.
    5.  **Ordering Requirements:** Sequential is simpler if strict order matters.
    6.  **Benchmarking:** Measure performance for your specific case.

**Q28: Why is the `synchronized` keyword used?**
**A28:** To control access to shared mutable resources by multiple threads, preventing race conditions. It uses an intrinsic lock (monitor) associated with an object or class. Only one thread can hold the lock at a time for a given monitor, blocking others.

---

### IV. Spring Framework Core / AOP / DI

**Q29: What is Aspect-Oriented Programming (AOP) in Spring?**
**A29:** AOP increases modularity by separating **cross-cutting concerns** (logging, security, transactions) from core business logic.
    *   **Core Concepts:**
        *   **Aspect:** Module for a cross-cutting concern (contains Advice, Pointcuts).
        *   **Join Point:** Point during execution where an aspect could apply (e.g., method execution).
        *   **Advice:** Action taken by an aspect (@Before, @After, @Around, etc.).
        *   **Pointcut:** Expression selecting join points where advice applies.
        *   **Weaving:** Applying aspects to target objects (runtime via proxies in Spring).
    *   **Benefit:** Cleaner business logic, better maintainability/reusability of cross-cutting logic.

**Q30: How to handle multiple beans of the same type during dependency injection?**
**A30:** Resolve ambiguity using:
    *   **`@Qualifier("beanName")`:** Specify the exact bean name at the injection point.
    *   **`@Primary`:** Mark one bean definition as the default choice if no qualifier is used.
    *   **Inject `List<BeanType>` or `Map<String, BeanType>`:** Inject all beans of that type.
    *   **Naming Convention:** Field/parameter name matching bean name (less explicit).

**Q31: How does Spring Boot make Dependency Injection (DI) easier than traditional Spring?**
**A31:**
    *   **Auto-configuration:** Automatically configures many common beans, reducing explicit setup.
    *   **Component Scanning:** `@SpringBootApplication` includes `@ComponentScan`, automatically discovering beans (`@Component`, `@Service`, etc.).
    *   **Starters:** Provide curated dependencies and pre-configured beans.
    *   **Convention over Configuration:** Relies on sensible defaults, reducing boilerplate.

---

### V. Spring Boot Core & Features

**Q32: What is the primary role of Spring Boot Auto-configuration? How does it work internally?**
**A32:**
    *   **Role:** Automatically configures the Spring application based on JAR dependencies found on the classpath, reducing manual configuration. Triggered by `@EnableAutoConfiguration` (via `@SpringBootApplication`).
    *   **Internal Mechanism:**
        1.  Scans for `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` (newer) or `META-INF/spring.factories` (older) files in JARs.
        2.  These files list potential `@Configuration` classes.
        3.  Each auto-configuration class is evaluated using **Conditional Annotations** (`@ConditionalOnClass`, `@ConditionalOnProperty`, `@ConditionalOnMissingBean`, etc.).
        4.  If conditions are met, the `@Configuration` class is activated, and its `@Bean` methods are processed.

**Q33: What happens if multiple configurations (auto or user) define the same bean?**
**A33:**
    *   **Auto-config vs. serializableClass.User-config:** Auto-configuration typically uses `@ConditionalOnMissingBean`, meaning it backs off if a user defines a bean of the same type. The user's bean takes precedence.
    *   **serializableClass.User-config vs. serializableClass.User-config:** If multiple user configurations define beans of the same type without disambiguation, it leads to a `NoUniqueBeanDefinitionException`. Resolve using `@Qualifier` or `@Primary`.
    *   **Auto-config vs. Auto-config:** Unlikely by design, as they should use conditions to avoid clashes. If it occurred, it would likely cause an error.

**Q34: What are Conditional Annotations in Spring Boot? Give an example.**
**A34:**
    *   **Purpose:** Allow beans or configurations to be created only if certain conditions are met at runtime, enabling flexibility and adaptability (key to auto-configuration).
    *   **Example:** `@ConditionalOnClass`: Creates a bean only if specified classes are on the classpath. E.g., `DataSourceAutoConfiguration` might use `@ConditionalOnClass(DataSource.class)` to only activate if JDBC classes are present. Other examples: `@ConditionalOnProperty`, `@ConditionalOnBean`, `@ConditionalOnMissingBean`.

**Q35: How are properties managed in Spring Boot? (`.properties` vs YAML)**
**A35:**
    *   **Management:** Through externalized configuration from sources like properties files, YAML files, environment variables, command-line args, config servers (with defined precedence).
    *   **YAML Advantages:** Hierarchical structure (more readable for complex config), native support for lists/maps.
    *   **YAML Limitations:** Indentation sensitive, slightly more complex parsing.
    *   **`.properties` Advantages:** Simple key-value format, widely familiar.
    *   **`.properties` Limitations:** Can become repetitive for hierarchical data.

**Q36: If a project has both `application.yaml` and `application.properties`, which has higher priority?**
**A36:** Properties defined in `application.properties` generally override those defined in `application.yaml` for the same key, as `.properties` files are typically loaded later in the standard property source order.

**Q37: How do Spring Boot Profiles work?**
**A37:** Profiles allow defining sets of configurations for different environments (`dev`, `test`, `prod`).
    *   **Defining:** Use profile-specific files (`application-{profile}.properties` or `.yml`) or `@Profile` annotation on `@Configuration`/`@Bean`. Profile-specific properties override defaults.
    *   **Activating:** Set `spring.profiles.active` via properties, environment variable (`SPRING_PROFILES_ACTIVE`), JVM property (`-Dspring.profiles.active`), command-line arg (`--spring.profiles.active`), or programmatically.

**Q38: What does the `@SpringBootApplication` annotation do internally?**
**A38:** Convenience annotation combining:
    *   **`@EnableAutoConfiguration`:** Enables auto-configuration mechanism.
    *   **`@ComponentScan`:** Scans the current package and sub-packages for components (`@Component`, `@Service`, etc.).
    *   **`@Configuration`:** Tags the class as a source of bean definitions (`@Bean` methods).

**Q39: How does Spring Boot simplify deployment compared to traditional Spring?**
**A39:** Primarily via the **embedded servlet container** (Tomcat, Jetty, Undertow). Applications can be packaged as executable JARs (`java -jar myapp.jar`) containing the server, simplifying deployment as no external server installation/configuration is needed.

**Q40: How does Spring Boot decide which embedded server (Tomcat, Jetty, Undertow) to use? Can we override/replace it?**
**A40:**
    *   **Decision:** Based on classpath dependencies. `spring-boot-starter-web` includes Tomcat by default.
    *   **Override/Replace:**
        1.  Exclude the default starter (e.g., `spring-boot-starter-tomcat`).
        2.  Include the desired starter (e.g., `spring-boot-starter-jetty` or `spring-boot-starter-undertow`).
        Auto-configuration detects the available starter and configures the appropriate server factory.

**Q41: Difference between embedded server and external application server deployment?**
**A41:**
    *   **Embedded (Executable JAR):** Server inside JAR, run via `java -jar`. Simple, self-contained, good for microservices. Less external control over server config.
    *   **External (WAR):** Package as WAR, deploy to standalone server (Tomcat, JBoss). Fits traditional enterprise deployment, allows shared server resources, centralized management. More complex setup, potential version conflicts. Requires extending `SpringBootServletInitializer`.

**Q42: How to disable a specific auto-configuration class?**
**A42:**
    *   **Annotation:** Use `exclude` attribute: `@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})`.
    *   **Property:** Use `spring.autoconfigure.exclude` property in `application.properties`/`.yml`.

**Q43: Can we create a non-web application in Spring Boot?**
**A43:** Yes. Exclude web starters (`spring-boot-starter-web`, `spring-boot-starter-webflux`). Implement logic, often using `CommandLineRunner` or `ApplicationRunner` interfaces whose `run` methods execute after context load. No embedded server starts.

**Q44: What is the Spring Boot DevTools module used for?**
**A44:** Enhances development experience:
    *   **Automatic Restart:** Fast application restarts on classpath changes.
    *   **LiveReload:** Triggers browser refresh on resource changes.
    *   **Property Defaults:** Disables template caching, etc., during development.
    *   **(Should be excluded from production builds).**

**Q45: How to get the list of all beans in a Spring Boot application?**
**A45:** Inject `ApplicationContext` and use its methods:
```java
@Autowired
private ApplicationContext applicationContext;

// In a method or @PostConstruct
String[] beanNames = applicationContext.getBeanDefinitionNames();
for (String beanName : beanNames) {
    System.out.println(beanName + " : " + applicationContext.getBean(beanName).getClass().getName());
}
```

**Q46: How does Spring Boot support internationalization (i18n)?**
**A46:** Leverages Spring Framework's `MessageSource`. Auto-configures looking for resource bundles (e.g., `messages.properties`, `messages_de.properties`). Uses `LocaleResolver` (e.g., `AcceptHeaderLocaleResolver`) to determine locale. Inject `MessageSource` and use `getMessage()` to retrieve locale-specific text.

**Q47: Explain Spring Boot's approach to asynchronous operations. How to enable/use?**
**A47:**
    *   **Approach:** Uses Spring Framework's `@Async` support. Executes annotated methods in a separate thread pool.
    *   **Enable:** Add `@EnableAsync` to a `@Configuration` class.
    *   **Use:** Annotate public methods in Spring beans with `@Async`. Return `void` (fire-and-forget) or `Future<T>`/`CompletableFuture<T>` (track progress/result).
    *   **Call:** Call the `@Async` method from another bean (calling from within the same class instance bypasses the proxy).
    *   **Configuration:** Customize the thread pool by defining a `TaskExecutor` bean.

---

### VI. Spring Boot Data Access & Caching

**Q48: How does Spring Boot simplify data access layer implementation?**
**A48:**
    *   **Auto-configuration:** Sets up `DataSource`, `EntityManagerFactory`, `JdbcTemplate`, etc., based on dependencies and properties.
    *   **Spring Data JPA/JDBC etc.:** Provides repository interfaces (`JpaRepository`, `CrudRepository`) generating implementations for CRUD and custom queries (via method names or `@Query`), reducing boilerplate DAO code.
    *   **Database Initialization:** Can auto-create schema (`ddl-auto`) and seed data (`data.sql`).
    *   **Transaction Management:** Simplified via `@Transactional`.
    *   **Exception Translation:** Converts specific DB exceptions to Spring's `DataAccessException` hierarchy.

**Q49: How do you connect to a database in a Spring Boot application?**
**A49:**
    1.  **Add Dependencies:** `spring-boot-starter-data-jpa` and database driver (e.g., `mysql-connector-java`).
    2.  **Configure Datasource:** Set `spring.datasource.url`, `.username`, `.password` in `application.properties`/`.yml`.
    3.  **Define Entities:** Create `@Entity` classes.
    4.  **Create Repositories:** Define interfaces extending `JpaRepository`.
    5.  **Use Repositories:** Autowire repositories into services.

**Q50: Difference between `JpaRepository` and `CrudRepository`?**
**A50:** `JpaRepository` extends `PagingAndSortingRepository`, which extends `CrudRepository`.
    *   `CrudRepository`: Basic CRUD operations.
    *   `PagingAndSortingRepository`: Adds pagination and sorting methods.
    *   `JpaRepository`: Adds JPA-specific methods (batch operations, flushing, `getById`). Provides all features of the others plus JPA extras.

**Q51: Explain the caching mechanism in Spring Boot. How to implement it?**
**A51:**
    *   **Mechanism:** Uses Spring Cache Abstraction over providers (EhCache, Redis, Caffeine, default ConcurrentHashMap). Declarative caching via annotations.
    *   **Implementation:**
        1.  **Add Dependency:** `spring-boot-starter-cache` and optional provider (e.g., `spring-boot-starter-data-redis`).
        2.  **Enable Caching:** Add `@EnableCaching` to a `@Configuration` class.
        3.  **Annotate Methods:**
            *   `@Cacheable`: Caches method result; returns cache if args match.
            *   `@CachePut`: Always executes method; updates cache.
            *   `@CacheEvict`: Removes entries from cache.
        4.  **Configure (Optional):** Customize `CacheManager` bean or use provider config (e.g., `ehcache.xml`).

**Q52: Difference between cache eviction and cache expiration?**
**A52:**
    *   **Eviction:** Removing entries to manage cache **size** (based on policies like LRU, LFU when capacity is reached).
    *   **Expiration:** Removing entries because they are **stale** (based on time-to-live TTL or time-to-idle TTI). Ensures data freshness.

**Q53: Default caching vs. external caching tools (Redis, Hazelcast)?**
**A53:**
    *   **Default Cache (ConcurrentHashMap):** Simple, in-memory, single-instance only. Limited features. Uses application heap.
    *   **External Cache (Redis, etc.):** Distributed (shared across instances), advanced features (persistence, eviction policies, data structures), off-heap storage. Essential for scaled applications.

**Q54: Best practices for managing transactions in Spring Boot?**
**A54:**
    *   **Use `@Transactional`:** Declarative management on public service layer methods.
    *   **Keep Transactions Short:** Minimize lock contention. Avoid long-running/external calls within transactions if possible.
    *   **Understand Propagation:** Default `REQUIRED` often suitable. Be aware of `REQUIRES_NEW`, `NESTED`, etc.
    *   **Rollback Behavior:** Default for unchecked exceptions. Customize with `rollbackFor`/`noRollbackFor`.
    *   **Read-Only Optimization:** Use `@Transactional(readOnly = true)` for read operations (performance hint).
    *   **Avoid Self-Invocation Issues:** Calling `@Transactional` methods from within the same instance bypasses the proxy; structure calls across beans.

**Q55: How does Spring Boot handle database migration?**
**A55:** Integrates with tools like **Flyway** and **Liquibase**.
    *   Include dependency (`flyway-core` or `liquibase-core`).
    *   Auto-configuration detects them.
    *   **Flyway:** Looks for SQL scripts (`V1__*.sql`, `V2__*.sql`) in `classpath:db/migration`. Tracks applied migrations in DB table. Applies pending ones on startup.
    *   **Liquibase:** Uses changelog files (XML, YAML, JSON) in `classpath:db/changelog`. Tracks applied changesets. Applies pending ones on startup.
    *   **(Note:** `spring.jpa.hibernate.ddl-auto` is suitable for dev/test but not recommended for production schema management compared to Flyway/Liquibase).

**Q56: How to implement "soft delete" using JPA?**
**A56:**
    1.  **Add Flag/Timestamp:** Add `boolean deleted` or `LocalDateTime deletedAt` field to entity/table.
    2.  **Override Delete:**
        *   Use `@SQLDelete(sql = "UPDATE table SET deleted = true WHERE id = ?")` on the entity to intercept DELETE operations and perform an UPDATE instead.
    3.  **Filter Queries:** Ensure reads exclude soft-deleted records:
        *   **`@Where(clause = "deleted = false")` (Hibernate):** Add to entity to automatically filter most SELECTs.
        *   **Manual Conditions:** Add `AND deleted = false` to custom repository queries.
        *   **JPA Filters (Hibernate):** Use `@FilterDef`/`@Filter` for more dynamic filtering.

**Q57: How to implement pagination using Spring Data JPA?**
**A57:**
    1.  **Repository Method:** Accept `Pageable` parameter, return `Page<EntityType>`.
        ```java
        Page<Product> findAll(Pageable pageable);
        Page<Product> findByCategory(String category, Pageable pageable);
        ```
    2.  **Service/Controller:** Create `Pageable` instance: `PageRequest.of(pageNumber, pageSize, sort)`.
        ```java
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Product> productPage = productRepository.findAll(pageable);
        ```
    3.  **Process `Page`:** Use `page.getContent()` for data and metadata like `page.getTotalPages()`, `page.getTotalElements()`.

**Q58: How can indexing be done using Spring Boot/JPA?**
**A58:** Suggest index creation via annotations (when using schema generation):
    *   **`@Index`:** Within `@Table(indexes = { @Index(...) })` on the entity. Specify column(s) and name.
    *   **`@Column(unique=true)`:** Often creates a unique index.
    *   **Manual DDL:** Preferred for production. Use Flyway/Liquibase scripts to create indexes directly in the DB.

---

### VII. Spring Boot Web & Actuator

**Q59: What are Spring Boot Actuator endpoints? Why secure them? How?**
**A59:**
    *   **Purpose:** Ready-to-use HTTP endpoints (`/health`, `/metrics`, `/env`, `/loggers`, `/heapdump`, etc.) exposing operational info for monitoring/management. Provided by `spring-boot-starter-actuator`.
    *   **Security Importance:** Can expose sensitive data (config, passwords in `/env`) or allow modifications (`/loggers`). Unsecured exposure is a major risk.
    *   **Securing Methods:**
        1.  **Limit Exposure:** Use `management.endpoints.web.exposure.include`/`.exclude` properties.
        2.  **Spring Security:** Require authentication/authorization via `spring-boot-starter-security`.
        3.  **Separate Port/Path:** Use `management.server.port` or `management.endpoints.web.base-path` and firewall access.
        4.  **HTTPS:** Ensure `management.server.ssl.enabled=true`.

**Q60: What are best practices for versioning REST APIs in Spring Boot?**
**A60:** Common strategies:
    *   **URL Path Versioning:** `/api/v1/products` (Explicit, common).
    *   **Query Parameter Versioning:** `/api/products?version=1` (Less common).
    *   **Custom Header Versioning:** `X-API-Version: 1` (Cleaner URLs, less discoverable).
    *   **Media Type Versioning (Content Negotiation):** `Accept: application/vnd.company.app-v1+json` (RESTful, more complex).
    *   **Best Practices:** Choose one, apply consistently (URL Path often simplest), plan for backward compatibility, document clearly.

**Q61: How to resolve the "White Label Error Page"?**
**A61:** This is Spring Boot's default fallback error page. Replace/resolve it:
    1.  **Check Mappings:** Ensure URL path matches a `@Controller` method (`@GetMapping`, etc.). Typos are common.
    2.  **Custom Static Error Pages:** Create `src/main/resources/static/error/{status_code}.html` (e.g., `404.html`, `500.html`).
    3.  **Custom Template Error Pages:** Create `src/main/resources/templates/error/{status_code}.html` (for template engines like Thymeleaf).
    4.  **`@ControllerAdvice` + `@ExceptionHandler`:** Create global exception handlers to catch specific exceptions and return custom responses/views.
    5.  **`ErrorController` Implementation:** Implement `ErrorController` for full control over error handling logic.

**Q62: How to handle a 404 (Not Found) error specifically?**
**A62:**
    1.  **Static/Template `404.html`:** Place `404.html` in `static/error/` or `templates/error/`.
    2.  **`ErrorController`:** Check for 404 status code within the error handling method.
    3.  **`@ControllerAdvice` + `NoHandlerFoundException`:** Set `spring.mvc.throw-exception-if-no-handler-found=true` and catch `NoHandlerFoundException` in an `@ExceptionHandler`.

**Q63: Difference between returning `ResponseEntity<T>` and `T` directly from a controller?**
**A63:**
    *   **Returning `T`:** Spring serializes `T` to the response body with a default status (`200 OK`). Limited control over status/headers.
    *   **Returning `ResponseEntity<T>`:** Wrapper giving full control over the HTTP response: set body (`T`), status code (`HttpStatus`), and custom headers. Preferred for customizing responses.

**Q64: What's a common HTTP status code for successful resource deletion?**
**A64:** `204 No Content` (indicates success with no response body) or `200 OK` (if response includes status entity). `202 Accepted` for asynchronous deletion.

**Q65: How to implement rate limiting on API endpoints?**
**A65:** Use libraries or built-in gateway features:
    *   **Bucket4j:** Popular library. Add dependency, configure limits (e.g., requests/minute), integrate via interceptor/filter (Spring Boot starter available). Can use in-memory or distributed stores (Redis).
    *   **Resilience4j:** `RateLimiter` module.
    *   **Spring Cloud Gateway:** Built-in `RequestRateLimiter` filter, often uses Redis for distributed limiting.
    *   **Manual (Simple/Not Scalable):** Basic filter using `ConcurrentHashMap` to track requests per IP (not recommended for production/distributed).

**Q66: How to build a non-blocking, reactive REST API using Spring WebFlux?**
**A66:**
    1.  **Dependency:** Use `spring-boot-starter-webflux` (instead of `-web`). Includes Project Reactor and Netty.
    2.  **Reactive Controller (`@RestController`):** Methods return reactive types `Mono<T>` (0-1 items) or `Flux<T>` (0-N items).
    3.  **Reactive Data Access:** Use reactive drivers (R2DBC, MongoDB reactive) and reactive Spring Data repositories (`ReactiveCrudRepository`).
    4.  **Reactive Service Layer:** Operate on `Mono`/`Flux` using Reactor operators (`map`, `flatMap`, `filter`).
    5.  **Non-Blocking I/O:** Use reactive libraries for all blocking calls (e.g., `WebClient` for HTTP, reactive DB drivers).
    *   **Benefit:** Event-loop model handles high concurrency efficiently, especially for I/O-bound tasks.

---

### VIII. Microservices

**Q67: How to handle inter-service communication in microservices?**
**A67:** Depends on needs:
    *   **Synchronous (Request/Response):**
        *   **`RestTemplate`:** Simple, direct calls.
        *   **`WebClient`:** Modern, non-blocking/reactive client (can be used synchronously).
        *   **`Feign Client`:** Declarative REST client; define interface, Feign implements. Cleaner code.
    *   **Asynchronous (Decoupled):**
        *   **Message Brokers (Kafka, RabbitMQ):** Services publish/subscribe to messages/events. Improves robustness, decoupling. Use Spring Cloud Stream for abstraction.

**Q68: Strategies for scaling a Spring Boot application (potentially microservice)?**
**A68:**
    *   **Horizontal Scaling:** Add more instances.
    *   **Load Balancer:** Distribute traffic across instances.
    *   **Microservice Decomposition:** Scale independent services based on need.
    *   **Caching:** Redis, Hazelcast for frequently accessed data.
    *   **Database Optimization:** Query tuning, connection pooling, read replicas.
    *   **Asynchronous Processing:** Offload tasks using message queues or `@Async`.
    *   **Cloud Auto-Scaling:** Use cloud provider features.
    *   **API Gateway:** Handle routing, rate limiting, auth.
    *   **Reactive Programming (WebFlux):** For high concurrency I/O-bound apps.

**Q69: How to implement security in microservices using Spring Boot/Security?**
**A69:**
    *   **Decentralized Security:** Each service includes Spring Security, handles local authorization.
    *   **Centralized Auth Server (OAuth2/OIDC):** Dedicated service (e.g., Spring Authorization Server, Keycloak) handles authentication, issues tokens (JWT).
    *   **Token-Based Authorization (JWT):** Services validate incoming JWTs issued by Auth Server.
    *   **API Gateway Security:** Central enforcement point for token validation, rate limiting, auth checks.
    *   **Secure Communication:** Use HTTPS (SSL/TLS) for all inter-service communication.
    *   **Spring Security Config:** Configure `SecurityFilterChain` beans in each service for access control based on token claims (roles/permissions).

**Q70: How to manage externalized configuration and secrets in microservices?**
**A70:**
    *   **Centralized Configuration:** Spring Cloud Config Server (backed by Git, etc.). Services fetch config on startup.
    *   **Secrets Management:** Integrate with HashiCorp Vault, AWS Secrets Manager, Azure Key Vault for sensitive data (passwords, keys). Use Spring Cloud Vault/AWS/Azure integrations.
    *   **Encryption:** Spring Cloud Config Server supports encrypting properties in the backend repository.
    *   **Standard Externalization:** Leverage Spring Boot's mechanisms (properties, YAML, env vars) sourcing values from Config Server or secrets manager.

**Q71: How to handle API rate limits and failures when calling external APIs?**
**A71:** Use resilience patterns:
    *   **Circuit Breaker (Resilience4j):** Prevent calls to failing external APIs, provide fallbacks. Opens circuit on repeated failures.
    *   **Rate Limiter (Resilience4j, Bucket4j):** Client-side limiting to avoid exceeding external API quotas.
    *   **Retry (Resilience4j, Spring Retry):** Automatic retries with backoff for transient failures.
    *   **Timeouts:** Configure connection/read timeouts for HTTP clients.
    *   **Caching:** Cache responses where appropriate.
    *   **Asynchronous Calls:** Use `CompletableFuture`/WebFlux to avoid blocking main thread.

**Q72: How to make a Spring Boot microservice application more resilient?**
**A72:** Combine techniques:
    *   **Circuit Breakers (Resilience4j):** Isolate failures, provide fallbacks.
    *   **Retries (Resilience4j, Spring Retry):** Handle transient errors.
    *   **Timeouts:** Prevent indefinite waits.
    *   **Bulkheads:** Limit concurrent calls to specific services, preventing resource exhaustion.
    *   **Asynchronous Communication (Message Queues):** Decouple services.
    *   **Health Checks (Actuator):** Allow load balancers/callers to avoid unhealthy instances.
    *   **Logging & Distributed Tracing:** Monitor and diagnose failures (Micrometer, Sleuth, Zipkin/Jaeger).
    *   **Idempotency:** Design write operations to be safely retried.

**Q73: Explain converting business logic to serverless using Spring Cloud Function.**
**A73:** Allows writing standard Java `Function`, `Consumer`, `Supplier` beans containing business logic. Provides adapters to run these on serverless platforms (AWS Lambda, Azure Functions, etc.) without provider-specific code changes. Decouples logic from runtime, leverages serverless benefits (auto-scaling, pay-per-use).

**Q74: How can Spring Cloud Gateway be configured for routing, security, monitoring?**
**A74:**
    *   **Routing:** Configure routes (YAML or Java `RouteLocatorBuilder`) defining predicates (match path, host, etc.) and filters (modify request/response, forward to microservice URI).
    *   **Security:** Integrate Spring Security. Apply security filters (global/per-route) for auth (JWT validation), authorization, CSRF, HTTPS enforcement.
    *   **Monitoring:** Integrate Actuator endpoints (`/health`, `/metrics`, `/gateway/routes`). Use Micrometer for metrics export (Prometheus). Add filters for logging/tracing.

**Q75: How to mock microservices during testing?**
**A75:**
    *   **WireMock:** Stub/mock HTTP-based services. Run standalone or embedded server in tests, configure expected responses for specific requests.
    *   **`@MockBean` (Spring Boot):** Replace Spring beans (like Feign clients or services using `RestTemplate`) with Mockito mocks in the test context. Stub their methods.
    *   **Testcontainers:** Spin up lightweight Docker containers for dependencies, including mock servers like WireMock.

**Q76: What is Spring Cloud? How is it useful for microservices?**
**A76:** Tools/frameworks on top of Spring Boot for distributed systems challenges:
    *   **Configuration Management:** Spring Cloud Config.
    *   **Service Discovery:** Eureka, Consul.
    *   **Circuit Breakers:** Resilience4j integration.
    *   **Routing/Load Balancing:** Spring Cloud Gateway, Spring Cloud LoadBalancer.
    *   **Distributed Tracing:** Micrometer Tracing / Spring Cloud Sleuth (integrates Zipkin/Jaeger).
    *   **Messaging:** Spring Cloud Stream (Kafka/RabbitMQ abstraction).
    Provides building blocks for resilient, configurable, discoverable microservices.

**Q77: How to integrate distributed tracing in Spring Boot?**
**A77:**
    *   **Integration:** Use Micrometer Tracing (newer Spring Boot) or Spring Cloud Sleuth (older). Automatically adds/propagates trace/span IDs across services (HTTP headers, message headers).
    *   **Exporters:** Add dependency for exporting trace data to systems like Zipkin, Jaeger (e.g., `micrometer-tracing-bridge-brave` + `micrometer-tracing-reporter-zipkin`).
    *   **Use:** Visualize request flow, identify latency bottlenecks, troubleshoot errors across services in tracing UI (Zipkin, Jaeger).

**Q78: How to integrate cloud storage (e.g., S3, GCS) in Spring Boot?**
**A78:**
    1.  **Add SDK Dependency:** Include official Java SDK (e.g., `software.amazon.awssdk:s3`). Spring Cloud Starters (`spring-cloud-aws-starter`) can simplify.
    2.  **Configure Credentials:** Securely (IAM roles/service accounts preferred, environment variables, credential files). Avoid hardcoding keys. Starters provide property-based config.
    3.  **Create Client Bean (Optional):** Define `@Bean` for `S3Client`, `Storage`, etc. (Starters may auto-configure).
    4.  **Create Service Layer:** Encapsulate storage operations (upload, download, delete) in a `@Service`. Inject client bean.
    5.  **Implement Operations:** Use SDK client methods within the service.
    6.  **Controller Layer:** Expose endpoints (e.g., file upload using `MultipartFile`) that call the storage service.

**Q79: How can Spring Boot be used to implement an Event-Driven Architecture (EDA)?**
**A79:**
    *   **Spring Application Events (In-Process):** Extend `ApplicationEvent`, publish via `ApplicationEventPublisher`, listen with `@EventListener`. Decouples components *within* one application.
    *   **Spring Cloud Stream (Inter-Process):** Abstraction over message brokers (Kafka, RabbitMQ). Use functional style (`Supplier`, `Function`, `Consumer` beans) or annotations (`@StreamListener` - deprecated) to produce/consume messages. Ideal for microservice communication.
    *   **Spring Integration:** Comprehensive framework for integration patterns, including event-driven flows.
    *   **Direct Broker Integration (Spring Kafka, Spring AMQP):** Use specific Spring projects for fine-grained broker control.

---

### IX. Build Tools & Deployment

**Q80: What is the Spring Boot CLI? How to execute a project?**
**A80:**
    *   **What:** Command Line Interface for rapid prototyping/running Spring apps, often using Groovy scripts. Reduces boilerplate.
    *   **Execution:**
        1.  Install CLI.
        2.  Write Groovy script (`app.groovy`) using annotations (`@RestController`, etc.). CLI handles imports/dependencies.
        3.  Run `spring run app.groovy`.

**Q81: Role of Docker in deploying Spring Boot applications?**
**A81:**
    *   **Containerization:** Packages the app (executable JAR), JRE, and dependencies into a container image.
    *   **Consistency:** Runs the same way across environments.
    *   **Isolation:** Runs app in isolated environment.
    *   **Scalability:** Easily managed by orchestrators (Kubernetes) for scaling, deployment automation.
    *   **CI/CD:** Standard artifact in pipelines.

**Q82: How to optimize a Maven build for a large project?**
**A82:**
    *   **Parallel Builds:** `mvn -T 4` or `-T 1C` for multi-module projects.
    *   **Incremental Builds:** Use Maven Daemon (`mvnd`).
    *   **Skip Tests:** `mvn package -DskipTests`.
    *   **Clean Dependencies:** Exclude unnecessary plugins/dependencies.
    *   **Repository Manager:** Use Nexus/Artifactory for caching dependencies.
    *   **Profile-Specific Builds:** Use Maven profiles.
    *   **Optimize Plugin Config:** Configure Surefire, compiler, etc., efficiently.

**Q83: What is the Maven lifecycle? Purpose of the `clean` phase?**
**A83:**
    *   **Lifecycle:** Predefined sequence of phases (e.g., `validate`, `compile`, `test`, `package`, `install`, `deploy` for the default lifecycle). Executing a phase runs all preceding phases.
    *   **`clean` Lifecycle:** Contains the `clean` phase.
    *   **Purpose of `clean`:** Removes artifacts from previous builds (usually deletes the `target` directory) to ensure a fresh build environment.

---

### X. Version Control (Git)

**Q84: How do you manage merge conflicts in Git?**
**A84:**
    1.  **Prevention:** Pull latest changes (`git pull` or `fetch`+`merge`/`rebase`) frequently into feature branches.
    2.  **Detection:** Git pauses `merge`/`rebase`, marks conflicted files.
    3.  **Resolution:**
        *   Identify conflicted files (`git status`).
        *   Edit files, removing conflict markers (`<<<<<<<`, `=======`, `>>>>>>>`) and keeping desired code.
        *   Stage resolved files (`git add <file>`).
        *   Complete operation (`git commit` for merge, `git rebase --continue` for rebase).
    4.  **Tools:** Use visual merge tools (IDE integration, KDiff3, Meld).

**Q85: What is `git rebase` used for?**
**A85:** Reapplies commits from one branch onto another.
    *   **Updating Feature Branch:** `git rebase main` (on feature branch) reapplies feature commits onto the latest `main`, creating a linear history (alternative to merge commit).
    *   **Interactive Rebase (`-i`):** Modify local commits before pushing (squash, reword, reorder).
    *   **Caution:** Avoid rebasing already pushed/shared branches as it rewrites history.

---

### XI. Design Patterns

**Q86: Why are Design Patterns required? Give an example.**
**A86:** Provide proven, reusable solutions to common design problems, leading to more maintainable, flexible, reusable, robust code. Provide common vocabulary.
    *   **Example (Builder Pattern):** For creating complex objects with many optional parameters. Provides readable fluent API (`new Config.Builder().setHost("...").setPort(123).build();`), handles optional parameters gracefully, allows immutable object creation, centralizes validation.
    *   **Example (PRG - Post/Redirect/Get):** Solves double-submit problem in web forms. POST processes data, then redirects (302/303) to a GET URL (success page). Refreshing the browser re-requests the GET page, not the original POST.
    *   **Example (Singleton Pattern):** Ensures only one instance of a class exists. In Spring, beans are singletons by default (managed by IoC container), so manual implementation is often unnecessary for Spring beans.

---

### XII. Coding Examples (Java Stream API)

**Q87: Given `List<Integer> numbers`, find all numbers starting with the digit '1'.**
**A87:**
```java
List<Integer> startingWithOne = numbers.stream()
        .filter(n -> String.valueOf(n).startsWith("1"))
        .collect(Collectors.toList());
// Or collect as List<String> if needed
List<String> startingWithOneStrings = numbers.stream()
        .map(String::valueOf)
        .filter(s -> s.startsWith("1"))
        .collect(Collectors.toList());
```

**Q88: Given `List<Integer> numbers`, find the three maximum and three minimum numbers.**
**A88:**
```java
// Minimum
List<Integer> minThree = numbers.stream()
        .sorted()
        .limit(3)
        .collect(Collectors.toList());
// Maximum
List<Integer> maxThree = numbers.stream()
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .collect(Collectors.toList());
```

**Q89: Given `List<Integer> numbers`, find the sum of the odd numbers.**
**A89:**
```java
// Using mapToInt
int sum = numbers.stream()
                 .filter(n -> n % 2 != 0) // Filter odd
                 .mapToInt(Integer::intValue)
                 .sum();
// Using reduce
Optional<Integer> sumOpt = numbers.stream()
                                 .filter(n -> n % 2 != 0)
                                 .reduce(Integer::sum); // Or .reduce(0, Integer::sum);
```

**Q90: Given `List<Integer> numbers`, find the sum of the squares of the odd numbers.**
**A90:**
```java
int sumOfSquares = numbers.stream()
                          .filter(n -> n % 2 != 0)      // Filter odd
                          .map(n -> n * n)              // Square
                          .mapToInt(Integer::intValue)
                          .sum();
```