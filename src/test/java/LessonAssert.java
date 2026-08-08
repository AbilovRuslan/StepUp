import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LessonAssert {

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

    @RepeatedTest(10)
    @Tag("even")
    void testIsEven() {
        int n = random.nextInt(1, 101);
        boolean actual = lesson1.IsEven.isEven(n);
        boolean expected = n % 2 == 0;

        assertEquals(expected, actual);
    }

    @RepeatedTest(10)
    @Tag("positive")
    void testIsPositive() {
        int n = random.nextInt(201) - 100;
        boolean actual = lesson1.IsPositive.isPositive(n);
        boolean expected = n >= 0;

        assertEquals(expected, actual);
    }

    @RepeatedTest(10)
    @Tag("string")
    void testBlastOff() {
        int start = random.nextInt(20) + 1;
        String actual = lesson1.BlastOff.blastOff(start);
        StringBuilder sb = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            sb.append(i).append(" ");
        }

        sb.append("Поехали!");
        String expected = sb.toString();

        assertEquals(expected, actual);
    }

    @RepeatedTest(10)
    @Tag("math")
    void testSumToN() {
        int n = random.nextInt(20) + 1;
        int actual = lesson1.SumToN.sumToN(n);
        int expected = n * (n + 1) / 2;

        assertEquals(expected, actual);
    }

    @RepeatedTest(20)
    @Tag("access")
    void testCheckAccess() {
        int age = random.nextInt(100);
        String actual = lesson1.CheckAccess.checkAccess(age);
        String expected = age >= 18 ? "Allowed" : "Denied";

        assertEquals(expected, actual);
    }

    @RepeatedTest(10)
    @Tag("search")
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

        assertEquals(expected, actual);
    }

    @RepeatedTest(10)
    @Tag("math")
    void testGetEvenInRange() {
        int start = random.nextInt(20);
        int end = start + random.nextInt(10) + 1;
        StringBuilder sb = new StringBuilder();
        String actual = lesson1.GetEvenInRange.getEvenInRange(start, end);

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                sb.append(i).append(" ");
            }
        }

        String expected = sb.toString().trim();

        assertEquals(expected, actual);
    }

    @RepeatedTest(10)
    @Tag("math")
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

        int expected = sum / list.size();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @Tag("logic")
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

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @Tag("array")
    @MethodSource("arrayProvider")
    void testReverse(String[] arr) {
        String[] actual = lesson1.Reverse.reverse(arr);
        String[] expected = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            expected[i] = arr[arr.length - 1 - i];
        }

        assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @Tag("List")
    @MethodSource("nameLists")
    void testRemoveSpecificName(List<String> list) {
        List<String> actual = lesson1.RemoveSpecificName.removeSpecificName(list, "Анна");
        List<String> expected = new ArrayList<>();

        for (String name : list) {
            if (!name.equals("Анна")) {
                expected.add(name);
            }
        }

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @Tag("array")
    @MethodSource("findMaxArrays")
    void testFindMax(int[] arr) {
        int actual = lesson1.FindMax.findMax(arr);
        int max = arr[0];

        for (int n : arr) {
            if (n > max) {
                max = n;
            }
        }

        assertEquals(max, actual);
    }

    @Test
    @Tag("negative")
    void testSumToNNegative() {
        int n = 10;
        int actual = lesson1.SumToN.sumToN(n);
        int expected = n * (n + 1) / 2;

        assertEquals(expected, actual);
    }

    @Test
    @Tag("failing")
    void testSumToNWithWrongExpected() {
        int n = 5;
        int actual = lesson1.SumToN.sumToN(n);
        int expected = 100;

        assertEquals(expected, actual,
                "Этот тест специально падает — проверяем, что ассерт работает");
    }

    static IntStream scores() {
        Random r = new Random();
        return IntStream.generate(() -> r.nextInt(101)).limit(10);
    }

    static Stream<Arguments> arrayProvider() {
        Random random = new Random();

        return Stream.generate(() -> {
            String[] arr = new String[5];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = "test" + random.nextInt(100);
            }

            return Arguments.of((Object) arr);
        }).limit(10);
    }

    static Stream<int[]> findMaxArrays() {
        Random r = new Random();

        return Stream.generate(() -> {
            int[] arr = new int[5];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = r.nextInt(100);
            }

            return arr;
        }).limit(10);
    }

    static Stream<List<String>> nameLists() {
        Random random = new Random();
        String[] names = {"Анна", "Иван", "Петр", "Мария"};

        return Stream.generate(() -> {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                list.add(names[random.nextInt(names.length)]);
            }

            return list;
        }).limit(10);
    }
}