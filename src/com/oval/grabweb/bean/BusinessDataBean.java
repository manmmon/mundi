package com.oval.grabweb.bean;

import java.io.Serializable;

public class BusinessDataBean implements Serializable {
    protected static String column_speractor = "!!";

    /**
     * 采购取数模板(PD) ｜｜ 销售取数模板(SD)
     */
    public static class PurchaseInfo{
        //入库时间	生产厂家	供应商代码	供应商名称	产品代码	产品名称	产品规格	剂型	单位	批号	数量	含税单价	含税金额	进货类型	供应商入库单号	有效期	开票时间
        String purchasDate =""; //入库时间
        String factoryname=""; //生产厂家
        String supplierId =""; //供应商代码
        String supplierName =""; //供应商名称
        String proId = ""; //产品代码
        String proName ="";//产品名称
        String proSpec =""; //产品规格
        String dosageForm = ""; //剂型
        String proUnit = ""; //产品单位
        String proPh ="";//产品批号
        String proQty =""; //库存数量
        String taxPrice = "";//含税单价
        String taxMoney = "";//含税金额
        String purchaseType = "";//进货类型
        String supplierWarehousNo = ""; //供应商入库单号
        String effectiveDate=""; //产品有效期
        String invoiceTime = "";//开票时间

        public String getPurchasDate() {
            return purchasDate;
        }

        public void setPurchasDate(String purchasDate) {
            this.purchasDate = purchasDate;
        }

        public String getFactoryname() {
            return factoryname;
        }

        public void setFactoryname(String factoryname) {
            this.factoryname = factoryname;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getProSpec() {
            return proSpec;
        }

        public void setProSpec(String proSpec) {
            this.proSpec = proSpec;
        }

        public String getDosageForm() {
            return dosageForm;
        }

        public void setDosageForm(String dosageForm) {
            this.dosageForm = dosageForm;
        }

        public String getProUnit() {
            return proUnit;
        }

        public void setProUnit(String proUnit) {
            this.proUnit = proUnit;
        }

        public String getProPh() {
            return proPh;
        }

        public void setProPh(String proPh) {
            this.proPh = proPh;
        }

        public String getProQty() {
            return proQty;
        }

        public void setProQty(String proQty) {
            this.proQty = proQty;
        }

        public String getTaxPrice() {
            return taxPrice;
        }

        public void setTaxPrice(String taxPrice) {
            this.taxPrice = taxPrice;
        }

        public String getTaxMoney() {
            return taxMoney;
        }

        public void setTaxMoney(String taxMoney) {
            this.taxMoney = taxMoney;
        }

        public String getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(String purchaseType) {
            this.purchaseType = purchaseType;
        }

        public String getSupplierWarehousNo() {
            return supplierWarehousNo;
        }

        public void setSupplierWarehousNo(String supplierWarehousNo) {
            this.supplierWarehousNo = supplierWarehousNo;
        }

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public String getInvoiceTime() {
            return invoiceTime;
        }

        public void setInvoiceTime(String invoiceTime) {
            this.invoiceTime = invoiceTime;
        }

        public String joinStr(){
            StringBuffer str = new StringBuffer();
            str.append(purchasDate).append(column_speractor);
            str.append(factoryname).append(column_speractor);
            str.append(supplierId).append(column_speractor);
            str.append(supplierName).append(column_speractor);
            str.append(proId).append(column_speractor);
            str.append(proName).append(column_speractor);
            str.append(proSpec).append(column_speractor);
            str.append(dosageForm).append(column_speractor);
            str.append(proUnit).append(column_speractor);
            str.append(proPh).append(column_speractor);
            str.append(proQty).append(column_speractor);
            str.append(taxPrice).append(column_speractor);
            str.append(taxMoney).append(column_speractor);
            str.append(purchaseType).append(column_speractor);
            str.append(supplierWarehousNo).append(column_speractor);
            str.append(effectiveDate).append(column_speractor);
            str.append(invoiceTime);
            return str.toString();
        }
    }

    /**
     * 销售取数模板(SD)
     */
    public static class SaleInfo{
        //订单时间	出库时间	生产厂家	客户代码	客户名称	产品代码	产品名称	产品规格	剂型	单位	批号	数量	含税单价	含税金额	出货类型	客户城市	客户地址	经销商发货单号	开票时间
        String purchasDate =""; //订单时间
        String deliveryTime = "";//出库时间
        String factoryname=""; //生产厂家
        String customerNo =""; //客户代码
        String customerName =""; //客户名称
        String proId = ""; //产品代码
        String proName ="";//产品名称
        String proSpec =""; //产品规格
        String dosageForm = ""; //剂型
        String proUnit = ""; //产品单位
        String proPh ="";//产品批号
        String proQty =""; //数量
        String taxPrice = "";//含税单价
        String taxMoney = "";//含税金额
        String purchaseType = "";//出货类型
        String customerCity = "";//客户城市
        String customerAddress = "";//客户地址
        String dealerDeliveryNo = ""; //经销商发货单号
        String invoiceTime = "";//开票时间

        public String getPurchasDate() {
            return purchasDate;
        }

