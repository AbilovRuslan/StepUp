package tasks;

import java.util.Arrays;

public class Task4 {

    public static Integer[] stringsToIntegers(String[] strings) {
        return Arrays.stream(strings)
                .filter(s -> s != null && s.matches("-?\\d+"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

    public static void main(String[] args) {
        String[] input = {"123", "abc", "45", "-7", "hello", "0"};
        Integer[] result = stringsToIntegers(input);
        System.out.println(Arrays.toString(result));
    }
}