import javax.swing.JFrame;
public class Project2048
{
	public static void main(String[] args)
	{
		Board playBoard = new Board();
		playBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playBoard.setSize(386, 479);
		playBoard.setResizable(false);
		playBoard.setVisible(true);
	}
}
