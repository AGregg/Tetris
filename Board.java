import java.util.*;

//@author Andrew Gregg, Craig Murdoch

public class Board 
{
	public char [][] board;
	public ArrayList<Board> children;
	int score;
	
	public Board(char[][] b)
	{
		board = b;
		children = new ArrayList<Board>();
		score = score(b);
	}
	
	public Board()
	{
		board = new char[21][10];
		for (int i = 0; i < 20; i++)
		{
			for (int k = 0; k < 10; k++)
			{
				board[i][k] = 'e';
			}
		}
		for (int k = 0; k < 10; k++)
		{
			board[20][k] = 'b';
		}
		
		score = 0;
		children = new ArrayList<Board>();
	}
	
	public Board nextMove()
	{
		try
		{
			return Collections.max(children, new compScore());
		} catch (NoSuchElementException e)
		{
			return null;
		}
	}
	
	public void propagateScores()
	{
		score = 0;
		if (!children.isEmpty())
		{
			for (Board child : children)
			{
				child.propagateScores();
				if (child.score > score) score = child.score;
			}
		}
	}
	
	public void generateChildren(char block)
	{
		ArrayList<char[][]> positions = this.getPossiblePositions(block);
		for (int c = 0; c < positions.size(); c++)
		{
			if (positions.get(c) != null) children.add(new Board(positions.get(c)));
		}
		if (children.size() > 0) score = children.get(0).score;
		for (Board child : children)
		{
			if (child.score > score) score = child.score;
		}
	}
	
	public void addToQueue(char block)
	{
		if (children.isEmpty()) generateChildren(block);
		else
		{
			score = children.get(0).score;
			for (Board child : children)
			{
				child.addToQueue(block);
				if (child.score > score) score = child.score;
			}
		}
	}
	
	public void printChildren()
	{
		for (int c = 0; c < children.size(); c++)
		{
			children.get(c).printBoard(0);
		}
	}
	
	public ArrayList<char[][]> getPossiblePositions(char block)
	{
		ArrayList<char[][]> possibilities = new ArrayList<char[][]>();
		switch (block)
		{
			default:
				for (int c = 0; c < 9; c++)
				{
					possibilities.add(place(block, 1, c));
				}
				break;
			case 'o':
				for (int c = 0; c < 9; c++)
				{
					possibilities.add(place(block, 1, c));
				}
				break;
			case 'i':
				for (int c = 0; c < 17; c++){
					if (c < 10) possibilities.add(place('i', 1, c));
					else possibilities.add(place('i', 2, c-10));
				}
				break;
			case 's':
				for (int c = 0; c < 17; c++){
					if (c < 8) possibilities.add(place('s', 1, c));
					else possibilities.add(place('s', 2, c-8));
				}
				break;
			case 'z':
				for (int c = 0; c < 17; c++){
					if (c < 8) possibilities.add(place('z', 1, c));
					else possibilities.add(place('z', 2, c-8));
				}
				break;
			case 'l':
				for (int c = 0; c < 34; c++){
					if (c < 9) possibilities.add(place('l', 1, c));
					else if (c < 17) possibilities.add(place('l', 2, c-9));
					else if (c < 26) possibilities.add(place('l', 3, c-17));
					else possibilities.add(place('l', 4, c-26));
				}
				break;
			case 'j':
				for (int c = 0; c < 34; c++){
					if (c < 9) possibilities.add(place('j', 1, c));
					else if (c < 17) possibilities.add(place('j', 2, c-9));
					else if (c < 26) possibilities.add(place('j', 3, c-17));
					else possibilities.add(place('j', 4, c-26));
				}
				break;
			case 't':
				for (int c = 0; c < 34; c++){
					if (c < 8) possibilities.add(place('t', 1, c));
					else if (c < 17) possibilities.add(place('t', 2, c-8));
					else if (c < 25) possibilities.add(place('t', 3, c-17));
					else possibilities.add(place('t', 4, c-25));
				}
				break;
		}
		return possibilities;
	}
	
	public char[][] boardCopy()
	{
		char[][] copy = new char[21][10];
		for (int i = 0; i < 21; i++)
		{
			for (int k = 0; k < 10; k++)
			{
				copy[i][k] = board[i][k];
			}
		}
		return copy;
	}
	
