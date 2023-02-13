/**   
* @Company: LuxonData 
* @Title: SaleInfo.java 
* @Package com.oval.grabweb.bean 
* @Description: TODO
* @author yaokaichang  
* @date 2015-1-28 下午03:30:54 
* @version V1.0   
*/ 
package com.oval.grabweb.bean;

/** 
 * @ClassName: SaleInfo 
 * @Description: TODO 
 * @author yaokaichang 
 * @date 2015-1-28 下午03:30:54  
 */
public class SaleInfo {
	
	protected static String column_speractor = "!!";
	String saleDate ="";//销售日期
	String manufacturers="";//生产厂商
	String customerCode =""; //客户编码
	String customerName =""; //客户名称
	String productCode ="";//产品编码
	String productName ="";//产品名称
	String productSpec =""; //产品规格
	String unit=""; 	//单位
	String productNumber ="";//批号
	String quantity =""; //数量
	String salePrice ="";//单价
	String amount="";//金额
	String type ="";//出货类型
	String ticketNum="";//经销商发货单号
	String customerCity="";//客户城市
	String customerAddress="";//客户地址
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	
	
	
	/** 
	 * @return customerCode 客户编码
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/** 
	 * @param customerCode 客户编码
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return customerName 客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}
	/** 
	 * @param customerName 客户名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return productCode 产品编码
	 */
	public String getProductCode() {
		return productCode;
	}
	/** 
	 * @param productCode 产品编码
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return productName 产品名称
	 */
	public String getProductName() {
		return productName;
	}
	/** 
	 * @param productName 产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return productSpec 产品规格
	 */
	public String getProductSpec() {
		return productSpec;
	}
	/** 
	 * @param productSpec 产品规格
	 */
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return quantity 数量
	 */
	public String getQuantity() {
		return quantity;
	}
	/** 
	 * @param quantity 数量
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return saleDate 销售日期
	 */
	public String getSaleDate() {
		return saleDate;
	}
	/** 
	 * @param saleDate 销售日期
	 */
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return salePrice 销售单价
	 */
	public String getSalePrice() {
		return salePrice;
	}
	/** 
	 * @param salePrice 销售单价
	 */
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return productNumber 批号
	 */
	public String getProductNumber() {
		return productNumber;
	}
	/** 
	 * @param productNumber 批号
	 */
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return manufacturers 生产厂商
	 */
	public String getManufacturers() {
		return manufacturers;
	}
	/** 
	 * @param manufacturers 生产厂商
	 */
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return unit 单位
	 */
	public String getUnit() {
		return unit;
	}
	/** 
	 * @param unit 单位
	 */
	public void setUnit(String unit) {
		this.unit = unit.trim().replace("&nbsp;", "");
	}
	
	public String joinStr(){
		StringBuffer str = new StringBuffer();
		str.append(saleDate).append(column_speractor);//订单时间
		str.append(saleDate).append(column_speractor);//销售日期
		str.append(manufacturers).append(column_speractor);//生产厂商
		str.append(customerCode).append(column_speractor); //客户编码
		str.append(customerName).append(column_speractor); //客户名称
		str.append(productCode).append(column_speractor);//产品编码
		str.append(productName).append(column_speractor);//产品名称
		str.append(productSpec).append(column_speractor); //产品规格
		str.append("").append(column_speractor);
		str.append(unit).append(column_speractor);
		str.append(productNumber).append(column_speractor);
		str.append(quantity).append(column_speractor); 
		str.append(salePrice).append(column_speractor);
		str.append(amount).append(column_speractor);
		str.append(type).append(column_speractor); 
		str.append(customerCity).append(column_speractor);
		str.append(customerAddress).append(column_speractor);
		str.append(ticketNum).append(column_speractor);
		str.append("").append(column_speractor);
		return str.toString();
	}
}
