package com.indocosmo.pos.forms.components.labels;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;  
import java.util.TimerTask;

import javax.swing.JLabel;  

import org.apache.poi.ss.formula.ptg.AttrPtg.SpaceType;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosSyncUtil;

@SuppressWarnings("serial")
public class PosMarqueeLabel extends JLabel {  
	
  public static int LEFT_TO_RIGHT = 1;  
  public static int RIGHT_TO_LEFT = 2;  
  
  String marqueeText;  
  int option;  
  int place=1;
  String[] messages;
  int index=-1;
  private final static int SPEED=9;  
 
  public PosMarqueeLabel() { 
	  
	    this.setText("");  
	    marqueeText="";
	    option=RIGHT_TO_LEFT;  
	    initControl();
	   
	} 
  public PosMarqueeLabel(String text) {  
	    this.setText(text);
	    option=RIGHT_TO_LEFT;  
	    initControl();
	}  
  public PosMarqueeLabel(String text, int Option) {  
    this.option = Option;  
    this.setText(text);  
    initControl();
  }  
  
  private  void initControl(){
	  
	  setFont(this.getFont().deriveFont(Font.BOLD, 16f));
	  setForeground(Color.GREEN);
//	  setBackground(Color.YELLOW);
//	  setOpaque(true);
//	  setScrolling();
	  
  }

// 
///**
// * @return the marqueeText
// */
//public String getMarqueeText() {
//	return marqueeText;
//}
///**
// * @param marqueeText the marqueeText to set
// */
//public void setMarqueeText(String marqueeText) {
//	this.marqueeText = marqueeText;
//	
//	messages=marqueeText.split("\n");
//	setMessage();
//	
//}
/**
 * @return the option
 */
public int getOption() {
	return option;
}
/**
 * @param option the option to set
 */
public void setOption(int option) {
	this.option = option;
}
/**
 * @return the speed
 */
public int getSpeed() {
	return SPEED;
}
@Override  
  protected void paintComponent(Graphics g) {  
	
    if (option == LEFT_TO_RIGHT) {  
      g.translate((int) ((System.currentTimeMillis() / SPEED) % (getWidth() * 2) - getWidth()), 0);  
    } else if (option == RIGHT_TO_LEFT) {  
      g.translate((int) (getWidth() - (System.currentTimeMillis() / SPEED) % (getWidth() * 2)), 0); 

    }    
	

	
     

    super.paintComponent(g);  
//    setText("dsfsdf");
//    g.drawString("aaasdasadsdsd", 10, 2);
    repaint(5);  
  }
//private void setMessage(){
//	
//	if (getX()<=1){
//		if(messages.length>0 ){
//			if (messages.length-1>index)
//				++index;
//			else
//				index=0;
//			setText(messages[index]);		
//		}
//	}
//		
//}

// 
//private void setScrolling(){
//	
//	java.util.Timer timer = new java.util.Timer();
//    timer.scheduleAtFixedRate(timerTask, 1, 10000);
//}
//
//TimerTask timerTask=new TimerTask() {
//
//    @Override
//    public void run() {
//
//    	try {
//    		setMessage();
////        	char firstChar  = getText().charAt( 0 );
////        	setText( getText().substring( 1, getText().length() ) + firstChar );
//			
//		} catch (Exception e) {
//			PosLog.write(this, "MarqueeLabel", e);
//		} 
//    }
//};

  
}  