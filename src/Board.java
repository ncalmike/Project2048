import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


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
	
	

	
	//private final int bManager.getNUM_ROWS() = 4; //bManager.getbManager.getNUM_ROWS()(); //***Make an accessor for these from BoardManager
	//private final int bManager.getNUM_COLUMNS() = 4; //bManager.getbManager.getNUM_COLUMNS()(); //^^^^
	
	
	/**Stores the tiles in a 2D array of tile2048.<br>
	The first index represents the row position where the tile is to be displayed.<br>
	The second index represents the column position where the tile is to be displayed.
	 */
	private final tile2048[][] theTiles;
	
	
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


	/**Updates the value of each tile.<br>
	
	Retrieves the tile values from Board Manager as a delimited String,
	tokenizes this String, puts it in a 2D array, and sets the value
	of each tile.
	 */
	private void revalueTiles()
	{
		//Fetches delimited string from Board Manager and converts it
		//into a tokenized 2D array. Stored locally in tileValues.
		//The first index of tileValues
		String[][] tileValues = getValsAsArray(bManager.getTilesValues());
		
		//Sets the value of each tile; needs to convert each tile value
		//to an integer, as each was received as a String.
		for (int i = 0; i < bManager.getNUM_ROWS(); i++)
		{//Loops through rows
			for (int j = 0; j < bManager.getNUM_COLUMNS(); j++)
			{//Loops through columns
				setTile(i,j, Integer.parseInt(tileValues[i][j]));
			}
		}
		
	}//revalueTiles()


	/**Repaints each tile.<br>
	
	Should be used after each tile has be reassigned a value.
	 */
	private void repaintAll()
	{
		//Repaints all of the tiles in theTiles (of type tile2048[][])
		for (int i = 0; i < bManager.getNUM_ROWS(); i++)
		{//Loops through rows
			for (int j = 0; j < bManager.getNUM_COLUMNS(); j++)
			{//Loops through columns
				getTile(i,j).repaint(); //Repaints tile at row i, column j
			}
		}
	}//repaintAll()


	/**
	*/
	private void updateBoardStatus()
	{
		if (bManager.isGameOver())
		{
			newBBorder.setMoveInstructions(newBBorder.getGameOver());
		}
		else
		{
			newBBorder.setScoreLabel(bManager.getScore());
		}
		revalueTiles();
		repaintAll();
	}
	
	/**Accessor for theTiles.<br>
	
	theTiles is the 2D array of tile2048 which is used to display the board.
	
	@return Returns the 2D array of tile2048 used to display the board.
	 */
	public JPanel[][] getTiles()
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
	public JPanel getTile(int row, int col)
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
			updateBoardStatus();
			//Swipe Left
			if (e.getKeyCode() == 79)
			{
				updateBoardStatus();
			}
			if (e.getKeyCode() == 78 && e.getModifiers() == 2)
			{
				bManager.startNewGame();
				newBBorder.setScoreLabel(bManager.getScore());
				newBBorder.setMoveInstructions(newBBorder.getMoveInstruction());
				revalueTiles();
				repaintAll();
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				bManager.alignWest(true);
				System.out.println("ActionListener:");
				System.out.println(bManager); //TESTSUNDAY
				System.out.flush();
				updateBoardStatus();
			}
			
			//Swipe Up
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				bManager.alignNorth(true);
				System.out.println("ActionListener:");
				System.out.println(bManager); //TESTSUNDAY
				System.out.flush();
				updateBoardStatus();
			}
			
			//Swipe Right
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				bManager.alignEast(true);
				System.out.println("ActionListener:");
				System.out.println(bManager); //TESTSUNDAY
				System.out.flush();
				updateBoardStatus();
			}
			//Swipe Down
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				bManager.alignSouth(true);
				System.out.println("ActionListener:");
				System.out.println(bManager); //TESTSUNDAY
				System.out.flush();
				updateBoardStatus();
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
	
	/*
	//Contains all of the buttons that appear on the border of the GUI
	private class BoardBorder
	{
		//Cardinal direction panels
		private final JPanel northPanel; //Contains (Left to Right) newGame, swipeUp, gameScorePanel
		private final JPanel southPanel; //Contains (Left to Right) autoMove, swipeDown, suggestMovePanel
		
		//Corner Buttons (and positions)
		private final JButton newGame; //Northwest
		private final JPanel gameScorePanel; //Northeast, holds scoreString and scoreInt
		private final JLabel scoreString; //Simply stores the string "Score:", lives in gameScorePanel
		private final JLabel scoreInt; //Stores the score, lives in gameScorePanel
		
			//FUNCTIONALITY FOR autoMove AND suggestMoveButton
			//WILL NOT BE IMPLEMENTED AT THIS TIME
			//THEY ARE CURRENTLY PLACEHOLDERS FOR POTENTIAL
			//FUTURE FUNCTIONALITY
		private final JButton autoMove; //Southwest (this will be based on logic that is currently not being implemented)
		private final JPanel suggestMovePanel; //Southeast, holds suggestMoveButton and moveSuggestion
		private final JButton suggestMoveButton; //Press this button to see a move suggestion (this will be based on logic that is currently not being implemented), lives in suggestMovePanel
		private final JLabel moveSuggestion; //This is where the move suggestion appears, lives in suggestMovePanel
		
		//Move Buttons (and positions)
		private final JButton swipeLeft; //West
		private final JButton swipeRight; //East
		private final JButton swipeUp; //North
		private final JButton swipeDown; //South
		
		//Constructor for BoardBorder
		//Initializes all of the components of the GUI
		public BoardBorder()
		{
			//Initializes the BoardBorder's EventManager
			//All of the buttons with planned functionality will be registered
			//to this EventManager, which is the ActionListener for the Board
			EventManager evtMgr = new EventManager();
			
			//Corner Buttons (In order: NW, NE, SW, SE)
			//New Game Button
			newGame = new JButton("New Game");
			
			//Game Score Panel, contains scoreString and scoreInt
			gameScorePanel = new JPanel(); //Game Score Panel
			scoreString = new JLabel("Score:"); //Score String Label, displays "Score:" and lives in Game Score Panel
			scoreString.setHorizontalAlignment(SwingConstants.CENTER);
			scoreInt = new JLabel("0");//Score Int Label, displays the current Score of the user's game and lives in Game Score Panel
			scoreInt.setHorizontalAlignment(SwingConstants.CENTER);
			gameScorePanel.setLayout(new GridLayout(2,1)); //Sets up Game Score Panel's Layout, then adds the two labels that live in it
			gameScorePanel.add(scoreString);
			gameScorePanel.add(scoreInt);

			//Auto Move Button
			autoMove = new JButton("Auto Move");
			
			//Suggest Move Panel, contains suggestMoveButton and moveSuggestion
			suggestMovePanel = new JPanel(); //Suggest Move Panel
			suggestMoveButton = new JButton("Suggest Move"); //Suggest Move Button, suggests a move and displays that move in moveSuggestionLabel
			moveSuggestion = new JLabel("N/A"); //Move Suggestion Label, displays a suggested move when Suggest Move Button is activated
			moveSuggestion.setHorizontalAlignment(SwingConstants.CENTER);
			suggestMovePanel.setLayout(new GridLayout(2,1)); //Sets up Suggest Move Panel's Layout, then adds the button and label that live in it
			suggestMovePanel.add(suggestMoveButton);
			suggestMovePanel.add(moveSuggestion);
			
			//Move Buttons
			swipeLeft = new JButton("Swipe Left");
			swipeRight = new JButton("Swipe Right");
			swipeUp = new JButton("Swipe Up");
			swipeDown = new JButton("Swipe Down");
			
			//Registers the Move Buttons to the EventManager
			//swipeLeft.addActionListener(evtMgr);
			//swipeRight.addActionListener(evtMgr);
			//swipeUp.addActionListener(evtMgr);
			//swipeDown.addActionListener(evtMgr);
			
			//Cardinal Direction Panels
			
			//North
			//The Northern portion of the GUI contains the New Game Button, the Swipe Up button, and the Game Score Panel
			northPanel = new JPanel();
			northPanel.setLayout(new GridLayout(1,3));
			northPanel.add(newGame);
			northPanel.add(swipeUp);
			northPanel.add(gameScorePanel);
			
			//The Eastern portion of the GUI is simply the Swipe Right Button
			
			//South
			//The Southern portion of the GUI contains the Auto Move BUtton, the Swipe Down button, and the Suggest Move Panel
			southPanel = new JPanel();
			southPanel.setLayout(new GridLayout(1,3));
			southPanel.add(autoMove);
			southPanel.add(swipeDown);
			southPanel.add(suggestMovePanel);
			
			//The Western portion of the GUI is simply the Swipe Left Button
			
			
		}//BoardBorder()
		
		//Accessor for newGame
		public JButton getNewGame()
		{
			return newGame;
		}//getNewGame()
		
		//Accessor for gameScorePanel
		public JPanel getGameScorePanel()
		{
			return gameScorePanel;
		}//getGameScorePanel()
		
		//Accessor for autoMove
		public JButton getAutoMove()
		{
			return autoMove;
		}//getAutoMove()
		
		//Accessor for suggestMovePanel
		public JPanel getSuggestMovePanel()
		{
			return suggestMovePanel;
		}//getSuggestMovePanel()
		
		//Accessor for swipeLeft
		public JButton getSwipeLeft()
		{
			return swipeLeft;
		}//getSwipeLeft()
		
		//Accessor for swipeRight
		public JButton getSwipeRight()
		{
			return swipeRight;
		}//getSwipeRight()
		
		//Accessor for swipeUp
		public JButton getSwipeUp()
		{
			return swipeUp;
		}//getSwipeUp()
		
		//Accessor for swipeDown
		public JButton getSwipeDown()
		{
			return swipeDown;
		}//getSwipeDown()
		
		//Cardinal Direction Panels
		//Accessor for northPanel
		public JPanel getNorthPanel()
		{
			return northPanel;
		}//getNorthPanel()
		
		//Accessor for southPanel
		public JPanel getSouthPanel()
		{
			return southPanel;
		}//getSouthPanel()
		
		
		private class EventManager implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//Swipe Left
				if (event.getSource() == getSwipeLeft())
				{
					bManager.alignWest(true);
					revalueTiles();
					repaintAll();
					System.out.println("You Swiped Left");
				}
				
				//Swipe Up
				else if (event.getSource() == getSwipeUp())
				{
					bManager.alignNorth(true);
					revalueTiles();
					repaintAll();
					System.out.println("You Swiped Up");
				}
				
				//Swipe Right
				else if (event.getSource() == getSwipeRight())
				{
					bManager.alignEast(true);
					revalueTiles();
					repaintAll();
					System.out.println("You Swiped Right");
				}
				
				//Swipe Down
				else if (event.getSource() == getSwipeDown())
				{
					bManager.alignSouth(true);
					revalueTiles();
					repaintAll();
					System.out.println("You Swiped Down");
				}
				
				
			}//actionPerformed()
			
			
		}//EventManager
		
		
	}//BoardBorder
	*/
	
	
}
