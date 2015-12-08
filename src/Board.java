import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

public class Board extends JFrame
{
	/**Board's PlayBoard object.
	 */
	private final PlayBoard pBoard;
	
	/**Board's BoardBorder object.
	 */
	//private final BoardBorder bBorder;
	private final NewBoardBorder newBBorder;
	
	/**Board's BoardManager object.
	 */
	private final BoardManager bManager;
	
	
	/**Stores the tiles in a 2D array of tile2048.<br>
	The first index represents the row position where the tile is to be displayed.<br>
	The second index represents the column position where the tile is to be displayed.
	 */
	private tile2048[][] theTiles;
	
	List<tile2048> theList = new LinkedList<>();
	
	
	/**Constructor for Board.<br>
	
	Initializes the Board with a BoardManager, PlayBoard, and BoardBorder.<br>
	
	Sets the dimensions of theTiles based on the constants NUM_ROWS and NUM_COLUMNS
	provided by the BoardManager.<br>
	
	Assigns the elements of PlayBoard and BoardBorder to the zones of
	the frame that they should be in.
	
	 */
	public Board()
	{
		
		bManager = new BoardManager(); //Initializes the Board's Board Manager object
		theTiles = new tile2048[bManager.getNUM_ROWS()][bManager.getNUM_COLUMNS()]; //Sets the dimensions of the tile2048[][]
		pBoard = new PlayBoard(); //Initializes the Board's Play Board object
		newBBorder = new NewBoardBorder();
		//bBorder = new BoardBorder(); //Initializes the Board's Board Border object
		
		
		EventManager listener = new EventManager();
		addKeyListener(listener);
		setFocusable(true);
		
		
		//Adds the PlayBoard to the center of the frame
		add(pBoard.getBoard(), BorderLayout.CENTER);
		
		
		//Adds the NewBoardBorder components around the border of the frame.
		add(newBBorder.getNorthPanel(), BorderLayout.NORTH);
		add(newBBorder.getSouthPanel(), BorderLayout.SOUTH);
		//newBBorder.add(newBBorder.getSouthPanel(), BorderLayout.SOUTH);
		
		//Adds the BoardBorder components around the border of the frame
		//add(bBorder.getSwipeRight(), BorderLayout.EAST);
		//add(bBorder.getSwipeLeft(), BorderLayout.WEST);
		//add(bBorder.getNorthPanel(), BorderLayout.NORTH);
		//add(bBorder.getSouthPanel(), BorderLayout.SOUTH);
		
	}//Board()
	
	
	/**Converts a Delimited String into a 2D String Array<br>
	
	Takes the delimited string of integer values to be represented
	as tiles. Uses the delimiters ";" and "," to convert the delimited
	String passed from the BoardManager to a 2D array of Strings.
	@param delimitedVals
	@return 2D array of Strings<br>
	The first index represents the rows of the tiles,
	the second index represents the columns of the tiles.
	 */
	public String[][] getValsAsArray(String delimitedVals)
	{
		//2D array of Strings to be returned.
		String[][] returnArray = new String[bManager.getNUM_ROWS()][bManager.getNUM_COLUMNS()];
		
		//Stores the first tokenization of the String of delimited values.
		//This represents each row of tile values.
		String[] firstSplit = delimitedVals.split(";");
		
		for (int i = 0; i < bManager.getNUM_ROWS(); i++)
		{//Tokenizes each "row" of tile values into individual column values.
			String[] rowArray = firstSplit[i].split(",");
			for (int j = 0; j < bManager.getNUM_COLUMNS(); j++)
			{//Assigns each individual column value to the appropriate position in the returnArray
				returnArray[i][j] = rowArray[j];
			}
		}
		
		return returnArray;
	}//getValsAsArray(String)

	/**
	*/
	private void updateBoardStatus(String tileValues)
	{
		newBBorder.setScoreLabel(bManager.getScore());
		
		pBoard.getBoard().removeAll();
		
		pBoard.getBoard().setLayout(new GridLayout(bManager.getNUM_ROWS(),bManager.getNUM_COLUMNS()));

		theList.removeAll(theList);
		
		String[][] newValues = getValsAsArray(tileValues);
		for (int i = 0; i < bManager.getNUM_ROWS(); i++)
		{
			for (int j = 0; j < bManager.getNUM_COLUMNS(); j++)
			{
				theList.add(new tile2048(0,0,Integer.parseInt(newValues[i][j])));
			}
		}
		for (int i = 0; i < theList.size(); i++)
			pBoard.getBoard().add(theList.get(i));
		pBoard.getBoard().validate();
		pBoard.getBoard().repaint();
		
		if (bManager.isGameOver())
		{
			newBBorder.setMoveInstructions(newBBorder.getGameOver());
		}
	}
	
	/**Accessor for theTiles.<br>
	
	theTiles is the 2D array of tile2048 which is used to display the board.
	
	@return Returns the 2D array of tile2048 used to display the board.
	 */
	public tile2048[][] getTiles()
	{
		return theTiles;
	}//getTiles()


	/**Accessor for a single tile in theTiles.<br>
	
	Takes the parameters row and col to access the element of theTiles at
	theTiles[row][col]. Ideally used to modify a single tile at a time,
	typically in a loop.
	
	@param row
	@param col
	@return The tile at position (row,col) in theTiles.
	 */
	public tile2048 getTile(int row, int col)
	{
		return theTiles[row][col];
	}//getTile(int,int)


