package games.icebreaker;

import java.util.ArrayList;

import iialib.games.algs.IHeuristic;
import iialib.games.algs.algorithms.AlphaBeta;

public class IcebreakerAlphaBeta
        extends AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard> {

    public IcebreakerAlphaBeta(IcebreakerRole playerAlphaRole, IcebreakerRole playerBetaRole,
            IHeuristic<IcebreakerBoard, IcebreakerRole> h) {
        super(playerAlphaRole, playerBetaRole, h);
    }

    public IcebreakerAlphaBeta(IcebreakerRole playerAlphaRole, IcebreakerRole playerBetaRole,
            IHeuristic<IcebreakerBoard, IcebreakerRole> h, int depthMax) {
        super(playerAlphaRole, playerBetaRole, h, depthMax);
    }

    @Override
    public IcebreakerMove bestMove(IcebreakerBoard board) {
        ArrayList<IcebreakerMove> bestMoves = this.bestMoves(board);

        IcebreakerMove bestMove = bestMoves.get(0);
        int bestDistance = board.getIcebergDistance(bestMove.getEnd());

        // Choisir le mouvement permettant de se rapprocher le plus d'un iceberg
        for (IcebreakerMove move : bestMoves) {
            int distance = board.getIcebergDistance(move.getEnd());

            if (distance < bestDistance) {
                bestMove = move;
                bestDistance = distance;
            }
        }

        return bestMove;
    }
}
