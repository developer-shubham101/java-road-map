### What are the SOLID Principles?

SOLID is an acronym for five design principles in object-oriented programming intended to make software designs more understandable, flexible, and maintainable.

Think of them as a set of rules or best practices that help you avoid messy, fragile code. Following SOLID helps you build software with a strong foundation, much like an architect uses blueprints and engineering principles to design a sturdy building.

Here is the breakdown of each principle:

*   **S** - Single Responsibility Principle
*   **O** - Open/Closed Principle
*   **L** - Liskov Substitution Principle
*   **I** - Interface Segregation Principle
*   **D** - Dependency Inversion Principle

---

### SOLID Principles with an E-commerce Website Example

Let's imagine we are building the backend for a typical e-commerce website.

#### 1. S - Single Responsibility Principle (SRP)

**The Idea:** A class should have only one reason to change. This means it should have only one job or responsibility.

**Why it's important:** It makes your code easier to understand, test, and maintain. When you need to make a change, you only have to look in one place.

**E-commerce Example:**

*   **Bad Design (Violates SRP):** You have a single `Order` class that does everything.

    ```
    // BAD: This class has too many responsibilities
    class Order {
        public function calculateTotal() { /* ... */ }
        public function getItems() { /* ... */ }
        public function saveToDatabase() { /* ... logic to save order to SQL */ }
        public function generateInvoicePDF() { /* ... logic to create a PDF */ }
        public function sendConfirmationEmail() { /* ... logic to use an email service */ }
    }
    ```
    **Why is this bad?** If you need to change the database logic, you edit the `Order` class. If you need to change the invoice format, you edit the `Order` class. If you need to change the email provider, you *still* edit the `Order` class. It has too many reasons to change.

*   **Good Design (Follows SRP):** You break down the responsibilities into separate classes.

    ```
    // GOOD: Each class has a single, clear purpose
    class Order {
        // Only responsible for holding order data
        public function calculateTotal() { /* ... */ }
        public function getItems() { /* ... */ }
    }

    class OrderRepository {
        // Only responsible for saving/retrieving the order from the database
        public function save(Order $order) { /* ... */ }
    }

    class InvoiceGenerator {
        // Only responsible for creating an invoice for an order
        public function generate(Order $order) { /* ... */ }
    }

    class NotificationService {
        // Only responsible for sending notifications about an order
        public function sendConfirmation(Order $order) { /* ... */ }
    }
    ```

---

#### 2. O - Open/Closed Principle (OCP)

**The Idea:** Software entities (classes, modules, functions) should be open for extension, but closed for modification. This means you should be able to add new functionality without changing existing, working code.

**Why it's important:** It prevents you from introducing bugs into code that is already tested and stable.

**E-commerce Example:** Calculating shipping costs.

*   **Bad Design (Violates OCP):** Using a giant `if/else` block.

    ```
    // BAD: We have to modify this class every time a new shipping method is added
    class ShippingCalculator {
        public function calculate(Order $order, string $method) {
            if ($method === 'Standard') {
                return 10.00;
            } else if ($method === 'Express') {
                return 25.00;
            }
            // What if we add 'NextDay' or 'International'? We have to come back and add another 'else if'.
        }
    }
    ```

*   **Good Design (Follows OCP):** Using an interface and strategy pattern.

    ```
    // GOOD: We can add new shipping methods without touching existing code.

    // 1. Create an interface (the "contract")
    interface ShippingStrategy {
        public function calculate(Order $order): float;
    }

    // 2. Create concrete implementations
    class StandardShipping implements ShippingStrategy {
        public function calculate(Order $order): float { return 10.00; }
    }

    class ExpressShipping implements ShippingStrategy {
        public function calculate(Order $order): float { return 25.00; }
    }

    // Now, to add a new method, we just create a NEW class
    class NextDayShipping implements ShippingStrategy {
        public function calculate(Order $order): float { return 40.00; }
    }
    ```
    The main code simply accepts any object that follows the `ShippingStrategy` contract. It doesn't need to be modified.

---

#### 3. L - Liskov Substitution Principle (LSP)

**The Idea:** Subtypes must be substitutable for their base types. In simpler terms, if class `B` is a subclass of class `A`, you should be able to use an object of class `B` wherever an object of class `A` is expected, without causing any issues.

**Why it's important:** It ensures that your inheritance hierarchy is correct and that subclasses don't behave in unexpected ways.

**E-commerce Example:** Product types.

*   **Bad Design (Violates LSP):** A subclass changes the expected behavior.

    ```
    // BAD: The subclass has a different contract
    class Product {
        public function getPrice(): float { /* ... */ }
    }

    class DiscountedProduct extends Product {
        // This is fine, it still returns a price.
    }

    class FreebieProduct extends Product {
        // This breaks the expectation. Code expecting a price might get an error.
        public function getPrice(): float {
            throw new Exception("Freebie products don't have a price!");
        }
    }
    ```
    If your shopping cart loop calls `getPrice()` on every product, it will crash when it hits a `FreebieProduct`. The subtype is not a valid substitute.

