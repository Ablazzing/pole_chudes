package org.javaacademy.wonderfield.player;

public enum AnswerType {
    WORD("с"), LETTER("б");
    private String commandName;

    AnswerType(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
