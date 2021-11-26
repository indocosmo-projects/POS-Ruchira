/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;

/**
 * @author jojesh
 *
 */
public class SplitMethodSelectionPanelButtons extends SplitMethodSelectionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MAX_SHOW_BUTTONS=5;
	private static final int PANEL_CONTENT_V_GAP=2;
	private static  final int PANEL_CONTENT_H_GAP=3;
	
	private HashMap<SplitBasedOn, PosSelectButton> splitButtons;
	

	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	public SplitMethodSelectionPanelButtons(RootPaneContainer parent, int width,
			int height) {
		super(parent, width, height);
	}
	

	/**
	 * 
	 */
	private void createButtonList(){
		
		final int MAX_BUTTONS=MAX_SHOW_BUTTONS;
		final int buttonWidth= (getWidth()-PANEL_CONTENT_H_GAP*MAX_BUTTONS+1)/MAX_BUTTONS; //101
		final int buttonHeight= getHeight()-PANEL_CONTENT_V_GAP*2;//46
		int btnConut=0;
		
		splitButtons=new HashMap<SplitBasedOn, PosSelectButton>();
		for(SplitBasedOn item:SplitBasedOn.values()){
			
			PosSelectButton button=new PosSelectButton();
			button.setSize(new Dimension(buttonWidth,buttonHeight));
			button.setPreferredSize(button.getSize());
			button.setText(item.getShortText());
			button.setTag(item);
			button.setOnSelectListner(new IPosSelectButtonListner() {
				
				@Override
				public void onSelected(PosSelectButton button) {
					SplitBasedOn newBasedon=(SplitBasedOn) button.getTag();
					if(splitBasedOn !=newBasedon){
					
						if(onSPlitMethodChanged(newBasedon)){
							
							if(splitBasedOn!=null)
								splitButtons.get(splitBasedOn).setSelected(false);;
							splitBasedOn=newBasedon;
						}else{
							
							splitButtons.get(newBasedon).setSelected(false);;
						}
					}
					
				}
			});
			button.setImage("split_basedon_button.png");
			button.setTouchedImage("split_basedon_button_selected.png");
			splitButtons.put(item, button);
			add(button);
			btnConut++;
			if(btnConut>=MAX_SHOW_BUTTONS) break;
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodSelectionPanel#initComponent()
	 */
	@Override
	protected void initComponent() {
		super.initComponent();
		setLayout(new FlowLayout(FlowLayout.CENTER, PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		createButtonList();
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodSelectionPanel#setSplitMethod(com.indocosmo.pos.common.enums.split.SplitBasedOn)
	 */
	@Override
	protected void setSplitMethod(SplitBasedOn splitBasedOn) {

		splitButtons.get(splitBasedOn).setSelected(true);
	}


	/**
	 * @param basedOn
	 * @param enabled
	 */
	public void setSplitMethodEnabled(SplitBasedOn basedOn, boolean enabled){
		
		splitButtons.get(basedOn).setEnabled(enabled);
		
	}
	

}
