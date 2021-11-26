package com.indocosmo.pos.forms.components.keypads.listners;

import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.IPosNumkeypadListner;

public abstract class PosNumkeypadListnerAdapter  implements IPosNumkeypadListner{
	public void onAcceptButton(String value){};
	public void onCancelButton(){};
}