	/**Mutator for an individual tile in theTiles<br>
	
	Changes the value to be displayed by a single tile in theTiles.
	Must be repainted to reflect this change graphically.
	
	@param row
	@param col
	@param newValue
	*/
	public void setTile(int row, int col, int newValue)
	{
		System.out.println("Row: " + row + " Col: " + col + " Val: " + newValue);
		theTiles[row][col].setValue(newValue);
	}//setTile(int,int,int)


	/**Contains the graphical component which represents the 4x4 grid of tiles.<br>
	
	@author Lucifer
	*/
	private class PlayBoard
	{

		/**The panel that gets added to the Board's JFrame which contains the 4x4 grid of tiles.
		*/
		private final JPanel theBoard; 
		
		
		/**Constructor for PlayBoard<br>
		 * 
		Sets the characteristics for theBoard, initializes all 16 of the tile2048
		while populating theTiles with these tiles, and populates theBoard with theTiles.<br>
		
		theBoard is initialized to a GridLayout of dimensions bManager.getNUM_ROWS() and bManager.getNUM_COLUMNS(), which are
		constants determined by and retrieved from the BoardManager.<br>
		
		The elements of theTiles are each initialized with a tile2048, which gets its
		value from the initialization of the BoardManager. 
		 */
		public PlayBoard()
		{
			//Sets the characteristics for the Tiles and the Board
			theBoard = new JPanel();
			theBoard.setLayout(new GridLayout(bManager.getNUM_ROWS(),bManager.getNUM_COLUMNS()));
						
			//Locally stores the initial values of each tile as a string,
			//as provided by the BoardManager.
			String[][] tileValues = getValsAsArray(bManager.getTilesValues());
		
			
			//Populates theTiles with the initial values for the PlayBoard.
			for (int i = 0; i < bManager.getNUM_ROWS(); i++)
			{//Loops through rows
				for (int j = 0; j < bManager.getNUM_COLUMNS(); j++)
				{//Loops through columns
					theTiles[i][j] = new tile2048(0,0,Integer.parseInt(tileValues[i][j]));
				}
			}
		
			//Adds the elements of theTiles to theBoard
			for (JPanel[] panelRows : theTiles)
			{//Enhanced loop through the "rows" of theTiles
				for (JPanel panelColumns : panelRows)
				{//Enhanced loop through the "columns" of theTiles
					theBoard.add(panelColumns); //Adds each tile2048 to PlayBoard's theBoard, from left to right, top to bottom.
				}
			}
		
		}//PlayBoard()

		/**Accessor for theBoard.<br>
		
		theBoard is the graphical component which contains the grid of tiles
		to be displayed to the user.
		@return Returns the JPanel, theBoard.
		*/
		public JPanel getBoard()
		{
			return theBoard;
		}//getBoard()
		
	}//PlayBoard
	
	private class EventManager implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			System.out.println(e);
			
			//New Game
			if (e.getKeyCode() == 78 && e.getModifiers() == 2)
			{
				updateBoardStatus(bManager.startNewGame());
				newBBorder.setScoreLabel(bManager.getScore());
				newBBorder.setMoveInstructions(newBBorder.getMoveInstruction());
			}
			
			//Swipe Left
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				updateBoardStatus(bManager.alignWest(true));
			}
			
			//Swipe Up
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				updateBoardStatus(bManager.alignNorth(true));
			}
			
			//Swipe Right
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				updateBoardStatus(bManager.alignEast(true));
			}
			
			//Swipe Down
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				updateBoardStatus(bManager.alignSouth(true));
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {}
	}
	
	private class NewBoardBorder
	{
		
		private final JPanel northPanel;
		private final JPanel southPanel;
		
		private final JLabel scoreLabel;
		
		private final JLabel moveInstructions;
		private final JLabel newGameInstructions;
		
		private String currentScore;
		
		private final String moveInstruction = "Use arrows key to move tiles.";
		private final String gameOver = "Game Over!";
		
		public NewBoardBorder()
		{
			northPanel = new JPanel();
			southPanel = new JPanel();

			//North Panel
			scoreLabel = new JLabel("Score: 0");
			scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 32));

			northPanel.add(scoreLabel);
			
			//South Panel
			moveInstructions = new JLabel("Use arrows key to move tiles.");
			moveInstructions.setFont(new Font("Monospaced", Font.BOLD, 20));
			moveInstructions.setHorizontalAlignment((moveInstructions.getWidth())/2);
			
			newGameInstructions = new JLabel("Ctrl+n to Start New Game");
			newGameInstructions.setFont(new Font("Monospaced",Font.BOLD, 26));
			newGameInstructions.setHorizontalAlignment((newGameInstructions.getWidth())/2);
			
			southPanel.setLayout(new GridLayout(2,1));
			southPanel.add(moveInstructions);
			southPanel.add(newGameInstructions);
	
		}
		
		public void setScoreLabel(int newScore)
		{
			scoreLabel.setText("Score: " + newScore);
		}
		
		public JLabel getScoreLabel()
		{
			return scoreLabel;
		}
		
		public void setMoveInstructions(String newInstructions)
		{
			moveInstructions.setText(newInstructions);
		}
		
		public JLabel getMoveInstructions()
		{
			return moveInstructions;
		}
		
		public void setNewGameInstructions(String newInstructions)
		{
			newGameInstructions.setText(newInstructions);
		}
		
		public JLabel getNewGameInstructions()
		{
			return newGameInstructions;
		}
		
		public JPanel getNorthPanel()
		{
			return northPanel;
		}
		
		public JPanel getSouthPanel()
		{
			return southPanel;
		}
		
		public String getMoveInstruction()
		{
			return moveInstruction;
		}
		
		public String getGameOver()
		{
			return gameOver;
		}
		
		
	}
	
	
}
