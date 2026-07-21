package lesson1;

public class Reverse {
    public static String[] reverse(String[] arr) {
        String[] result = new String[arr.length];
        int index = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            result[index] = arr[i];
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        String[] words = {"один", "два", "три", "четыре", "пять"};
        String[] reversed = reverse(words);

        for (String word : reversed) {
            System.out.print(word + " ");
        }
    }
}