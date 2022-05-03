package games.icebreaker;

import java.util.Collection;
import java.util.HashMap;

public class IcebreakerPositions {
    private static final HashMap<IcebreakerCoordinate, IcebreakerPosition> positions = create();

    private static final HashMap<IcebreakerCoordinate, IcebreakerPosition> create() {
        HashMap<IcebreakerCoordinate, IcebreakerPosition> positions = new HashMap<IcebreakerCoordinate, IcebreakerPosition>();

        // Créer les positions
        for (int x = 1; x <= 9; x++)
            for (int y = 1; y <= 9; y++)
                try {
                    positions.put(new IcebreakerCoordinate(x, y), new IcebreakerPosition(x, y));
                } catch (Exception exception) {
                }

        // Ajouter les positions adjacentes à toutes les positions
        for (IcebreakerPosition position : positions.values()) {
            for (IcebreakerCoordinate adjacentCoordinate : position.getCoordinate().getAdjacents()) {
                IcebreakerPosition adjacentPosition = positions.get(adjacentCoordinate);

                if (adjacentPosition != null)
                    position.addAdjacent(adjacentPosition);
            }
        }

        return positions;
    }

    public static Collection<IcebreakerPosition> get() {
        return positions.values();
    }

    public static IcebreakerPosition get(IcebreakerCoordinate coordinate) {
        return positions.get(coordinate);
    }

    public static IcebreakerPosition get(int x, int y) {
        return positions.get(new IcebreakerCoordinate(x, y));
    }

    public static IcebreakerPosition get(String string) {
        return positions.get(new IcebreakerCoordinate(string));
    }
}
