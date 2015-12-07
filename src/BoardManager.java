import java.security.SecureRandom;

// boardMngr controls the game of 2048
public class BoardManager {
	// VARIABLE DECLERATION
	private final	int 			NUM_ROWS	= 4;
	private final 	int		 		NUM_COLUMNS	= 4;
	private 		int				score		= 0;
	private 		GameTile[][] 	BoardTiles	= new GameTile[NUM_ROWS][NUM_COLUMNS];
	
	/************************************************************************************/
	// BoardManager INITIALIZATION FUNCTIONS
	/************************************************************************************/	
	BoardManager(){
		initBoardTiles();
		addTile();
		addTile();
	}
	
	// INITIALIZE EACH INSTANCE OF BoardTiles ARRAY WITH new GameTile() OBJECT
	public void initBoardTiles(){
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				BoardTiles[i][j] = new GameTile();
			}
		}
	}
	public String startNewGame(){
		
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				BoardTiles[i][j].setValue(0);
			}
		}
		addTile();
		addTile();
		setScore(0);
		return getTilesValues();
	}

	
	/************************************************************************************/
	// ACCESSOR METHODS TO RETURN INITIAL TILE VALUE, CURRENT SCORE, OR ARRAY OF ALL TILE VALUES
	// ACCESSOR METHODS INCLUDED FOR CONSTANTS NUM_ROWS AND NUM_COLUMNS
	/************************************************************************************/
	public int getInitVal(){
		SecureRandom getRand = new SecureRandom();
		return (getRand.nextInt(2) + 1) * 2; // RETURNS VALUE OF 2 OR 4 
	}
	public int getScore(){
		return this.score;
	}
	public String getTilesValues(){
		String 			tileValues;
		StringBuilder 	fromAry = new StringBuilder();
		
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				fromAry.append(BoardTiles[i][j].getValAsStr());
				if(j < NUM_COLUMNS - 1) // ADD COMMAS AS DELIMETERS EXEPT LAST VALUE IN j LOOP
					fromAry.append(",");
			}
			if(i < NUM_ROWS - 1) // ADD SEMICOLONS AS DELIMETERS EXEPT LAST VALUE IN i LOOP
				fromAry.append(";");
		}
		tileValues = fromAry.toString();
		System.out.println("getTileValues()\n"+ tileValues); //TESTSUNDAY
		System.out.flush();
		return tileValues;
	}
	
	public int getNUM_ROWS(){
		return NUM_ROWS;
	}
	
	public int getNUM_COLUMNS(){
		return NUM_COLUMNS;
	}
	/************************************************************************************/
	// SETTER METHOD TO INCREMENT SCORE BY VALUE OF COMBINED TILES
	/************************************************************************************/
	public void setScore(int toAdd){
		this.score = toAdd;
	}
	/************************************************************************************/
	// FUNCTIONS TO TEST IF TILE CAN BE ADDED AND TO ADD A TILE TO THE GAMEBOARD
	/************************************************************************************/
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
	// ACTIVATE NEW GAME PIECE
	private boolean addTile(){
		// CREATING COORD CLASS TO STORE row AND col VALUES FOR TILES THAT HAVE A VALUE OF 0
		class aryCoord{
			int row;
			int col;
		}
		
		int numCoords 			= 0;
		int coordIndex			= 0;
		aryCoord[] emptyCells	= new aryCoord[NUM_ROWS * NUM_COLUMNS];
		
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < NUM_COLUMNS; j++){
				if(!tileHasValue(BoardTiles[i][j])){ // ADD REFERENCE TO TILES THAT CAN BE INITILIZED TO emptyCells ARRAY 
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
			BoardTiles[emptyCells[coordIndex].row][emptyCells[coordIndex].col].setValue(getInitVal());
		}
		return numCoords > 0;
	}
	/************************************************************************************/
	// TEST CAN SWAP, TEST CAN MERGE, SWAP, AND MERGE TILES METHODS
	/************************************************************************************/
	public boolean tileHasValue(GameTile tile){
		return tile.getValue() > 0;
	}
	public boolean canMove(GameTile tile1, GameTile tile2){
		return tile1.getValue() > 0 && tile2.getValue() == 0;
	}
	public boolean canCombine(GameTile tile1, GameTile tile2){
		return tile1.getValue() == tile2.getValue();
	}
	public boolean moveTileValues(GameTile tile1, GameTile tile2){
		tile2.setValue(tile1.getValue());
		tile1.setValue(0);
		return true;
	}
	public boolean marryTileValues(GameTile tile1, GameTile tile2){
		int combinedValue = tile1.getValue() + tile2.getValue();
		tile2.setValue(combinedValue);
		tile1.setValue(0);
		this.setScore(getScore()+combinedValue);
		return true;
	}
	/************************************************************************************/
	// JOIN LIKE VALUES, ALIGN TILES, AND ADD A NEW TILE IF addNewTile IS TRUE
	/************************************************************************************/
	// REMARKS DESCRIBE ALL ALIGN FUNCTIONS
	public String alignNorth(boolean addNewTile){
		boolean qualified			= false;
		// LOOP THROUGH ROWS OR COLUMNS AND CALL SPECIFIC MOVE AND ALIGN FUNCTIONS
		for(int k = 0; k < NUM_ROWS; k++){
			boolean firstMove 		= moveNorth(k);
			boolean combined		= combineNorth(k);
					qualified		= moveNorth(k);
			// ASSIGN qualified TO TRUE IF TILE HAS BEEN MOVED OR COMBINED ON THIS OR PREVIOUS ITERATIONS
			qualified 				= qualified || firstMove || combined;
		}
		// CALL isBoardFull TO SEE IF OUT OF MOVES AND PASS true IF TILE WAS MOVED OR COMBINED
		isBoardFull(qualified);
		return getTilesValues();
	}
	public String alignSouth(boolean addNewTile){
		boolean qualified			= false;
		for(int k = 0; k < NUM_ROWS; k++){
			boolean firstMove 		= moveSouth(k);
			boolean combined		= combineSouth(k);
					qualified		= moveSouth(k);
			qualified 				= qualified || firstMove || combined;
		}
		isBoardFull(qualified);
		return getTilesValues();
	}
	public String alignEast(boolean addNewTile){
		boolean qualified			= false;
		for(int k = 0; k < NUM_ROWS; k++){
			boolean firstMove 		= moveEast(k);
			boolean combined		= combineWest(k);
					qualified		= moveEast(k);
			qualified 				= qualified || firstMove || combined;
		}
		isBoardFull(qualified);
		return getTilesValues();
	}
	public String alignWest(boolean addNewTile){
		boolean qualified			= false;
		for(int k = 0; k < NUM_ROWS; k++){
			boolean firstMove 		= moveWest(k);
			boolean combined		= combineWest(k);
					qualified		= moveWest(k);
			qualified 				= qualified || firstMove || combined;
		}
		isBoardFull(qualified);
		return getTilesValues();
	}
	/************************************************************************************/
	// MOVE VALUES TO TILES CLOSE TO SIDE THAT GAME PLAYER HAS SELECTED
	/************************************************************************************/
	// REMARKS DESCRIBE ALL MOVE FUNCTIONS
	public boolean moveNorth(int index){
		boolean movedTiles = false;
		// LOOP THROUGH ROWS OR COLUMNS
		for(int i = 1; i < NUM_ROWS; i++){
			// TEST THAT TILE IN CURRENT ROW AND COLUMN IS AN ACTIVE GAME PIECE (value > 0)
			if(BoardTiles[i][index].getValue() > 0){
				// CONTINUE TO MOVE TILE VALUE UNTIL BLOCKED BY TILE THAT HAS VALUE > 0
				while(i > 0 && canMove(BoardTiles[i][index], BoardTiles[i-1][index])){
					movedTiles = moveTileValues(BoardTiles[i][index], BoardTiles[i-1][index]);
					// INCREMENT i IN DIRECTION OF PLAYER MOVE
					i--;
				}
			}
		}
		return movedTiles;
	}
	public boolean moveSouth(int index){
		boolean movedTiles = false;
		for(int i = NUM_COLUMNS - 1; i >= 0 ; i--){
			if(BoardTiles[i][index].getValue() > 0){
				while(i < NUM_COLUMNS - 1 && canMove(BoardTiles[i][index], BoardTiles[i+1][index])){
					movedTiles = moveTileValues(BoardTiles[i][index], BoardTiles[i+1][index]);
					i++;
				}
			}
			
		}
		return movedTiles;
	}
	public boolean moveEast(int index){
		boolean movedTiles = false;
		for(int i = NUM_ROWS - 1; i >= 0 ; i--){
			if(BoardTiles[index][i].getValue() > 0){
				while(i < NUM_ROWS - 1 && canMove(BoardTiles[index][i], BoardTiles[index][i+1])){
					movedTiles = moveTileValues(BoardTiles[index][i], BoardTiles[index][i+1]);
					i++;
				}
			}
			
		}
		return movedTiles;
	}
	public boolean moveWest(int index){
		boolean movedTiles = false;
		for(int i = 1; i < NUM_ROWS; i++){
			
			if(BoardTiles[index][i].getValue() > 0){
				while(i > 0 && canMove(BoardTiles[index][i], BoardTiles[index][i-1])){
					movedTiles = moveTileValues(BoardTiles[index][i], BoardTiles[index][i-1]);
					i--;
				}
			}
		}
		return movedTiles;
	}
	/************************************************************************************/
	// COMBINE TILES WITH SAME VALUE WHEN NEXT TO EACH OTHER IN DIRECTON OF PLAYER MOVE 
	/************************************************************************************/
	public boolean combineNorth(int index){
		boolean combinedTiles = false;
		for(int i = 1; i < NUM_COLUMNS; i++){
			if(tileHasValue(BoardTiles[i][index]) && canCombine(BoardTiles[i][index], BoardTiles[i-1][index])){
				combinedTiles = marryTileValues(BoardTiles[i-1][index], BoardTiles[i][index]);
				i++;
			}
		}
		return combinedTiles;
	}
	public boolean combineSouth(int index){
		boolean combinedTiles = false;
		for(int i = NUM_COLUMNS - 2; i >= 0 ; i--){
			if(tileHasValue(BoardTiles[i][index]) && canCombine(BoardTiles[i][index], BoardTiles[i+1][index])){
				combinedTiles = marryTileValues(BoardTiles[i+1][index], BoardTiles[i][index]);
				i--;
			}
		}
		return combinedTiles;
	}
	public boolean combineEast(int index){
		boolean combinedTiles = false;
		for(int i = NUM_COLUMNS - 2; i > 0 ; i--){
			if(tileHasValue(BoardTiles[index][i]) && canCombine(BoardTiles[index][i], BoardTiles[index][i+1])){
				combinedTiles = marryTileValues(BoardTiles[index][i+1], BoardTiles[index][i]);
				i++;
			}
		}
		return combinedTiles;
	}
	public boolean combineWest(int index){
		boolean combinedTiles = false;
		for(int i = 1; i < NUM_ROWS; i++){
			if(tileHasValue(BoardTiles[index][i]) && canCombine(BoardTiles[index][i], BoardTiles[index][i-1])){
				combinedTiles = marryTileValues(BoardTiles[index][i-1], BoardTiles[index][i]);
				i++;
			}
		}
		return combinedTiles;
	}
	/************************************************************************************/
	// TEST IF ALL TILES HAVE A VALUE GREATER THAN ZERO
	/************************************************************************************/
	public void isBoardFull(boolean moveMade){
		if(moveMade && canAddTile()){
			addTile();
		} /*else {
			if(moveMade)
				System.out.println("No more moves can be made!");
		}*/
	}
	/************************************************************************************/
	// METHODS TO TEST IF THERE IS AN EMPTY TILE 
	// OR IF TILES CAN BE COMBINED VERTICALLY OR HORIZONTALLY
	// AND RETURN THAT GAME IS OVER IF NONE ARE FOUND
	/************************************************************************************/
	public boolean hasHorizontalCombine(){
		boolean found = false;
		int row = 0;
		
		do{
			int col = 0;
			do{
				found = canCombine(BoardTiles[row][col], BoardTiles[row][col+1]);
				col++;
			}while(!found && col < getNUM_COLUMNS()-1);
			row++;
		}while(!found && row < getNUM_ROWS());
		return found;
	}
	public boolean hasVerticalCombine(){
		boolean found = false;
		int row = 0;
		
		do{
			int col = 0;
			do{
				found = canCombine(BoardTiles[row][col], BoardTiles[row+1][col]);
				col++;
			}while(!found && col < getNUM_COLUMNS());
			row++;
		}while(!found && row < getNUM_ROWS()-1);
		return found;
	}
	public boolean isGameOver(){
		return !canAddTile() && !hasHorizontalCombine() && !hasVerticalCombine();
	}
	/************************************************************************************/
	// toString METHOD TO RETURN TILE VALUES IN TABBED OUTPUT FOR TESTING PURPOSES
	/************************************************************************************/
	@Override
	public String toString() {
		String toRtrn = "";
		for(int i=0;i<NUM_ROWS;i++){
			for(int j=0;j<NUM_COLUMNS;j++){
				toRtrn += BoardTiles[i][j].toString();
			}
			toRtrn += "\n";
		}
		toRtrn += "\nScore: " + this.getScore() + "\n\n";
		return toRtrn;
	}
	/************************************************************************************/
	// END OF BoarManager class
	/************************************************************************************/
}


