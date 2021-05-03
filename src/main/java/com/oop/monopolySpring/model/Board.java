package com.oop.monopolySpring.model;

import com.oop.monopolySpring.exceptions.OutOfBoardBoundsException;

public class Board {
    private static final Property[] properties = new Property[40];

    public enum PositionType {
        PROPERTY, CHANCE, COMMUNITY, JAIL, GO_TO_JAIL, PARKING, GO, INCOME_TAX, LUXURY_TAX;

        public static PositionType getPositionType(int position) throws OutOfBoardBoundsException {
            if (position < 0 || position >= 40) throw new OutOfBoardBoundsException();
            return switch (position) {
                case 7, 22, 36 -> CHANCE;
                case 2, 17, 33 -> COMMUNITY;
                case 0 -> GO;
                case 10 -> JAIL;
                case 20 -> PARKING;
                case 30 -> GO_TO_JAIL;
                case 38 -> LUXURY_TAX;
                case 4 -> INCOME_TAX;
                default -> PROPERTY;
            };
        }
    }

    public static void setup() {
        properties[1] = new ColoredProperty("Mediterranean Avenue", 60, 30, ColoredProperty.Color.BROWN, new int[]{2, 10, 30, 90, 160, 250}, 50);
        properties[3] = new ColoredProperty("Baltic Avenue", 60, 30, ColoredProperty.Color.BROWN, new int[]{4, 20, 60, 180, 320, 450}, 50);
        properties[5] = new RailRoadProperty("Reading Railroad");
        properties[6] = new ColoredProperty("Oriental Avenue", 100, 50, ColoredProperty.Color.PURPLE, new int[]{6, 30, 90, 270, 400, 550}, 50);
        properties[8] = new ColoredProperty("Vermont Avenue", 100, 50, ColoredProperty.Color.PURPLE, new int[]{6, 30, 90, 270, 400, 550}, 50);
        properties[9] = new ColoredProperty("Connecticut Avenue", 120, 60, ColoredProperty.Color.PURPLE, new int[]{8, 40, 100, 300, 450, 600}, 50);

        properties[11] = new ColoredProperty("St. Charles Place", 140, 70, ColoredProperty.Color.PINK, new int[]{10, 50, 150, 450, 625, 750}, 100);
        properties[12] = new UtilityProperty("Electric Company", 28);
        properties[13] = new ColoredProperty("States Avenue", 140, 70, ColoredProperty.Color.PINK, new int[]{10, 50, 150, 450, 625, 750}, 100);
        properties[14] = new ColoredProperty("Virginia Avenue", 160, 80, ColoredProperty.Color.PURPLE, new int[]{12, 60, 180, 500, 700, 900}, 100);
        properties[15] = new RailRoadProperty("Pennsylvania Railroad");
        properties[16] = new ColoredProperty("St. James Place", 180, 90, ColoredProperty.Color.ORANGE, new int[]{14, 70, 200, 550, 750, 950}, 100);
        properties[18] = new ColoredProperty("Tennessee Avenue", 180, 90, ColoredProperty.Color.ORANGE, new int[]{14, 70, 200, 550, 750, 950}, 100);
        properties[19] = new ColoredProperty("New York Avenue", 200, 100, ColoredProperty.Color.ORANGE, new int[]{16, 80, 220, 600, 800, 1000}, 100);

        properties[21] = new ColoredProperty("Kentucky Avenue", 220, 110, ColoredProperty.Color.RED, new int[]{18, 90, 250, 700, 875, 1050}, 150);
        properties[23] = new ColoredProperty("Indiana Avenue", 220, 110, ColoredProperty.Color.RED, new int[]{18, 90, 250, 700, 875, 1050}, 150);
        properties[24] = new ColoredProperty("Illinois Avenue", 240, 120, ColoredProperty.Color.RED, new int[]{20, 100, 300, 750, 925, 1100}, 150);
        properties[25] = new RailRoadProperty("B & O Railroad");
        properties[26] = new ColoredProperty("Atlantic Avenue", 260, 130, ColoredProperty.Color.YELLOW, new int[]{22, 110, 330, 800, 975, 1150}, 150);
        properties[27] = new ColoredProperty("Ventnor Avenue", 260, 130, ColoredProperty.Color.YELLOW, new int[]{22, 110, 330, 800, 975, 1150}, 150);
        properties[28] = new UtilityProperty("Water Works", 12);
        properties[29] = new ColoredProperty("Marvin Gardens", 280, 140, ColoredProperty.Color.YELLOW, new int[]{24, 120, 360, 850, 1025, 1200}, 150);

        properties[31] = new ColoredProperty("Pacific Avenue", 300, 150, ColoredProperty.Color.GREEN, new int[]{26, 130, 390, 900, 1100, 1275}, 200);
        properties[32] = new ColoredProperty("North Carolina Avenue", 300, 150, ColoredProperty.Color.GREEN, new int[]{26, 130, 390, 900, 1100, 1275}, 200);
        properties[34] = new ColoredProperty("Pennsylvania Avenue", 320, 160, ColoredProperty.Color.GREEN, new int[]{28, 150, 540, 1000, 1200, 1400}, 200);
        properties[35] = new RailRoadProperty("Short Line");
        properties[37] = new ColoredProperty("Park Place", 350, 175, ColoredProperty.Color.BLUE, new int[]{35, 175, 500, 1100, 1300, 1500}, 200);
        properties[39] = new ColoredProperty("Boardwalk", 400, 200, ColoredProperty.Color.BLUE, new int[]{50, 200, 600, 1400, 1700, 2000}, 200);
    }

    public static Property getPropertyAtIndex(int index) {
        return properties[index];
    }

    public static Property[] getProperties() {
        Property[] copy = new Property[40];
        for (int i = 0; i < 40; i++)
            copy[i] = properties[i];
        return copy;
    }
}
