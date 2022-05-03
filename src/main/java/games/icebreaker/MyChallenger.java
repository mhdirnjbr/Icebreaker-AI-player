package games.icebreaker;

import java.util.HashSet;
import java.util.Set;

import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.model.IChallenger;

public class MyChallenger implements IChallenger {
	private IcebreakerRole role;
	private IcebreakerBoard board;

	public MyChallenger() {
		this.board = IcebreakerBoard.fromString(IcebreakerBoard.INITIAL_BOARD_STRING);
	}

	@Override
	public String teamName() {
		return "DUBOS - RANJBAR";
	}

	@Override
	public void setRole(String string) {
		this.role = IcebreakerRole.get(string);
	}

	@Override
	public void iPlay(String string) {
		this.board = board.play(new IcebreakerMove(string), this.role);
	}

	@Override
	public void otherPlay(String string) {
		this.board = board.play(new IcebreakerMove(string), this.role.invert());
	}

	@Override
	public String bestMove() {
		if (board.getIsBeginingMatch()) {
			board.setIsBeginingMatch();

			System.out.println("=============== Beginning-game ===============");

			AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard> algorithm = new AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard>(
					this.role, this.role.invert(),
					IcebreakerHeuristics.start, 2);

			IcebreakerMove move = (IcebreakerMove) algorithm.bestMove(board, this.role);

			return move.toString();
		} else if (board.getIsMiddleMatch()) {
			board.setIsMiddleMatch();

			System.out.println("---------------    Mid-game    ---------------");

			AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard> algorithm = new IcebreakerAlphaBeta(
					this.role, this.role.invert(),
					IcebreakerHeuristics.middle, 8);

			IcebreakerMove move = (IcebreakerMove) algorithm.bestMove(board, this.role);

			return move.toString();
		} else {
			System.out.println("_______________    End-game    _______________");

			AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard> algorithm = new IcebreakerAlphaBeta(
					this.role, this.role.invert(),
					IcebreakerHeuristics.end, 4);

			IcebreakerMove move = (IcebreakerMove) algorithm.bestMove(board, this.role);

			return move.toString();
		}
	}

	@Override
	public String victory() {
		return "Vous avez gagné !";
	}

	@Override
	public String defeat() {
		return "Vous avez perdu !";
	}

	@Override
	public String tie() {
		return "Vous êtes à ex aequo !";
	}

	@Override
	public String boardToString() {
		return this.board.toString();
	}

	@Override
	public void setBoardFromFile(String fileName) {
		this.board = new IcebreakerBoard(fileName);
	}

	@Override
	public Set<String> possibleMoves(String string) {
		HashSet<String> possibleMoves = new HashSet<String>();

		for (IcebreakerMove possibleMove : this.board.possibleMoves(IcebreakerRole.get(string)))
			possibleMoves.add(possibleMove.toString());

		return possibleMoves;
	}

	public static void main(String[] args) {
		MyChallenger challenger = new MyChallenger();
		challenger.setBoardFromFile("res/boards/board");

		// Copier un plateau existant
		IcebreakerBoard board = new IcebreakerBoard(challenger.board);
		System.out.print(board);

		// Obtenir les mouvements possibles
		System.out.println(board.possibleMoves(IcebreakerRole.RED));
		System.out.println(board.possibleMoves(IcebreakerRole.BLACK));

		// Obtenir le meilleur mouvement
		AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard> algorithm = new AlphaBeta<IcebreakerMove, IcebreakerRole, IcebreakerBoard>(
				IcebreakerRole.BLACK, IcebreakerRole.RED,
				IcebreakerHeuristics.naive, 7);

		IcebreakerMove move = (IcebreakerMove) algorithm.bestMove(board,
				IcebreakerRole.BLACK);

		System.out.println(move);

		// Executer le meilleur mouvement

		board = board.play(move, IcebreakerRole.BLACK);

		System.out.print(board);
	}
}
