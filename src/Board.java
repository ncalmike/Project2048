import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//
//euhfwuihkshdkjfhksdhf 
public class Board extends JFrame
{
	//Board's PlayBoard object
	private final PlayBoard pBoard;
	
	//Board's BoardBorder object
	private final BoardBorder bBorder;
	
	//Board's BoardManager object
	private final BoardManager bManager;
	
	private final int NUM_ROWS = 4; //bManager.getNUM_ROWS(); //***Make an accessor for these from BoardManager
	private final int NUM_COLS = 4; //bManager.getNUM_COLS(); //^^^^
	
	//Initializes the Board with a PlayBoard and a BoardBorder
	//Assigns the elements of PlayBoard and BoardBorder
	//to the zones of the the frame that they should be in.
	public Board()
	{
		bManager = new BoardManager(); //Initializes the Board's Board Manager object
		pBoard = new PlayBoard(); //Initializes the Board's Play Board object
		bBorder = new BoardBorder(); //Initializes the Board's Board Border object
		
		//Adds the PlayBoard to the center of the frame
		add(pBoard.getBoard(), BorderLayout.CENTER);
		
		//Adds the BoardBorder components around the border of the frame
		add(bBorder.getSwipeRight(), BorderLayout.EAST);
		add(bBorder.getSwipeLeft(), BorderLayout.WEST);
		add(bBorder.getNorthPanel(), BorderLayout.NORTH);
		add(bBorder.getSouthPanel(), BorderLayout.SOUTH);
		
	}//Board()
	
	
	//The 4x4 grid that contains the numbers of the board
	private class PlayBoard
	{
		//Each pane that makes up the Play Board.
		private final tile2048[][] theTiles;
		
		//The panel that will contain all of the panels of the Play Board
		private final JPanel theBoard; 
		
		//Each tile2048 that gets put into theTiles.
		//private final tile2048[][] theTiles;
		
		//Constructor for PlayBoard
		//Sets the characteristics for theTiles, theBoard,
		//and populates theBoard with theTiles.
		public PlayBoard()
		{
			//Sets the characteristics for the Tiles and the Board
			theTiles = new tile2048[NUM_ROWS][NUM_COLS];
			theBoard = new JPanel();
			theBoard.setLayout(new GridLayout(NUM_ROWS,NUM_COLS));
						
			//Initializes the elements of theTiles
			//(*currently initializes their labels to their row,column location*) 
			int counterRows = 0, counterCols = 0;
			String tileString;
			
			String[][] tileValues = getValsAsArray(bManager.getTilesValues());

			
			
			//Populates theTiles with the initial values for the PlayBoard
			for (int i = 0; i < NUM_ROWS; i++)
			{
				for (int j = 0; j < NUM_COLS; j++)
				{
					tileString = counterRows + "," + counterCols;
					theTiles[i][j] = new tile2048(0,0,Integer.parseInt(tileValues[i][j]));
					counterCols++;
				}
				counterCols = 0;
				counterRows++;
			}

			//Adds the elements of theTiles to theBoard
			for (JPanel[] panelRows : theTiles)
			{
				for (JPanel panelColumns : panelRows)
				{
					theBoard.add(panelColumns);
				}
			}

		}//PlayBoard()
		
		
		//Populates theTiles with the initial values for the PlayBoard
		
		
		public void revalueTiles()
		{
			int counterRows = 0, counterCols = 0;
			String tileString;
			
			String[][] tileValues = getValsAsArray(bManager.getTilesValues());

			
			
			for (int i = 0; i < NUM_ROWS; i++)
			{
				for (int j = 0; j < NUM_COLS; j++)
				{
					tileString = counterRows + "," + counterCols;
					theTiles[i][j] = new tile2048(0,0,Integer.parseInt(tileValues[i][j]));
					counterCols++;
				}
				counterCols = 0;
				counterRows++;
			}
			
		}

		
		//
		public String[][] getValsAsArray(String delimitedVals)
		{
			String[][] returnArray = new String[NUM_ROWS][NUM_COLS];
			
			String[] firstSplit = delimitedVals.split(";");
			for (int i = 0; i < NUM_ROWS; i++)
			{
				String[] rowArray = firstSplit[i].split(",");
				for (int j = 0; j < NUM_COLS; j++)
				{
					returnArray[i][j] = rowArray[j];
				}//Splits the delimited String into columns
			}//Splits the delimited String into rows
			
			//
			
			return returnArray;
		}//getValsAsArray()
		
		public JPanel[][] getTiles()
		{
			return theTiles;
		}
		
		//Accessor for theBoard
		public JPanel getBoard()
		{
			return theBoard;
		}//getBoard()
		
		//Mutator for each pane of
		public void setPane(int row, int col, int newValue)
		{
			theTiles[row][col].setValue(newValue);
		}
		
	}//PlayBoard
	
	
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
			swipeLeft.addActionListener(evtMgr);
			swipeRight.addActionListener(evtMgr);
			swipeUp.addActionListener(evtMgr);
			swipeDown.addActionListener(evtMgr);
			
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
					System.out.println("You Swiped Left");
				}
				
				//Swipe Up
				else if (event.getSource() == getSwipeUp())
				{
					System.out.println("You Swiped Up");
				}
				
				//Swipe Right
				else if (event.getSource() == getSwipeRight())
				{
					pBoard.setPane(0, 0, 32);
					pBoard.theTiles[0][0].repaint();
					
					System.out.println("You Swiped Right");
				}
				
				//Swipe Down
				else if (event.getSource() == getSwipeDown())
				{
					System.out.println("You Swiped Down");
				}
				
				
			}
			
			
		}//EventManager
		
		
	}//BoardBorder
	
	//Event
	
	
}
