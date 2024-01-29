package org.javaacademy.wonderfield;

import org.javaacademy.wonderfield.gifts.PointItem;
import org.javaacademy.wonderfield.gifts.SuperGift;
import org.javaacademy.wonderfield.player.Player;
import org.javaacademy.wonderfield.player.PlayerAnswer;
import org.javaacademy.wonderfield.util.GameUtil;
import org.javaacademy.wonderfield.util.StringUtil;
import org.javaacademy.wonderfield.wheel.RoundWheel;
import org.javaacademy.wonderfield.wheel.RoundWheelValue;
import org.javaacademy.wonderfield.wheel.SectorType;

import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static org.javaacademy.wonderfield.util.GameUtil.changePlayerPoints;
import static org.javaacademy.wonderfield.util.GameUtil.getNameFromPlayers;
import static org.javaacademy.wonderfield.util.GameUtil.giveRandomSuperGift;
import static org.javaacademy.wonderfield.util.GameUtil.printItemsForBuy;
import static org.javaacademy.wonderfield.util.GameUtil.printPlayerPoints;
import static org.javaacademy.wonderfield.util.GameUtil.sleep;

/**
 * Игра "Поле Чудес"
 */
public class Game {
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final Random RANDOM = new Random();
    private static final int ROUND_COUNT = 4;
    public static final int INDEX_FINAL_GROUP_ROUND = 3;
    private static final int GROUP_ROUND_COUNT = 3;
    private static final int PLAYERS_ROUND_COUNT = 3;
    private static final int COUNT_RIGHT_ANSWERS_FOR_CASH_BOX = 3;
    private static final String INIT_START_TEXT = "Запуск игры \"Поле чудес\" - подготовка к игре. "
            + "Вам нужно ввести вопросы и ответы для игры";
    private static final String TEST_QUESTION = "Что?";
    private static final String TEST_ANSWER = "что";
    private static final String TEST_PLAYER_NAME = "Юрий";
    private static final String TEST_PLAYER_CITY = "Москва";
    public static final int MONEY_IN_BOX = 5_000;
    private static final long TIME_TO_SLEEP_AFTER_INIT = 5_000;
    private final Question[] roundQuestions = new Question[ROUND_COUNT];
    private final Yakubovich yakubovich = new Yakubovich();
    private final Player[] winnersOfRounds = new Player[PLAYERS_ROUND_COUNT];
    private final Tableau tableau = new Tableau();
    private final RoundWheel roundWheel = new RoundWheel();
    private boolean isGameInit = false;
    private Question superGameQuestion;
    private Player gameWinner;
    private SuperGift superGift;

    /**
     * Инициализация игры (создание вопросов и игроков)
     */
    public void init(boolean isTestRun) {
        System.out.println(INIT_START_TEXT);
        if (isTestRun) {
            initQuestionsTest();
        } else {
            initQuestions();
        }
        endOfInit();
    }

    /**
     * Запуск игры
     */
    public void start(boolean isTestRun) {
        if (!isGameInit) {
            throw new RuntimeException("Игра не инициализирована! Старт не возможен!");
        }
        yakubovich.startSpeech();
        runGroupRounds(isTestRun);
        runFinalGroupRound();
        winnerBuyItemsForPoints();
        superGameAsk();
        yakubovich.sayGoodbye();
    }

    /**
     * Хочет ли победитель сыграть в суперигру
     */
    private void superGameAsk() {
        superGift = giveRandomSuperGift();
        System.out.println("Хотите ли вы сыграть в суперигру? Введите 'да' или 'нет'");
        while (true) {
            System.out.print("Мой ответ: ");
            String inputData = SCANNER.nextLine();
            if ("нет".equalsIgnoreCase(inputData)) {
                break;
            }
            if ("да".equalsIgnoreCase(inputData)) {
                superGameStart();
                break;
            }
            System.out.println("Некорректное значение");
        }
        gameWinner.printResultGame();
    }

    /**
     * Старт суперигры
     */
    private void superGameStart() {
        System.out.println("___________________________________________");
        yakubovich.askQuestion(superGameQuestion.getQuestion());
        yakubovich.giveThreeAttemptsInSuperGame();
        tableau.addAnswer(superGameQuestion.getAnswer());
        for (int i = 0; i < 3; i++) {
            tableau.openLetter(gameWinner.sayLetter());
        }
        yakubovich.openLetters();
        tableau.show();
        yakubovich.askAnswer();
        String playerAnswer = gameWinner.sayWord();
        boolean isRightAnswer = yakubovich.checkSuperGameWord(superGameQuestion.getAnswer(), playerAnswer);
        yakubovich.openSuperGift(superGift);
        if (isRightAnswer) {
            gameWinner.setSuperGift(superGift);
        }
        System.out.println("___________________________________________");
    }

