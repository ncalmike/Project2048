import java.util.Scanner;

public class BoardMngrTest {
	BoardManager testBoard = new BoardManager();
	
	BoardMngrTest(){
		System.out.println("Enter (N)orth, (W)est, (S)outh, (E)ast or (Q)uit");
		emulateCommands();
		System.out.print(testBoard.toString());
	}
	public void emulateCommands(){
		Scanner userInput 	= new Scanner(System.in);
		char 	playerMove;
		do{
			playerMove = userInput.nextLine().charAt(0);
			System.out.println("emulateCommands loop");
			switch(playerMove) {
				case 'N':
				case 'n':
					testBoard.alignNorth(true);
					break;
				case 'S':
				case 's':
					testBoard.alignSouth(true);
					break;
				case 'E':
				case 'e':
					testBoard.alignEast(true);
					break;
				case 'W':
				case 'w':
					testBoard.alignWest(true);
					break;
				case 'Q':
					playerMove = 'q';
					break;
				default:
					System.out.println("Enter (N)orth, (W)est, (S)outh, (E)ast or (Q)uit");
			}
			System.out.print(testBoard.toString());
		} while(!(playerMove == 'q'));
		
	}
	@Override
	public String toString(){
		return testBoard.toString();
	}
}
