/**   
* @Company: LuxonData 
* @Title: StockInfo.java 
* @Package com.oval.grabweb.bean 
* @Description: TODO
* @author yaokaichang  
* @date 2015-1-28 下午03:30:39 
* @version V1.0   
*/ 
package com.oval.grabweb.bean;

/** 
 * @ClassName: StockInfo 
 * @Description: TODO 
 * @author yaokaichang 
 * @date 2015-1-28 下午03:30:39  
 */
public class StockInfo {
	protected static String column_speractor = "!!";
	String inventoryDate =""; //库存日期
	String manufacturers="";
	String productCode =""; //产品编码
	String productName =""; //产品名称
	String productSpec =""; //产品规格
	String unit=""; //单位
	String productNumber =""; //批号
	String quantity =""; //数量
	String price = "";//d单价
	String amount ="";//金额
	String invState="";//库存状态
	String effectiveDate=""; //有效期
	String supplierName="";
	String supplierCode="";
	String warehousingDate="";//入库时间

	/** 
	 * 
	 * @return inventoryDate  库存日期
	 */
	public String getInventoryDate() {
		return inventoryDate;
	}
	/** 
	 * 
	 * @param inventoryDate 库存日期
	 */
	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate.trim().replace("&nbsp;", "");
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
	 * @return productNumber 产品批号
	 */
	public String getProductNumber() {
		return productNumber;
	}
	/** 
	 * @param productNumber 产品批号
	 */
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber.trim().replace("&nbsp;", "");
	}
	/** 
	 * @return unit 计量单位
	 */
	public String getUnit() {
		return unit;
	}
	/** 
	 * @param unit 计量单位
	 */
	public void setUnit(String unit) {
		this.unit = unit.trim().replace("&nbsp;", "");
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
	
	public String getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
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
	public String getInvState() {
		return invState;
	}
	public void setInvState(String invState) {
		this.invState = invState;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getWarehousingDate() {
		return warehousingDate;
	}
	public void setWarehousingDate(String warehousingDate) {
		this.warehousingDate = warehousingDate;
	}
	public String joinStr(){
		StringBuffer str = new StringBuffer();
		str.append(inventoryDate).append(column_speractor);//库存日期
		str.append(manufacturers).append(column_speractor);
		str.append(supplierCode).append(column_speractor);
		str.append(supplierName).append(column_speractor);
		str.append(productCode).append(column_speractor);//产品编码
		str.append(productName).append(column_speractor);//产品名称
		str.append(productSpec).append(column_speractor);//产品规格
		str.append("").append(column_speractor);
		str.append(unit).append(column_speractor);//计量单位
		str.append(productNumber).append(column_speractor);//产品批号
		str.append(quantity).append(column_speractor);//数量
		str.append(price).append(column_speractor);
		str.append(amount).append(column_speractor);
		str.append(invState).append(column_speractor);
		str.append("").append(column_speractor);//入库时间
		return str.toString();
	}
}
