Okay, let's continue with the next set of design patterns, again using our e-commerce website project as the context.

---

### 1. Adapter Pattern (Wrapper)

**Concept:** Allows objects with incompatible interfaces to collaborate. It acts as a bridge between two incompatible interfaces.

**E-commerce Situation:**
Your e-commerce site needs to integrate with an external, third-party shipping provider. This provider has its own API for calculating shipping costs and creating shipments, which is different from the interface your system expects (`ShippingCalculator`). You need an adapter to make them work together.

**Java Code Example:**

```java
// Target Interface (what our system expects)
interface ShippingCalculator {
    double calculateShippingCost(String address, double weight);
    String createShipment(String address, String orderId);
}

// Adaptee (the third-party shipping provider's class with an incompatible interface)
class ExternalShippingProvider {
    public double getPrice(String destinationZip, double packageWeightLbs) {
        // Complex logic to call external API
        System.out.println("ExternalProvider: Calculating cost for ZIP " + destinationZip + ", Weight: " + packageWeightLbs + " lbs");
        return 5.0 + packageWeightLbs * 0.5; // Example calculation
    }

    public String schedulePickup(String fullAddress, String trackingReference) {
        // Complex logic to schedule pickup via external API
        System.out.println("ExternalProvider: Scheduling pickup for " + fullAddress + ", Ref: " + trackingReference);
        return "ShipmentID-" + System.currentTimeMillis();
    }
}

// Adapter Class
class ShippingAdapter implements ShippingCalculator {
    private ExternalShippingProvider externalProvider;

    public ShippingAdapter(ExternalShippingProvider externalProvider) {
        this.externalProvider = externalProvider;
    }

    @Override
    public double calculateShippingCost(String address, double weightKg) {
        // Convert our system's parameters to what the external provider expects
        // For simplicity, let's assume address contains ZIP and weight is converted
        String zipCode = extractZipCode(address); // Assume this helper method exists
        double weightLbs = weightKg * 2.20462; // Convert kg to lbs
        return externalProvider.getPrice(zipCode, weightLbs);
    }

    @Override
    public String createShipment(String address, String orderId) {
        // Adapt the call
        return externalProvider.schedulePickup(address, "Order-" + orderId);
    }

    private String extractZipCode(String address) {
        // Dummy implementation
        if (address.contains("90210")) return "90210";
        return "10001";
    }
}

// Main.java - How to use it
public class Main {
    public static void main(String[] args) {
        // Our system needs a ShippingCalculator
        ShippingCalculator calculator;

        // We want to use the ExternalShippingProvider
        ExternalShippingProvider externalService = new ExternalShippingProvider();

        // We adapt it
        calculator = new ShippingAdapter(externalService);

        String customerAddress = "123 Main St, Beverly Hills, CA 90210";
        double orderWeightKg = 2.5;

        double cost = calculator.calculateShippingCost(customerAddress, orderWeightKg);
        System.out.println("OurSystem: Calculated shipping cost: $" + cost);

        String shipmentId = calculator.createShipment(customerAddress, "ORD-12345");
        System.out.println("OurSystem: Shipment created with ID: " + shipmentId);
    }
}
```
**Explanation:**
*   `ShippingCalculator` is the target interface our system uses.
*   `ExternalShippingProvider` is the adaptee with a different interface.
*   `ShippingAdapter` implements `ShippingCalculator` and internally uses an instance of `ExternalShippingProvider`, translating the calls and data formats.

---

### 2. Bridge Pattern

**Concept:** Decouples an abstraction from its implementation so the two can vary independently. This is often achieved by having the abstraction contain a reference to an implementor.

**E-commerce Situation:**
Your e-commerce site needs to apply different types of discount policies (e.g., percentage-based, fixed amount) to different product types or order levels (e.g., product-level discount, cart-level discount). The type of discount and how it's applied can change independently.

**Java Code Example:**

