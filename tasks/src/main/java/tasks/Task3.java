package tasks;

import java.util.Collection;
import java.util.List;

public class Task3 {

    public static long countGreaterThanFive(Collection<Integer> collection) {
        return collection.stream()
                .filter(n -> n > 5)
                .count();
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 3, 7, 10, 2, 8, 5);
        System.out.println(countGreaterThanFive(list));
    }
}