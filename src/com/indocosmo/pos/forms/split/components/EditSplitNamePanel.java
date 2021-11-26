/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.split.listners.IPosSplitNameEditPanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class EditSplitNamePanel extends JPanel {

	private static final int NAME_LOOKUP_BUTTON_HEIGHT=40;
	private static final int NAME_LOOKUP_BUTTON_WIDTH=50;
	private static final String NAME_LOOKUP_BUTTON_NORMAL="split_name_lookup.png";
	private static final String NAME_LOOKUP_BUTTON_TOUCHED="split_name_lookup_touch.png";
	private static final int TITLE_HEIGHT=30; 

	protected static final Color TITLE_LABEL_BG_COLOR = new Color(78, 128, 188);
	protected static final Color TITLE_LABEL_FG_COLOR = Color.WHITE;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RootPaneContainer mParent;
	private PosTouchableTextField txtNameField;
	private IPosBrowsableItem[] definedSplitsNames;
	private PosButton nameLookUpButton;
	private IPosSplitNameEditPanelListener listener;
	

	/**
	 * 
	 */
	public EditSplitNamePanel(RootPaneContainer parent,int width,int height) {

		this.mParent=parent;

		setLayout(null);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setBorder(new EtchedBorder());
		createContents();

	}

	/**
	 * 
	 */
	private void createContents() {

		final JLabel title=PosFormUtil.setHeading("Split Name", getWidth()-6,TITLE_HEIGHT);
		title.setLocation(2, 2);
		this.add(title);

		final int itemWidth=(getWidth()-NAME_LOOKUP_BUTTON_WIDTH-PANEL_CONTENT_H_GAP*2);
		int top=(getHeight()/2-PosTouchableTextField.LAYOUT_DEF_HEIGHT/2)+TITLE_HEIGHT/2;
		int left=PANEL_CONTENT_H_GAP;

		txtNameField=new PosTouchableTextField(mParent,itemWidth);
		txtNameField.setLocation(left, top);
		txtNameField.setTitle("Split Name");
		txtNameField.setFocusable(true);
		txtNameField.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
				final String name=(value!=null)?String.valueOf(value):null;
					
				if(listener!=null)
					listener.onNameSelected(name);
				
				txtNameField.requestFocus();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});

		this.add(txtNameField);

		final int width=NAME_LOOKUP_BUTTON_WIDTH;
		final int height=NAME_LOOKUP_BUTTON_HEIGHT;
		
		left=txtNameField.getX()+txtNameField.getWidth();
		top=txtNameField.getY()+1;
		nameLookUpButton=new PosButton();
		nameLookUpButton.setSize(width, height);
		nameLookUpButton.setPreferredSize(new Dimension(width, height));
		nameLookUpButton.setLocation(left, top);
		nameLookUpButton.setEnabled(false);
		nameLookUpButton.setImage(PosResUtil.getImageIconFromResource(NAME_LOOKUP_BUTTON_NORMAL));
		nameLookUpButton.setTouchedImage(PosResUtil.getImageIconFromResource(NAME_LOOKUP_BUTTON_TOUCHED));
		nameLookUpButton.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				showSplitNameBroswerForm();
			}

		});
		this.add(nameLookUpButton);

	}

	/**
	 * 
	 */
	private void showSplitNameBroswerForm() {

		if(definedSplitsNames==null)return;
		PosObjectBrowserForm mBrowseForm=new PosObjectBrowserForm("Split Names", definedSplitsNames,ItemSize.Wider,2,3);
		mBrowseForm.setListner(new IPosObjectBrowserListner() {
			@Override
			public void onItemSelected(IPosBrowsableItem item) {
				txtNameField.setText(item.getDisplayText());
				if(listener!=null)
					listener.onNameSelected(item.getDisplayText());
			}

			@Override
			public void onCancel() {

			}
		});		

		PosFormUtil.showLightBoxModal(mParent,mBrowseForm);

	}


	/**
	 * Craetes the IPosBrowsableItem list from the given set
	 * @param list
	 * @return
	 */
	private IPosBrowsableItem[] getDefinedSplitList(String[] list){

		IPosBrowsableItem[] itemList=new DefinedSplits[list.length];

		for(int index=0; index<list.length;index++)
			itemList[index]=new DefinedSplits(list[index]);

		return itemList;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getName()
	 */
	public String getName(){
		return txtNameField.getText();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {

		txtNameField.setText(name);
	}

	/**
	 * @param definedSplitsNames
	 */
	public void setDefinedSplitsNames(Set<String> definedSplitsNames) {

		if(definedSplitsNames==null || definedSplitsNames.size()<=0 ) return;

		nameLookUpButton.setEnabled(true);
		this.definedSplitsNames=getDefinedSplitList(definedSplitsNames.toArray(new String[definedSplitsNames.size()]));

	}

	/**
	 * @return
	 */
	public boolean onValidate(){

		boolean valid=true;
		
		if(getName()==null || getName().isEmpty()){
			
			PosFormUtil.showErrorMessageBox(mParent, "Please specify the split name.");
			txtNameField.requestFocus();
			valid=false;
		}
		
		return valid;
	}


	/**
	 * @author jojesh-13.2
	 *
	 */
	private class DefinedSplits implements IPosBrowsableItem{

		private String name;

		public DefinedSplits(String name){

			this.name=name;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
		 */
		@Override
		public Object getItemCode() {
			// TODO Auto-generated method stub
			return name;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
		 */
		@Override
		public String getDisplayText() {
			// TODO Auto-generated method stub
			return name;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
		 */
		@Override
		public boolean isVisibleInUI() {
			// TODO Auto-generated method stub
			return true;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
		 */
		@Override
		public KeyStroke getKeyStroke() {
			// TODO Auto-generated method stub
			return null;
		}

	}


	/**
	 * @return the listener
	 */
	public IPosSplitNameEditPanelListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosSplitNameEditPanelListener listener) {
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		
		txtNameField.requestFocus();
		
	}

}
