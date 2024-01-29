package org.javaacademy.wonderfield.gifts;

public enum SuperGift {
    CAR("Автомобиль"),
    APARTMENT("Квартира в Сыктывкаре"),
    WORLD_TRAVEL("Кругосветное путешествие"),
    FREE_YEAR_TICKETS_ON_RAILS("Бесплатные путешествия в плацкарте в течении года");

    private String description;

    SuperGift(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
