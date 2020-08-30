package lesson2.exception;

/*Написать собственные классы исключений для каждого из случаев*/
/**
 * Класс исключения, если в одной из ячеек полученной матрицы не число; (например символ или слово)
 */
public class WordInsteadOfNumber extends Exception {
    public WordInsteadOfNumber(String message) {
        super(message);
    }
}
