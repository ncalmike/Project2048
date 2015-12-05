

import java.awt.*;
 
import javax.swing.JPanel;
 
public class tile2048 extends JPanel  {
      /**
	 * 
	 */
	private static final long serialVersionUID = -8027840392789294305L;

	/**
     @param x the left of the bounding rectangle
     @param y the top of the bounding rectangle
     @param width the width of the bounding rectangle
  */

     private static final int WIDTH = 80;
 
        private int value;
        private Color text;
        private Color background;
        // private Font font;
        //private int x;
        //private int y;
        
     //constructor1
    public tile2048(int x, int y, int value)
    {
       this.value = value;
       //this.x = x;
       //this.y = y;
    }

 
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        if(value == 0){
            //colors hex http://www.w3schools.com/tags/ref_colorpicker.asp
            text = new Color(217, 230, 242);
            background =  new Color(217, 230, 242);
        }
        else if(value == 2){
            //colors hex http://www.w3schools.com/tags/ref_colorpicker.asp
            text = new Color(48,48,48);
            background =  new Color(217, 230, 242);
        }
        else if(value == 4){
            text = new Color(48,48,48);
            background =  new Color(217, 230, 242);
        }
        else if(value == 8){
            text = new Color(240,240,240);
            background =  new Color(141, 180, 216);
        }
        else if(value == 16){
            text = new Color(240,240,240);
            background =  new Color(179, 205, 229);
        }
        else if(value == 32){
            text = new Color(240,240,240);
            background =  new Color(59, 117, 171);
        }
        else if(value == 64){
            text = new Color(240,240,240);
            background =  new Color(38, 76, 115);
        }
        else if(value == 128){
            text = new Color(240,240,240);
            background =  new Color(153, 153, 255);
        }
        else if(value == 256){
            text = new Color(240,240,240);
            background =  new Color(77, 184, 255);
        }
        else if(value == 512){
            text = new Color(240,240,240);
            background =  new Color(102, 193, 255);
        }
        else if(value == 1024){
            text = new Color(240,240,240);
            background =  new Color(0, 230, 172);
        }
        else if(value == 2048){
            text = new Color(240,240,240);
            background =  new Color(0, 179, 133);
        }
        else{
            text = Color.white;
            background = Color.black;}
 
        g2d.setPaint(background);
        g2d.fillRoundRect(8,8, WIDTH, WIDTH, WIDTH/8, WIDTH/8);
        Font font = new Font("Monospaced", Font.BOLD, 48);
        g2d.setPaint(text);
 
        if(value >64){
            font = font.deriveFont(36f);
        }else {
            //font = font;
        }
        g.setFont(font);
 
        int drawX = WIDTH/2 - DrawUtils.getMessageWidth(""+value, font, g2d)/2;
        int drawY = WIDTH/2 + DrawUtils.getMessageHeight(""+value, font, g2d)/2;
        g.drawString(""+String.valueOf(value), drawX, drawY);
        g.dispose();
        
 
    }
    int getValue()
    {
    	return value;
    }
    
    void setValue(int val)
    {
    	value = val;
    }
}