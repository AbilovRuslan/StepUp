package tasks;

import java.util.Arrays;
import java.util.Comparator;

public class Task1 {

    public static void sortByAbsDesc(Integer[] array) {
        Arrays.sort(array, Comparator.comparingInt((Integer n) -> Math.abs(n)).reversed());
    }

    public static void main(String[] args) {
        Integer[] arr = {-900, 783, 650, -500, -400, 321};
        sortByAbsDesc(arr);
        System.out.println(Arrays.toString(arr));
    }
}