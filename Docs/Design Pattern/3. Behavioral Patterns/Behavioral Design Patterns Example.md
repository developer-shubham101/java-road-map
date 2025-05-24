Okay, let's dive into the final set of design patterns, the Behavioral Patterns, with examples tailored for an e-commerce website.

---

### 1. Chain of Responsibility Pattern

**Concept:** Avoids coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. It chains the receiving objects and passes the request along the chain until an object handles it or the chain ends.

**E-commerce Situation:**
Imagine an order approval process in your e-commerce system. An order might need to go through several checks:
1.  Inventory Check: Is the item in stock?
2.  Fraud Check: Does the order look suspicious?
3.  Payment Authorization Check: Can the payment be authorized?
Each check can be a handler in a chain.

**Java Code Example:**

```java
// Request class (can be more complex)
class OrderRequest {
    String productId;
    int quantity;
    double amount;
    boolean approved = true; // Default to true, handlers can set to false
    String notes = "";

    public OrderRequest(String productId, int quantity, double amount) {
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }
}

// Handler interface
interface OrderHandler {
    void setNextHandler(OrderHandler nextHandler);
    void processOrder(OrderRequest request);
}

// Abstract base handler (optional, for common logic)
abstract class AbstractOrderHandler implements OrderHandler {
    protected OrderHandler nextHandler;

    @Override
    public void setNextHandler(OrderHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Template method for processing and passing to next
    protected void passToNext(OrderRequest request) {
        if (nextHandler != null && request.approved) { // Only pass if still approved
            nextHandler.processOrder(request);
        }
    }
}

// Concrete Handlers
class InventoryHandler extends AbstractOrderHandler {
    @Override
    public void processOrder(OrderRequest request) {
        System.out.println("InventoryHandler: Checking stock for " + request.productId);
        if (request.quantity > 5) { // Simple stock rule
            request.approved = false;
            request.notes += "Inventory: Not enough stock. ";
            System.out.println("InventoryHandler: Stock check FAILED for " + request.productId);
        } else {
            System.out.println("InventoryHandler: Stock check PASSED for " + request.productId);
        }
        passToNext(request);
    }
}

class FraudDetectionHandler extends AbstractOrderHandler {
    @Override
    public void processOrder(OrderRequest request) {
        System.out.println("FraudDetectionHandler: Checking for fraud on order amount $" + request.amount);
        if (request.amount > 1000) { // Simple fraud rule
            request.approved = false;
            request.notes += "Fraud: High value order flagged. ";
            System.out.println("FraudDetectionHandler: Fraud check FAILED for amount " + request.amount);
        } else {
            System.out.println("FraudDetectionHandler: Fraud check PASSED for amount " + request.amount);
        }
        passToNext(request);
    }
}

class PaymentAuthHandler extends AbstractOrderHandler {
    @Override
    public void processOrder(OrderRequest request) {
        System.out.println("PaymentAuthHandler: Authorizing payment for $" + request.amount);
        // For simplicity, let's assume payment always succeeds if it reaches here and is approved
        if (request.approved) {
            System.out.println("PaymentAuthHandler: Payment authorized.");
            request.notes += "Payment: Authorized. ";
        } else {
            System.out.println("PaymentAuthHandler: Payment authorization skipped due to prior failure.");
            request.notes += "Payment: Not attempted. ";
        }
        // No next handler in this simple chain for successful payment
        if (!request.approved) {
            passToNext(request); // Could pass to an error logging handler etc.
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        // Build the chain
        OrderHandler inventory = new InventoryHandler();
        OrderHandler fraud = new FraudDetectionHandler();
        OrderHandler payment = new PaymentAuthHandler();

        inventory.setNextHandler(fraud);
        fraud.setNextHandler(payment);

        // Process an order
        System.out.println("--- Processing Order 1 (should pass) ---");
        OrderRequest order1 = new OrderRequest("PROD123", 2, 50.00);
        inventory.processOrder(order1);
        System.out.println("Order 1 Final Status: Approved=" + order1.approved + ", Notes: " + order1.notes);

        System.out.println("\n--- Processing Order 2 (fail inventory) ---");
        OrderRequest order2 = new OrderRequest("PROD456", 10, 100.00);
        inventory.processOrder(order2);
        System.out.println("Order 2 Final Status: Approved=" + order2.approved + ", Notes: " + order2.notes);

        System.out.println("\n--- Processing Order 3 (fail fraud) ---");
        OrderRequest order3 = new OrderRequest("PROD789", 3, 1500.00);
        inventory.processOrder(order3);
        System.out.println("Order 3 Final Status: Approved=" + order3.approved + ", Notes: " + order3.notes);
    }
}
```
**Explanation:**
*   Each handler (`InventoryHandler`, `FraudDetectionHandler`, `PaymentAuthHandler`) decides if it can process the request.
*   If a handler processes it or a condition is met, it might stop the chain. Otherwise, it passes the request to the `nextHandler`.
*   This decouples senders from specific receivers and makes it easy to add or remove handlers.

---

### 2. Command Pattern (Action, Transaction)

**Concept:** Encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

**E-commerce Situation:**
Actions performed by a user on their shopping cart, like "Add Item," "Remove Item," "Update Quantity." These actions can be represented as command objects. This allows for features like an undo stack or logging actions.

**Java Code Example:**

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Receiver: ShoppingCart
class ShoppingCart {
    private List<String> items = new ArrayList<>();

    public void addItem(String item) {
        items.add(item);
        System.out.println("Cart: Added '" + item + "'. Current items: " + items);
    }

    public void removeItem(String item) {
        if (items.remove(item)) {
            System.out.println("Cart: Removed '" + item + "'. Current items: " + items);
        } else {
            System.out.println("Cart: Item '" + item + "' not found to remove.");
        }
    }
}

// Command Interface
interface CartCommand {
    void execute();
    void undo(); // For undoable commands
}

// Concrete Command: AddItemCommand
class AddItemCommand implements CartCommand {
    private ShoppingCart cart;
    private String item;

