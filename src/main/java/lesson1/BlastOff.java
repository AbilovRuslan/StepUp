package lesson1;

public class BlastOff {
    public static String blastOff(int start) {
        String result = "";
        for (int i = start; i >= 1; i--) {
            result += i + " ";
        }
        return result + "Поехали!";
    }

    public static void main(String[] args) {
        System.out.println(blastOff(5));
        System.out.println(blastOff(3));
        System.out.println(blastOff(1));
    }
}