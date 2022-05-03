package games.icebreaker;

import iialib.games.algs.IHeuristic;

public class IcebreakerHeuristics {
    // Heuristic naive : ne prend en compte que les scores des joueurs
    public static IHeuristic<IcebreakerBoard, IcebreakerRole> naive = new IHeuristic<IcebreakerBoard, IcebreakerRole>() {
        @Override
        public int eval(IcebreakerBoard board, IcebreakerRole role) {
            if (board.getScore(role) == 28)
                return Integer.MAX_VALUE;

            if (board.getScore(role.invert()) == 28)
                return Integer.MIN_VALUE;

            return board.getScore(role) - board.getScore(role.invert());
        }
    };

    // Heuristic start (de début de partie) : favorise le fait d'avoir uniquement
    // deux icebergs autour de ses bateaux
    public static IHeuristic<IcebreakerBoard, IcebreakerRole> start = new IHeuristic<IcebreakerBoard, IcebreakerRole>() {
        @Override
        public int eval(IcebreakerBoard board, IcebreakerRole role) {
            if (board.getScore(role) == 28)
                return Integer.MAX_VALUE;

            if (board.getScore(role.invert()) == 28)
                return Integer.MIN_VALUE;

            int heuristic = board.getScore(role) - board.getScore(role.invert());

            for (IcebreakerPosition shipPosition : board.getShipPositions(role)) {
                int icebergs = 0;

                for (IcebreakerPosition adjacent : shipPosition.getAdjacents())
                    if (board.getCell(adjacent) == IcebreakerCell.ICEBERG)
                        icebergs++;

                if (icebergs == 2)
                    heuristic++;
            }

            return heuristic;
        }
    };

    // Heuristic middle (de milieu de partie) : favorise le fait d'avoir un maximum
    // d'iceberg autour de ses bateau et d'avoir un miminium de iceberg autour de
    // bateaux ennemis
    public static IHeuristic<IcebreakerBoard, IcebreakerRole> middle = new IHeuristic<IcebreakerBoard, IcebreakerRole>() {
        @Override
        public int eval(IcebreakerBoard board, IcebreakerRole role) {
            if (board.getScore(role) == 28)
                return Integer.MAX_VALUE;

            if (board.getScore(role.invert()) == 28)
                return Integer.MIN_VALUE;

            int score = board.getScore(role) - board.getScore(role.invert());

            int heuristic = 8 * score;

            for (IcebreakerPosition shipPosition : board.getShipPositions(role))
                for (IcebreakerPosition adjacent : shipPosition.getAdjacents())
                    if (board.getCell(adjacent) == IcebreakerCell.ICEBERG)
                        heuristic += 4;

            for (IcebreakerPosition shipPosition : board.getShipPositions(role.invert()))
                for (IcebreakerPosition adjacent : shipPosition.getAdjacents())
                    if (board.getCell(adjacent) != IcebreakerCell.ICEBERG)
                        heuristic++;

            return heuristic;
        }
    };

    // Heuristic end (de fin de partie) : favorise le fait d'avoir un miminium de
    // iceberg autour de bateaux ennemis et de ne pas être à côté de ses derniers
    public static IHeuristic<IcebreakerBoard, IcebreakerRole> end = new IHeuristic<IcebreakerBoard, IcebreakerRole>() {
        @Override
        public int eval(IcebreakerBoard board, IcebreakerRole role) {
            if (board.getScore(role) == 28)
                return Integer.MAX_VALUE;

            if (board.getScore(role.invert()) == 28)
                return Integer.MIN_VALUE;

            int score = board.getScore(role) - board.getScore(role.invert());

            int heuristic = 8 * score;

            for (IcebreakerPosition shipPosition : board.getShipPositions(role.invert()))
                for (IcebreakerPosition adjacent : shipPosition.getAdjacents()) {
                    if (board.getCell(adjacent) != IcebreakerCell.ICEBERG)
                        heuristic++;

                    if (board.getCell(adjacent) != role.getCell())
                        heuristic += 4;
                }

            return heuristic;
        }
    };
}