    public AddItemCommand(ShoppingCart cart, String item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public void execute() {
        cart.addItem(item);
    }

    @Override
    public void undo() {
        System.out.print("Undoing AddItem: ");
        cart.removeItem(item);
    }
}

// Concrete Command: RemoveItemCommand
class RemoveItemCommand implements CartCommand {
    private ShoppingCart cart;
    private String item;

    public RemoveItemCommand(ShoppingCart cart, String item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public void execute() {
        cart.removeItem(item);
    }
    
    @Override
    public void undo() {
        System.out.print("Undoing RemoveItem: ");
        cart.addItem(item); // To undo removal, we add it back
    }
}

// Invoker: CartActionsManager
class CartActionsManager {
    private Stack<CartCommand> commandHistory = new Stack<>();

    public void executeCommand(CartCommand command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            CartCommand lastCommand = commandHistory.pop();
            System.out.println("--- Undoing last action ---");
            lastCommand.undo();
        } else {
            System.out.println("No actions to undo.");
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        ShoppingCart myCart = new ShoppingCart();
        CartActionsManager actionsManager = new CartActionsManager();

        actionsManager.executeCommand(new AddItemCommand(myCart, "Laptop"));
        actionsManager.executeCommand(new AddItemCommand(myCart, "Mouse"));
        actionsManager.executeCommand(new AddItemCommand(myCart, "Keyboard"));

        actionsManager.executeCommand(new RemoveItemCommand(myCart, "Mouse"));

        actionsManager.undoLastCommand(); // Undoes "Remove Mouse" -> Mouse is added back
        actionsManager.undoLastCommand(); // Undoes "Add Keyboard" -> Keyboard is removed
    }
}
```
**Explanation:**
*   `CartCommand` is the command interface with `execute()` and `undo()`.
*   `AddItemCommand` and `RemoveItemCommand` are concrete commands that encapsulate an action on the `ShoppingCart` (the receiver).
*   `CartActionsManager` (the invoker) executes commands and can keep a history for undo operations.

---

### 3. Interpreter Pattern

**Concept:** Given a language, defines a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language. Typically used for parsing and evaluating simple languages or query expressions.

**E-commerce Situation:**
A simple system for applying product discounts based on specific rules, e.g., "category IS 'Electronics' AND price OVER 100". This is a highly specialized pattern and often overkill for simple scenarios. Let's simplify it to interpreting a basic discount rule string.

**Java Code Example:**

```java
import java.util.Map;
import java.util.HashMap;

// Context (Product data)
class ProductContext {
    String category;
    double price;

    public ProductContext(String category, double price) {
        this.category = category;
        this.price = price;
    }
}

// Abstract Expression
interface DiscountExpression {
    boolean interpret(ProductContext context);
}

// Terminal Expressions
class CategoryExpression implements DiscountExpression {
    private String expectedCategory;

    public CategoryExpression(String expectedCategory) {
        this.expectedCategory = expectedCategory;
    }

    @Override
    public boolean interpret(ProductContext context) {
        return expectedCategory.equalsIgnoreCase(context.category);
    }
}

class PriceExpression implements DiscountExpression {
    private double minPrice;

    public PriceExpression(double minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean interpret(ProductContext context) {
        return context.price > minPrice;
    }
}

// Non-Terminal Expression (combines other expressions)
class AndExpression implements DiscountExpression {
    private DiscountExpression expr1;
    private DiscountExpression expr2;

    public AndExpression(DiscountExpression expr1, DiscountExpression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public boolean interpret(ProductContext context) {
        return expr1.interpret(context) && expr2.interpret(context);
    }
}

// Main.java (Client)
public class Main {
    // Helper to build expression from a simple rule string (very basic parser)
    public static DiscountExpression getDiscountRule(String ruleString) {
        // "CATEGORY=Electronics AND PRICE_OVER=100"
        if (ruleString.contains("AND")) {
            String[] parts = ruleString.split(" AND ");
            // For simplicity, assume only two parts and specific keywords
            DiscountExpression expr1 = parseSimpleCondition(parts[0]);
            DiscountExpression expr2 = parseSimpleCondition(parts[1]);
            if (expr1 != null && expr2 != null) {
                return new AndExpression(expr1, expr2);
            }
        } else {
            return parseSimpleCondition(ruleString);
        }
        return ctx -> false; // Default no discount
    }

    private static DiscountExpression parseSimpleCondition(String condition) {
        if (condition.startsWith("CATEGORY=")) {
            return new CategoryExpression(condition.substring("CATEGORY=".length()));
        } else if (condition.startsWith("PRICE_OVER=")) {
            return new PriceExpression(Double.parseDouble(condition.substring("PRICE_OVER=".length())));
        }
        return null;
    }

    public static void main(String[] args) {
        DiscountExpression electronicsOver100Rule = getDiscountRule("CATEGORY=Electronics AND PRICE_OVER=100");
        DiscountExpression booksRule = getDiscountRule("CATEGORY=Books");

        ProductContext laptop = new ProductContext("Electronics", 150.0);
        ProductContext cheapPhone = new ProductContext("Electronics", 80.0);
        ProductContext book = new ProductContext("Books", 25.0);

        System.out.println("Laptop (Electronics, $150) eligible for 'Electronics Over $100' discount? "
                           + electronicsOver100Rule.interpret(laptop)); // true
        System.out.println("Cheap Phone (Electronics, $80) eligible for 'Electronics Over $100' discount? "
                           + electronicsOver100Rule.interpret(cheapPhone)); // false
        System.out.println("Book (Books, $25) eligible for 'Electronics Over $100' discount? "
                           + electronicsOver100Rule.interpret(book)); // false
        System.out.println("Book (Books, $25) eligible for 'Books' discount? "
                           + booksRule.interpret(book)); // true
    }
}
```
**Explanation:**
*   `DiscountExpression` is the interface for all expressions.
*   `CategoryExpression` and `PriceExpression` are terminal expressions that evaluate a basic condition.
*   `AndExpression` is a non-terminal expression that combines other expressions.
*   The client builds an expression tree (e.g., based on a rule string) and then calls `interpret()` on the root expression with a `ProductContext`.
*   Note: Building a robust parser for a complex language is non-trivial. This example uses a very simplified "parser."

---

### 4. Iterator Pattern (Cursor)

**Concept:** Provides a way to access the elements of an aggregate object (collection) sequentially without exposing its underlying representation (e.g., `ArrayList`, `LinkedList`, custom data structure).

**E-commerce Situation:**
You have a `ProductCatalog` which might store products in different ways (e.g., a `List`, a `Map`, or a custom tree structure). The Iterator pattern allows clients to traverse the products in the catalog consistently, regardless of the internal storage.

**Java Code Example:**

```java
import java.util.ArrayList;
import java.util.List;
// java.util.Iterator is the standard Java interface for this pattern.
// We'll create custom ones for clarity.

// Product class (simple)
class Product {
    String name;
    double price;
    public Product(String name, double price) { this.name = name; this.price = price; }
    @Override public String toString() { return name + " - $" + price; }
}

// Aggregate Interface
interface ProductCollection {
    ProductIterator createIterator();
}

// Iterator Interface
interface ProductIterator {
    boolean hasNext();
    Product next();
}

// Concrete Aggregate: ProductCatalog (using ArrayList internally)
class ProductCatalog implements ProductCollection {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public ProductIterator createIterator() {
        return new CatalogIterator(products);
    }

