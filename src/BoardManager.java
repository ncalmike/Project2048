import java.security.SecureRandom;

// boardMngr controls the game of 2048
public class BoardManager {
	// VARIABLE DECLERATION
	private final	int 			NUM_ROWS	= 4;
	private final 	int 			NUM_COLUMNS	= 4;
	private 		GameTile[][] 	BoardTiles	= new GameTile[NUM_ROWS][NUM_COLUMNS];
	private			int				initVal		= 2;
	
	// ALIGN FUNCTIONS MOVE VALUES TO TILES CLOSE TO SIDE THAT GAME PLAYER HAS SELECTED
	BoardManager(){
		initBoardTiles();
		boolean trash = addTile(); // OUTPUT FROM addTile IS IRRELEVENT BECAUSE BOARD IS EMPTY
	}
	
	// INITIALIZE EACH INSTANCE OF BoardTiles ARRAY WITH new GameTile() OBJECT
	public void initBoardTiles(){
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				BoardTiles[i][j] = new GameTile();
			}
		}
	}
	
	// ACTIVATE NEW GAME PIECE
	private boolean addTile(){
		// CREATING COORD CLASS TO STORE row AND col VALUES FOR TILES THAT HAVE A VALUE OF 0
		class aryCoord{
			int row;
			int col;
		}
		
		int numCoords 			= 0;
		int coordIndex			= 0;
		aryCoord[] emptyCells	= new aryCoord[16];
		
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				if(BoardTiles[i][j].getValue() == 0){ // ADD REFERENCE TO TILES THAT CAN BE INITILIZED TO emptyCells ARRAY 
					emptyCells[numCoords] = new aryCoord();
					emptyCells[numCoords].row = i;
					emptyCells[numCoords].col = j;
					numCoords++;
				}
			}
		}
		if(numCoords > 0){
			SecureRandom getRand = new SecureRandom();
			coordIndex = getRand.nextInt(numCoords);
			BoardTiles[emptyCells[coordIndex].row][emptyCells[coordIndex].col].setValue(this.initVal);
		}
		return numCoords > 0;
	}
	
	// TEST THAT BoardTiles HAS AT LEAST ONE TILE THAT CAN ACCEPT INITIALIZED VALUE
	private boolean canAddTile(){
		boolean canAdd 	= false;
		int		row		= 0;
		int		col		= 0;
		
		do{
			do{
				canAdd = BoardTiles[row][col].getValue() == 0;
				col = col == BoardTiles[row].length - 1 ? 0 : col + 1; 
			}
			while(!canAdd && col > 0);
			
			row++;
		}
		while(!canAdd && row < NUM_ROWS);
		
		return canAdd;
	}
	
	// LOOP THROUGH ROWS OR COLUMNS AND CALL CORRECT FUNCTION TO ALIGN AND MERGE TILES
	public void alignNorth(boolean addNewTile){
		for(int k = 0; k < NUM_ROWS; k++){
			combineAndMoveNorth(k);
		}
		isBoardFull();
	}
	public void alignSouth(boolean addNewTile){
		for(int k = 0; k < NUM_ROWS; k++){
			combineAndMoveSouth(k);
		}
		isBoardFull();
	}
	public void alignEast(boolean addNewTile){
		for(int k = 0; k < NUM_ROWS; k++){
			combineAndMoveEast(k);
		}
		isBoardFull();
	}
	public void alignWest(boolean addNewTile){
		for(int k = 0; k < NUM_ROWS; k++){
			combineAndMoveWest(k);
		}
		isBoardFull();
	}

	// JOIN LIKE VALUES, ALIGN TILES, AND ADD A NEW TILE IF addNewTile IS TRUE
	public boolean combineAndMoveNorth(int index){
		boolean combinedTiles = false;
		for(int i = 1; i < NUM_ROWS; i++){
			
			if(BoardTiles[i][index].getValue() > 0){
				// CONTINUE TO MOVE TILE UNTIL BLOCKED BY TILE THAT HAS VALUE > 0
				while(i > 0 && canMove(BoardTiles[i][index], BoardTiles[i-1][index])){
					moveTileValues(BoardTiles[i][index], BoardTiles[i-1][index]);
					i--;
				}
				while(i > 0 && canCombine(BoardTiles[i][index], BoardTiles[i-1][index])){
					combinedTiles = true;
					marryTileValues(BoardTiles[i][index], BoardTiles[i-1][index]);
					i--;
				}
			}
			
		}
		return combinedTiles;
	}
	public boolean combineAndMoveSouth(int index){
		boolean combinedTiles = false;
		for(int i = NUM_ROWS - 1; i >= 0 ; i--){
			if(BoardTiles[i][index].getValue() > 0){
				// CONTINUE TO MOVE TILE UNTIL BLOCKED BY TILE THAT HAS VALUE > 0
				while(i < NUM_ROWS - 1 && canMove(BoardTiles[i][index], BoardTiles[i+1][index])){
					moveTileValues(BoardTiles[i][index], BoardTiles[i+1][index]);
					i++;
				}
				while(i < NUM_ROWS - 1 && canCombine(BoardTiles[i][index], BoardTiles[i+1][index])){
					combinedTiles = true;
					marryTileValues(BoardTiles[i][index], BoardTiles[i+1][index]);
					i++;
				}
			}
			
		}
		return combinedTiles;
	}
	public boolean combineAndMoveEast(int index){
		boolean combinedTiles = false;
		for(int i = NUM_ROWS - 1; i >= 0 ; i--){
			if(BoardTiles[index][i].getValue() > 0){
				// CONTINUE TO MOVE TILE UNTIL BLOCKED BY TILE THAT HAS VALUE > 0
				while(i < NUM_ROWS - 1 && canMove(BoardTiles[index][i], BoardTiles[index][i+1])){
					moveTileValues(BoardTiles[index][i], BoardTiles[index][i+1]);
					i++;
				}
				while(i < NUM_ROWS - 1 && canCombine(BoardTiles[index][i], BoardTiles[index][i+1])){
					combinedTiles = true;
					marryTileValues(BoardTiles[index][i], BoardTiles[index][i+1]);
					i++;
				}
			}
			
		}
		return combinedTiles;
	}
	
	public boolean combineAndMoveWest(int index){
		boolean combinedTiles = false;
		for(int i = 1; i < NUM_ROWS; i++){
			
			if(BoardTiles[index][i].getValue() > 0){
				// CONTINUE TO MOVE TILE UNTIL BLOCKED BY TILE THAT HAS VALUE > 0
				while(i > 0 && canMove(BoardTiles[index][i], BoardTiles[index][i-1])){
					moveTileValues(BoardTiles[index][i], BoardTiles[index][i-1]);
					i--;
				}
				while(i > 0 && canCombine(BoardTiles[index][i], BoardTiles[index][i-1])){
					combinedTiles = true;
					marryTileValues(BoardTiles[index][i], BoardTiles[index][i-1]);
					i--;
				}
			}
			
		}
		return combinedTiles;
	}
	
	// TEST, SWAP, AND MERGE METHODS
	public boolean canMove(GameTile tile1, GameTile tile2){
		return tile1.getValue() > 0 && tile2.getValue() == 0;
	}
	public boolean canCombine(GameTile tile1, GameTile tile2){
		return tile1.getValue() == tile2.getValue();
	}
	public void moveTileValues(GameTile tile1, GameTile tile2){
		tile2.setValue(tile1.getValue());
		tile1.setValue(0);
	}
	public void marryTileValues(GameTile tile1, GameTile tile2){
		System.out.println("Marrying values");
		tile2.setValue(tile1.getValue() + tile2.getValue());
		tile1.setValue(0);
	}
	
	// toString METHOD TO RETURN TILE VALUES IN TABBED OUTPUT FOR TESTING PURPOSES
	@Override
	public String toString() {
		String toRtrn = "";
		for(int i=0;i<NUM_ROWS;i++){
			for(int j=0;j<NUM_COLUMNS;j++){
				toRtrn += BoardTiles[i][j].toString();
			}
			toRtrn += "\n";
		}
		return toRtrn;
	}
	
	// TEST IF ALL TILES HAVE A VALUE GREATER THAN ZERO
	public void isBoardFull(){
		if(canAddTile()){
			addTile();
		} else {
			System.out.println("No more moves can be made!");
		}
	}
}

