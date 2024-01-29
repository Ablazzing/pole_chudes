package org.javaacademy.wonderfield.util;

import org.javaacademy.wonderfield.Tableau;
import org.javaacademy.wonderfield.gifts.PointItem;
import org.javaacademy.wonderfield.gifts.SuperGift;
import org.javaacademy.wonderfield.player.Player;
import org.javaacademy.wonderfield.wheel.RoundWheelValue;
import org.javaacademy.wonderfield.wheel.SectorType;

import static org.javaacademy.wonderfield.Game.MONEY_IN_BOX;
import static org.javaacademy.wonderfield.Game.RANDOM;

public class GameUtil {

    /**
     * Создание двух шкатулок (одна с деньгами)
     */
    public static int[] makeTwoCashBoxes() {
        int numberBox = RANDOM.nextInt(2);
        int[] boxes = new int[2];
        boxes[numberBox] = MONEY_IN_BOX;
        return boxes;
    }

    /**
     * Печать очков игрока
     */
    public static void printPlayerPoints(Player player) {
        System.out.printf("У игрока %s очков \n", player.getPoints());
    }

    /**
     * Печать вещей на покупку
     */
    public static void printItemsForBuy() {
        System.out.println("Выберите вещи, которые хотите взять за очки");
        PointItem[] pointItems = PointItem.values();
        for (int i = 0; i < pointItems.length; i++) {
            int itemIndex = i + 1;
            System.out.printf("%s. %s\n", itemIndex, pointItems[i].getInfo());
        }
    }

    /**
     * Получить список купленных вещей
     */
    public static String getItemsText(PointItem[] items) {
        int countItems = 0;
        for (PointItem item : items) {
            if (item == null) {
                break;
            }
            countItems++;
        }
        String[] array = new String[countItems];
        for (int i = 0; i < countItems; i++) {
            array[i] = items[i].getName();
        }
        return String.join(",", array);
    }

    /**
     * Получись рандомный супер приз
     */
    public static SuperGift giveRandomSuperGift() {
        return SuperGift.values()[RANDOM.nextInt(SuperGift.values().length)];
    }

    /**
     * Поиск свободного места для новой вещи
     */
    public static int findFirstNullInItemsArray(PointItem[] items) {
        int index = 0;
        for (PointItem item : items) {
            if (item == null) {
                return index;
            }
            index++;
        }
        throw new RuntimeException("Нет места для вставки подарка!");
    }

    /**
     * Получение имен игроков
     */
    public static String[] getNameFromPlayers(Player[] players) {
        String[] names = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        return names;
    }

    /**
     * Прибавка/Умножение очков игрока
     */
    public static void changePlayerPoints(Player player, RoundWheelValue roundWheelValue) {
        if (roundWheelValue.getType() == SectorType.DOUBLE_SCORE) {
            player.doublePoints();
        } else {
            player.addPoints(roundWheelValue.getPoints());
        }
    }

    /**
     * Проверка, выиграл ли раунд игрок (заполнены все буквы на табло)
     */
    public static boolean isWinRound(Tableau tableau) {
        return !tableau.containsUnknownLetters();
    }

    /**
     * Сон программы
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
