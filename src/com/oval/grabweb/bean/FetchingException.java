package com.oval.grabweb.bean;

public class FetchingException {
	
	private String fetchingSuccessInfo="取数成功,库存和流向数据都已正常取数完成";
	private String fetchingSuccessType="消息";
	private String fetchingSuccessCode="001";
	
	private String loginExceptionInfo="用户登录异常,请检查网页是否肯以正常登录";
	private String loginExceptionType="错误";
	private String loginExceptionCode="002";
	
	private String stockFetchingExceptionInfo="库存取数异常,该异常请联系开发人员";
	private String stockFetchingExceptionType="错误";
	private String stockFetchingExceptionCode="003";
	
	private String saleFetchingExceptionInfo="流向取数异常,该异常请联系开发人员";
	private String saleFetchingExceptionType="错误";
	private String saleFetchingExceptionCode="004";
	
	private String stockDataIsNullInfo="库存取得数据结果为空";
	private String stockDataIsNullType="消息";
	private String stockDataIsNullCode="005";
	
	private String saleDataIsNullInfo="流向取得数据结果为空";
	private String saleDataIsNullType="消息";
	private String saleDataIsNullCode="006";
	
	public String getFetchingSuccessInfo() {
		return fetchingSuccessInfo;
	}
	public String getFetchingSuccessType() {
		return fetchingSuccessType;
	}
	public String getFetchingSuccessCode() {
		return fetchingSuccessCode;
	}
	public String getLoginExceptionInfo() {
		return loginExceptionInfo;
	}
	public String getLoginExceptionType() {
		return loginExceptionType;
	}
	public String getLoginExceptionCode() {
		return loginExceptionCode;
	}
	public String getStockFetchingExceptionInfo() {
		return stockFetchingExceptionInfo;
	}
	public String getStockFetchingExceptionType() {
		return stockFetchingExceptionType;
	}
	public String getStockFetchingExceptionCode() {
		return stockFetchingExceptionCode;
	}
	public String getSaleFetchingExceptionInfo() {
		return saleFetchingExceptionInfo;
	}
	public String getSaleFetchingExceptionType() {
		return saleFetchingExceptionType;
	}
	public String getSaleFetchingExceptionCode() {
		return saleFetchingExceptionCode;
	}
	public String getStockDataIsNullInfo() {
		return stockDataIsNullInfo;
	}
	public String getStockDataIsNullType() {
		return stockDataIsNullType;
	}
	public String getStockDataIsNullCode() {
		return stockDataIsNullCode;
	}
	public String getSaleDataIsNullInfo() {
		return saleDataIsNullInfo;
	}
	public String getSaleDataIsNullType() {
		return saleDataIsNullType;
	}
	public String getSaleDataIsNullCode() {
		return saleDataIsNullCode;
	}
}