	public char[][] place(char block, int orientation, int offset)
	{
		//1 is the orientation that corresponds to the letter shape
		//2-4 are the permutations starting with the first clockwise rotation
		//offset is the number of spaces the block is moved to the right from the left edge
		int [][] coordinates =  new int[4][2];
		
		switch (block)
		{
			case 'o':
				coordinates[0][0] = 0;
				coordinates[0][1] = 0 + offset;
				coordinates[1][0] = 0;
				coordinates[1][1] = 1 + offset;
				coordinates[2][0] = 1;
				coordinates[2][1] = 0 + offset;
				coordinates[3][0] = 1;
				coordinates[3][1] = 1 + offset;
				break;
			case 'i':
				if (orientation == 1)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 2;
					coordinates[2][1] = 0 + offset;
					coordinates[3][0] = 3;
					coordinates[3][1] = 0 + offset;
				}
				else
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 0;
					coordinates[2][1] = 2 + offset;
					coordinates[3][0] = 0;
					coordinates[3][1] = 3 + offset;
				}
				break;
			case 's':
				if (orientation == 1)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 1 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 2 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 0 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 1 + offset;
				}
				else
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 1 + offset;
				}
				break;
			case 'z':
				if (orientation == 1)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 2 + offset;
				}
				else
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 1 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 0 + offset;
				}
				break;
			case 'l':
				if (orientation == 1)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 2;
					coordinates[2][1] = 0 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 1 + offset;
				}
				else if (orientation == 2)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 0;
					coordinates[2][1] = 2 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 0 + offset;
				}
				else if (orientation == 3)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 1 + offset;
				}
				else
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 2 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 2 + offset;
				}
				break;
			case 'j':
				if (orientation == 1)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 1 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 2;
					coordinates[2][1] = 0 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 1 + offset;
				}
				else if (orientation == 2)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 2 + offset;
				}
				else if (orientation == 3)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 0 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 0 + offset;
				}
				else
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 0;
					coordinates[2][1] = 2 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 2 + offset;
				}
				break;
			case 't':  // the starting position is a capital 'T'
				if (orientation == 1)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 0;
					coordinates[1][1] = 1 + offset;
					coordinates[2][0] = 0;
					coordinates[2][1] = 2 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 1 + offset;
				}
				else if (orientation == 2)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 1 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 1 + offset;
				}
				else if (orientation == 3)
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 1 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 1;
					coordinates[3][1] = 2 + offset;
				}
				else
				{
					coordinates[0][0] = 0;
					coordinates[0][1] = 0 + offset;
					coordinates[1][0] = 1;
					coordinates[1][1] = 0 + offset;
					coordinates[2][0] = 1;
					coordinates[2][1] = 1 + offset;
					coordinates[3][0] = 2;
					coordinates[3][1] = 0 + offset;
				}
				break;				
			//defaults to an 'o' block in case something goes wrong
			default:
				coordinates[0][0] = 0;
				coordinates[0][1] = 0 + offset;
				coordinates[1][0] = 0;
				coordinates[1][1] = 1 + offset;
				coordinates[2][0] = 1;
				coordinates[2][1] = 0 + offset;
				coordinates[3][0] = 1;
				coordinates[3][1] = 1 + offset;
				break;
		}
		
		return clearLines(drop(boardCopy(), block, coordinates));
	}
	
	private char[][] drop(char[][] tempBoard, char block, int[][] coordinates)
	{
		int dropDistance = -1;
		boolean freeSpace = true;
		
		while (freeSpace)
		{
			for (int c = 0; c < 4; c++)
			{
				if (tempBoard[coordinates[c][0]+dropDistance+1][coordinates[c][1]] != 'e')
				{
					freeSpace = false;
					if (dropDistance == -1) return null;
					else
					{
						for (int k = 0; k < 4; k++)
						{
							tempBoard[coordinates[k][0]+dropDistance][coordinates[k][1]] = block;
						}
						return tempBoard;
					}
				}
			}
			dropDistance++;
		}
		
		return tempBoard;
	}
	
	public int score(char [][] b)
	{
		int points = 0;
		
		for (int i = 0; i < 20; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (board[i][j] != 'e')
				{
					points = points + i;
					if (i != 0)
					{
						if (board[i-1][j] != 'e') points++;
					}
					if (i != 19) 
					{
						if (board[i+1][j] != 'e') points++;
					} else points++;
					if (j != 0)
					{
						if (board[i][j-1] != 'e') points++;
					} else points++;
						
					if (j != 9)
					{
						if (board[i][j+1] != 'e') points++;
					} else points++;
				}
				else
				{
					points += 24;
				}
			}
		}
		
		return points;
	}
		
	public void printBoard(int move)
	{
		boolean start = false;
		System.out.println("Board " + move + ": " );
		for (int i = 0; i < 20; i++)
		{
			if (!start)
			{
				for (int j = 0; j < 10; j++)
				{
					if (board[i][j] != 'e') start = true;
				}	
			}
			if (start)
			{
				for (int j = 0; j < 10; j++)
				{
					if (board[i][j] == 'e') System.out.print(" ");
					else System.out.print(board[i][j]);
				}
				System.out.print("\n");
			}
		}
		System.out.print("0123456789\n\n");
	}
	
	public char[][] clearLines(char [][] b)
	{
		if (b == null) return null;
		boolean fullRow;
		for (int i = 0; i < 20; i++)
		{
			fullRow = true;
			for (int j = 0; j < 10; j++)
			{
				if (fullRow)
					{
						if (b[i][j] == 'e') fullRow = false;
					}
			}
			if (fullRow)
			{
				for (int c = i; c > 0; c--)
				{
					for (int j = 0; j < 10; j++)
					{
						b[c][j] = b[c-1][j];
					}
				}
				for (int j = 0; j < 10; j++)
				{
					b[0][j] = 'e';
				}
			}
		}
		return b;
	}
	
	public int countBlocks()
	{
		int count = 0;
		for (int i = 0; i < 20; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (board[i][j] != 'e') count++;
			}
		}
		return count;
	}
	
	public void printPossibilities(char block)
	{
		ArrayList<char[][]> p = getPossiblePositions(block);
		boolean start = false;
		
		for (int c = 0; c < p.size(); c++)
		{
			start = false;
			System.out.println("Board " + c + ":");
			{
				for (int i = 0; i < 20; i++)
				{
					if (!start)
					{
						for (int j = 0; j < 10; j++)
						{
							if (p.get(c)[i][j] != 'e') start = true;
						}
					}
					if (start)
					{
						for (int j = 0; j < 10; j++)
						{
							if (p.get(c)[i][j] == 'e') System.out.print(" ");
							else System.out.print(p.get(c)[i][j]);
						}
						System.out.print("\n");
					}
				}
				System.out.print("0123456789\n");
			}
		}
	}
}