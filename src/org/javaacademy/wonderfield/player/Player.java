package org.javaacademy.wonderfield.player;

import org.javaacademy.wonderfield.Game;
import org.javaacademy.wonderfield.gifts.PointItem;
import org.javaacademy.wonderfield.gifts.SuperGift;
import org.javaacademy.wonderfield.util.GameUtil;

import static org.javaacademy.wonderfield.player.AnswerType.LETTER;
import static org.javaacademy.wonderfield.player.AnswerType.WORD;
import static org.javaacademy.wonderfield.util.GameUtil.findFirstNullInItemsArray;
import static org.javaacademy.wonderfield.util.StringUtil.RUSSIAN_ALPHABET;

/**
 * Игрок
 */
public class Player {
    private static final String ASK_TYPE_QUESTION = "Если хотите сказать букву нажмите 'б' и enter,"
            + " если хотите сказать слово нажмите 'c' и enter";
    private final String name;
    private final String city;
    private int points;
    private int money;
    private int countRightAnswersInRow = 0;
    private final PointItem[] items = new PointItem[20];
    private SuperGift superGift;

    public Player(String name, String city) {
        this.name = name;
        this.city = city;
    }

    /**
     * Сказать ответ
     */
    public PlayerAnswer makeAnswer() {
        System.out.println(ASK_TYPE_QUESTION);
        while (true) {
            String letter = Game.SCANNER.nextLine().trim().substring(0, 1);
            if (LETTER.getCommandName().equals(letter)) {
                return new PlayerAnswer(LETTER, sayLetter());
            } else if (WORD.getCommandName().equals(letter)) {
                return new PlayerAnswer(WORD, sayWord());
            }
            System.out.println("Некорректное значение, введите 'б' или 'с'");
        }
    }

    /**
     * Купить вещь за очки
     */
    public void buyItem(PointItem pointItem) {
        this.items[findFirstNullInItemsArray(this.items)] = pointItem;
        this.addPoints(pointItem.getPointCost() * -1);
    }

    public void printResultGame() {
        String itemsText = GameUtil.getItemsText(items);
        String superGiftText = this.superGift == null ? "" : "Суперприз - " + this.superGift.getDescription();
        String result = String.format("Победитель ушел с следующим результатом:\nДеньги - %s\nВещи: %s\n"
                        + "%s", money, itemsText, superGiftText);
        System.out.println(result.trim());
        System.out.println("___________________________________________");
    }

    /**
     * Добавить денег
     */
    public void addMoney(int money) {
        this.money += money;
    }

    /**
     * Инкремент количества правильных ответов подряд
     */
    public void addRightAnswer() {
        countRightAnswersInRow++;
    }

    /**
     * Очистка количества правильных ответов подряд
     */
    public void clearRightAnswersInRow() {
        countRightAnswersInRow = 0;
    }

    /**
     * Добавить очки
     */
    public void addPoints(int value) {
        this.points += value;
    }

    /**
     * Удвоение очков
     */
    public void doublePoints() {
        this.points *= 2;
    }

    /**
     * Сказать слово
     */
    public String sayWord() {
        System.out.printf("Игрок %s: слово (введите слово) - ", name);
        return Game.SCANNER.nextLine().trim();
    }

    /**
     * Сказать букву
     */
    public String sayLetter() {
        System.out.printf("Игрок %s: Буква (введите букву) - ", name);
        while (true) {
            String letter = Game.SCANNER.nextLine().trim().substring(0, 1).toLowerCase();
            if (RUSSIAN_ALPHABET.contains(letter)) {
                return letter;
            }
            System.out.println("Ошибка! это не русская буква, введите русскую букву.");
        }
    }

    public int getCountRightAnswersInRow() {
        return countRightAnswersInRow;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setSuperGift(SuperGift superGift) {
        this.superGift = superGift;
    }
}
