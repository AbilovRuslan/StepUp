package lesson1;

public class GetGrade {
    public static String getGrade(int score) {
        if (score >= 81 && score <= 100) {
            return "A";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 41 && score <= 60) {
            return "C";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else {
            return "E";
        }
    }

    public static void main(String[] args) {
        System.out.println(getGrade(95));
        System.out.println(getGrade(70));
        System.out.println(getGrade(50));
        System.out.println(getGrade(30));
        System.out.println(getGrade(10));
    }
}