package tasks;

import java.util.Arrays;
import java.util.Comparator;

public class Task2 {

    public static void sudoFirst(String[] array) {
        Arrays.sort(array, Comparator.comparing(s -> !s.contains("sudo")));
    }

    public static void main(String[] args) {
        String[] arr = {"hello", "sudo apt", "world", "sudo rm", "java"};
        sudoFirst(arr);
        System.out.println(Arrays.toString(arr));
    }
}