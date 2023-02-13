/**   
* @Company: LuxonData 
* @Title: CustomArrayList.java 
* @Package com.oval.util 
* @Description: TODO
* @author yaokaichang  
* @date 2015-3-3 下午03:25:30 
* @version V1.0   
*/ 
package com.oval.util;

import java.util.List;

/** 
 * @ClassName: CustomArrayList 
 * @Description: TODO 
 * @author yaokaichang 
 * @date 2015-3-3 下午03:25:30  
 */
public class CompareList  {

	
    /** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;

	public static boolean contains(String o, List<String> objects) {
        return indexOf(o,objects) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public static int indexOf(String o, List<String> objects) {
        if (o == null) {
            for (int i = 0; i < objects.size(); i++)
                if (objects.get(i)==null)
                    return i;
        } else {
        	String compareContext = o.substring(0, o.lastIndexOf("!!",o.lastIndexOf("!!")-1));//记录行内容
        	String compareAccount = o.substring(o.lastIndexOf("!!",o.lastIndexOf("!!")-1),o.length());//记录行账号 截取倒数第二个！！
			for (int i = 0; i < objects.size(); i++){
	        	String context = objects.get(i).substring(0, objects.get(i).lastIndexOf("!!",objects.get(i).lastIndexOf("!!")-1));//记录行内容 截取倒数第二个！！
        	    String account = objects.get(i).substring(objects.get(i).lastIndexOf("!!",objects.get(i).lastIndexOf("!!")-1),objects.get(i).length());//记录行账号
        	    //内容一致且账号不一致
        	    if (compareContext.equals(context)&&!compareAccount.equals(account))
                    return i;
			}
        }
        return -1;
    }
}
