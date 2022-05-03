package iialib.games.algs.algorithms;

import java.util.ArrayList;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

public class AlphaBeta<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>>
        implements GameAlgorithm<Move, Role, Board> {
    private final static int DEPTH_MAX_DEFAUT = 4;

    private Role playerAlphaRole;
    private Role playerBetaRole;
    private IHeuristic<Board, Role> h;

    private int depthMax = DEPTH_MAX_DEFAUT;

    private int nodeCount = 0;
    private int leafCount = 0;

    public AlphaBeta(Role playerAlphaRole, Role playerBetaRole, IHeuristic<Board, Role> h) {
        this.playerAlphaRole = playerAlphaRole;
        this.playerBetaRole = playerBetaRole;
        this.h = h;
    }

    public AlphaBeta(Role playerAlphaRole, Role playerBetaRole, IHeuristic<Board, Role> h, int depthMax) {
        this(playerAlphaRole, playerBetaRole, h);
        this.depthMax = depthMax;
    }

    @Override
    public Move bestMove(Board board, Role playerRole) {
        return bestMove(board);
    }

    public Move bestMove(Board board) {
        nodeCount = 1;
        leafCount = 0;

        System.out.println("[AlphaBeta]");

        ArrayList<Move> possibleMoves = board.possibleMoves(playerAlphaRole);

        Move bestMove = possibleMoves.get(0);

        int alpha = IHeuristic.MIN_VALUE;
        int beta = IHeuristic.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        if (depthMax > 0) {
            for (Move nextMove : possibleMoves) {
                if (System.currentTimeMillis() - startTime > 12_000)
                    break;

                int nextAlpha = betaAlpha(board.play(nextMove, playerAlphaRole), alpha, beta, depthMax - 1);

                if (nextAlpha > alpha) {
                    alpha = nextAlpha;
                    bestMove = nextMove;
                }
            }
        } else {
            leafCount++;
        }

        System.out.println("    * " + nodeCount + " nodes explored");
        System.out.println("    * " + leafCount + " leaves explored");
        System.out.println("Best value is: " + alpha);

        return bestMove;
    }

    public ArrayList<Move> bestMoves(Board board) {
        nodeCount = 1;
        leafCount = 0;

        System.out.println("[AlphaBeta]");

        ArrayList<Move> possibleMoves = board.possibleMoves(playerAlphaRole);

        ArrayList<Move> bestMoves = new ArrayList<Move>();
        bestMoves.add(possibleMoves.get(0));

        int alpha = IHeuristic.MIN_VALUE;
        int beta = IHeuristic.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        if (depthMax > 0) {
            for (Move nextMove : possibleMoves) {
                if (System.currentTimeMillis() - startTime > 12_000)
                    break;

                int nextAlpha = betaAlpha(board.play(nextMove, playerAlphaRole), alpha, beta, depthMax - 1);

                if (nextAlpha > alpha) {
                    alpha = nextAlpha;

                    bestMoves.clear();
                    bestMoves.add(nextMove);
                } else if (nextAlpha == alpha) {
                    bestMoves.add(nextMove);
                }
            }
        } else {
            leafCount++;
        }

        System.out.println("    * " + bestMoves.size() + " best moves");
        System.out.println("    * " + nodeCount + " nodes explored");
        System.out.println("    * " + leafCount + " leaves explored");
        System.out.println("Best value is: " + alpha);

        return bestMoves;
    }

    public int alphaBeta(Board board, int alpha, int beta, int d) {
        nodeCount++;

        if (d == 0 || board.isGameOver()) {
            leafCount++;

            return h.eval(board, playerAlphaRole);
        }

        for (Move nextMove : board.possibleMoves(playerAlphaRole)) {
            alpha = Math.max(alpha, betaAlpha(board.play(nextMove, playerAlphaRole),
                    alpha, beta, d - 1));

            if (alpha >= beta)
                return beta;
        }

        return alpha;
    }

    public int betaAlpha(Board board, int alpha, int beta, int d) {
        nodeCount++;

        if (d == 0 || board.isGameOver()) {
            leafCount++;

            return h.eval(board, playerAlphaRole);
        }

        for (Move nextMove : board.possibleMoves(playerBetaRole)) {
            beta = Math.min(beta, alphaBeta(board.play(nextMove, playerBetaRole), alpha, beta, d - 1));

            if (alpha >= beta)
                return alpha;
        }

        return beta;
    }
}
