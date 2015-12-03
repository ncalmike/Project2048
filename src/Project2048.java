import javax.swing.JFrame;
public class Project2048
{
	public static void main(String[] args)
	{
		Board playBoard = new Board();
		playBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playBoard.setSize(300, 300);
		playBoard.setVisible(true);
		
		BoardMngrTest test1 = new BoardMngrTest();
	}
}