    /**
     * Покупка победителем подарков за очки
     */
    private void winnerBuyItemsForPoints() {
        printItemsForBuy();
        System.out.println("Выберите вещь, указав ее номер. Для окончания выбора введите 'все'");
        while (true) {
            printPlayerPoints(gameWinner);
            boolean isEndOfBuying = chooseItemForPoints();
            if (isEndOfBuying) {
                break;
            }
        }
    }

    /**
     * Выбор одной вещи
     */
    private boolean chooseItemForPoints() {
        try {
            System.out.print("Я выбираю (введите номер): ");
            String inputData = SCANNER.nextLine();
            if ("все".equalsIgnoreCase(inputData)) {
                return true;
            }
            int indexItem = Integer.parseInt(inputData) - 1;
            PointItem item = PointItem.values()[indexItem];
            if (gameWinner.getPoints() < item.getPointCost()) {
                System.out.println("Не хватает очков для покупки, выберите другую вещь");
                return false;
            }
            gameWinner.buyItem(item);
        } catch (Exception exception) {
            System.out.println("Некорректный индекс вещи");
        }
        return false;
    }

    /**
     * Инициализация вопросов для раундов
     */
    private void initQuestions() {
        for (int i = 0; i < ROUND_COUNT; i++) {
            Question roundQuestion = new Question();
            int numberQuestion = i + 1;
            System.out.println("Введите вопрос #" + numberQuestion);
            roundQuestion.setQuestion(SCANNER.nextLine().toLowerCase());
            System.out.println("Введите ответ на вопрос #" + numberQuestion);
            roundQuestion.setAnswer(SCANNER.nextLine().toLowerCase());
            roundQuestions[i] = roundQuestion;
        }
        this.superGameQuestion = new Question();
        System.out.println("Введите вопрос на суперигру");
        superGameQuestion.setQuestion(SCANNER.nextLine().toLowerCase());
        System.out.println("Введите ответ на вопрос");
        superGameQuestion.setAnswer(SCANNER.nextLine().toLowerCase());
    }

    /**
     * Инициализация вопросов тестовая (без ввода)
     */
    private void initQuestionsTest() {
        System.out.printf("Ответ на все тестовые вопросы: %s\n", TEST_ANSWER);
        for (int i = 0; i < ROUND_COUNT; i++) {
            Question roundQuestion = new Question();
            roundQuestion.setQuestion(TEST_QUESTION);
            roundQuestion.setAnswer(TEST_ANSWER);
            roundQuestions[i] = roundQuestion;
        }
        superGameQuestion = new Question(TEST_QUESTION, TEST_ANSWER);
    }

    /**
     * Старт групповых раундов
     */
    private void runGroupRounds(boolean isTestRun) {
        for (int i = 0; i < GROUP_ROUND_COUNT; i++) {
            Player[] players = makePlayers(isTestRun);
            tableau.addAnswer(roundQuestions[i].getAnswer());
            yakubovich.invitePeople(i, getNameFromPlayers(players));
            yakubovich.askQuestion(roundQuestions[i].getQuestion());
            tableau.show();
            playRound(roundQuestions[i], players, i, false);
        }
    }

    /**
     * Запуск финального раунда
     */
    private void runFinalGroupRound() {
        tableau.addAnswer(roundQuestions[INDEX_FINAL_GROUP_ROUND].getAnswer());
        yakubovich.invitePeople(INDEX_FINAL_GROUP_ROUND, getNameFromPlayers(winnersOfRounds));
        yakubovich.askQuestion(roundQuestions[INDEX_FINAL_GROUP_ROUND].getQuestion());
        tableau.show();
        playRound(roundQuestions[INDEX_FINAL_GROUP_ROUND], winnersOfRounds, INDEX_FINAL_GROUP_ROUND, true);
    }

    /**
     * Играть раунд
     */
    private void playRound(Question roundQuestion, Player[] players,
                           int roundNumber, boolean isFinalRound) {
        while (!playersTurn(players, roundQuestion, isFinalRound, roundNumber));
    }

