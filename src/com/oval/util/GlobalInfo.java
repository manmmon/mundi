package com.oval.util;

import java.util.ArrayList;
import java.util.List;

public class GlobalInfo {
	public static  String rootPath;
	public static  String verifyStorePath;

	public static  String beginDate = DateUtil.getBeforeDayAgainstToday(45,"yyyy-MM-dd");
	public static  String endDate = DateUtil.getBeforeDayAgainstToday(1,"yyyy-MM-dd");
	
//	public static  String beginDate = "2011-03-01";
//	public static  String endDate = "2011-03-31";

	List<OrgInfo> orgInfos = new ArrayList<OrgInfo>();
}
