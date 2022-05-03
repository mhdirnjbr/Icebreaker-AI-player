package games.icebreaker;

import java.util.ArrayList;

public class IcebreakerPosition {
    private final int x; // [A-I]
    private final int y; // [1-9]

    private ArrayList<IcebreakerPosition> adjacents;

    // À ne pas utiliser pour créer de nouvelles positions
    // À la place, utiliser la méthode : IcebreakerPositions.get(int x, int y)
    public IcebreakerPosition(int x, int y) throws Exception {
        if (y < 1 || y > 9)
            throw new Exception();

        if (y <= 5 && (x < 1 || x > 9)) // [A-I][1-5]
            throw new Exception();
        if ((y == 6) && (x < 2 || x > 8)) // [B-H]6
            throw new Exception();
        if ((y == 7) && (x < 3 || x > 7)) // [C-G]7
            throw new Exception();
        if ((y == 8) && (x < 4 || x > 6)) // [D-F]8
            throw new Exception();
        if ((y == 9) && (x != 5)) // E9
            throw new Exception();

        this.x = x;
        this.y = y;

        this.adjacents = new ArrayList<IcebreakerPosition>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<IcebreakerPosition> getAdjacents() {
        return this.adjacents;
    }

    public void addAdjacent(IcebreakerPosition adjacent) {
        this.adjacents.add(adjacent);
    }

    public IcebreakerCoordinate getCoordinate() {
        return new IcebreakerCoordinate(this.x, this.y);
    }

    @Override
    public String toString() {
        return (new IcebreakerCoordinate(this.x, this.y)).toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (!(object instanceof IcebreakerPosition))
            return false;

        return this.getCoordinate().equals(((IcebreakerPosition) object).getCoordinate());
    }

    @Override
    public int hashCode() {
        return this.getCoordinate().hashCode();
    }
}
