Okay, let's illustrate these design patterns within the context of an e-commerce website. We'll keep the Java code examples simple and focused on the pattern's core idea.

Imagine our e-commerce website sells various products, needs to handle user configurations, process payments, and display different UI elements.

---

### 1. Singleton Pattern

**Concept:** Ensures a class has only one instance and provides a global point of access to it.

**E-commerce Situation:**
You need a single point to manage application-wide configurations, such as currency settings, tax calculation rules, or API keys for external services (like a payment gateway or shipping provider). You don't want multiple configuration objects floating around, potentially with different values.

**Java Code Example:**

```java
// AppConfiguration.java - Manages global application settings

class AppConfiguration {
    // 1. Private static instance of itself
    private static AppConfiguration instance;

    // Configuration properties
    private String currencySymbol;
    private double defaultTaxRate;
    private String paymentGatewayApiKey;

    // 2. Private constructor to prevent instantiation from outside
    private AppConfiguration() {
        // Load configuration from a file, database, or set defaults
        this.currencySymbol = "$";
        this.defaultTaxRate = 0.08; // 8%
        this.paymentGatewayApiKey = "your_api_key_here_loaded_securely";
        System.out.println("AppConfiguration instance created.");
    }

    // 3. Public static method to get the instance
    public static synchronized AppConfiguration getInstance() {
        if (instance == null) {
            instance = new AppConfiguration();
        }
        return instance;
    }

    // Getters for configuration properties
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public double getDefaultTaxRate() {
        return defaultTaxRate;
    }

    public String getPaymentGatewayApiKey() {
        return paymentGatewayApiKey;
    }

    // Example: Update a setting (in a real app, this might be more complex)
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}

// Main.java - How to use it
public class Main {
    public static void main(String[] args) {
        AppConfiguration config1 = AppConfiguration.getInstance();
        AppConfiguration config2 = AppConfiguration.getInstance();

        System.out.println("Config1 Currency: " + config1.getCurrencySymbol());
        System.out.println("Config2 Tax Rate: " + config2.getDefaultTaxRate());

        config1.setCurrencySymbol("€");
        System.out.println("Config2 Currency after change by config1: " + config2.getCurrencySymbol());

        System.out.println("Are config1 and config2 the same instance? " + (config1 == config2));
    }
}
```

**Explanation:**
*   `AppConfiguration` has a private constructor and a static `getInstance()` method.
*   The first time `getInstance()` is called, it creates an `AppConfiguration` object. Subsequent calls return the *same* object.
*   `synchronized` is used to make it thread-safe, though for very high-performance scenarios, double-checked locking or initialization-on-demand holder idiom might be preferred.

---

### 2. Factory Method Pattern

**Concept:** Defines an interface for creating an object, but lets subclasses decide which class to instantiate. The Factory Method lets a class defer instantiation to subclasses.

**E-commerce Situation:**
Your e-commerce site needs to send notifications to users (e.g., order confirmation, shipping updates). You might want to send these via email, SMS, or push notification. The *type* of notification service to use might be determined at runtime or by user preference.

**Java Code Example:**

```java
// NotificationService.java - Product Interface
interface NotificationService {
    void sendNotification(String userId, String message);
}

// Concrete Products
class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String userId, String message) {
        System.out.println("Sending EMAIL to " + userId + ": " + message);
    }
}

class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(String userId, String message) {
        System.out.println("Sending SMS to " + userId + ": " + message);
    }
}

// NotificationFactory.java - Creator (Abstract Factory)
abstract class NotificationFactory {
    // The Factory Method
    public abstract NotificationService createNotificationService();

    // You can also have common logic here
    public void send(String userId, String message) {
        NotificationService service = createNotificationService();
        service.sendNotification(userId, message);
    }
}

// Concrete Creators
class EmailNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new EmailNotificationService();
    }
}

class SMSNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new SMSNotificationService();
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        NotificationFactory emailFactory = new EmailNotificationFactory();
        emailFactory.send("user123", "Your order has been confirmed!");

        NotificationFactory smsFactory = new SMSNotificationFactory();
        smsFactory.send("user456", "Your package is out for delivery!");

        // Example: Deciding factory at runtime
        String preferredChannel = "SMS"; // This could come from user preferences
        NotificationFactory chosenFactory;
        if ("EMAIL".equalsIgnoreCase(preferredChannel)) {
            chosenFactory = new EmailNotificationFactory();
        } else {
            chosenFactory = new SMSNotificationFactory();
        }
        chosenFactory.send("user789", "Special offer just for you!");
    }
}
```
**Explanation:**
*   `NotificationService` is the interface for the products (notification services).
*   `EmailNotificationService` and `SMSNotificationService` are concrete products.
*   `NotificationFactory` is an abstract creator with an abstract `createNotificationService()` method (the factory method).
*   `EmailNotificationFactory` and `SMSNotificationFactory` are concrete creators that decide which concrete `NotificationService` to instantiate.
*   The client code uses a specific factory (e.g., `EmailNotificationFactory`) to get a `NotificationService` object without knowing its concrete type.

