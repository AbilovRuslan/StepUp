package lesson1;

import java.util.List;

public class CalcAverage {
    public static int calcAverage(List<Integer> list) {
        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        return sum / list.size();
    }

    public static void main(String[] args) {
        System.out.println(calcAverage(List.of(1, 2, 3, 4, 5)));
        System.out.println(calcAverage(List.of(10, 20, 30)));
        System.out.println(calcAverage(List.of(-5, 0, 5)));
        System.out.println(calcAverage(List.of(100)));
    }
}