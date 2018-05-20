package ship.battle.impl;

import java.util.Scanner;
import java.util.Vector;

import ship.battle.BattleField;
import ship.battle.Board;
import ship.battle.Coord;
import ship.battle.Direction;
import ship.battle.Move;
import ship.battle.Player;
import ship.battle.Ship;
import ship.battle.ShipType;
import ship.battle.State;


public class PromptPlayer implements Player
{

	private final Scanner input;
	private final String name;
	
	public PromptPlayer()
	{
		input = new Scanner(System.in);
		
		System.out.print("Name: ");
		name = input.nextLine();
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public void place(Board board, Ship ship)
	{
		printBoard(board);
		Direction[] dirs = Direction.values();
		String rowPrompt = String.format("Row[0-%d]: ", board.getRows() - 1);
		String colPrompt = String.format("Column[0-%d]: ", board.getColumns() - 1);
		String dirPrompt = String.format("[0=%s, 1=%s, 2=%s, 3=%s]\nDirection: ",
				dirs[0], dirs[1], dirs[2], dirs[3]);
			
		System.out.format("Placing %s(%d):\n", ship.type, ship.type.length);
		do {
			System.out.print(rowPrompt);
			int row = input.nextInt();
			System.out.print(colPrompt);
			int column = input.nextInt();
			System.out.print(dirPrompt);
			int dir = input.nextInt();
			
			if (row < 0 || row >= board.getRows()) {
				continue;
			}
			if (column < 0 || column >= board.getColumns()) {
				continue;
			}
			if (dir < 0 || dir >= dirs.length) {
				continue;
			}
			ship.place(column, row, dirs[dir]);
			
		} while (!board.addShip(ship));
	}
	
	private void printBoard(Board board)
	{
		int rows = board.getRows();
		int cols = board.getColumns();
		int cellWidth = (int)Math.log10(cols - 1) + 1;
		int cellHeight = (int)Math.log10(rows - 1) + 1;
		int span = (cellWidth + 1) * cols;
		String rowFormat = String.format("%%%ds ", cellWidth);
		String colFormat = String.format("%%%ds ", cellHeight);
		
		// Column headers
		System.out.format(rowFormat, "");
		System.out.print("  ");
		for (int x = 0; x < cols; x++) {
			System.out.format(colFormat, x);
		}
		System.out.println();
		System.out.format(rowFormat, "");
		System.out.print("+-");
		for (int i = 0; i < span; i++) {
			System.out.print("-");
		}
		System.out.println("+");
		// Board
		for (int y = 0; y < rows; y++) {
			// A single row
			System.out.format(rowFormat, y);
			System.out.print("| ");
			for (int x = 0; x < cols; x++) {
				Ship s = board.getShip(x, y);
				if (s != null) {
					System.out.format(colFormat, s.type.length);
				}
				else {
					System.out.format(colFormat, ".");
				}
			}
			System.out.println("|");
		}
		System.out.format(rowFormat, "");
		System.out.print("+-");
		for (int i = 0; i < span; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}
	
	@Override
	public Coord bomb(BattleField opponent, Vector<Move> moves, Vector<Ship> sunkShips)
	{	
		printBattleField(opponent);
		String rowPrompt = String.format("Row[0-%d]: ", opponent.getRows() - 1);
		String colPrompt = String.format("Column[0-%d]: ", opponent.getColumns() - 1);
		
		int x, y;
		do {
			System.out.print(rowPrompt);
			y = input.nextInt();
			System.out.print(colPrompt);
			x = input.nextInt();
			
			if (y < 0 || y >= opponent.getRows()) {
				continue;
			}
			if (x < 0 || x >= opponent.getColumns()) {
				continue;
			}
			
		} while (opponent.getState(x, y) != State.Empty);
		
		return new Coord(x, y);
	}
	
	private void printBattleField(BattleField field)
	{
		int rows = field.getRows();
		int cols = field.getColumns();
		int cellWidth = (int)Math.log10(cols - 1) + 1;
		int cellHeight = (int)Math.log10(rows - 1) + 1;
		int span = (cellWidth + 1) * cols;
		String rowFormat = String.format("%%%ds ", cellWidth);
		String colFormat = String.format("%%%ds ", cellHeight);
		
		// Column headers
		System.out.format(rowFormat, "");
		System.out.print("  ");
		for (int x = 0; x < cols; x++) {
			System.out.format(colFormat, x);
		}
		System.out.println();
		// Line
		System.out.format(rowFormat, "");
		System.out.print("+-");
		for (int i = 0; i < span; i++) {
			System.out.print("-");
		}
		System.out.println("+");
		// Board
		for (int y = 0; y < rows; y++) {
			// A single row
			System.out.format(rowFormat, y);
			System.out.print("| ");
			for (int x = 0; x < cols; x++) {
				System.out.format(colFormat, field.getState(x, y).getToken());
			}
			System.out.println("|");
		}
		System.out.format(rowFormat, "");
		System.out.print("+-");
		for (int i = 0; i < span; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}

	@Override
	public String getHitMessage(Coord coord, ShipType type)
	{
		return String.format("I hit your %s!", type);
	}

	@Override
	public String getMissMessage(Coord coord)
	{
		return null;
	}

	@Override
	public String getSunkMessage(ShipType type)
	{
		return String.format("I sunk your %s!", type);
	}

}
