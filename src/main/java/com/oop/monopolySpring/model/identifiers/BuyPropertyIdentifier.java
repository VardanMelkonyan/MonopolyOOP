package com.oop.monopolySpring.model.identifiers;

import com.oop.monopolySpring.model.Player;
import com.oop.monopolySpring.model.identifiers.PlayerIdentifier;

public class BuyPropertyIdentifier {
    private final int propertyIndex;
    private final PlayerIdentifier player;

    public BuyPropertyIdentifier(int propertyIndex, PlayerIdentifier player) {
        this.propertyIndex = propertyIndex;
        this.player = player;
    }

    public int getPropertyIndex() {
        return propertyIndex;
    }

    public Player getPlayer() {
        return player.getPlayer();
    }
}
