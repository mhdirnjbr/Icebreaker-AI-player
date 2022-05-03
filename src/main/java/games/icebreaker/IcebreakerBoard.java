package games.icebreaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class IcebreakerBoard implements IBoard<IcebreakerMove, IcebreakerRole, IcebreakerBoard> {
	public static String INITIAL_BOARD_STRING = " Red Score : 0 --- Black Score : 0 \n\n A R o o o B\n B o o o o o o\n C o o o o o o o\n D o o o o o o o o\n E B o o o o o o o R\n F o o o o o o o o\n G o o o o o o o\n H o o o o o o\n I R o o o B";

	// Somme des scores limites pour chaque phase de jeu
	private static final int BEGINING_MATCH_LIMIT = 20; // Début de partie
	private static final int MIDDLE_MATCH_LIMIT = 40; // Milieu de partie

	private HashMap<IcebreakerPosition, IcebreakerCell> grid;
	private HashMap<IcebreakerRole, Integer> scores;

	private HashMap<IcebreakerRole, ArrayList<IcebreakerPosition>> shipPositions;

	private boolean isBeginingMatch;
	private boolean isMiddleMatch;

	public IcebreakerBoard() {
		this.grid = new HashMap<IcebreakerPosition, IcebreakerCell>();

		this.scores = new HashMap<IcebreakerRole, Integer>();

		this.shipPositions = new HashMap<IcebreakerRole, ArrayList<IcebreakerPosition>>();

		this.shipPositions.put(IcebreakerRole.RED,
				new ArrayList<IcebreakerPosition>());

		this.shipPositions.put(IcebreakerRole.BLACK,
				new ArrayList<IcebreakerPosition>());

		this.isBeginingMatch = true;
		this.isMiddleMatch = true;
	}

	public IcebreakerBoard(HashMap<IcebreakerPosition, IcebreakerCell> grid, HashMap<IcebreakerRole, Integer> scores,
			HashMap<IcebreakerRole, ArrayList<IcebreakerPosition>> shipPositions, boolean isBeginingMatch,
			boolean isMiddleMatch) {
		this.grid = grid;

		this.scores = scores;

		this.shipPositions = shipPositions;

		this.isBeginingMatch = isBeginingMatch;
		this.isMiddleMatch = isMiddleMatch;
	}

	public IcebreakerBoard(IcebreakerBoard board) {
		this.grid = new HashMap<IcebreakerPosition, IcebreakerCell>(board.grid);

		this.scores = new HashMap<IcebreakerRole, Integer>(board.scores);

		this.shipPositions = new HashMap<IcebreakerRole, ArrayList<IcebreakerPosition>>();

		this.shipPositions.put(IcebreakerRole.RED,
				new ArrayList<IcebreakerPosition>(board.shipPositions.get(IcebreakerRole.RED)));

		this.shipPositions.put(IcebreakerRole.BLACK,
				new ArrayList<IcebreakerPosition>(board.shipPositions.get(IcebreakerRole.BLACK)));

		this.isBeginingMatch = board.isBeginingMatch;
		this.isMiddleMatch = board.isMiddleMatch;
	}

	public IcebreakerBoard(String fileName) {
		IcebreakerBoard board = fromFileName(fileName);

		this.grid = board.grid;
		this.scores = board.scores;
		this.shipPositions = board.shipPositions;
		this.isBeginingMatch = board.isBeginingMatch;
		this.isMiddleMatch = board.isMiddleMatch;
	}

	public static IcebreakerBoard fromFileName(String fileName) {
		try {
			return fromString(Files.readString(Path.of(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static IcebreakerBoard fromString(String lines) {
		Scanner scanner = new Scanner(lines);

		// Scores

		String line = scanner.nextLine();

		String[] tokens = line.split(" ");

		HashMap<IcebreakerRole, Integer> scores = new HashMap<IcebreakerRole, Integer>();
		scores.put(IcebreakerRole.RED, Integer.parseInt(tokens[4]));
		scores.put(IcebreakerRole.BLACK, Integer.parseInt(tokens[9]));

		// Grid et ShipPositions

		HashMap<IcebreakerPosition, IcebreakerCell> grid = new HashMap<IcebreakerPosition, IcebreakerCell>();

		ArrayList<IcebreakerPosition> redShipPositions = new ArrayList<IcebreakerPosition>();
		ArrayList<IcebreakerPosition> blackShipPositions = new ArrayList<IcebreakerPosition>();

		HashMap<IcebreakerRole, ArrayList<IcebreakerPosition>> shipPositions = new HashMap<IcebreakerRole, ArrayList<IcebreakerPosition>>();

		shipPositions.put(IcebreakerRole.RED, redShipPositions);
		shipPositions.put(IcebreakerRole.BLACK, blackShipPositions);

		line = scanner.nextLine();

		// Lire le plateau
		for (int x = 1; x <= 9; x++) {
			line = scanner.nextLine();

			tokens = line.replaceAll(" +", " ").split(" ");

			for (int y = 1; y <= tokens.length - 2; y++) {
				IcebreakerPosition position = IcebreakerPositions.get(x, y);

				if (position != null) {
					IcebreakerCell cell = IcebreakerCell.get(tokens[y + 1]);

					if (cell != null) {
						grid.put(position, cell);

						// Ajouter la position du bateau
						if (cell == IcebreakerCell.RED_SHIP)
							redShipPositions.add(position);
						else if (cell == IcebreakerCell.BLACK_SHIP)
							blackShipPositions.add(position);
					}
				}
			}
		}

		scanner.close();

		return new IcebreakerBoard(grid, scores, shipPositions, true, true);
	}

	// METHODES : GETTERS

	public IcebreakerCell getCell(IcebreakerPosition position) {
		return this.grid.get(position);
	}

	public int getScore(IcebreakerRole role) {
		return this.scores.get(role);
	}

	public ArrayList<IcebreakerPosition> getShipPositions(IcebreakerRole role) {
		return this.shipPositions.get(role);
	}

	public boolean getIsBeginingMatch() {
		return this.isBeginingMatch;
	}

	public boolean getIsMiddleMatch() {
		return this.isMiddleMatch;
	}

	// METHODES : SETTERS

	public void setScore(IcebreakerRole role, int score) {
		this.scores.put(role, score);
	}

	public void setIsBeginingMatch() {
		this.isBeginingMatch = this.scores.get(IcebreakerRole.RED)
				+ this.scores.get(IcebreakerRole.BLACK) < BEGINING_MATCH_LIMIT;
	}

	public void setIsMiddleMatch() {
		this.isMiddleMatch = this.scores.get(IcebreakerRole.RED)
				+ this.scores.get(IcebreakerRole.BLACK) < MIDDLE_MATCH_LIMIT;
	}

	public void addCell(IcebreakerPosition position, IcebreakerCell cell) {
		this.grid.put(position, cell);
	}

	// METHODES

	// Algorithme de parcours en largeur
	public int getIcebergDistance(IcebreakerPosition root) {
		LinkedList<IcebreakerNode> queue = new LinkedList<IcebreakerNode>();
		queue.add(new IcebreakerNode(root, null, 0));

		LinkedList<IcebreakerNode> visitedNodes = new LinkedList<IcebreakerNode>();

		while (!queue.isEmpty()) {
			IcebreakerNode node = queue.poll();
			visitedNodes.add(node);

			IcebreakerPosition position = node.getPosition();

			if (this.grid.get(position) == IcebreakerCell.ICEBERG)
				return node.getDistance();

			for (IcebreakerPosition adjacent : position.getAdjacents()) {
				if (this.grid.get(adjacent) == IcebreakerCell.RED_SHIP
						|| this.grid.get(adjacent) == IcebreakerCell.BLACK_SHIP)
					continue;

				IcebreakerNode adjacentNode = new IcebreakerNode(adjacent, node, node.getDistance() + 1);
				visitedNodes.add(adjacentNode);

				queue.add(adjacentNode);
			}
		}

		return Integer.MAX_VALUE;
	}

	public int getIcebergSize(IcebreakerPosition root) {
		int size = 0;

		LinkedList<IcebreakerPosition> queue = new LinkedList<IcebreakerPosition>();
		queue.add(root);

		while (!queue.isEmpty()) {
			IcebreakerPosition position = queue.poll();

			if (this.grid.get(position) == IcebreakerCell.ICEBERG) {
				queue.addAll(position.getAdjacents());

				size++;
			}
		}

		return size;
	}

	public void addShipPosition(IcebreakerRole role, IcebreakerPosition position) {
		ArrayList<IcebreakerPosition> positions = this.shipPositions.get(role);

		positions.add(position);

		this.shipPositions.put(role, positions);
	}

	private Score.Status getScoreStatus(IcebreakerRole role) {
		int scoreOne = this.scores.get(role);
		int scoreTwo = this.scores.get(role.invert());

		if (scoreOne > scoreTwo)
			return Score.Status.WIN;

		else if (scoreOne < scoreTwo)
			return Score.Status.LOOSE;

		return Score.Status.TIE;
	}

	private Score<IcebreakerRole> getScore_(IcebreakerRole role) {
		Score.Status status = getScoreStatus(role);

		int score = this.scores.get(role);

		return new Score<IcebreakerRole>(role, status, score);
	}

	// METHODES : OVERRIDE

	@Override
	public ArrayList<IcebreakerMove> possibleMoves(IcebreakerRole role) {
		ArrayList<IcebreakerMove> possibleMoves = new ArrayList<IcebreakerMove>();

		// Pour chaque position de chaque bateau...
		for (IcebreakerPosition shipPosition : this.shipPositions.get(role)) {
			// Obtenir les positions adjacentes
			ArrayList<IcebreakerPosition> adjacents = new ArrayList<IcebreakerPosition>(shipPosition.getAdjacents());

			// Filtrer les positions adjacentes
			for (IcebreakerPosition adjacent : new ArrayList<IcebreakerPosition>(adjacents))
				if (grid.get(adjacent) == IcebreakerCell.RED_SHIP || grid.get(adjacent) == IcebreakerCell.BLACK_SHIP)
					adjacents.remove(adjacent);

			// Obtenir les distances entre les positions adjacentes et les icebergs
			HashMap<IcebreakerPosition, Integer> distances = new HashMap<IcebreakerPosition, Integer>();

			for (IcebreakerPosition adjacent : adjacents) {
				int adjacentDistance = getIcebergDistance(adjacent);

				if (!distances.isEmpty()) {
					int distance = distances.get(distances.keySet().toArray()[0]);

					if (adjacentDistance < distance) {
						distances.clear();
						distances.put(adjacent, adjacentDistance);
					} else if (adjacentDistance == distance) {
						distances.put(adjacent, adjacentDistance);
					}
				} else {
					distances.put(adjacent, adjacentDistance);
				}
			}

			for (IcebreakerPosition a : distances.keySet())
				possibleMoves.add(new IcebreakerMove(shipPosition, a));
		}

		return possibleMoves;

	}

	@Override
	public IcebreakerBoard play(IcebreakerMove move, IcebreakerRole role) {
		IcebreakerBoard board = new IcebreakerBoard(this);

		// Supprimer la cellule de départ
		IcebreakerPosition start = move.getStart();

		board.grid.remove(start);

		// Ajouter/Remplacer la cellule d'arrivée (avec incrémentation du score)
		IcebreakerPosition end = move.getEnd();

		if (board.grid.get(end) == IcebreakerCell.ICEBERG)
			board.scores.put(role, board.scores.get(role) + 1);

		board.grid.put(end, role.getCell());

		// Modifier la position du bateau
		ArrayList<IcebreakerPosition> shipPositions = board.shipPositions.get(role);

		shipPositions.remove(start);
		shipPositions.add(end);

		board.shipPositions.put(role, shipPositions);

		return board;
	}

	@Override
	// Méthode non-utilisée: utilisée implicitement dans la méthode 'possibleMove'
	public boolean isValidMove(IcebreakerMove move, IcebreakerRole playerRole) {
		return false;
	}

	@Override
	public boolean isGameOver() {
		return scores.get(IcebreakerRole.RED) == 28 || scores.get(IcebreakerRole.BLACK) == 28;
	}

	@Override
	public ArrayList<Score<IcebreakerRole>> getScores() {
		ArrayList<Score<IcebreakerRole>> scores = new ArrayList<Score<IcebreakerRole>>();

		scores.add(getScore_(IcebreakerRole.RED));
		scores.add(getScore_(IcebreakerRole.BLACK));

		return scores;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		char letter = 'A';
		int cpt = 10;

		res.append(" Red Score : ").append(this.getScore(IcebreakerRole.RED));
		res.append(" ---");
		res.append(" Black Score : ").append(this.getScore(IcebreakerRole.BLACK));

		res.append("\n\n");

		for (int i = 1; i < 10; i++) {
			res.append(" " + letter);

			for (int t = 0; t < cpt; t++) {
				res.append(" ");
			}
			if (i < 5) {
				cpt -= 2;
			} else {
				cpt += 2;
			}
			for (int j = 1; j < 10; j++) {
				IcebreakerPosition position = IcebreakerPositions.get(i, j);

				if (position != null) {
					IcebreakerCell cell = this.getCell(position);

					if (cell == null)
						res.append("•");
					else
						res.append(cell);

					res.append("   ");
				}
			}
			res.append("  ");
			res.append("\n");

			letter++;
		}

		return res.toString();
	}
}
