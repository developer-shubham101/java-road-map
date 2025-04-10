package lambdas;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public class Lambdas {

    public static void main(String[] args) {



        Consumer<String> greet = (name) -> System.out.println("Hello, " + name);
        greet.accept("Alice"); // Outputs: Hello, Alice

        BiConsumer<String, Integer> greet2 = (name, age) -> System.out.println("Hello, " + name + " Age" + age);
        greet2.accept("Alice", 23); // Outputs: Hello, Alice Age23


        Predicate<String> predicate = (name) ->  true;


    }
}