```java
// Implementor Interface: DiscountType
interface DiscountType {
    double apply(double originalPrice);
    String getDescription();
}

// Concrete Implementor A: PercentageDiscount
class PercentageDiscount implements DiscountType {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double apply(double originalPrice) {
        return originalPrice * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return percentage + "% off";
    }
}

// Concrete Implementor B: FixedAmountDiscount
class FixedAmountDiscount implements DiscountType {
    private double amount;

    public FixedAmountDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double apply(double originalPrice) {
        return Math.min(amount, originalPrice); // Discount cannot be more than price
    }
    
    @Override
    public String getDescription() {
        return "$" + amount + " off";
    }
}

// Abstraction: DiscountPolicy
abstract class DiscountPolicy {
    protected DiscountType discountType; // Bridge to the implementor

    public DiscountPolicy(DiscountType discountType) {
        this.discountType = discountType;
    }

    public abstract double calculateDiscount(double price);
    public abstract void displayDiscountInfo();
}

// Refined Abstraction A: ProductDiscountPolicy
class ProductDiscountPolicy extends DiscountPolicy {
    private String productName;

    public ProductDiscountPolicy(DiscountType discountType, String productName) {
        super(discountType);
        this.productName = productName;
    }

    @Override
    public double calculateDiscount(double price) {
        double discountAmount = discountType.apply(price);
        System.out.println("Applying " + discountType.getDescription() + " to product '" + productName + "'. Original: $" + price + ", Discount: $" + discountAmount);
        return discountAmount;
    }
    
    @Override
    public void displayDiscountInfo() {
        System.out.println("Discount for " + productName + ": " + discountType.getDescription());
    }
}

// Refined Abstraction B: CartDiscountPolicy
class CartDiscountPolicy extends DiscountPolicy {
    public CartDiscountPolicy(DiscountType discountType) {
        super(discountType);
    }

    @Override
    public double calculateDiscount(double cartTotal) {
        double discountAmount = discountType.apply(cartTotal);
        System.out.println("Applying " + discountType.getDescription() + " to cart total. Original: $" + cartTotal + ", Discount: $" + discountAmount);
        return discountAmount;
    }
    
    @Override
    public void displayDiscountInfo() {
        System.out.println("Cart-wide discount: " + discountType.getDescription());
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        // Discount Types (Implementors)
        DiscountType tenPercentOff = new PercentageDiscount(10);
        DiscountType fiveDollarsOff = new FixedAmountDiscount(5);

        // Discount Policies (Abstractions)
        DiscountPolicy laptopDiscount = new ProductDiscountPolicy(tenPercentOff, "Laptop X");
        laptopDiscount.calculateDiscount(1000); // Applies 10% off
        laptopDiscount.displayDiscountInfo();

        System.out.println("---");

        DiscountPolicy bookDiscount = new ProductDiscountPolicy(fiveDollarsOff, "Java Design Patterns Book");
        bookDiscount.calculateDiscount(50); // Applies $5 off
        bookDiscount.displayDiscountInfo();
        
        System.out.println("---");
        
        DiscountPolicy cartDiscount = new CartDiscountPolicy(new PercentageDiscount(15)); // 15% off entire cart
        cartDiscount.calculateDiscount(200); // Applies 15% off $200
        cartDiscount.displayDiscountInfo();
    }
}
```
**Explanation:**
*   `DiscountType` is the implementor interface, with `PercentageDiscount` and `FixedAmountDiscount` as concrete implementations.
*   `DiscountPolicy` is the abstraction, holding a reference to a `DiscountType`.
*   `ProductDiscountPolicy` and `CartDiscountPolicy` are refined abstractions.
*   You can now change the discount *type* (percentage, fixed) independently of how it's *applied* (to a product, to a cart).

---

### 3. Composite Pattern

**Concept:** Composes objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

**E-commerce Situation:**
Your e-commerce site sells individual products and "product bundles" (e.g., a "Gaming Starter Kit" containing a mouse, keyboard, and headset). You want to calculate the total price or display details uniformly, whether it's a single product or a bundle. Product categories and sub-categories also fit this.

**Java Code Example:**