    // Concrete Iterator (nested class for access to products)
    private class CatalogIterator implements ProductIterator {
        private List<Product> productList;
        private int position = 0;

        public CatalogIterator(List<Product> productList) {
            this.productList = productList;
        }

        @Override
        public boolean hasNext() {
            return position < productList.size();
        }

        @Override
        public Product next() {
            if (this.hasNext()) {
                return productList.get(position++);
            }
            return null; // Or throw NoSuchElementException
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        ProductCatalog catalog = new ProductCatalog();
        catalog.addProduct(new Product("Laptop", 1200.00));
        catalog.addProduct(new Product("Mouse", 25.00));
        catalog.addProduct(new Product("Keyboard", 75.00));

        ProductIterator iterator = catalog.createIterator();

        System.out.println("Products in catalog:");
        while (iterator.hasNext()) {
            Product product = iterator.next();
            System.out.println(product);
        }
        
        // Using Java's built-in Iterator (if ProductCatalog implemented Iterable<Product>)
        // for (Product product : catalog) { /* ... */ }
    }
}
```
**Explanation:**
*   `ProductCollection` defines a method `createIterator()`.
*   `ProductIterator` defines methods for traversal (`hasNext()`, `next()`).
*   `ProductCatalog` is a concrete collection that creates a specific `CatalogIterator`.
*   The client uses the `ProductIterator` to go through products without knowing that `ProductCatalog` uses an `ArrayList` internally.

---

### 5. Mediator Pattern

**Concept:** Defines an object (the mediator) that encapsulates how a set of objects (colleagues) interact. It promotes loose coupling by keeping colleagues from referring to each other explicitly.

**E-commerce Situation:**
In a complex product configurator UI (e.g., "Build Your Own PC"), changing one component (e.g., CPU) might affect available options for other components (e.g., Motherboard, RAM) and update the total price. A `ConfiguratorMediator` can manage these dependencies.

**Java Code Example:**

```java
import java.util.ArrayList;
import java.util.List;

// Mediator Interface
interface ConfiguratorMediator {
    void componentChanged(UIComponent component);
    void registerComponent(UIComponent component);
}

// Colleague (Abstract UI Component)
abstract class UIComponent {
    protected ConfiguratorMediator mediator;
    protected String name;

    public UIComponent(ConfiguratorMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
        mediator.registerComponent(this);
    }

    public String getName() { return name; }
    public abstract void receiveUpdate(String message); // Method for mediator to update component
    public void changed() { // Called when this component's state changes
        mediator.componentChanged(this);
    }
}

// Concrete Colleagues
class DropdownComponent extends UIComponent {
    private String selectedValue;

    public DropdownComponent(ConfiguratorMediator mediator, String name) {
        super(mediator, name);
    }

    public void selectValue(String value) {
        this.selectedValue = value;
        System.out.println(name + " selected: " + value);
        changed(); // Notify mediator
    }
    public String getSelectedValue() { return selectedValue; }
    @Override public void receiveUpdate(String message) { System.out.println(name + " received update: " + message); }
}

class PriceDisplayComponent extends UIComponent {
    private double price;

    public PriceDisplayComponent(ConfiguratorMediator mediator, String name) {
        super(mediator, name);
    }

    public void setPrice(double price) {
        this.price = price;
        System.out.println(name + " updated to: $" + price);
        // This component might not trigger 'changed()' if it only displays
    }
    @Override public void receiveUpdate(String message) { System.out.println(name + " received update: " + message); }
}

// Concrete Mediator: PCConfiguratorMediator
class PCConfiguratorMediator implements ConfiguratorMediator {
    private DropdownComponent cpuDropdown;
    private DropdownComponent motherboardDropdown;
    private PriceDisplayComponent totalPriceDisplay;
    // Could have more components (RAM, GPU, etc.)

    @Override
    public void registerComponent(UIComponent component) {
        if (component.getName().equals("CPU")) this.cpuDropdown = (DropdownComponent) component;
        else if (component.getName().equals("Motherboard")) this.motherboardDropdown = (DropdownComponent) component;
        else if (component.getName().equals("TotalPrice")) this.totalPriceDisplay = (PriceDisplayComponent) component;
    }

