package games.icebreaker;

import java.util.HashSet;

public class IcebreakerCoordinate {
    private int x;
    private int y;

    public IcebreakerCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IcebreakerCoordinate(String string) {
        this.x = string.getBytes()[0] - 64;
        this.y = Character.getNumericValue(string.charAt(1));
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public HashSet<IcebreakerCoordinate> getAdjacents() {
        HashSet<IcebreakerCoordinate> adjacents = new HashSet<IcebreakerCoordinate>();

        if (this.x < 5) {
            adjacents.add(new IcebreakerCoordinate(x - 1, y - 1));
            adjacents.add(new IcebreakerCoordinate(x - 1, y));
            adjacents.add(new IcebreakerCoordinate(x, y - 1));
            adjacents.add(new IcebreakerCoordinate(x, y + 1));
            adjacents.add(new IcebreakerCoordinate(x + 1, y));
            adjacents.add(new IcebreakerCoordinate(x + 1, y + 1));
        } else if (this.x == 5) {
            adjacents.add(new IcebreakerCoordinate(x - 1, y - 1));
            adjacents.add(new IcebreakerCoordinate(x - 1, y));
            adjacents.add(new IcebreakerCoordinate(x, y - 1));
            adjacents.add(new IcebreakerCoordinate(x, y + 1));
            adjacents.add(new IcebreakerCoordinate(x + 1, y - 1));
            adjacents.add(new IcebreakerCoordinate(x + 1, y));
        } else {
            adjacents.add(new IcebreakerCoordinate(x - 1, y));
            adjacents.add(new IcebreakerCoordinate(x - 1, y + 1));
            adjacents.add(new IcebreakerCoordinate(x, y - 1));
            adjacents.add(new IcebreakerCoordinate(x, y + 1));
            adjacents.add(new IcebreakerCoordinate(x + 1, y - 1));
            adjacents.add(new IcebreakerCoordinate(x + 1, y));
        }

        return adjacents;
    }

    @Override
    public String toString() {
        return Character.toString((char) this.x + 64) + this.y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (!(object instanceof IcebreakerCoordinate))
            return false;

        IcebreakerCoordinate coordinate = (IcebreakerCoordinate) object;

        return this.x == coordinate.x && this.y == coordinate.y;
    }

    @Override
    public int hashCode() {
        return this.x * 10 + this.y;
    }
}
