package com.oval.grabweb.action;

import com.oval.util.StringUtil;

public abstract class GKAbstractAction extends AbstractAction {

	protected void addBottomField(StringBuilder sb,String content,int index){
		sb.append(StringUtil.getValue(content, "border-bottom-width:0px;\">","</td>",index)).append(column_speractor);
	}

}
