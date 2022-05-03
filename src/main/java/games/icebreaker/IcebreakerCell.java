package games.icebreaker;

public enum IcebreakerCell {
    ICEBERG, RED_SHIP, BLACK_SHIP;

    public static IcebreakerCell get(String string) {
        switch (string) {
            case "o":
                return ICEBERG;
            case "R":
                return RED_SHIP;
            case "B":
                return BLACK_SHIP;
            default:
                return null;
        }
    }

    public IcebreakerRole getRole() {
        switch (this) {
            case RED_SHIP:
                return IcebreakerRole.RED;
            case BLACK_SHIP:
                return IcebreakerRole.BLACK;
            default:
                return null;
        }
    }

    public String toString() {
        switch (this) {
            case ICEBERG:
                return "o";
            case RED_SHIP:
                return "R";
            case BLACK_SHIP:
                return "B";
            default:
                return "•";
        }
    }
}
