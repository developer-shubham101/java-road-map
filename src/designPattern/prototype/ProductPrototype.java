package designPattern.prototype;

interface ProductPrototype extends Cloneable { // Must extend Cloneable for Object.clone()
    ProductPrototype cloneProduct();

    void display();

    void setAttribute(String key, String value); // Simple way to modify
}