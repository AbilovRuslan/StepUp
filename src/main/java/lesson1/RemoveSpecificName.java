package lesson1;

import java.util.ArrayList;
import java.util.List;

public class RemoveSpecificName {
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> result = new ArrayList<>();
        for (String name : list) {
            if (!name.equals(nameToRemove)) {
                result.add(name);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> names = List.of("Анна", "Борис", "Вика", "Анна", "Глеб");
        System.out.println(removeSpecificName(names, "Анна"));
    }
}