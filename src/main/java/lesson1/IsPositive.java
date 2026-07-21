package lesson1;

public class IsPositive {
    public static boolean isPositive(int n) {
        return (n >= 0) ? true : false; // у нас boolean и так выведет тру/фолс...
    }

    public static void main(String[] args) {
        System.out.println(isPositive(4));
        System.out.println(isPositive(7));
        System.out.println(isPositive(0));
        System.out.println(isPositive(-3));
    }
}