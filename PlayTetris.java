import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

//@ author: Andrew Gregg, Craig Murdoch

public class PlayTetris {

	public static void main(String[] args) throws IOException 
	{
		Board mainBoard = new Board();
		Board next = new Board();
		Queue<Character> q = new LinkedList<Character>();
		char cha;
		
		//uncomment all lines of code using Scanner sc to view the game played move by move.
		Scanner sc = new Scanner(System.in);
		
		//the program looks an additional move ahead for each random character added to the queue
		cha = randomChar();
		mainBoard.addToQueue(cha);
		q.add(cha);
		cha = randomChar();
		mainBoard.addToQueue(cha);
		q.add(cha);
				//cha = randomChar();
		//mainBoard.addToQueue(cha);
		//q.add(cha);
		
		boolean gameOver = false;
		int c = 0;
		
		while (!gameOver)
		{
			c++;
			mainBoard.printBoard(c);
			System.out.println("falling block: " + q.poll() + "  next block: " + q.peek());
			next = mainBoard.nextMove();
			if (next != null) 
			{
				mainBoard = next;
				cha = randomChar();
				mainBoard.addToQueue(cha);
				q.add(cha);
			}
			else gameOver = true;
			//sc.nextLine();
		}
		
		int linesCleared = (c + mainBoard.countBlocks()) / 10;
		System.out.print("GAME OVER! \n" + linesCleared +" lines cleared");
		//sc.close();
	}
	
	public static char randomChar()
	{
		Random r = new Random();
		int num = r.nextInt(7);
		switch(num)
		{
			case 0:
				return 'o';
			case 1:
				return 'i';
			case 2:
				return 's';
			case 3:
				return 'z';
			case 4:
				return 'l';
			case 5:
				return 'j';
			case 6:
				return 't';
		}
		return randomChar();
	}
}