package lesson5;

import java.util.Arrays;

/**
 * @author Valeriy Lazarev
 * @since 09.09.2020
 */

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    public static void main(String[] args) throws InterruptedException {

        long a = System.currentTimeMillis();
        methodOne();
        methodTwo();
        printingTheSpentTime(a, "method main");
    }

    static void methodOne() {
        float[] arr = createsAOneDimensionalLongArray();
        long a = System.currentTimeMillis();

        fillsThisArrayWithOnes(arr);
        methodThatCountsSomething(arr);

        printingTheSpentTime(a, "methodOne");
    }

    private static void methodTwo() throws InterruptedException {
        float[] arr = createsAOneDimensionalLongArray();
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        long a = System.currentTimeMillis();
        Thread thread0 = new Thread(() -> assemblyDisassemblyArray(arr, a1, 0));

        Thread thread1 = new Thread(() -> assemblyDisassemblyArray(arr, a2, h));

        thread0.start();
        thread1.start();

        thread0.join();
        thread1.join();

        printingTheSpentTime(a, "methodTwo");
    }

    private static void printingTheSpentTime(long a, String methodName) {
        System.out.printf("%s, %s: %d ms to run. \n", Thread.currentThread().getName(), methodName, (System.currentTimeMillis() - a));
    }

    private static void assemblyDisassemblyArray(float[] sourceArray, float[] destinationArray, int startingElementIndex) {

        long a = System.currentTimeMillis();
        System.arraycopy(sourceArray, startingElementIndex, destinationArray, 0, h);
        fillsThisArrayWithOnes(destinationArray);
        methodThatCountsSomething(destinationArray);
        System.arraycopy(destinationArray, 0, sourceArray, startingElementIndex, h);
    }

    static float[] methodThatCountsSomething(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }

    static void fillsThisArrayWithOnes(float[] arr) {
        Arrays.fill(arr, 1);
    }

    static float[] createsAOneDimensionalLongArray() {
        return new float[size];
    }
}

