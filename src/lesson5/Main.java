package lesson5;

import java.util.Arrays;

/**
 * @author Valeriy Lazarev
 * @since 09.09.2020
 */

public class Main {
    private static final int SIZE = 10_000_000;
    private static final int HALF_LENGTH = SIZE / 2;
    private static float[] result0;
    private static float[] result1;

    public static void main(String[] args) throws InterruptedException {
        long a = System.currentTimeMillis();

        methodOne();
        methodTwo();
        printingTheSpentTime(a, "Total execution time of two methods");

        a = System.currentTimeMillis();

        System.out.println("Two arrays are " + ((Arrays.equals(result0, result1) ? "equivalent " : "not equivalent")));
        printingTheSpentTime(a, "Array comparison time: ");
    }

    static void methodOne() {
        float[] arr = createsAOneDimensionalLongArray(SIZE);


        fillsThisArrayWithOnes(arr);

        long a = System.currentTimeMillis();
        methodThatCountsSomething(arr, 0);
        printingTheSpentTime(a, "methodOne");
        result0 = arr;
    }

    private static void methodTwo() throws InterruptedException {
        float[] arr = createsAOneDimensionalLongArray(SIZE);
        fillsThisArrayWithOnes(arr);
        float[] a1 = createsAOneDimensionalLongArray(HALF_LENGTH);
        float[] a2 = createsAOneDimensionalLongArray(HALF_LENGTH);

        long a = System.currentTimeMillis();
        Thread thread0 = new Thread(() -> assemblyDisassemblyArray(arr, a1, 0, 0));
        Thread thread1 = new Thread(() -> assemblyDisassemblyArray(arr, a2, HALF_LENGTH, HALF_LENGTH));

        thread0.start();
        thread1.start();

        thread0.join();
        thread1.join();

        printingTheSpentTime(a, "methodTwo");

        result1 = arr;
    }

    private static void printingTheSpentTime(long a, String text) {
        System.out.printf("%s: %dms to run. \n", text, (System.currentTimeMillis() - a));
    }

    private static void assemblyDisassemblyArray(float[] sourceArray,
                                                 float[] destinationArray, int startingElementIndex, int offset) {
        System.arraycopy(sourceArray, startingElementIndex,
                destinationArray, 0, HALF_LENGTH);
        methodThatCountsSomething(destinationArray, offset);
        System.arraycopy(destinationArray, 0, sourceArray, startingElementIndex, HALF_LENGTH);
    }

    private static void methodThatCountsSomething(float[] arr, int offset) {
        for (int i = offset; i < arr.length + offset; i++) {
            arr[i - offset] = (float) (arr[i - offset] *
                    Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
    }

    private static void fillsThisArrayWithOnes(float[] arr) {
        Arrays.fill(arr, (float) 1.0);
    }

    private static float[] createsAOneDimensionalLongArray(int size) {
        return new float[size];
    }
}