---

### 3. Abstract Factory Pattern

**Concept:** Provides an interface for creating families of related or dependent objects without specifying their concrete classes.

**E-commerce Situation:**
Your e-commerce site might have different UI themes (e.g., "Light Mode" and "Dark Mode"). Each theme would affect multiple UI elements like buttons, text boxes, and panels. You want to ensure that all UI elements for a chosen theme are consistent.

**Java Code Example:**

```java
// AbstractProductA: Button.java
interface Button {
    void render();
    void onClick();
}

// AbstractProductB: TextBox.java
interface TextBox {
    void render();
    void onInput();
}

// ConcreteProductA1: LightButton.java
class LightButton implements Button {
    @Override
    public void render() { System.out.println("Rendering Light Button."); }
    @Override
    public void onClick() { System.out.println("Light Button clicked."); }
}

// ConcreteProductA2: DarkButton.java
class DarkButton implements Button {
    @Override
    public void render() { System.out.println("Rendering Dark Button."); }
    @Override
    public void onClick() { System.out.println("Dark Button clicked."); }
}

// ConcreteProductB1: LightTextBox.java
class LightTextBox implements TextBox {
    @Override
    public void render() { System.out.println("Rendering Light TextBox."); }
    @Override
    public void onInput() { System.out.println("Light TextBox input."); }
}

// ConcreteProductB2: DarkTextBox.java
class DarkTextBox implements TextBox {
    @Override
    public void render() { System.out.println("Rendering Dark TextBox."); }
    @Override
    public void onInput() { System.out.println("Dark TextBox input."); }
}

// AbstractFactory: UIFactory.java
interface UIFactory {
    Button createButton();
    TextBox createTextBox();
}

// ConcreteFactory1: LightThemeFactory.java
class LightThemeFactory implements UIFactory {
    @Override
    public Button createButton() { return new LightButton(); }
    @Override
    public TextBox createTextBox() { return new LightTextBox(); }
}

// ConcreteFactory2: DarkThemeFactory.java
class DarkThemeFactory implements UIFactory {
    @Override
    public Button createButton() { return new DarkButton(); }
    @Override
    public TextBox createTextBox() { return new DarkTextBox(); }
}

// Client: Application.java
class Application {
    private Button button;
    private TextBox textBox;

    public Application(UIFactory factory) {
        button = factory.createButton();
        textBox = factory.createTextBox();
    }

    public void displayUI() {
        button.render();
        textBox.render();
    }
    
    public void interactWithButton() {
        button.onClick();
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        // Decide which theme to use (e.g., from user settings)
        String themeChoice = "Dark"; // Could be "Light"
        UIFactory factory;

        if ("Dark".equalsIgnoreCase(themeChoice)) {
            factory = new DarkThemeFactory();
        } else {
            factory = new LightThemeFactory();
        }

        Application app = new Application(factory);
        app.displayUI();
        app.interactWithButton();
        
        System.out.println("\n--- Switching theme to Light ---");
        factory = new LightThemeFactory();
        Application appLight = new Application(factory);
        appLight.displayUI();
    }
}
```
**Explanation:**
*   We have abstract products (`Button`, `TextBox`).
*   We have concrete products for each theme (`LightButton`, `DarkButton`, `LightTextBox`, `DarkTextBox`).
*   `UIFactory` is the abstract factory that declares methods to create a family of products (a button and a textbox).
*   `LightThemeFactory` and `DarkThemeFactory` are concrete factories that create products belonging to a specific theme.
*   The `Application` (client) is configured with a specific factory and uses it to create UI elements, ensuring they are from the same theme family.

---

### 4. Builder Pattern

**Concept:** Separates the construction of a complex object from its representation, allowing the same construction process to create different representations. Useful for objects with many optional parameters or a complex setup process.

