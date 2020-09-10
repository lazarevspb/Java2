package lesson5;

import java.util.Arrays;

/**
 * @author Valeriy Lazarev
 * @since 09.09.2020
 */

public class Main {
    static final int SIZE = 10_000_000;
    static final int HALF_LENGTH = SIZE / 2;
    public static void main(String[] args) throws InterruptedException {

        long a = System.currentTimeMillis();
        methodOne();
        methodTwo();
        printingTheSpentTime(a, "total execution time");
    }

    static void methodOne() {
        float[] arr = createsAOneDimensionalLongArray(SIZE);


        fillsThisArrayWithOnes(arr);

        long a = System.currentTimeMillis();
        methodThatCountsSomething(arr);

        printingTheSpentTime(a, "methodOne");
    }

    private static void methodTwo() throws InterruptedException {
        float[] arr = createsAOneDimensionalLongArray(SIZE);
        fillsThisArrayWithOnes(arr);
        float[] a1 = createsAOneDimensionalLongArray(HALF_LENGTH);
        float[] a2 = createsAOneDimensionalLongArray(HALF_LENGTH);

        long a = System.currentTimeMillis();
        Thread thread0 = new Thread(() -> assemblyDisassemblyArray(arr, a1, 0));
        Thread thread1 = new Thread(() -> assemblyDisassemblyArray(arr, a2, HALF_LENGTH));

        thread0.start();
        thread1.start();

        thread0.join();
        thread1.join();

        printingTheSpentTime(a, "methodTwo");
    }

    private static void printingTheSpentTime(long a, String text) {
        System.out.printf("%s: %d ms to run. \n", text, (System.currentTimeMillis() - a));
    }

    private static void assemblyDisassemblyArray(float[] sourceArray, float[] destinationArray, int startingElementIndex) {
        System.arraycopy(sourceArray, startingElementIndex, destinationArray, 0, HALF_LENGTH);
        methodThatCountsSomething(destinationArray);
        System.arraycopy(destinationArray, 0, sourceArray, startingElementIndex, HALF_LENGTH);
    }

    static float[] methodThatCountsSomething(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }

    static void fillsThisArrayWithOnes(float[] arr) {
        Arrays.fill(arr, (float) 1.0);
    }

    static float[] createsAOneDimensionalLongArray(int size) {
        return new float[size];
    }
}

