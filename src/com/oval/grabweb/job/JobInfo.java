/**   
* @Company: LuxonData 
* @Title: jobInfo.java 
* @Package com.oval.grabweb.job 
* @Description: TODO
* @author yaokaichang  
* @date 2015-1-27 下午03:15:28 
* @version V1.0   
*/ 
package com.oval.grabweb.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: jobInfo 
 * @Description: TODO 
 * @author yaokaichang 
 * @date 2015-1-27 下午03:15:28  
 */
public class JobInfo {
	private String classname =null;
	private String orgcode =null;
	private String dailyExpr =null;
	private String orgName =null;
	private boolean ismerge  =false;
	private String userNameKey =null;
	private String userNameValue =null;
	private String passwordKey =null;
	private String passwordValue =null;
	private String distIdKey =null;
	private String distIdValue =null;
	private String callIdKey =null;
	private String callIdValue =null;
	private String uuidKey =null;
	private String uuidValue =null;


	/** 
	 * @return userNameKey 
	 */
	public String getUserNameKey() {
		return userNameKey;
	}
	/** 
	 * @param userNameKey 
	 */
	public void setUserNameKey(String userNameKey) {
		this.userNameKey = userNameKey;
	}
	/** 
	 * @return passwordKey 
	 */
	public String getPasswordKey() {
		return passwordKey;
	}
	/** 
	 * @param passwordKey 
	 */
	public void setPasswordKey(String passwordKey) {
		this.passwordKey = passwordKey;
	}
	/** 
	 * @return userNameValue 
	 */
	public String getUserNameValue() {
		return userNameValue;
	}
	/** 
	 * @param userNameValue 
	 */
	public void setUserNameValue(String userNameValue) {
		this.userNameValue = userNameValue;
	}
	/** 
	 * @return passwordValue 
	 */
	public String getPasswordValue() {
		return passwordValue;
	}
	/** 
	 * @param passwordValue 
	 */
	public void setPasswordValue(String passwordValue) {
		this.passwordValue = passwordValue;
	}
	/** 
	 * @return classname 
	 */
	public String getClassname() {
		return classname;
	}
	/** 
	 * @param classname 
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}
	/** 
	 * @return orgcode 
	 */
	public String getOrgcode() {
		return orgcode;
	}
	/** 
	 * @param orgcode 
	 */
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	/** 
	 * @return dailyExpr 
	 */
	public String getDailyExpr() {
		return dailyExpr;
	}
	/** 
	 * @param dailyExpr 
	 */
	public void setDailyExpr(String dailyExpr) {
		this.dailyExpr = dailyExpr;
	}
	/** 
	 * @return orgName 
	 */
	public String getOrgName() {
		return orgName;
	}
	/** 
	 * @param orgName 
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public boolean isIsmerge() {
		return ismerge;
	}
	/** 
	 * @param ismerge 
	 */
	public void setIsmerge(boolean ismerge) {
		this.ismerge = ismerge;
	}
	public String getDistIdKey() {
		return distIdKey;
	}
	public void setDistIdKey(String distIdKey) {
		this.distIdKey = distIdKey;
	}
	public String getDistIdValue() {
		return distIdValue;
	}
	public void setDistIdValue(String distIdValue) {
		this.distIdValue = distIdValue;
	}
	public String getCallIdKey() {
		return callIdKey;
	}
	public void setCallIdKey(String callIdKey) {
		this.callIdKey = callIdKey;
	}
	public String getCallIdValue() {
		return callIdValue;
	}
	public void setCallIdValue(String callIdValue) {
		this.callIdValue = callIdValue;
	}
	public String getUuidKey() {
		return uuidKey;
	}
	public void setUuidKey(String uuidKey) {
		this.uuidKey = uuidKey;
	}
	public String getUuidValue() {
		return uuidValue;
	}
	public void setUuidValue(String uuidValue) {
		this.uuidValue = uuidValue;
	}
	
}
