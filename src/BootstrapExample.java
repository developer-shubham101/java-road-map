public class BootstrapExample {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        System.out.println(BootstrapExample.class.getClassLoader()); // Outputs the class loader for this class
        System.out.println(String.class.getClassLoader()); // Outputs null for Bootstrap-loaded classes
    }
}