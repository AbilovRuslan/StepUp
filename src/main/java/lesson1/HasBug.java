package lesson1;

public class HasBug {
    public static boolean hasBug(String[] messages) {
        for (String message : messages) {
            if (message.equals("Bug")) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String[] messages1 = {"мир", "труд", "май"};
        String[] messages2 = {"мир", "труд", "Bug"};
        String[] messages3 = {"мир"};

        System.out.println(hasBug(messages1));
        System.out.println(hasBug(messages2));
        System.out.println(hasBug(messages3));
    }
}