package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.NotEnoughMoneyException;
import com.oop.monopolySpring.storage.GameStorage;

public class ColoredProperty extends Property {
    private final Color color;
    private final int[] rentPrice;
    private final int housePrice;
    private int numberOfHouses;

    public ColoredProperty(String name, int price, int mortgageValue, Color color, int[] rentPrice, int housePrice) {
        super(name, price, mortgageValue, PGroup.COLORED);
        this.color = color;
        this.rentPrice = rentPrice;
        this.housePrice = housePrice;
        this.numberOfHouses = 0;
    }

    @Override
    public int getRent() {
        return rentPrice[numberOfHouses];
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public void buildHouse() throws NotEnoughMoneyException {
        if (numberOfHouses == 5)
            return;
        for (int index : color.sameColorPs) {
            if (Board.getPropertyAtIndex(index).getOwner() != getOwner())
                return;
        }
        if (numberOfHouses < 4 && GameStorage.getGame().getRemainingNumberOfHouses() == 0)
            return;
        else if (numberOfHouses == 4 && GameStorage.getGame().getRemainingNumberOfHotels() == 0)
            return;
        getOwner().subtractBalance(housePrice);
        numberOfHouses++;
        if (numberOfHouses < 5) {
            GameStorage.getGame().decreaseRemainingNumberOfHouses(1);
        } else if (numberOfHouses == 5) {
            GameStorage.getGame().decreaseRemainingNumberOfHotels(1);
            GameStorage.getGame().decreaseRemainingNumberOfHouses(-4);
        }
    }

    public void sellHouseOrHotel() {
        if (numberOfHouses == 5) {
            getOwner().addBalance(5 * housePrice / 2);
            GameStorage.getGame().decreaseRemainingNumberOfHotels(-1);
            numberOfHouses = 0;
        } else if (numberOfHouses > 0) {
            getOwner().addBalance(housePrice / 2);
            GameStorage.getGame().decreaseRemainingNumberOfHouses(-1);
            numberOfHouses--;
        }
    }

    public enum Color {
        BROWN(1, 3),
        PURPLE(6, 8, 9),
        PINK(11, 13, 14),
        ORANGE(16, 18, 19),
        RED(21, 23, 24),
        YELLOW(26, 27, 29),
        GREEN(31, 32, 34),
        BLUE(37, 39);

        private final int[] sameColorPs;

        Color(int... indexes) {
            sameColorPs = indexes;
        }
    }

}
