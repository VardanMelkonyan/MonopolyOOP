package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.InvalidParamException;
import com.oop.monopolySpring.exceptions.NotEnoughMoneyException;
import com.oop.monopolySpring.storage.GameStorage;

public class ColoredProperty extends Property {
    private final Color color;
    private final int[] rentPrice;
    private final int housePrice;
    private int numberOfHouses;

    public ColoredProperty(int position, String name, int price, int mortgageValue, Color color, int[] rentPrice, int housePrice) {
        super(position, name, price, mortgageValue, PGroup.COLORED);
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
    public int getHousePrice(){
        return this.housePrice;
    }

    public void buildHouse() throws NotEnoughMoneyException, InvalidParamException {
        if (numberOfHouses == 5)
            return;
        for (int index : color.sameColorPs) {
            if (Board.getPropertyAtIndex(index).getOwner() != getOwner())
                throw new InvalidParamException("You need to own all of the same color properties");
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

    public void sellHouseOrHotel() throws InvalidParamException {
        if (numberOfHouses == 5) {
            getOwner().addBalance(5 * housePrice / 2);
            GameStorage.getGame().decreaseRemainingNumberOfHotels(-1);
            numberOfHouses = 0;
            return;
        } else if (numberOfHouses > 0) {
            getOwner().addBalance(housePrice / 2);
            GameStorage.getGame().decreaseRemainingNumberOfHouses(-1);
            numberOfHouses--;
            return;
        }
        throw new InvalidParamException("There are no houses or hotels on this property");
    }

    @Override
    public void reset() {
        super.reset();
        numberOfHouses = 0;
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
