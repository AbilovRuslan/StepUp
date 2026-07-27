import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lesson2 {

    private final Random random = new Random();

    @BeforeEach
    void start() {
        System.out.println("========================");
        System.out.println("Test method start");
    }

    @AfterEach
    void end() {
        System.out.println("Test method end");
        System.out.println("========================");
    }

    private void check(boolean ok) {
        System.out.println(ok ? "TEST PASSED" : "TEST FAILED");
    }

    @Test
    void testIsEven() {
        int n = random.nextInt(1, 101);
        check(lesson1.IsEven.isEven(n) == (n % 2 == 0));
    }

    @Test
    void testIsPositive() {
        int n = random.nextInt(201) - 100;
        check(lesson1.IsPositive.isPositive(n) == (n >= 0));
    }

    @Test
    void testBlastOff() {
        int start = random.nextInt(20) + 1;
        String actual = lesson1.BlastOff.blastOff(start);
        StringBuilder sb = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            sb.append(i).append(" ");
        }

        sb.append("Поехали!");
        check(actual.equals(sb.toString()));
    }

    @Test
    void testSumToN() {
        int n = random.nextInt(20) + 1;
        check(lesson1.SumToN.sumToN(n) == n * (n + 1) / 2);
    }

    @RepeatedTest(20)
    void testCheckAccess() {
        int age = random.nextInt(100);
        check(lesson1.CheckAccess.checkAccess(age).equals(age >= 18 ? "Allowed" : "Denied"));
    }

    @RepeatedTest(5)
    void testHasBug() {
        String[] arr = {"мир", "труд", random.nextBoolean() ? "Bug" : "май"};
        boolean actual = lesson1.HasBug.hasBug(arr);
        boolean expected = false;

        for (String s : arr) {
            if (s.equals("Bug")) {
                expected = true;
                break;
            }
        }

        check(actual == expected);
    }

    @RepeatedTest(5)
    void testGetEvenInRange() {
        int start = random.nextInt(20);
        int end = start + random.nextInt(10) + 1;
        String actual = lesson1.GetEvenInRange.getEvenInRange(start, end);
        StringBuilder sb = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                sb.append(i).append(" ");
            }
        }

        check(actual.equals(sb.toString().trim()));
    }

    @RepeatedTest(5)
    void testCalcAverage() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(random.nextInt(100));
        }

        int actual = lesson1.CalcAverage.calcAverage(list);
        int sum = 0;

        for (int n : list) {
            sum += n;
        }

        check(actual == sum / list.size());
    }

    @ParameterizedTest
    @MethodSource("scores")
    void testGetGrade(int score) {
        String actual = lesson1.GetGrade.getGrade(score);
        String expected;

        if (score >= 81) {
            expected = "A";
        } else if (score >= 61) {
            expected = "B";
        } else if (score >= 41) {
            expected = "C";
        } else if (score >= 21) {
            expected = "D";
        } else {
            expected = "E";
        }

        check(actual.equals(expected));
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void testReverse(String[] arr) {
        String[] actual = lesson1.Reverse.reverse(arr);
        String[] expected = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            expected[i] = arr[arr.length - 1 - i];
        }

        check(Arrays.equals(actual, expected));
    }

    @ParameterizedTest
    @MethodSource("nameLists")
    void testRemoveSpecificName(List<String> list) {
        List<String> actual = lesson1.RemoveSpecificName.removeSpecificName(list, "Анна");
        List<String> expected = new ArrayList<>();

        for (String name : list) {
            if (!name.equals("Анна")) {
                expected.add(name);
            }
        }

        check(actual.equals(expected));
    }

    @ParameterizedTest
    @MethodSource("findMaxArrays")
    void testFindMax(int[] arr) {
        int actual = lesson1.FindMax.findMax(arr);
        int max = arr[0];

        for (int n : arr) {
            if (n > max) {
                max = n;
            }
        }

        check(actual == max);
    }

    static IntStream scores() {
        Random r = new Random();
        return IntStream.generate(() -> r.nextInt(101)).limit(10);
    }

    static Stream<Arguments> arrayProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{"A", "B", "C"}),
                Arguments.of((Object) new String[]{"один", "два", "три", "четыре"}),
                Arguments.of((Object) new String[]{"Java"})
        );
    }

    static Stream<List<String>> nameLists() {
        return Stream.of(
                List.of("Анна", "Иван", "Анна", "Петр"),
                List.of("Анна", "Анна"),
                List.of("Иван", "Петр")
        );
    }

    static Stream<int[]> findMaxArrays() {
        Random r = new Random();
        return Stream.generate(() -> {
            int[] arr = new int[5];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = r.nextInt(100);
            }

            return arr;
        }).limit(5);
    }
}