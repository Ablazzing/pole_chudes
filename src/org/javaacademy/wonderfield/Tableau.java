package org.javaacademy.wonderfield;

/**
 * Табло
 */
public class Tableau {
    private String answer;
    private String[] letters;

    /**
     * Добавить ответ на табло(инициализация)
     */
    public void addAnswer(String text) {
        this.letters = new String[text.length()];
        this.answer = text;
    }

    /**
     * Показать табло
     */
    public void show() {
        checkFilledAnswer();
        StringBuilder text = new StringBuilder("ТАБЛО: ");
        for (String letter : letters) {
            text.append(letter == null ? "_" : letter).append(" ");
        }
        System.out.println(text.toString());
    }

    /**
     * Открыть букву
     */
    public void openLetter(String letter) {
        checkFilledAnswer();
        String[] answerLetters = answer.split("");
        for (int i = 0; i < answerLetters.length; i++) {
            if (answerLetters[i].equals(letter)) {
                letters[i] = letter;
            }
        }
    }

    /**
     * Открыть слово
     */
    public void openWord() {
        checkFilledAnswer();
        letters = answer.split("");
    }

    /**
     * Наличие неотгаданных букв на табло
     */
    public boolean containsUnknownLetters() {
        for (String letter : letters) {
            if (letter == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка наличия ответа на табло
     */
    private void checkFilledAnswer() {
        if (letters == null || answer == null || letters.length == 0 || answer.length() == 0) {
            throw new RuntimeException("Табло не инициализировано");
        }
    }
}
