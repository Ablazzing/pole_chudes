package org.javaacademy.wonderfield.util;

/**
 * Вспомогательный класс для текста
 */
public class StringUtil {
    //Вариант без регулярных выражений :)
    public static final String RUSSIAN_ALPHABET = "йцукенгшщзхъфывапролджэячсмитьбю";
    public static final String COMMA_DELIMITER = ",";

    /**
     * Соединение строк через разделитель
     */
    public static String joinWithDelimiter(String[] array, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            if (i != array.length - 1) {
                stringBuilder.append(delimiter);
            }
        }
        return stringBuilder.toString();
    }
}
