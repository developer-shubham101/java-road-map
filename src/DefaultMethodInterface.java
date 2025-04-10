interface InterfaceA {
    default void greet() {
        System.out.println("Hello from InterfaceA");
    }
}

interface InterfaceB {
    default void greet() {
        System.out.println("Hello from InterfaceB");
    }
}

interface MyInterface {
    static void staticMethod() {
        System.out.println("This is the static method.");
    }

    void regularMethod();

    default void defaultMethod() {
        System.out.println("This is the default method.");
    }
}

class Example2 implements InterfaceA, InterfaceB {
    // Must override greet() to resolve the conflict
    @Override
    public void greet() {
        InterfaceA.super.greet(); // Can choose one implementation or provide a new one
        InterfaceB.super.greet(); // Can choose one implementation or provide a new one
        System.out.println("Hello from MyClass");
    }
}

class Example1 implements MyInterface {
    @Override
    public void regularMethod() {
        System.out.println("Implementing regular method.");
    }

    // Optionally override the default method
    /*@Override
    public void defaultMethod() {
        System.out.println("Overridden default method.");
    }*/
}

public class DefaultMethodInterface {
    public static void main(String[] args) {
        Example1 obj2 = new Example1();
        obj2.defaultMethod();

        Example2 obj = new Example2();
        obj.greet();

        MyInterface.staticMethod();
    }
}
