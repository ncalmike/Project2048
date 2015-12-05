import javax.swing.JFrame;
public class Project2048
{
	public static void main(String[] args)
	{
		Board playBoard = new Board();
		playBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playBoard.setSize(586, 500);
		playBoard.setVisible(true);
	}
}
