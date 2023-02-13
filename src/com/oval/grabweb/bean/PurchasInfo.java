package com.oval.grabweb.bean;

public class PurchasInfo {

	protected static String column_speractor = "!!";
	String purchasDate = ""; // 入库时间
	String manufacturers="";
	String supplierCode = "";
	String supplierName = ""; // 供应商名称
	String productCode = ""; // 产品编码
	String productName = ""; // 产品名称
	String productSpec = ""; // 产品规格
	String unit="";
	String productNumber = ""; // 产品批号
	String quantity = ""; // 数量
	String price ="";//单价
	String amount="";
	String type ="";
	String ticketNum="";
	String invalidDate="";//有效期

	/**
	 * @return purchasDate 入库时间
	 */
	public String getPurchasDate() {
		return purchasDate;
	}

	/**
	 * @param purchasDate
	 *            入库时间
	 */
	public void setPurchasDate(String purchasDate) {
		this.purchasDate = purchasDate.trim().replace("&nbsp;", "");
	}

	/**
	 * @return supplierName 供应商名称
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName
	 *            供应商名称
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName.trim().replace("&nbsp;", "");
	}

	/**
	 * @return productCode 产品编码
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            产品编码
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
	 * @param productName
	 *            产品名称
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
	 * @param productSpec
	 *            产品规格
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
	 * @param quantity
	 *            数量
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
	 * @param productNumber
	 *            批号
	 */
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber.trim().replace("&nbsp;", "");
	}



	public String joinStr() {
		StringBuffer str = new StringBuffer();
//		入库时间	生产厂家	供应商代码	供应商名称	产品代码	产品名称	产品规格	剂型	单位	批号	数量	含税单价	含税金额	进货类型	供应商出库单号	有效期	开票时间
		str.append(purchasDate).append(column_speractor);
		str.append(manufacturers).append(column_speractor);
		str.append(supplierCode).append(column_speractor);
		str.append(supplierName).append(column_speractor);// 供应商
		str.append(productCode).append(column_speractor); // 产品编码
		str.append(productName).append(column_speractor);// 产品名称
		str.append(productSpec).append(column_speractor); // 产品规格
		str.append("").append(column_speractor); // 剂型
		str.append(unit).append(column_speractor);
		str.append(productNumber).append(column_speractor);// 批号
		str.append(quantity).append(column_speractor); // 数量
		str.append(price).append(column_speractor);
		str.append(amount).append(column_speractor);
		str.append(type).append(column_speractor);
		str.append(ticketNum).append(column_speractor);
		str.append(invalidDate).append(column_speractor);
		str.append("").append(column_speractor);
		return str.toString();
	}

	public String getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

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

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}
}
