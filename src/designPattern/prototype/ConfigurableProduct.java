package designPattern.prototype;

class ConfigurableProduct implements ProductPrototype {
    private final String baseSku;
    private final java.util.Map<String, String> attributes; // e.g., color, size
    private String name;
    private double price;

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
        /*
         Alternatively, for simple cases:
         try {
             ConfigurableProduct cloned = (ConfigurableProduct) super.clone();
             // Deep copy mutable fields if any
             cloned.attributes = new java.util.HashMap<>(this.attributes);
             return cloned;
         } catch (CloneNotSupportedException e) {
             e.printStackTrace(); // Should not happen if Cloneable is implemented
             return null;
         }
        */
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
 