        public void setPurchasDate(String purchasDate) {
            this.purchasDate = purchasDate;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getFactoryname() {
            return factoryname;
        }

        public void setFactoryname(String factoryname) {
            this.factoryname = factoryname;
        }

        public String getCustomerNo() {
            return customerNo;
        }

        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getProSpec() {
            return proSpec;
        }

        public void setProSpec(String proSpec) {
            this.proSpec = proSpec;
        }

        public String getDosageForm() {
            return dosageForm;
        }

        public void setDosageForm(String dosageForm) {
            this.dosageForm = dosageForm;
        }

        public String getProUnit() {
            return proUnit;
        }

        public void setProUnit(String proUnit) {
            this.proUnit = proUnit;
        }

        public String getProPh() {
            return proPh;
        }

        public void setProPh(String proPh) {
            this.proPh = proPh;
        }

        public String getProQty() {
            return proQty;
        }

        public void setProQty(String proQty) {
            this.proQty = proQty;
        }

        public String getTaxPrice() {
            return taxPrice;
        }

        public void setTaxPrice(String taxPrice) {
            this.taxPrice = taxPrice;
        }

        public String getTaxMoney() {
            return taxMoney;
        }

        public void setTaxMoney(String taxMoney) {
            this.taxMoney = taxMoney;
        }

        public String getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(String purchaseType) {
            this.purchaseType = purchaseType;
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

        public String getDealerDeliveryNo() {
            return dealerDeliveryNo;
        }

        public void setDealerDeliveryNo(String dealerDeliveryNo) {
            this.dealerDeliveryNo = dealerDeliveryNo;
        }

        public String getInvoiceTime() {
            return invoiceTime;
        }

        public void setInvoiceTime(String invoiceTime) {
            this.invoiceTime = invoiceTime;
        }

        public String joinStr(){
            StringBuffer str = new StringBuffer();
            str.append(purchasDate).append(column_speractor);
            str.append(deliveryTime).append(column_speractor);
            str.append(factoryname).append(column_speractor);
            str.append(customerNo).append(column_speractor);
            str.append(customerName).append(column_speractor);
            str.append(proId).append(column_speractor);
            str.append(proName).append(column_speractor);
            str.append(proSpec).append(column_speractor);
            str.append(dosageForm).append(column_speractor);
            str.append(proUnit).append(column_speractor);
            str.append(proPh).append(column_speractor);
            str.append(proQty).append(column_speractor);
            str.append(taxPrice).append(column_speractor);
            str.append(taxMoney).append(column_speractor);
            str.append(purchaseType).append(column_speractor);
            str.append(customerCity).append(column_speractor);
            str.append(customerAddress).append(column_speractor);
            str.append(dealerDeliveryNo).append(column_speractor);
            str.append(invoiceTime).append(column_speractor);
            return str.toString();
        }
    }

    /**
     * 库存取数模板(ID)
     */
    public static class StockInfo{
        //库存时间	生产厂家	供应商代码	供应商名称	产品代码	产品名称	产品规格	产品剂型	单位	批号	数量	单价	金额	库存状态	入库时间
        String inventoryDate =""; //库存日期  指定为查询的日期
        String factoryname=""; //生产厂家
        String supplierId =""; //供应商代码
        String supplierName =""; //供应商名称
        String proId = ""; //产品代码
        String proName ="";//产品名称
        String proSpec =""; //产品规格
        String dosageForm = ""; //剂型
        String proQty =""; //库存数量
        String proPh ="";//产品批号
        String proUnit = ""; //产品单位
        String taxPrice = "";//含税单价
        String taxMoney = "";//含税金额
        String inventoryStatus = ""; //库存状态
        String purchasDate = ""; //入库时间

        public String getInventoryDate() {
            return inventoryDate;
        }

        public void setInventoryDate(String inventoryDate) {
            this.inventoryDate = inventoryDate;
        }

        public String getFactoryname() {
            return factoryname;
        }

        public void setFactoryname(String factoryname) {
            this.factoryname = factoryname;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getProSpec() {
            return proSpec;
        }

        public void setProSpec(String proSpec) {
            this.proSpec = proSpec;
        }

        public String getDosageForm() {
            return dosageForm;
        }

        public void setDosageForm(String dosageForm) {
            this.dosageForm = dosageForm;
        }

        public String getProQty() {
            return proQty;
        }

        public void setProQty(String proQty) {
            this.proQty = proQty;
        }

        public String getProPh() {
            return proPh;
        }

        public void setProPh(String proPh) {
            this.proPh = proPh;
        }

        public String getProUnit() {
            return proUnit;
        }

        public void setProUnit(String proUnit) {
            this.proUnit = proUnit;
        }

        public String getTaxPrice() {
            return taxPrice;
        }

        public void setTaxPrice(String taxPrice) {
            this.taxPrice = taxPrice;
        }

        public String getTaxMoney() {
            return taxMoney;
        }

        public void setTaxMoney(String taxMoney) {
            this.taxMoney = taxMoney;
        }

        public String getPurchasDate() {
            return purchasDate;
        }

        public void setPurchasDate(String purchasDate) {
            this.purchasDate = purchasDate;
        }

        public String getInventoryStatus() {
            return inventoryStatus;
        }

        public void setInventoryStatus(String inventoryStatus) {
            this.inventoryStatus = inventoryStatus;
        }

        public String joinStr(){
            //库存日期 产品代码 产品名称 产品规格 库存数量 产品批号 产品单位 产品有效期
            StringBuffer str = new StringBuffer();
            str.append(inventoryDate).append(column_speractor);
            str.append(factoryname).append(column_speractor);
            str.append(supplierId).append(column_speractor);
            str.append(supplierName).append(column_speractor);
            str.append(proId).append(column_speractor);
            str.append(proName).append(column_speractor);
            str.append(proSpec).append(column_speractor);
            str.append(dosageForm).append(column_speractor);
            str.append(proUnit).append(column_speractor);
            str.append(proPh).append(column_speractor);
            str.append(proQty).append(column_speractor);
            str.append(taxPrice).append(column_speractor);
            str.append(taxMoney).append(column_speractor);
            str.append(inventoryStatus).append(column_speractor);
            str.append(purchasDate).append(column_speractor);
            return str.toString();
        }
    }

    }
