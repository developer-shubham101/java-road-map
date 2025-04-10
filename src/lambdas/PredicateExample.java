package lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        Predicate<String> startsWithA = (name) -> name.startsWith("A");

        // Filter names starting with "A"
        List<String> result = names.stream()
                                   .filter(startsWithA)
                                   .collect(Collectors.toList());

        System.out.println(result); // Outputs: [Alice]
    }
}

