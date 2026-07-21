package lesson1;

public class FindMax {
    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(findMax(new int[]{3, 7, 2, 9, 5}));
        System.out.println(findMax(new int[]{-5, -2, -8, -1}));
        System.out.println(findMax(new int[]{10}));
        System.out.println(findMax(new int[]{100, 200, 50, 300, 150}));
    }
}