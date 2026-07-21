package lesson1;

public class SumToN {
    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sumToN(5));
        System.out.println(sumToN(3));
        System.out.println(sumToN(1));
        System.out.println(sumToN(10));
    }
}