**E-commerce Situation:**
Creating a `Product` object. A product can have many attributes: name, description, price, category, SKU, images, variants (size, color), stock, dimensions, weight, supplier info, etc. Many of these can be optional, and using a constructor with a long list of parameters is unwieldy and error-prone.

**Java Code Example:**

```java
// Product.java - The complex object we want to build
class Product {
    // Required parameters
    private final String sku;
    private final String name;
    private final double price;

    // Optional parameters
    private final String description;
    private final String category;
    private final int stockQuantity;
    private final double weight;

    // Private constructor, takes the builder as an argument
    private Product(ProductBuilder builder) {
        this.sku = builder.sku;
        this.name = builder.name;
        this.price = builder.price;
        this.description = builder.description;
        this.category = builder.category;
        this.stockQuantity = builder.stockQuantity;
        this.weight = builder.weight;
    }

    @Override
    public String toString() {
        return "Product [SKU=" + sku + ", Name=" + name + ", Price=" + price +
               (description != null ? ", Desc=" + description : "") +
               (category != null ? ", Category=" + category : "") +
               (stockQuantity > 0 ? ", Stock=" + stockQuantity : "") +
               (weight > 0 ? ", Weight=" + weight + "kg" : "") + "]";
    }

    // Static nested Builder class
    public static class ProductBuilder {
        // Required parameters mirrored from Product
        private final String sku;
        private final String name;
        private final double price;

        // Optional parameters initialized to default values
        private String description = null;
        private String category = "General";
        private int stockQuantity = 0;
        private double weight = 0.0;

        // Builder constructor for required fields
        public ProductBuilder(String sku, String name, double price) {
            this.sku = sku;
            this.name = name;
            this.price = price;
        }

        // Setter-like methods for optional fields, returning the builder itself (fluent interface)
        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ProductBuilder stockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public ProductBuilder weight(double weight) {
            this.weight = weight;
            return this;
        }

        // The build method that returns the final Product object
        public Product build() {
            // Can add validation here before creating the Product
            if (price <= 0) {
                throw new IllegalArgumentException("Price must be positive.");
            }
            return new Product(this);
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Product laptop = new Product.ProductBuilder("LPTP-001", "SuperBook Pro", 1299.99)
                            .description("A powerful laptop for professionals.")
                            .category("Electronics")
                            .stockQuantity(50)
                            .weight(1.8)
                            .build();

        Product tShirt = new Product.ProductBuilder("TSHT-005", "Cool T-Shirt", 19.99)
                             .category("Apparel")
                             .stockQuantity(200)
                             // weight and description are not set, will use defaults or be null
                             .build();
        
        Product basicItem = new Product.ProductBuilder("GEN-001", "Basic Item", 9.99)
                               .build(); // Only required fields

        System.out.println(laptop);
        System.out.println(tShirt);
        System.out.println(basicItem);

        try {
             Product invalidProduct = new Product.ProductBuilder("ERR-001", "Error Product", -5.00)
                                    .build();
             System.out.println(invalidProduct);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating product: " + e.getMessage());
        }
    }
}
```
**Explanation:**
*   The `Product` class has a private constructor that takes a `ProductBuilder` object.
*   `ProductBuilder` is a static nested class. It has fields corresponding to `Product`'s fields.
*   The builder has a constructor for required fields and "setter-like" methods (e.g., `description()`, `category()`) for optional fields. These methods return the builder itself, allowing for a fluent (chained) interface.
*   The `build()` method in the builder creates and returns the `Product` instance.
*   This makes `Product` creation more readable and less error-prone, especially when many fields are optional.

---

### 5. Prototype Pattern

**Concept:** Specifies the kinds of objects to create using a prototypical instance, and creates new objects by copying (cloning) this prototype.

**E-commerce Situation:**
Imagine you have a "base" product configuration, like a standard T-shirt. Sellers might want to quickly create new T-shirt listings that are very similar to this base T-shirt but with minor variations (e.g., different color, different graphic, slightly different price). Instead of filling all details from scratch, they can clone the base T-shirt and modify the clone.

**Java Code Example:**

