package com.oop.monopolySpring.model;

public class PlayerIdentifier {

    private String name;
    private String figureName;

    public PlayerIdentifier(String name, String figureName) {
        this.name = name;
        this.figureName = figureName;
    }

    public String getFigureName() {
        return figureName;
    }

    public String getName() {
        return name;
    }

    public void setFigureName(String figureName) {
        this.figureName = figureName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean compareToPlayer(Player player) {
        return name.equals(player.getName()) && figureName.equals(player.getFigure().name());
    }
}