*   **Good Design (Follows LSP):** Ensure all subtypes honor the contract of the parent class.

    ```
    // GOOD: The subclass honors the contract
    class Product {
        public function getPrice(): float { /* ... */ }
    }
    
    class FreebieProduct extends Product {
        // A free product has a price, it's just zero.
        public function getPrice(): float {
            return 0.00;
        }
    }
    ```
    Now, the shopping cart loop can safely call `getPrice()` on any `Product` or its subclasses without fear of an unexpected error.

---

#### 4. I - Interface Segregation Principle (ISP)

**The Idea:** Don't force a class to implement an interface with methods it doesn't use. It's better to have many small, specific interfaces than one large, general-purpose one.

**Why it's important:** It prevents classes from having to implement "dummy" methods and keeps the system clean and decoupled.

**E-commerce Example:** Different kinds of products (physical vs. digital).

*   **Bad Design (Violates ISP):** One "fat" interface for all products.

    ```
    // BAD: "Fat" interface forces classes to implement methods they don't need
    interface IProduct {
        public function getName(): string;
        public function getPrice(): float;
        public function getWeight(): int;         // For physical goods
        public function getDownloadUrl(): string; // For digital goods
    }

    class PhysicalBook implements IProduct {
        // ... other methods
        public function getWeight(): int { return 500; } // This is fine
        public function getDownloadUrl(): string { return null; } // Useless! This book isn't downloadable.
    }
    
    class Ebook implements IProduct {
        // ... other methods
        public function getWeight(): int { return 0; } // Useless! An ebook has no weight.
        public function getDownloadUrl(): string { return "/downloads/ebook.pdf"; } // This is fine
    }
    ```

*   **Good Design (Follows ISP):** Segregated, role-based interfaces.

    ```
    // GOOD: Small, focused interfaces

    interface IProduct {
        public function getName(): string;
        public function getPrice(): float;
    }

    interface IShippable {
        public function getWeight(): int;
    }

    interface IDownloadable {
        public function getDownloadUrl(): string;
    }

    // Now classes only implement what they need
    class PhysicalBook implements IProduct, IShippable {
        public function getName(): string { /* ... */ }
        public function getPrice(): float { /* ... */ }
        public function getWeight(): int { return 500; }
    }

    class Ebook implements IProduct, IDownloadable {
        public function getName(): string { /* ... */ }
        public function getPrice(): float { /* ... */ }
        public function getDownloadUrl(): string { return "/downloads/ebook.pdf"; }
    }
    ```

---

#### 5. D - Dependency Inversion Principle (DIP)

**The Idea:** High-level modules should not depend on low-level modules. Both should depend on abstractions (e.g., interfaces). Abstractions should not depend on details; details should depend on abstractions.

**Why it's important:** It makes your code highly decoupled and flexible. You can easily swap out components (like a payment gateway or database) without changing the core business logic.

**E-commerce Example:** Payment processing.

*   **Bad Design (Violates DIP):** The high-level `OrderProcessor` depends directly on a low-level `StripePaymentGateway`.

    ```
    // BAD: The high-level class is tightly coupled to the low-level class
    class StripePaymentGateway {
        public function chargeCard(array $cardDetails, float $amount) { /* ... */ }
    }

    class OrderProcessor {
        public function process(Order $order) {
            // ...
            $stripe = new StripePaymentGateway(); // Direct dependency!
            $stripe->chargeCard($order->cardDetails, $order->getTotal());
            // What if we want to add PayPal? We have to change THIS class.
        }
    }
    ```

*   **Good Design (Follows DIP):** Both modules depend on an abstraction (an interface).

    ```
    // GOOD: Both classes depend on the interface, not on each other.

    // 1. The Abstraction (Interface)
    interface PaymentGateway {
        public function processPayment(float $amount);
    }

    // 2. The Low-Level Details (Concrete Implementations)
    class StripeGateway implements PaymentGateway {
        public function processPayment(float $amount) { /* ... logic for Stripe */ }
    }

    class PayPalGateway implements PaymentGateway {
        public function processPayment(float $amount) { /* ... logic for PayPal */ }
    }

    // 3. The High-Level Module
    class OrderProcessor {
        private $paymentGateway;

        // The dependency is "injected" via the constructor
        public function __construct(PaymentGateway $gateway) {
            $this->paymentGateway = $gateway;
        }

        public function process(Order $order) {
            // ...
            // No direct dependency on Stripe or PayPal!
            $this->paymentGateway->processPayment($order->getTotal());
        }
    }

    // How you use it:
    $stripeProcessor = new OrderProcessor(new StripeGateway());
    $payPalProcessor = new OrderProcessor(new PayPalGateway());
    ```
    The `OrderProcessor` doesn't know or care if it's using Stripe or PayPal. You can easily swap them or add new ones without ever touching the `OrderProcessor` class.