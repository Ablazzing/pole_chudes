package org.javaacademy.wonderfield.gifts;

/**
 * Подарки которые можно купить за очки
 */
public enum PointItem {
    FRIDGE("Холодильник", 1000),
    MICROWAVE("Микроволновка", 500),
    PC("Компьютер", 2000),
    LAPTOP("Ноутбук", 2500),
    TRAVEL("Путешествие в Анапу", 3000);
    private String name;
    private int pointCost;

    PointItem(String name, int pointCost) {
        this.name = name;
        this.pointCost = pointCost;
    }

    public String getInfo() {
        return name + " - " + pointCost;
    }

    public String getName() {
        return name;
    }

    public int getPointCost() {
        return pointCost;
    }
}
