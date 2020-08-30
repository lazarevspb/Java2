package lesson2.exception;

/*Написать собственные классы исключений для каждого из случаев*/
/**
 * Класс исключения, если размер матрицы, полученной из строки, не равен 4x4;
 */
public class ArraySizeDoesNotMatchFourByFourException extends Exception {
    public ArraySizeDoesNotMatchFourByFourException(String message) {
        super(message);
    }
}
