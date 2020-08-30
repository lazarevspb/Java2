package lesson2;

import lesson2.exception.ArraySizeDoesNotMatchFourByFourException;
import lesson2.exception.WordInsteadOfNumber;
import src.draft.UtilMethod;

/**
 * Homework for lesson #2
 * <p>
 * Есть строка вида: "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
 * (другими словами матрица 4x4)
 * <p>
 * 10 3 1 2
 * 2 3 2 2
 * 5 6 7 1
 * 300 3 1 0
 * <p>
 * Написать метод, на вход которого подаётся такая строка,
 * метод должен преобразовать строку в двумерный массив типа String[][];
 * <p>
 * 2. Преобразовать все элементы массива в числа типа int,
 * просуммировать, поделить полученную сумму на 2, и вернуть результат;
 * <p>
 * 3. Ваши методы должны бросить исключения в случаях:
 * Если размер матрицы, полученной из строки, не равен 4x4;
 * Если в одной из ячеек полученной матрицы не число; (например символ или слово);
 * <p>
 * 4. В методе main необходимо вызвать полученные методы,
 * обработать возможные исключения и вывести результат расчета.
 * <p>
 * 5. * Написать собственные классы исключений для каждого из случаев
 *
 * @author Valeriy Lazarev
 * @since 29.08.2020
 */

public class Main extends UtilMethod {
    private static String[][] stringArray;
    private static final String stringResources = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";
    private static int[][] numeric;
    public static int sumNumbers;
    public static float result;

    public static void main(String[] args) {
        /*Написать метод, на вход которого подаётся такая строка,
         * метод должен преобразовать строку в двумерный массив типа String[][];*/
        stringArray = stringToArrayStringConvert(stringResources);

        /*4. В методе main необходимо вызвать полученные методы,
         * обработать возможные исключения */
        try {
            numeric = stringConvertToNumber(stringArray);
        } catch (ArraySizeDoesNotMatchFourByFourException | WordInsteadOfNumber e) {
            e.printStackTrace();
        }
        /*просуммировать*/
        sumNumbers = sumTheNumbers(numeric);
        /*поделить полученную сумму на 2*/
        result = divisionOfNumbers(sumNumbers);
        /*и вывести результат расчета.*/
        System.out.println(result);
    }

    /**
     * Метод, делящий число на 2;
     *
     * @param i целочисленная переменнаяж
     * @return возвращает число с плавающей точкой;
     * @throws ArithmeticException при делении на ноль выбрасывает исключение;
     */
    public static float divisionOfNumbers(int i) throws ArithmeticException {
        return i / 2f;
    }

    /**
     * Метод, производящий суммирование всех элементов двумерного массиива;
     *
     * @param numeric на вход подается двумерный массив;
     * @return возвращает целочисленнуй перемменную.
     */
    public static int sumTheNumbers(int[][] numeric) {
        int resultSum = 0;
        for (int[] ints : numeric) {
            for (int anInt : ints) {
                resultSum += anInt;
            }
        }
        return resultSum;
    }

    /**
     * Метод распарсивающий симолы в числа;
     *
     * @param stringArray двумерный массив строк содержащий числа;
     * @return двумерный массив чисе;
     * @throws ArraySizeDoesNotMatchFourByFourException выкидывает исключение, если размер матрицы,
     *                                                  полученной из строки, не равен 4x4;
     * @throws WordInsteadOfNumber                      выкидывает исключение если в одной из ячеек
     *                                                  полученной матрицы не число; (например символ или слово);
     */
    private static int[][] stringConvertToNumber(String[][] stringArray)
            throws ArraySizeDoesNotMatchFourByFourException, WordInsteadOfNumber {
        int[][] resultArray;


        if (stringArray.length == 4 && stringArray[0].length == 4) {
            resultArray = new int[stringArray.length][stringArray[0].length];
            for (int i = 0; i < stringArray.length; i++) {
                for (int j = 0; j < stringArray[i].length; j++) {

                    if (stringArray[i][j].matches("\\d+")) { //Проверяет является ли строка числом
                        resultArray[i][j] = Integer.parseInt(stringArray[i][j]);
                    } else {
                        throw new WordInsteadOfNumber("The array contains a Word instead of a digit.");
                    }
                }
            }
        } else {
            throw new ArraySizeDoesNotMatchFourByFourException("array size does not match four by four.");
        }
        return resultArray;
    }

    /**
     * Метод, получающий на вход строку, разделяет ее на элементы массива при нахождении символа перенос строки и,
     * в полследствии символа пробела;
     *
     * @param stringResources строковый ресурс;
     * @return результат работы метода - двумерный массив.
     */
    private static String[][] stringToArrayStringConvert(String stringResources) {
        String[] tempArray;
        tempArray = stringResources.split("\n");
        String[][] tempMultyStringArray = new String[tempArray.length][tempArray[0].length() / 2];
        for (int i = 0; i < tempArray.length; i++) {
            tempMultyStringArray[i] = tempArray[i].split(" ");
        }
        return tempMultyStringArray;
    }
}
