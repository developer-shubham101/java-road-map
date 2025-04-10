public class StringExample {
    public StringExample() {
        String str1 = "Hello";
        String str2 = str1.concat(" World");

        System.out.println(str1); // Outputs "Hello"
        System.out.println(str2); // Outputs "Hello World"

    }

    public void memoryCheck() {
        String str1 = "Hello";
        String str2 = "Hello";

        // Check if both references point to the same memory location
        if (str1 == str2) {
            System.out.println("Both variables point to the same memory location.");
        } else {
            System.out.println("Variables point to different memory locations.");
        }

        // Creating a new String object explicitly
        String str3 = "Hello";
        if (str1 == str3) {
            System.out.println("str1 and str3 point to the same memory location.");
        } else {
            System.out.println("str1 and str3 point to different memory locations.");
        }
    }
}