```java
import java.util.ArrayList;
import java.util.List;

// Component Interface
interface ProductComponent {
    String getName();
    double getPrice();
    void display(String indent); // For displaying hierarchy
}

// Leaf: IndividualProduct
class IndividualProduct implements ProductComponent {
    private String name;
    private double price;

    public IndividualProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- " + name + ": $" + price);
    }
}

// Composite: ProductBundle (or ProductCategory)
class ProductBundle implements ProductComponent {
    private String name;
    private List<ProductComponent> children = new ArrayList<>();

    public ProductBundle(String name) {
        this.name = name;
    }

    public void addProduct(ProductComponent product) {
        children.add(product);
    }

    public void removeProduct(ProductComponent product) {
        children.remove(product);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        double totalPrice = 0;
        for (ProductComponent child : children) {
            totalPrice += child.getPrice();
        }
        return totalPrice; // Could also apply a bundle discount here
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ " + name + " (Bundle Total: $" + getPrice() + ")");
        for (ProductComponent child : children) {
            child.display(indent + "  ");
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        IndividualProduct mouse = new IndividualProduct("Gaming Mouse", 25.00);
        IndividualProduct keyboard = new IndividualProduct("Mechanical Keyboard", 75.00);
        IndividualProduct headset = new IndividualProduct("Gaming Headset", 50.00);

        ProductBundle gamingKit = new ProductBundle("Gaming Starter Kit");
        gamingKit.addProduct(mouse);
        gamingKit.addProduct(keyboard);
        gamingKit.addProduct(headset);

        IndividualProduct monitor = new IndividualProduct("27-inch Monitor", 300.00);
        
        ProductBundle officeSetup = new ProductBundle("Basic Office Setup");
        officeSetup.addProduct(keyboard); // Re-using keyboard
        officeSetup.addProduct(new IndividualProduct("Office Mouse", 15.00));

        System.out.println("--- Displaying Gaming Kit ---");
        gamingKit.display("");
        System.out.println("Total Price for " + gamingKit.getName() + ": $" + gamingKit.getPrice());

        System.out.println("\n--- Displaying Monitor ---");
        monitor.display("");
        System.out.println("Total Price for " + monitor.getName() + ": $" + monitor.getPrice());
        
        System.out.println("\n--- Displaying Office Setup ---");
        officeSetup.display("");
        System.out.println("Total Price for " + officeSetup.getName() + ": $" + officeSetup.getPrice());
    }
}
```
**Explanation:**
*   `ProductComponent` is the common interface for both individual products and bundles.
*   `IndividualProduct` is a leaf node.
*   `ProductBundle` is a composite node that can contain other `ProductComponent`s (either individual products or other bundles).
*   The client code can treat `gamingKit` (a bundle) and `monitor` (an individual product) uniformly when calling `getPrice()` or `display()`.

---

### 4. Decorator Pattern (Wrapper)

**Concept:** Attaches additional responsibilities or behaviors to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

**E-commerce Situation:**
When a customer places an order, they might want to add optional services like gift wrapping, express shipping, or insurance. These options add cost and modify the description of the order.

**Java Code Example:**

```java
// Component Interface: Order
interface Order {
    double calculateCost();
    String getDescription();
}

// Concrete Component: BasicOrder
class BasicOrder implements Order {
    private double itemsTotal;

    public BasicOrder(double itemsTotal) {
        this.itemsTotal = itemsTotal;
    }

    @Override
    public double calculateCost() {
        return itemsTotal;
    }

    @Override
    public String getDescription() {
        return "Basic Order";
    }
}

// Abstract Decorator: OrderDecorator
abstract class OrderDecorator implements Order {
    protected Order decoratedOrder; // The component being decorated

    public OrderDecorator(Order decoratedOrder) {
        this.decoratedOrder = decoratedOrder;
    }

    @Override
    public double calculateCost() {
        return decoratedOrder.calculateCost();
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription();
    }
}

// Concrete Decorator A: GiftWrapDecorator
class GiftWrapDecorator extends OrderDecorator {
    private double giftWrapCost = 5.00;

    public GiftWrapDecorator(Order decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
    public double calculateCost() {
        return super.calculateCost() + giftWrapCost;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Gift Wrapping";
    }
}

// Concrete Decorator B: ExpressShippingDecorator
class ExpressShippingDecorator extends OrderDecorator {
    private double expressShippingCost = 15.00;

    public ExpressShippingDecorator(Order decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
    public double calculateCost() {
        return super.calculateCost() + expressShippingCost;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Express Shipping";
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        // An order with items costing $100
        Order myOrder = new BasicOrder(100.00);
        System.out.println("Order 1: " + myOrder.getDescription() + ", Cost: $" + myOrder.calculateCost());

        // Decorate with Gift Wrapping
        Order giftWrappedOrder = new GiftWrapDecorator(new BasicOrder(100.00));
        System.out.println("Order 2: " + giftWrappedOrder.getDescription() + ", Cost: $" + giftWrappedOrder.calculateCost());

        // Decorate with Express Shipping
        Order expressOrder = new ExpressShippingDecorator(new BasicOrder(100.00));
        System.out.println("Order 3: " + expressOrder.getDescription() + ", Cost: $" + expressOrder.calculateCost());
        
        // Decorate with both Gift Wrapping and Express Shipping
        Order deluxeOrder = new ExpressShippingDecorator(new GiftWrapDecorator(new BasicOrder(100.00)));
        System.out.println("Order 4: " + deluxeOrder.getDescription() + ", Cost: $" + deluxeOrder.calculateCost());
        // Note: order of decoration can matter if decorators interact in complex ways
        
        Order deluxeOrderAlt = new GiftWrapDecorator(new ExpressShippingDecorator(new BasicOrder(100.00)));
        System.out.println("Order 5: " + deluxeOrderAlt.getDescription() + ", Cost: $" + deluxeOrderAlt.calculateCost());

    }
}
```
**Explanation:**
*   `Order` is the component interface.
*   `BasicOrder` is the concrete component.
*   `OrderDecorator` is an abstract decorator that holds a reference to an `Order` object.
*   `GiftWrapDecorator` and `ExpressShippingDecorator` are concrete decorators that add their specific cost and description by calling the wrapped object's methods and then adding their own behavior.
*   You can dynamically "wrap" a basic order with multiple decorators.

