package designPattern.prototype;

class ProductRegistry {
    private final java.util.Map<String, ProductPrototype> prototypes = new java.util.HashMap<>();

    public void addPrototype(String key, ProductPrototype prototype) {
        prototypes.put(key, prototype);
    }

    public ProductPrototype getPrototype(String key) {
        ProductPrototype p = prototypes.get(key);
        return p != null ? p.cloneProduct() : null;
    }
}