package org.javaacademy.wonderfield;

import org.javaacademy.wonderfield.gifts.SuperGift;
import org.javaacademy.wonderfield.player.PlayerAnswer;
import org.javaacademy.wonderfield.wheel.RoundWheelValue;
import org.javaacademy.wonderfield.wheel.SectorType;

import static org.javaacademy.wonderfield.util.StringUtil.COMMA_DELIMITER;
import static org.javaacademy.wonderfield.util.StringUtil.joinWithDelimiter;

/**
 * Якубович (методы не нуждаются в описании)
 */
public class Yakubovich {

    public void startSpeech() {
        System.out.println("Якубович: Здравствуйте, уважаемые дамы и господа!"
                + " Пятница! В эфире капитал-шоу «Поле чудес»!");
    }

    public void sayGoodbye() {
        System.out.println("Якубович: Мы прощаемся с вами ровно на одну неделю! Здоровья вам, до встречи!");
    }

    public void invitePeople(int round, String[] people) {
        if (round == Game.INDEX_FINAL_GROUP_ROUND) {
            System.out.printf("Якубович: приглашаю победителей групповых этапов: %s\n",
                    joinWithDelimiter(people, COMMA_DELIMITER));
        } else {
            System.out.printf("Якубович: приглашаю %s тройку игроков: %s\n",
                    round + 1, joinWithDelimiter(people, COMMA_DELIMITER));
        }
    }

    public void askQuestion(String question) {
        System.out.println("Якубович: Внимание вопрос!\n" + question);
    }

    public void giveThreeAttemptsInSuperGame() {
        System.out.println("Якубович: вы можете назвать 3 любые буквы, и мы откроем их, если они есть на табло!");
    }

    public void winner(String name, String city, boolean isFinalRound) {
        if (isFinalRound) {
            System.out.printf("Якубович: И перед нами победитель! Это %s из %s\n", name, city);
        } else {
            System.out.printf("Якубович: Молодец! %s из %s проходит в финал!\n", name, city);
        }
        System.out.println("__________________________________");
    }

    public boolean checkAnswer(PlayerAnswer playerAnswer, Question roundQuestion, Tableau tableau) {
        switch (playerAnswer.getAnswerType()) {
            case WORD:
                return checkWord(roundQuestion.getAnswer(), playerAnswer.getAnswer(), tableau);
            case LETTER:
                return checkLetter(roundQuestion.getAnswer(), playerAnswer.getAnswer(), tableau);
            default:
                throw new RuntimeException("Неизвестный тип ответа");
        }
    }

    public boolean checkWord(String rightAnswer, String word, Tableau tableau) {
        if (rightAnswer.contains(word)) {
            System.out.printf("Якубович: %s! Абсолютно верно!\n", word);
            tableau.openWord();
            return true;
        }
        System.out.println("Якубович: Не верно! Следующий игрок, крутите барабан!");
        System.out.println("__________________________________");
        return false;
    }

    public boolean checkSuperGameWord(String rightAnswer, String word) {
        if (rightAnswer.contains(word)) {
            System.out.printf("Якубович: %s! Абсолютно верно! И перед нами победитель супер игры!\n", word);
            return true;
        }
        System.out.printf("Якубович: Не верно! Правильный ответ: %s! "
                + "К сожалению сегодня не очень повезло!", rightAnswer);
        return false;
    }

    public void openSuperGift(SuperGift superGift) {
        System.out.printf("Якубович: Супер приз сегодня - %s\n", superGift.getDescription());
    }

    public boolean checkLetter(String rightAnswer, String letter, Tableau tableau) {
        if (rightAnswer.contains(letter)) {
            System.out.println("Якубович: Есть такая буква, откройте ее!");
            tableau.openLetter(letter);
            return true;
        }
        System.out.println("Якубович: Нет такой буквы! Следующий игрок, крутите барабан!");
        System.out.println("__________________________________");
        return false;
    }

    public void sayRoundWheelValue(RoundWheelValue roundWheelValue) {
        if (roundWheelValue.getType() == SectorType.SKIP_TURN) {
            System.out.println("Якубович: На барабане сектор пропуск хода! Следующий игрок!");
        } else if (roundWheelValue.getType() == SectorType.DOUBLE_SCORE) {
            System.out.println("Якубович: На барабане сектор удвоение! При правильном ответе игрока, очки удвоятся!");
        } else {
            System.out.printf("Якубович: На барабане %s очков!\n", roundWheelValue.getPoints());
        }
    }

    public void giveTwoCashBoxes() {
        System.out.println("Якубович: три правильных ответа подряд дают вам право на две шкатулки!\n"
                + "Якубович: Две шкатулки в студию!");
    }

    public void openBox(boolean hasMoney) {
        if (hasMoney) {
            System.out.println("Якубович: Шкатулка пустая! Не расстраивайтесь!");
        } else {
            System.out.println("Якубович: Вы угадали шкатулку с деньгами! Поздравляю, возьмите деньги!");
        }
    }

    public void openLetters() {
        System.out.println("Якубович: откройте буквы, если они есть!");
    }

    public void askAnswer() {
        System.out.println("Якубович: ваш ответ?");
    }
}
