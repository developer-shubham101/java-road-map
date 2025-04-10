import java.util.*;
import java.util.stream.Collectors;

public class StreamAPI {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 50);
        map.put("Banana", 10);
        map.put("Orange", 30);
        map.put("Mango", 40);
        map.put("Grapes", 20);


        LinkedHashMap<String, Integer> newList = map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));


        System.out.println(map);
        System.out.println(newList);

        System.out.println("============");


        List<String> names = Arrays.asList("Bob", "David", "Alice", "Charlie");


        ArrayList<String> newNamesList = names.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> newNamesList2 = names.stream().filter(e -> !Objects.equals(e, "Bob")).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> newNamesList3 = names.stream().map(String::toUpperCase).collect(Collectors.toCollection(ArrayList::new));
        Optional<String> bob = names.stream().filter(e -> Objects.equals(e, "Bob")).findFirst();


        System.out.println(names);
        System.out.println(newNamesList);
        System.out.println(newNamesList2);
        System.out.println(newNamesList3);
        System.out.println(bob.get());


    }
}