    @Override
    public void componentChanged(UIComponent component) {
        System.out.println("Mediator: " + component.getName() + " changed.");
        // Logic to handle interactions
        if (component == cpuDropdown) {
            if ("CPU-HighEnd".equals(cpuDropdown.getSelectedValue())) {
                motherboardDropdown.receiveUpdate("Consider Motherboard-X for HighEnd CPU.");
            } else {
                motherboardDropdown.receiveUpdate("Standard Motherboard options available.");
            }
        }
        // Update total price (simplified)
        double currentPrice = 0;
        if (cpuDropdown != null && "CPU-HighEnd".equals(cpuDropdown.getSelectedValue())) currentPrice += 500;
        else if (cpuDropdown != null) currentPrice += 200;

        if (motherboardDropdown != null && "Motherboard-X".equals(motherboardDropdown.getSelectedValue())) currentPrice += 150;
        else if (motherboardDropdown != null) currentPrice += 80;
        
        if (totalPriceDisplay != null) totalPriceDisplay.setPrice(currentPrice);
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        ConfiguratorMediator mediator = new PCConfiguratorMediator();

        DropdownComponent cpuSelector = new DropdownComponent(mediator, "CPU");
        DropdownComponent motherboardSelector = new DropdownComponent(mediator, "Motherboard");
        PriceDisplayComponent priceDisplay = new PriceDisplayComponent(mediator, "TotalPrice");

        System.out.println("--- Simulating User Actions ---");
        cpuSelector.selectValue("CPU-MidRange");
        System.out.println("-----");
        motherboardSelector.selectValue("Motherboard-Standard");
        System.out.println("-----");
        cpuSelector.selectValue("CPU-HighEnd"); // This will trigger updates
    }
}
```
**Explanation:**
*   `ConfiguratorMediator` knows about all `UIComponent` colleagues.
*   When a `UIComponent` changes (e.g., `cpuSelector.selectValue()`), it calls its `changed()` method, which notifies the `mediator`.
*   The `mediator` then decides which other colleagues need to be updated (e.g., `motherboardSelector.receiveUpdate()`, `priceDisplay.setPrice()`).
*   Colleagues don't communicate directly, only through the mediator.

---

### 6. Memento Pattern (Token)

**Concept:** Without violating encapsulation, captures and externalizes an object's internal state so that the object can be restored to this state later.

**E-commerce Situation:**
A user is customizing a complex product (e.g., a t-shirt with custom text, color, and image). They might want to save their current design (state) and come back to it later, or undo a change.

**Java Code Example:**

```java
// Memento: Stores the state of the Originator
class ProductCustomizationMemento {
    private final String color;
    private final String customText;
    // Other state variables

    public ProductCustomizationMemento(String color, String customText) {
        this.color = color;
        this.customText = customText;
    }

    public String getColor() { return color; }
    public String getCustomText() { return customText; }
    // Getters for other state
}

// Originator: The object whose state needs to be saved/restored
class CustomizableProduct {
    private String color;
    private String customText;

    public void setColor(String color) {
        this.color = color;
        System.out.println("Product color set to: " + color);
    }

    public void setCustomText(String text) {
        this.customText = text;
        System.out.println("Product custom text set to: '" + text + "'");
    }

    // Creates a Memento containing a snapshot of its current state
    public ProductCustomizationMemento saveToMemento() {
        System.out.println("Saving current customization to Memento.");
        return new ProductCustomizationMemento(color, customText);
    }

    // Restores its state from a Memento object
    public void restoreFromMemento(ProductCustomizationMemento memento) {
        this.color = memento.getColor();
        this.customText = memento.getCustomText();
        System.out.println("Restored customization from Memento: Color=" + color + ", Text='" + customText + "'");
    }

    public void display() {
        System.out.println("Current Customization - Color: " + color + ", Text: '" + customText + "'");
    }
}

// Caretaker: Manages Mementos (e.g., history of saved states)
class CustomizationHistory {
    private java.util.Stack<ProductCustomizationMemento> history = new java.util.Stack<>();

    public void saveState(CustomizableProduct product) {
        history.push(product.saveToMemento());
    }

    public void undo(CustomizableProduct product) {
        if (!history.isEmpty()) {
            product.restoreFromMemento(history.pop());
        } else {
            System.out.println("No states to undo.");
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        CustomizableProduct tShirt = new CustomizableProduct();
        CustomizationHistory history = new CustomizationHistory();

        tShirt.setColor("Red");
        tShirt.setCustomText("Hello World!");
        history.saveState(tShirt); // Save state 1
        tShirt.display();

        tShirt.setColor("Blue");
        history.saveState(tShirt); // Save state 2 (Text is still "Hello World!")
        tShirt.display();

        tShirt.setCustomText("Awesome Design");
        // Not saved yet
        tShirt.display();

        System.out.println("\n--- Undoing last saved state ---");
        history.undo(tShirt); // Restores to: Color=Blue, Text=Hello World!
        tShirt.display();

        System.out.println("\n--- Undoing again ---");
        history.undo(tShirt); // Restores to: Color=Red, Text=Hello World!
        tShirt.display();
        
        System.out.println("\n--- Undoing again (no more states) ---");
        history.undo(tShirt);
        tShirt.display();
    }
}
```
**Explanation:**
*   `CustomizableProduct` (Originator) can create a `ProductCustomizationMemento` to save its state and restore its state from one.
*   `ProductCustomizationMemento` holds the state but doesn't have behavior.
*   `CustomizationHistory` (Caretaker) stores mementos, allowing the user to revert the `CustomizableProduct` to a previous state.

---

### 7. Observer Pattern (Dependents, Publish-Subscribe)

**Concept:** Defines a one-to-many dependency between objects so that when one object (the subject or publisher) changes state, all its dependents (observers or subscribers) are notified and updated automatically.

**E-commerce Situation:**
When a product's stock level changes (e.g., it comes back in stock), users who have added this product to their wishlist or signed up for "back-in-stock" notifications should be informed.

**Java Code Example:**

```java
import java.util.ArrayList;
import java.util.List;

// Observer Interface
interface StockObserver {
    void update(Product product, int newStockLevel);
}

// Subject Interface (Observable)
interface ProductNotifier {
    void registerObserver(StockObserver observer);
    void removeObserver(StockObserver observer);
    void notifyObservers();
}

// Concrete Subject: Product
class Product implements ProductNotifier {
    private String productId;
    private String name;
    private int stockLevel;
    private List<StockObserver> observers = new ArrayList<>();

    public Product(String productId, String name, int initialStock) {
        this.productId = productId;
        this.name = name;
        this.stockLevel = initialStock;
    }

    public String getName() { return name; }
    public int getStockLevel() { return stockLevel; }

    public void setStockLevel(int newStockLevel) {
        System.out.println("Product '" + name + "' stock changed from " + this.stockLevel + " to " + newStockLevel);
        this.stockLevel = newStockLevel;
        if (newStockLevel > 0) { // Notify only if it's back in stock or stock increased
            notifyObservers();
        }
    }

