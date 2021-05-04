package com.oop.monopolySpring.model.identifiers;

import com.oop.monopolySpring.model.identifiers.BuyPropertyIdentifier;
import com.oop.monopolySpring.model.identifiers.PlayerIdentifier;

public class BuyPropertyWithAuctionIdentifier extends BuyPropertyIdentifier {
    private final int amount;

    public BuyPropertyWithAuctionIdentifier(int propertyIndex, PlayerIdentifier player, int amount) {
        super(propertyIndex, player);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
