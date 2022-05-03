package games.icebreaker;

import iialib.games.model.IRole;

public enum IcebreakerRole implements IRole {
    RED, BLACK;

    public static IcebreakerRole get(String string) {
        return (string.equals("RED")) ? IcebreakerRole.RED : IcebreakerRole.BLACK;
    }

    public IcebreakerCell getCell() {
        return this == RED ? IcebreakerCell.RED_SHIP : IcebreakerCell.BLACK_SHIP;
    }

    public IcebreakerRole invert() {
        return this == RED ? BLACK : RED;
    }
}