    @Override
    public void registerObserver(StockObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        System.out.println("Notifying " + observers.size() + " observers for product '" + name + "'...");
        for (StockObserver observer : observers) {
            observer.update(this, this.stockLevel);
        }
    }
}

// Concrete Observer: UserWishlistNotifier
class UserWishlistNotifier implements StockObserver {
    private String userId;

    public UserWishlistNotifier(String userId) {
        this.userId = userId;
    }

    @Override
    public void update(Product product, int newStockLevel) {
        if (newStockLevel > 0) {
            System.out.println("WISHLIST NOTIFICATION for User " + userId +
                               ": Product '" + product.getName() + "' is back in stock! Current stock: " + newStockLevel);
            // In a real app, send an email/SMS/push notification
        }
    }
}

// Concrete Observer: InternalInventoryAlert
class InternalInventoryAlert implements StockObserver {
    @Override
    public void update(Product product, int newStockLevel) {
        if (newStockLevel > 0 && newStockLevel < 5) { // Low stock alert
             System.out.println("INTERNAL ALERT: Product '" + product.getName() +
                               "' stock is low (" + newStockLevel + "). Consider reordering.");
        } else if (newStockLevel > 0) {
             System.out.println("INTERNAL INFO: Product '" + product.getName() +
                               "' stock updated to " + newStockLevel + ".");
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Product laptop = new Product("PROD001", "Super Laptop", 0); // Initially out of stock

        UserWishlistNotifier user1 = new UserWishlistNotifier("Alice");
        UserWishlistNotifier user2 = new UserWishlistNotifier("Bob");
        InternalInventoryAlert inventoryTeam = new InternalInventoryAlert();

        laptop.registerObserver(user1);
        laptop.registerObserver(user2);
        laptop.registerObserver(inventoryTeam);

        System.out.println("\n--- Updating stock to 3 ---");
        laptop.setStockLevel(3); // This will trigger notifications

        laptop.removeObserver(user2); // Bob is no longer interested
        System.out.println("\n--- Bob unsubscribed. Updating stock to 10 ---");
        laptop.setStockLevel(10);
    }
}
```
**Explanation:**
*   `Product` is the subject. When its `stockLevel` changes (via `setStockLevel`), it calls `notifyObservers()`.
*   `StockObserver` is the interface for observers.
*   `UserWishlistNotifier` and `InternalInventoryAlert` are concrete observers that get updated when the product's state changes.

---

### 8. State Pattern (Objects for States)

**Concept:** Allows an object to alter its behavior when its internal state changes. The object will appear to change its class. This is achieved by encapsulating states into separate classes and delegating behavior to the current state object.

**E-commerce Situation:**
An `Order` in an e-commerce system goes through different states: `PendingConfirmation`, `Processing`, `Shipped`, `Delivered`, `Cancelled`. The actions you can perform on an order (e.g., `cancelOrder()`, `shipOrder()`) and how they behave depend on the order's current state.

**Java Code Example:**

```java
// Forward declaration for context
class OrderContext {
    private OrderState currentState;
    public final String orderId;

    public OrderContext(String orderId) {
        this.orderId = orderId;
        // Initial state
        this.currentState = new PendingConfirmationState(this);
        System.out.println("Order " + orderId + " created. Initial state: " + currentState.getName());
    }

    public void setState(OrderState state) {
        this.currentState = state;
        System.out.println("Order " + orderId + " transitioned to state: " + currentState.getName());
    }

    // Delegate actions to the current state
    public void confirmOrder() { currentState.confirmOrder(); }
    public void shipOrder() { currentState.shipOrder(); }
    public void deliverOrder() { currentState.deliverOrder(); }
    public void cancelOrder() { currentState.cancelOrder(); }
    public String getCurrentStateName() { return currentState.getName(); }
}


// State Interface
interface OrderState {
    void confirmOrder();
    void shipOrder();
    void deliverOrder();
    void cancelOrder();
    String getName(); // For display
}

// Concrete States
class PendingConfirmationState implements OrderState {
    private OrderContext order;
    public PendingConfirmationState(OrderContext order) { this.order = order; }

    @Override public void confirmOrder() {
        System.out.println("Order " + order.orderId + ": Confirmed. Moving to Processing.");
        order.setState(new ProcessingState(order));
    }
    @Override public void shipOrder() { System.out.println("Order " + order.orderId + ": Cannot ship. Order not confirmed yet."); }
    @Override public void deliverOrder() { System.out.println("Order " + order.orderId + ": Cannot deliver. Order not shipped yet."); }
    @Override public void cancelOrder() {
        System.out.println("Order " + order.orderId + ": Cancelled while pending confirmation.");
        order.setState(new CancelledState(order));
    }
    @Override public String getName() { return "Pending Confirmation"; }
}

class ProcessingState implements OrderState {
    private OrderContext order;
    public ProcessingState(OrderContext order) { this.order = order; }

    @Override public void confirmOrder() { System.out.println("Order " + order.orderId + ": Already confirmed."); }
    @Override public void shipOrder() {
        System.out.println("Order " + order.orderId + ": Shipped. Moving to Shipped state.");
        order.setState(new ShippedState(order));
    }
    @Override public void deliverOrder() { System.out.println("Order " + order.orderId + ": Cannot deliver. Order not shipped yet."); }
    @Override public void cancelOrder() {
        System.out.println("Order " + order.orderId + ": Cancelled during processing.");
        order.setState(new CancelledState(order));
    }
    @Override public String getName() { return "Processing"; }
}

class ShippedState implements OrderState {
    private OrderContext order;
    public ShippedState(OrderContext order) { this.order = order; }

