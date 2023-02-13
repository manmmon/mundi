package com.oval.grabweb.action;

import com.oval.util.PropertiesUtil;

/**
 *	全局常量
 * @author eli
 *
 */
public interface Constant {

	String STOCK = "ID";
	String STOCKAPPID="54";

	String SALE = "SD";
	String SALEAPPID="52";

	String PURCHASE = "PD";
	String PURCHASEAPPID="53";

	int PAGESIZE=1000;
	int beforedays=120;
	int endbeforedays=1;

	int rolldays=40;
	
	String DIR_AUTO = PropertiesUtil.getString("DIR_AUTO","paras") ;
	
	String DIR_PREFIX = PropertiesUtil.getString("DIR_PREFIX","paras") ;
	String BAK_PREFIX=PropertiesUtil.getString("BAK_PREFIX","paras");
	
//	String DIR_PREFIX_KW = "E:/SFTPDATAAuto/auto/kw/" ;
//	String BAK_PREFIX_KW = "E:/SFTPDATA/Shaphar/web/kw/" ;
//	String DIR_PREFIX_BY = "E:/SFTPDATAAuto/auto/by/" ;
//	String BAK_PREFIX_BY = "E:/SFTPDATA/Shaphar/web/by/" ;

	String TEMP_PREFIX=PropertiesUtil.getString("TEMP_PREFIX","paras");

	
	String FILE_LASTFIX="_DD_SPH_ALL_00";
	
	String FILE_LASTFIX_KW="_DD_KW_ALL_00";
	
	String FILE_LASTFIX_BY="_DD_BY_ALL_00";
	
	String FILE_LASTFIX_ZD="_DD_ZDJK_ALL_01";
	
	String FILE_LASTFIX_MD ="_DD_Mundi_ALL_00";

	String FILENAME_REGEX="";

	String FILE_NAME = DIR_PREFIX + FILENAME_REGEX;

	String BAKFILE_NAME = BAK_PREFIX + FILENAME_REGEX;
}
