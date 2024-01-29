package org.javaacademy.wonderfield.player;

import java.util.Locale;

/**
 * Ответ игрока
 */
public class PlayerAnswer {
    private AnswerType answerType;
    private String answer;

    public PlayerAnswer(AnswerType answerType, String answer) {
        this.answerType = answerType;
        this.answer = answer.toLowerCase(Locale.ROOT);
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public String getAnswer() {
        return answer;
    }
}
