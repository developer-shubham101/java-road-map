package designPattern.prototype;

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