    @Override public void confirmOrder() { System.out.println("Order " + order.orderId + ": Already confirmed and shipped."); }
    @Override public void shipOrder() { System.out.println("Order " + order.orderId + ": Already shipped."); }
    @Override public void deliverOrder() {
        System.out.println("Order " + order.orderId + ": Delivered. Moving to Delivered state.");
        order.setState(new DeliveredState(order));
    }
    @Override public void cancelOrder() { System.out.println("Order " + order.orderId + ": Cannot cancel. Order already shipped."); }
    @Override public String getName() { return "Shipped"; }
}

class DeliveredState implements OrderState {
    private OrderContext order;
    public DeliveredState(OrderContext order) { this.order = order; }
    // In Delivered state, usually fewer actions are possible.
    @Override public void confirmOrder() { System.out.println("Order " + order.orderId + ": Already delivered."); }
    @Override public void shipOrder() { System.out.println("Order " + order.orderId + ": Already delivered."); }
    @Override public void deliverOrder() { System.out.println("Order " + order.orderId + ": Already delivered."); }
    @Override public void cancelOrder() { System.out.println("Order " + order.orderId + ": Cannot cancel. Order already delivered."); }
    @Override public String getName() { return "Delivered"; }
}

class CancelledState implements OrderState {
    private OrderContext order;
    public CancelledState(OrderContext order) { this.order = order; }
    // In Cancelled state, no further actions typically.
    @Override public void confirmOrder() { System.out.println("Order " + order.orderId + ": Cannot confirm. Order is cancelled."); }
    @Override public void shipOrder() { System.out.println("Order " + order.orderId + ": Cannot ship. Order is cancelled."); }
    @Override public void deliverOrder() { System.out.println("Order " + order.orderId + ": Cannot deliver. Order is cancelled."); }
    @Override public void cancelOrder() { System.out.println("Order " + order.orderId + ": Already cancelled."); }
    @Override public String getName() { return "Cancelled"; }
}


// Main.java
public class Main {
    public static void main(String[] args) {
        OrderContext order = new OrderContext("ORD123");

        order.confirmOrder(); // Pending -> Processing
        order.shipOrder();    // Processing -> Shipped
        order.deliverOrder(); // Shipped -> Delivered
        order.cancelOrder();  // Tries to cancel, but already delivered

        System.out.println("\n--- New Order ---");
        OrderContext order2 = new OrderContext("ORD456");
        order2.confirmOrder();
        order2.cancelOrder(); // Processing -> Cancelled
        order2.shipOrder();   // Tries to ship, but already cancelled
    }
}
```
**Explanation:**
*   `OrderContext` holds a reference to the current `OrderState`.
*   `OrderState` is an interface defining actions.
*   Each concrete state (`PendingConfirmationState`, `ProcessingState`, etc.) implements these actions differently and handles transitions to other states by calling `order.setState()`.
*   The `OrderContext` delegates calls like `confirmOrder()` to its current state object.

---

### 9. Strategy Pattern (Policy)

**Concept:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

**E-commerce Situation:**
Calculating shipping costs. There can be different strategies:
1.  Flat Rate Shipping.
2.  Weight-Based Shipping.
3.  Shipping based on destination zone.
4.  Free shipping for orders over a certain amount.
The e-commerce system can choose a strategy at runtime.

**Java Code Example:**

```java
// Strategy Interface
interface ShippingStrategy {
    double calculate(Order order);
    String getName();
}

// Context Data: Order (simplified)
class Order {
    public double totalAmount;
    public double totalWeightKg;
    public String destinationZone;

    public Order(double totalAmount, double totalWeightKg, String destinationZone) {
        this.totalAmount = totalAmount;
        this.totalWeightKg = totalWeightKg;
        this.destinationZone = destinationZone;
    }
}

// Concrete Strategies
class FlatRateShipping implements ShippingStrategy {
    private double rate;
    public FlatRateShipping(double rate) { this.rate = rate; }
    @Override public double calculate(Order order) { return rate; }
    @Override public String getName() { return "Flat Rate Shipping ($" + rate + ")"; }
}

class WeightBasedShipping implements ShippingStrategy {
    private double ratePerKg;
    public WeightBasedShipping(double ratePerKg) { this.ratePerKg = ratePerKg; }
    @Override public double calculate(Order order) { return order.totalWeightKg * ratePerKg; }
    @Override public String getName() { return "Weight-Based Shipping ($" + ratePerKg + "/kg)"; }
}

class ZoneBasedShipping implements ShippingStrategy {
    @Override public double calculate(Order order) {
        if ("ZoneA".equals(order.destinationZone)) return 5.0;
        if ("ZoneB".equals(order.destinationZone)) return 10.0;
        return 15.0; // Default for other zones
    }
    @Override public String getName() { return "Zone-Based Shipping"; }
}

class FreeShippingOverThreshold implements ShippingStrategy {
    private double threshold;
    private ShippingStrategy fallbackStrategy; // What if not free?

