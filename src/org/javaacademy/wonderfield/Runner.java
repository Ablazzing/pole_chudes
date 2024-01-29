package org.javaacademy.wonderfield;

public class Runner {
    public static void main(String[] args) {
        Game game = new Game();
        //Тестовый запуск - чисто для обучения
        //В реале не надо смешивать тесты и промышленный код.
        boolean isTestRun = true;
        game.init(isTestRun);
        game.start(isTestRun);
    }
}