    /**
     * Ход игроков по очереди внутри раунда
     */
    private boolean playersTurn(Player[] players, Question roundQuestion,
                                boolean isFinalRound, int roundNumber) {
        for (Player player : players) {
            boolean isWinner = playerTurn(roundQuestion, player);
            if (isWinner) {
                makeWinner(isFinalRound, player, roundNumber);
                return true;
            }
        }
        return false;
    }

    /**
     * Ход игрока
     */
    private boolean playerTurn(Question roundQuestion, Player player) {
        while (true) {
            System.out.printf("Ход игрока %s,%s\n", player.getName(), player.getCity());
            RoundWheelValue roundWheelValue = roundWheel.rotateWheel();
            yakubovich.sayRoundWheelValue(roundWheelValue);
            if (roundWheelValue.getType() == SectorType.SKIP_TURN) {
                return false;
            }
            PlayerAnswer playerAnswer = player.makeAnswer();
            boolean isRightAnswer = yakubovich.checkAnswer(playerAnswer, roundQuestion, tableau);
            if (!isRightAnswer) {
                player.clearRightAnswersInRow();
                return false;
            }
            changePlayerPoints(player, roundWheelValue);
            player.addRightAnswer();
            checkThreeRightAnswersInRow(player);
            tableau.show();
            if (GameUtil.isWinRound(tableau)) {
                return true;
            }
        }
    }

    /**
     * Проверка на 3 правильных подряд ответа игроком
     */
    private void checkThreeRightAnswersInRow(Player player) {
        if (player.getCountRightAnswersInRow() == COUNT_RIGHT_ANSWERS_FOR_CASH_BOX) {
            player.clearRightAnswersInRow();
            giveTwoBoxes(player);
        }
    }

    /**
     * Вынос шкатулок и открытие шкатулок
     */
    private void giveTwoBoxes(Player player) {
        yakubovich.giveTwoCashBoxes();
        int[] boxes = GameUtil.makeTwoCashBoxes();
        System.out.println("Выберите шкатулку: введите 1 - для левой шкатулки, 2 - для правой шкатулки");
        int numberBox = chooseCashBox();
        openCashBox(player, boxes, numberBox);
    }

    /**
     * Выбор шкатулки
     */
    private int chooseCashBox() {
        while (true) {
            String numberBox = SCANNER.nextLine().trim().substring(0, 1).toLowerCase();
            if ("1".equals(numberBox) || "2".equals(numberBox)) {
                return parseInt(numberBox);
            }
            System.out.println("Ошибка! нужно ввести '1' или '2'");
        }
    }

    /**
     * Открытие шкатулки и прибавление денег игроку
     */
    private void openCashBox(Player player, int[] boxes, int numberChosenBox) {
        int money = boxes[numberChosenBox - 1];
        player.addMoney(money);
        boolean hasMoneyInBox = money == 0;
        yakubovich.openBox(hasMoneyInBox);
    }

    /**
     * Запомнить победителя раунда и объявить его
     */
    private void makeWinner(boolean isFinalRound, Player player, int roundNumber) {
        if (!isFinalRound) {
            winnersOfRounds[roundNumber] = player;
        } else {
            this.gameWinner = player;
        }
        yakubovich.winner(player.getName(), player.getCity(), isFinalRound);
    }

    /**
     * Создание игроков
     */
    private Player[] makePlayers(boolean isTestRun) {
        if (isTestRun) {
            return createTestPlayers();
        }
        return createPlayersConsoleInput();
    }

    /**
     * Создание игроков через консоль
     */
    private Player[] createPlayersConsoleInput() {
        System.out.println("Инициализация игроков.");
        Player[] players = new Player[PLAYERS_ROUND_COUNT];
        for (int i = 0; i < players.length; i++) {
            System.out.printf("Игрок %s, представьтесь в формате: имя, город\n", i + 1);
            String[] input = SCANNER.nextLine().split(StringUtil.COMMA_DELIMITER);
            players[i] = new Player(input[0], input[1]);
        }
        return players;
    }

    /**
     * Создание тестовых игроков
     */
    private Player[] createTestPlayers() {
        Player[] players = new Player[PLAYERS_ROUND_COUNT];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(TEST_PLAYER_NAME + i, TEST_PLAYER_CITY);
        }
        return players;
    }

    /**
     * Конец инициализации игры
     */
    private void endOfInit() {
        System.out.println("Инициализация закончена, игра начнется через 5 секунд");
        sleep(TIME_TO_SLEEP_AFTER_INIT);
        System.out.println("\n".repeat(50));
        isGameInit = true;
    }
}