    public FreeShippingOverThreshold(double threshold, ShippingStrategy fallback) {
        this.threshold = threshold;
        this.fallbackStrategy = fallback;
    }
    @Override public double calculate(Order order) {
        if (order.totalAmount >= threshold) return 0.0;
        return fallbackStrategy.calculate(order); // Use fallback if below threshold
    }
    @Override public String getName() { return "Free Shipping Over $" + threshold + " (otherwise " + fallbackStrategy.getName() + ")"; }
}


// Context: ShippingCalculator (uses a strategy)
class ShippingCostCalculator {
    private ShippingStrategy strategy;

    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateShippingCost(Order order) {
        if (strategy == null) {
            throw new IllegalStateException("Shipping strategy not set.");
        }
        System.out.println("Calculating shipping using: " + strategy.getName());
        double cost = strategy.calculate(order);
        System.out.println("Shipping cost: $" + cost);
        return cost;
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        ShippingCostCalculator calculator = new ShippingCostCalculator();
        Order order1 = new Order(50.0, 2.0, "ZoneA"); // $50, 2kg, ZoneA
        Order order2 = new Order(150.0, 5.0, "ZoneB"); // $150, 5kg, ZoneB

        // Use Flat Rate
        calculator.setStrategy(new FlatRateShipping(7.50));
        calculator.calculateShippingCost(order1);

        // Use Weight-Based
        calculator.setStrategy(new WeightBasedShipping(3.00)); // $3 per kg
        calculator.calculateShippingCost(order1); // 2kg * $3 = $6
        calculator.calculateShippingCost(order2); // 5kg * $3 = $15

        // Use Zone-Based
        calculator.setStrategy(new ZoneBasedShipping());
        calculator.calculateShippingCost(order1); // ZoneA = $5
        calculator.calculateShippingCost(order2); // ZoneB = $10
        
        // Use Free Shipping Over $100, fallback to Flat Rate $5
        ShippingStrategy freeShippingRule = new FreeShippingOverThreshold(100.0, new FlatRateShipping(5.00));
        calculator.setStrategy(freeShippingRule);
        calculator.calculateShippingCost(order1); // Amount 50 < 100 -> fallback to Flat Rate $5
        calculator.calculateShippingCost(order2); // Amount 150 > 100 -> Free Shipping $0
    }
}
```
**Explanation:**
*   `ShippingStrategy` is the interface for different calculation algorithms.
*   `FlatRateShipping`, `WeightBasedShipping`, etc., are concrete strategies.
*   `ShippingCostCalculator` is the context that uses a `ShippingStrategy`. It can switch strategies dynamically.

---

### 10. Template Method Pattern

**Concept:** Defines the skeleton of an algorithm in an operation (the template method), deferring some steps to subclasses. This lets subclasses redefine certain steps of an algorithm without changing the algorithm's overall structure.

**E-commerce Situation:**
A generic order processing flow. The overall flow (validate, process payment, update inventory, send notification) is fixed, but specific steps like `processPayment` (Credit Card vs. PayPal) or `sendNotification` (Email vs. SMS) can vary.

**Java Code Example:**

```java
// Abstract Class with the Template Method
abstract class OrderProcessor {
    // The Template Method - defines the skeleton of the algorithm
    public final void processOrder(String orderId, double amount, String customerEmail, String paymentType) {
        System.out.println("\n--- Processing Order: " + orderId + " ---");
        if (!validateOrder(orderId, amount)) {
            System.out.println("Order validation failed.");
            return;
        }
        
        // Step that varies and is implemented by subclasses
        if (!processPayment(paymentType, amount)) {
            System.out.println("Payment processing failed.");
            return;
        }
        
        updateInventory(orderId);
        
        // Another step that can vary
        sendConfirmation(customerEmail, orderId);
        
        System.out.println("Order " + orderId + " processed successfully.");
    }

    // Common steps (can be final if they should not be overridden)
    private boolean validateOrder(String orderId, double amount) {
        System.out.println("Validating order " + orderId + " for amount $" + amount);
        return amount > 0 && orderId != null && !orderId.isEmpty(); // Simple validation
    }

    private void updateInventory(String orderId) {
        System.out.println("Updating inventory for order " + orderId);
        // Actual inventory logic here
    }

    // Abstract "primitive" operations to be implemented by subclasses
    protected abstract boolean processPayment(String paymentType, double amount);
    protected abstract void sendConfirmation(String customerEmail, String orderId);

    // Hook method (optional, subclasses can override if needed, provides default behavior)
    protected void applyLoyaltyPoints(String customerId) {
        // Default: do nothing
        System.out.println("No loyalty points applied by default for " + customerId);
    }
}

// Concrete Subclass for Credit Card Orders
class CreditCardOrderProcessor extends OrderProcessor {
    @Override
    protected boolean processPayment(String paymentType, double amount) {
        if ("CreditCard".equalsIgnoreCase(paymentType)) {
            System.out.println("Processing Credit Card payment of $" + amount);
            // Actual credit card processing logic
            return true; // Assume success
        }
        System.out.println("Invalid payment type for CreditCardProcessor: " + paymentType);
        return false;
    }

    @Override
    protected void sendConfirmation(String customerEmail, String orderId) {
        System.out.println("Sending EMAIL confirmation for order " + orderId + " to " + customerEmail);
    }
}

// Concrete Subclass for PayPal Orders
class PayPalOrderProcessor extends OrderProcessor {
    @Override
    protected boolean processPayment(String paymentType, double amount) {
         if ("PayPal".equalsIgnoreCase(paymentType)) {
            System.out.println("Processing PayPal payment of $" + amount);
            // Actual PayPal API interaction
            return true; // Assume success
        }
        System.out.println("Invalid payment type for PayPalProcessor: " + paymentType);
        return false;
    }

    @Override
    protected void sendConfirmation(String customerEmail, String orderId) {
        System.out.println("Sending PayPal receipt and EMAIL confirmation for order " + orderId + " to " + customerEmail);
    }
    
    // Overriding a hook method
    @Override
    protected void applyLoyaltyPoints(String customerId) {
        System.out.println("Applying 10 loyalty points via PayPal integration for " + customerId);
    }
}


// Main.java
public class Main {
    public static void main(String[] args) {
        OrderProcessor ccProcessor = new CreditCardOrderProcessor();
        ccProcessor.processOrder("ORD-CC-001", 100.50, "customer1@example.com", "CreditCard");
        ccProcessor.applyLoyaltyPoints("customer1");


        OrderProcessor paypalProcessor = new PayPalOrderProcessor();
        paypalProcessor.processOrder("ORD-PP-002", 75.25, "customer2@example.com", "PayPal");
        paypalProcessor.applyLoyaltyPoints("customer2");
        
        // Example of trying an invalid payment type for a processor
        ccProcessor.processOrder("ORD-CC-003", 20.00, "customer3@example.com", "PayPal");
    }
}
```
**Explanation:**
*   `OrderProcessor` defines the `processOrder()` template method. This method calls common steps and abstract steps.
*   `processPayment()` and `sendConfirmation()` are abstract methods implemented by subclasses like `CreditCardOrderProcessor` and `PayPalOrderProcessor`.
*   `applyLoyaltyPoints()` is a hook method with default behavior that subclasses can override.
*   The overall algorithm structure in `processOrder()` is preserved, while specific steps are customized.

---

### 11. Visitor Pattern

**Concept:** Represents an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates. This is useful when you have a stable object structure but need to add various unrelated operations to it.

**E-commerce Situation:**
You have an order that consists of different types of items (e.g., `BookItem`, `ElectronicItem`, `ClothingItem`). You want to perform operations like:
1.  Calculate total shipping weight (where each item type might contribute differently).
2.  Apply specific tax calculations based on item type.
3.  Generate an XML/JSON representation for each item type.
Instead of adding these methods to each item class (which would bloat them), you can use Visitors.

**Java Code Example:**

```java
import java.util.ArrayList;
import java.util.List;

// Element Interface (what the Visitor visits)
interface OrderItemElement {
    void accept(OrderItemVisitor visitor); // Key method for Visitor pattern
    double getPrice(); // Example common property
    String getName();  // Example common property
}

// Concrete Elements
class BookItem implements OrderItemElement {
    private String title;
    private double price;
    private double weightKg;

