package lesson2.exception;

/*Написать собственные классы исключений для каждого из случаев*/
/**
 * Класс исключения, если размер матрицы, полученной из строки, не равен 4x4;
 */
public class SizeDoesNotMatchFourByFourException extends IndexOutOfBoundsException {
    public SizeDoesNotMatchFourByFourException(String message) {
        super(message);
    }
}