---

### 5. Facade Pattern

**Concept:** Provides a simplified, unified interface to a complex subsystem of classes. It hides the complexity of the subsystem from the client.

**E-commerce Situation:**
Placing an order in an e-commerce system involves multiple steps and interacts with several subsystems: checking inventory, processing payment, updating customer order history, and sending a confirmation notification. A facade can simplify this entire process into a single method call for the client.

**Java Code Example:**

```java
// Subsystem Class 1: InventoryService
class InventoryService {
    public boolean checkStock(String productId, int quantity) {
        System.out.println("Inventory: Checking stock for " + productId + ", quantity " + quantity);
        // Dummy check
        return quantity < 10;
    }

    public void deductStock(String productId, int quantity) {
        System.out.println("Inventory: Deducting " + quantity + " of " + productId + " from stock.");
    }
}

// Subsystem Class 2: PaymentService
class PaymentService {
    public boolean processPayment(String userId, double amount) {
        System.out.println("Payment: Processing payment of $" + amount + " for user " + userId);
        // Dummy processing
        return amount < 1000; // Fails for large amounts for demo
    }
}

// Subsystem Class 3: OrderHistoryService
class OrderHistoryService {
    public void recordOrder(String userId, String orderDetails) {
        System.out.println("OrderHistory: Recording order for user " + userId + ": " + orderDetails);
    }
}

// Subsystem Class 4: NotificationService
class NotificationService {
    public void sendOrderConfirmation(String userId, String orderId) {
        System.out.println("Notification: Sending order confirmation for " + orderId + " to user " + userId);
    }
}

// Facade Class: OrderFacade
class OrderFacade {
    private InventoryService inventoryService;
    private PaymentService paymentService;
    private OrderHistoryService orderHistoryService;
    private NotificationService notificationService;

    public OrderFacade() {
        this.inventoryService = new InventoryService();
        this.paymentService = new PaymentService();
        this.orderHistoryService = new OrderHistoryService();
        this.notificationService = new NotificationService();
    }

    public boolean placeOrder(String userId, String productId, int quantity, double price) {
        System.out.println("\n--- Attempting to place order for User: " + userId + ", Product: " + productId + " ---");
        
        // 1. Check stock
        if (!inventoryService.checkStock(productId, quantity)) {
            System.out.println("OrderFacade: Product out of stock.");
            return false;
        }

        // 2. Process payment
        double totalAmount = price * quantity;
        if (!paymentService.processPayment(userId, totalAmount)) {
            System.out.println("OrderFacade: Payment failed.");
            return false;
        }

        // 3. Deduct stock
        inventoryService.deductStock(productId, quantity);

        // 4. Record order
        String orderDetails = "Product: " + productId + ", Qty: " + quantity + ", Total: $" + totalAmount;
        orderHistoryService.recordOrder(userId, orderDetails);

        // 5. Send notification
        String orderId = "ORD-" + System.currentTimeMillis();
        notificationService.sendOrderConfirmation(userId, orderId);

        System.out.println("OrderFacade: Order placed successfully! Order ID: " + orderId);
        return true;
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        OrderFacade orderFacade = new OrderFacade();

        // Client interacts with the simplified facade
        boolean success1 = orderFacade.placeOrder("user123", "productABC", 2, 25.00);
        System.out.println("Order 1 placement status: " + (success1 ? "Successful" : "Failed"));

        boolean success2 = orderFacade.placeOrder("user456", "productXYZ", 15, 10.00); // Will fail stock check
        System.out.println("Order 2 placement status: " + (success2 ? "Successful" : "Failed"));
        
        boolean success3 = orderFacade.placeOrder("user789", "productDEF", 1, 1500.00); // Will fail payment
        System.out.println("Order 3 placement status: " + (success3 ? "Successful" : "Failed"));
    }
}
```
**Explanation:**
*   `InventoryService`, `PaymentService`, `OrderHistoryService`, and `NotificationService` are complex subsystem components.
*   `OrderFacade` provides a simple `placeOrder()` method that internally coordinates these services.
*   The client (e.g., your web controller handling an order request) only needs to interact with the `OrderFacade`, not the individual subsystem components directly.

