package com.oval.grabweb.bean;

public class PurchaseInfo {
	
	protected static String column_speractor = "!!";
	String warehousingDate =""; //入库时间
	String supplierName =""; //供应商名称
	String productName ="";//产品名称
	String productSpec =""; //产品规格
	String quantity =""; //数量
	String productNumber ="";//批号
	String effectiveDate=""; //有效日期
	
	public String getWarehousingDate() {
		return warehousingDate;
	}
	public void setWarehousingDate(String warehousingDate) {
		this.warehousingDate = warehousingDate.trim().replace("&nbsp;", "");
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName.trim().replace("&nbsp;", "");
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
	 * @return effectiveDate 有效日期
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/** 
	 * @param effectiveDate 有效日期
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate.trim().replace("&nbsp;", "");
	}
	
	public String joinStr(){
		StringBuffer str = new StringBuffer();
		str.append(warehousingDate).append(column_speractor);
		str.append(supplierName).append(column_speractor);
		str.append(productName).append(column_speractor);
		str.append(productSpec).append(column_speractor);
		str.append(quantity).append(column_speractor);
		str.append(productNumber).append(column_speractor);
		str.append(effectiveDate).append(column_speractor);
		return str.toString();
	}
}
