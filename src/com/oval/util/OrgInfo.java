package com.oval.util;

import java.util.Map;

public class OrgInfo {
    private String orgCode;
    private String orgName;
    private String className;
    private Map<String,String> loginParams;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Map<String, String> getLoginParams() {
		return loginParams;
	}
	public void setLoginParams(Map<String, String> loginParams) {
		this.loginParams = loginParams;
	}
    
    
}