```java
// ProductPrototype.java - The Prototype interface
interface ProductPrototype extends Cloneable { // Must extend Cloneable for Object.clone()
    ProductPrototype cloneProduct();
    void display();
    void setAttribute(String key, String value); // Simple way to modify
}

// ConcretePrototype: ConfigurableProduct.java
class ConfigurableProduct implements ProductPrototype {
    private String name;
    private String baseSku;
    private double price;
    private java.util.Map<String, String> attributes; // e.g., color, size

    public ConfigurableProduct(String name, String baseSku, double price) {
        this.name = name;
        this.baseSku = baseSku;
        this.price = price;
        this.attributes = new java.util.HashMap<>();
        System.out.println("Creating original product: " + name);
    }

    // Copy constructor for deep copying attributes map if needed, though String is immutable
    private ConfigurableProduct(ConfigurableProduct other) {
        this.name = other.name;
        this.baseSku = other.baseSku;
        this.price = other.price;
        // For deep copy of mutable objects in attributes, you'd iterate and clone them
        this.attributes = new java.util.HashMap<>(other.attributes);
    }

    public void setAttribute(String key, String value) {
        this.attributes.put(key, value);
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public ProductPrototype cloneProduct() {
        System.out.println("Cloning product: " + this.name);
        // Using copy constructor for better control over cloning process
        return new ConfigurableProduct(this); 
        // Alternatively, for simple cases:
        // try {
        //     ConfigurableProduct cloned = (ConfigurableProduct) super.clone();
        //     // Deep copy mutable fields if any
        //     cloned.attributes = new java.util.HashMap<>(this.attributes);
        //     return cloned;
        // } catch (CloneNotSupportedException e) {
        //     e.printStackTrace(); // Should not happen if Cloneable is implemented
        //     return null;
        // }
    }

    @Override
    public void display() {
        System.out.println("--- Product ---");
        System.out.println("Name: " + name);
        System.out.println("SKU: " + baseSku);
        System.out.println("Price: $" + price);
        attributes.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("---------------");
    }
}

// ProductRegistry.java (Optional, to manage prototypes)
class ProductRegistry {
    private java.util.Map<String, ProductPrototype> prototypes = new java.util.HashMap<>();

    public void addPrototype(String key, ProductPrototype prototype) {
        prototypes.put(key, prototype);
    }

    public ProductPrototype getPrototype(String key) {
        ProductPrototype p = prototypes.get(key);
        return p != null ? p.cloneProduct() : null;
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        // Create a base T-Shirt prototype
        ConfigurableProduct baseTShirt = new ConfigurableProduct("Basic T-Shirt", "TSHIRT-BASE", 15.00);
        baseTShirt.setAttribute("Material", "Cotton");
        baseTShirt.setAttribute("DefaultColor", "White");

        // Store it in a registry (optional)
        ProductRegistry registry = new ProductRegistry();
        registry.addPrototype("StandardTShirt", baseTShirt);

        // Seller wants to list a new Red T-Shirt
        ConfigurableProduct redTShirt = (ConfigurableProduct) registry.getPrototype("StandardTShirt");
        if (redTShirt != null) {
            redTShirt.setName("Red Cotton T-Shirt");
            redTShirt.setAttribute("Color", "Red"); // Add/override attribute
            redTShirt.setAttribute("DefaultColor", "Red"); // Override existing
            redTShirt.setPrice(16.50);
            redTShirt.display();
        }

        // Seller wants to list a new Blue T-Shirt
        ConfigurableProduct blueTShirt = (ConfigurableProduct) registry.getPrototype("StandardTShirt");
        if (blueTShirt != null) {
            blueTShirt.setName("Blue Cotton T-Shirt");
            blueTShirt.setAttribute("Color", "Blue");
            blueTShirt.setAttribute("DefaultColor", "Blue");
            blueTShirt.setPrice(16.00);
            blueTShirt.display();
        }
        
        System.out.println("\nOriginal base T-Shirt (should be unchanged):");
        baseTShirt.display(); // Verify original prototype is not modified by clones
    }
}
```
**Explanation:**
*   `ProductPrototype` extends `Cloneable` and defines a `cloneProduct()` method.
*   `ConfigurableProduct` is a concrete prototype. Its `cloneProduct()` method creates a copy of itself.
    *   **Important:** The `clone()` method needs to handle deep copying if the object contains references to other mutable objects. In this simple example, `String`s are immutable, and the `attributes` map is re-created with copied references (which is fine for Strings). If `attributes` held mutable custom objects, those would also need to be cloned. I've shown an alternative using a copy constructor which provides more control.
*   The `ProductRegistry` (optional) can store and manage different prototypes.
*   When a new product variant is needed, we get a prototype (e.g., `baseTShirt` or from the registry), clone it, and then modify the cloned instance. This avoids re-creating the object from scratch.

---

These examples should give you a good starting point for understanding how these design patterns can be applied in a real-world e-commerce project. Remember that the key is to identify the problem the pattern solves and see if it fits your specific situation.