---

### 6. Flyweight Pattern

**Concept:** Used to minimize memory usage or computational expenses by sharing as much as possible of an object's state with other similar objects. It separates intrinsic state (shared, context-independent) from extrinsic state (context-dependent, passed in by client).

**E-commerce Situation:**
Your e-commerce site displays many product listings. Each product might have several small icons or badges (e.g., "New Arrival," "On Sale," "Eco-Friendly," "Bestseller"). Instead of creating a new image object for each badge instance on every product, you can reuse a few badge objects. The badge *type* (image, text) is intrinsic, while its *position* on a specific product card is extrinsic.

**Java Code Example:**

```java
import java.util.HashMap;
import java.util.Map;

// Flyweight Interface: ProductBadge
interface ProductBadge {
    void display(String productId, int x, int y); // x, y are extrinsic state
}

// Concrete Flyweight: SharedProductBadge
class SharedProductBadge implements ProductBadge {
    private final String badgeName; // Intrinsic state (e.g., "Sale", "New")
    private final String badgeIconData; // Intrinsic state (e.g., path to image or SVG data)

    public SharedProductBadge(String badgeName) {
        this.badgeName = badgeName;
        this.badgeIconData = "IconData[" + badgeName + "]"; // Simulate loading icon data
        System.out.println("Creating new badge: " + badgeName);
    }

    @Override
    public void display(String productId, int x, int y) {
        // productId, x, y are extrinsic state, provided by the client
        System.out.println("Displaying '" + badgeName + "' badge (" + badgeIconData + ") for product "
                           + productId + " at (" + x + ", " + y + ")");
    }

    public String getBadgeName() { return badgeName; }
}

// Flyweight Factory: BadgeFactory
class BadgeFactory {
    private Map<String, ProductBadge> badges = new HashMap<>();

    public ProductBadge getBadge(String badgeName) {
        ProductBadge badge = badges.get(badgeName);
        if (badge == null) {
            badge = new SharedProductBadge(badgeName);
            badges.put(badgeName, badge);
        }
        return badge;
    }

    public int getTotalBadgesCreated() {
        return badges.size();
    }
}

// Context (Product Display): This is where extrinsic state is managed
class ProductDisplay {
    private String productId;
    private ProductBadge badge; // Reference to the flyweight
    private int xPosition;      // Extrinsic state
    private int yPosition;      // Extrinsic state

    public ProductDisplay(String productId, ProductBadge badge, int x, int y) {
        this.productId = productId;
        this.badge = badge;
        this.xPosition = x;
        this.yPosition = y;
    }

    public void render() {
        badge.display(productId, xPosition, yPosition);
    }
}


// Main.java
public class Main {
    public static void main(String[] args) {
        BadgeFactory badgeFactory = new BadgeFactory();

        // Products with badges
        // Product 1 needs a "Sale" badge
        ProductDisplay product1Display = new ProductDisplay("P101", badgeFactory.getBadge("Sale"), 10, 20);
        product1Display.render();

        // Product 2 needs a "New Arrival" badge
        ProductDisplay product2Display = new ProductDisplay("P102", badgeFactory.getBadge("New Arrival"), 15, 30);
        product2Display.render();

        // Product 3 also needs a "Sale" badge (should reuse existing flyweight)
        ProductDisplay product3Display = new ProductDisplay("P103", badgeFactory.getBadge("Sale"), 20, 40);
        product3Display.render();
        
        // Product 4 needs an "Eco-Friendly" badge
        ProductDisplay product4Display = new ProductDisplay("P104", badgeFactory.getBadge("Eco-Friendly"), 25, 50);
        product4Display.render();

        System.out.println("\nTotal unique badge objects created: " + badgeFactory.getTotalBadgesCreated());
        // This should be 3 ("Sale", "New Arrival", "Eco-Friendly"), not 4, demonstrating reuse.
    }
}
```
**Explanation:**
*   `ProductBadge` is the flyweight interface.
*   `SharedProductBadge` is the concrete flyweight, storing intrinsic state (`badgeName`, `badgeIconData`).
*   `BadgeFactory` creates and manages flyweight objects, ensuring that only one instance of each unique badge type exists.
*   When a product needs to display a badge, the client (here, simplified by `ProductDisplay` and `Main`) gets a badge object from the factory and provides the extrinsic state (product ID, x/y coordinates) to its `display()` method.

