
public class GameTile {
	private int value;
	GameTile(){
		setValue(0);
	}
	
	public void setValue(int val){
		this.value = val;
	}
	public int getValue(){
		return this.value;
	}
	
	public String toString(){
		return String.format("%5d", getValue());
	}
}
