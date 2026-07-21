package lesson1;

public class CheckAccess {
    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    public static void main(String[] args) {
        System.out.println(checkAccess(20));
        System.out.println(checkAccess(18));
        System.out.println(checkAccess(16));
    }
}