---

### 7. Proxy Pattern (Surrogate)

**Concept:** Provides a surrogate or placeholder for another object to control access to it. This control can be for various reasons: lazy initialization, access control, logging, etc.

**E-commerce Situation:**
Your product detail page needs to display high-resolution product images. These images can be large and take time to load. You can use a proxy to load these images lazily: initially, a placeholder or low-resolution image might be shown, and the high-resolution image is loaded only when it's actually needed (e.g., when the user scrolls to it or clicks to zoom). Another common use is access control.

Let's use the lazy loading example.

**Java Code Example:**

```java
// Subject Interface: Image
interface Image {
    void display();
    String getFilename();
}

// Real Subject: HighResolutionImage (the expensive object)
class HighResolutionImage implements Image {
    private String filename;
    private String imageData; // Simulate large image data

    public HighResolutionImage(String filename) {
        this.filename = filename;
        loadImageFromServer();
    }

    private void loadImageFromServer() {
        System.out.println("Loading high-resolution image: " + filename + " from server...");
        // Simulate network delay and loading large data
        try {
            Thread.sleep(2000); // Simulate 2 seconds loading time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.imageData = "LargeImageData[" + filename + "]";
        System.out.println("Finished loading: " + filename);
    }

    @Override
    public void display() {
        System.out.println("Displaying high-resolution image: " + filename + " (" + imageData.substring(0, 20) + "...)");
    }
    
    @Override
    public String getFilename() {
        return filename;
    }
}

// Proxy: ImageProxy
class ImageProxy implements Image {
    private String filename;
    private HighResolutionImage realImage; // Reference to the RealSubject

    public ImageProxy(String filename) {
        this.filename = filename;
        System.out.println("ImageProxy created for: " + filename + ". Real image not loaded yet.");
    }

    @Override
    public void display() {
        if (realImage == null) {
            // Lazy initialization: Load the real image only when display() is called
            realImage = new HighResolutionImage(filename);
        }
        realImage.display(); // Delegate to the RealSubject
    }
    
    @Override
    public String getFilename() {
        // Can return filename without loading the real image
        return filename;
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Initializing Image Proxies ---");
        Image image1 = new ImageProxy("product_photo_001.jpg");
        Image image2 = new ImageProxy("product_banner_002.png");
        Image image3 = new ImageProxy("user_avatar_003.gif");

        // At this point, no real images have been loaded yet.
        System.out.println("\n--- Requesting to display images ---");
        
        System.out.println("\nDisplaying image 1:");
        image1.display(); // Real image1 will be loaded now

        System.out.println("\nDisplaying image 2:");
        image2.display(); // Real image2 will be loaded now
        
        System.out.println("\nDisplaying image 1 again (should not reload):");
        image1.display(); // Should use already loaded image1
        
        System.out.println("\nGetting filename for image 3 (should not load image):");
        System.out.println("Filename: " + image3.getFilename());
        
        System.out.println("\nDisplaying image 3:");
        image3.display(); // Real image3 will be loaded now
    }
}
```
**Explanation:**
*   `Image` is the subject interface.
*   `HighResolutionImage` is the real subject, whose instantiation and loading are expensive.
*   `ImageProxy` implements the `Image` interface. It holds a reference to `HighResolutionImage`.
*   When `display()` is called on the `ImageProxy` for the first time, it creates and loads the `HighResolutionImage` object and then delegates the call. Subsequent calls to `display()` use the already loaded real image. Methods like `getFilename()` can be served by the proxy without loading the real image.

---

These patterns cover a range of problems you might encounter. The key is to understand the *intent* of each pattern to recognize when it can provide an elegant solution in your e-commerce project or any software development.