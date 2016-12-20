import java.util.Comparator;

public class compScore implements Comparator<Board> {
	
	public int compare(Board a, Board b) {
		return a.score - b.score;
	}
}