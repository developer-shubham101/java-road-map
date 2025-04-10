package collection;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class FailSafeExample {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "A");
        map.put("2", "B");

        Iterator<String> iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            // Modifying the map while iterating
            map.put("3", "C"); // No exception thrown
        }

        System.out.println("Map after iteration: " + map);
    }
}