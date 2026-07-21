package lesson1;

public class GetEvenInRange {
    public static String getEvenInRange(int start, int end) {
        String result = "";
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                result += i + " ";
            }
        }
        return result.trim();
    }

    public static void main(String[] args) {
        System.out.println(getEvenInRange(1, 10));
        System.out.println(getEvenInRange(2, 8));
        System.out.println(getEvenInRange(5, 15));
    }
}