    public BookItem(String title, double price, double weightKg) {
        this.title = title; this.price = price; this.weightKg = weightKg;
    }
    public double getWeightKg() { return weightKg; }
    @Override public String getName() { return title; }
    @Override public double getPrice() { return price; }
    @Override public void accept(OrderItemVisitor visitor) { visitor.visit(this); }
}

class ElectronicItem implements OrderItemElement {
    private String model;
    private double price;
    private boolean requiresFragilePacking;

    public ElectronicItem(String model, double price, boolean fragile) {
        this.model = model; this.price = price; this.requiresFragilePacking = fragile;
    }
    public boolean isFragile() { return requiresFragilePacking; }
    @Override public String getName() { return model; }
    @Override public double getPrice() { return price; }
    @Override public void accept(OrderItemVisitor visitor) { visitor.visit(this); }
}

class ClothingItem implements OrderItemElement {
    private String type;
    private String size;
    private double price;

    public ClothingItem(String type, String size, double price) {
        this.type = type; this.size = size; this.price = price;
    }
    public String getSize() { return size; }
    @Override public String getName() { return type + " (" + size + ")"; }
    @Override public double getPrice() { return price; }
    @Override public void accept(OrderItemVisitor visitor) { visitor.visit(this); }
}

// Visitor Interface
interface OrderItemVisitor {
    void visit(BookItem book);
    void visit(ElectronicItem electronic);
    void visit(ClothingItem clothing);
    // Add more visit methods for other OrderItemElement types
}

// Concrete Visitor 1: ShippingCostVisitor
class ShippingCostVisitor implements OrderItemVisitor {
    private double totalShippingCost = 0;

    @Override public void visit(BookItem book) {
        // Books might have a flat shipping rate or based on weight
        totalShippingCost += book.getWeightKg() * 1.5; // $1.5 per kg for books
        System.out.println("Shipping for Book '" + book.getName() + "': $" + (book.getWeightKg() * 1.5));
    }
    @Override public void visit(ElectronicItem electronic) {
        // Electronics might have higher shipping due to insurance/fragility
        totalShippingCost += 5.0; // Flat $5 for electronics
        if (electronic.isFragile()) totalShippingCost += 3.0; // Extra for fragile
        System.out.println("Shipping for Electronic '" + electronic.getName() + "': calculated.");
    }
    @Override public void visit(ClothingItem clothing) {
        // Clothing might be light and have cheap shipping
        totalShippingCost += 2.0; // Flat $2 for clothing
        System.out.println("Shipping for Clothing '" + clothing.getName() + "': $2.0");
    }
    public double getTotalShippingCost() { return totalShippingCost; }
}

// Concrete Visitor 2: TaxCalculationVisitor
class TaxCalculationVisitor implements OrderItemVisitor {
    private double totalTax = 0;

    @Override public void visit(BookItem book) {
        // Books might be tax-exempt or have a lower tax rate
        double tax = book.getPrice() * 0.05; // 5% tax for books
        totalTax += tax;
        System.out.println("Tax for Book '" + book.getName() + "': $" + tax);
    }
    @Override public void visit(ElectronicItem electronic) {
        double tax = electronic.getPrice() * 0.10; // 10% tax for electronics
        totalTax += tax;
        System.out.println("Tax for Electronic '" + electronic.getName() + "': $" + tax);
    }
    @Override public void visit(ClothingItem clothing) {
        double tax = clothing.getPrice() * 0.08; // 8% tax for clothing
        totalTax += tax;
        System.out.println("Tax for Clothing '" + clothing.getName() + "': $" + tax);
    }
    public double getTotalTax() { return totalTax; }
}


// Object Structure (e.g., a ShoppingCart or Order)
class ShoppingOrder {
    private List<OrderItemElement> items = new ArrayList<>();
    public void addItem(OrderItemElement item) { items.add(item); }
    
    public void applyVisitor(OrderItemVisitor visitor) {
        for (OrderItemElement item : items) {
            item.accept(visitor); // This triggers the double dispatch
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        ShoppingOrder order = new ShoppingOrder();
        order.addItem(new BookItem("Design Patterns", 50.00, 1.2));
        order.addItem(new ElectronicItem("Smartphone", 800.00, true));
        order.addItem(new ClothingItem("T-Shirt", "M", 20.00));

        System.out.println("--- Calculating Shipping Costs ---");
        ShippingCostVisitor shippingVisitor = new ShippingCostVisitor();
        order.applyVisitor(shippingVisitor);
        System.out.println("Total Shipping Cost: $" + shippingVisitor.getTotalShippingCost());

        System.out.println("\n--- Calculating Taxes ---");
        TaxCalculationVisitor taxVisitor = new TaxCalculationVisitor();
        order.applyVisitor(taxVisitor);
        System.out.println("Total Tax: $" + taxVisitor.getTotalTax());
    }
}
```
**Explanation:**
*   `OrderItemElement` is the interface for elements in the order. Each element has an `accept(OrderItemVisitor visitor)` method.
*   `BookItem`, `ElectronicItem`, `ClothingItem` are concrete elements. Their `accept` method calls `visitor.visit(this)`. This is known as "double dispatch" – the operation executed depends on both the type of the visitor and the type of the element.
*   `OrderItemVisitor` is the visitor interface with overloaded `visit()` methods for each concrete element type.
*   `ShippingCostVisitor` and `TaxCalculationVisitor` are concrete visitors that implement different operations.
*   The `ShoppingOrder` (object structure) iterates through its items and calls `accept()` on each, passing the current visitor.
*   This allows adding new operations (new visitors) without modifying the `OrderItemElement` classes.

---

This covers the main GoF behavioral patterns. I hope these e-commerce scenarios and simple Java examples make them clear and easier to understand!