package com.oval.grabweb.bean;

import com.oval.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZDBussinessDataBean {

    protected static String column_speractor = "!!";

    /**
     * 采购取数模板(PD)
     */
    public static class PurchaseInfo{
        //入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号 批准文号
        String purchasDate =""; //入库时间
        String factoryname=""; //生产厂家
        String supplierId =""; //供应商代码
        String supplierName =""; //供应商名称
        String proId = ""; //产品代码
        String proName ="";//产品名称
        String proSpec =""; //产品规格
        String dosageForm = ""; //剂型
        String proUnit = ""; //单位
        String proPh ="";//批号
        String proQty =""; //数量
        String taxPrice = "";//含税单价
        String taxMoney = "";//含税金额
        String purchaseType = "";//进货类型
        String supplierWarehousNo = ""; //供应商出库单号
        String approvalnum = ""; //批准文号


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

        public String getApprovalnum() {
            return approvalnum;
        }

        public void setApprovalnum(String approvalnum) {
            this.approvalnum = approvalnum;
        }

        public static List<String> createPurchas() {
            List<String> list = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();

            sb.append("入库时间").append(column_speractor);// 入库时间
            sb.append("生产厂家").append(column_speractor);
            sb.append("供应商代码").append(column_speractor);
            sb.append("供应商名称").append(column_speractor);// 供应商名称
            sb.append("产品代码").append(column_speractor);
            sb.append("产品名称").append(column_speractor);// 产品名称
            sb.append("产品规格").append(column_speractor);// 产品规格
            sb.append("剂型").append(column_speractor);
            sb.append("单位").append(column_speractor);
            sb.append("批号").append(column_speractor);
            sb.append("数量").append(column_speractor);//
            sb.append("含税单价").append(column_speractor);
            sb.append("含税金额").append(column_speractor);
            sb.append("进货类型").append(column_speractor);//
            sb.append("供应商出库单号").append(column_speractor);
            sb.append("批准文号");
            list.add(sb.toString());

            return list;
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
            str.append(approvalnum);
            return str.toString();
        }
    }

    /**
     * 销售取数模板(SD)
     */
    public static class SaleInfo{
        //销售时间 生产厂家  客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 出货类型 客户城市 客户地址 经销商发货单号 批准文号 备注
        String saleTime =""; //销售时间
        String factoryname=""; //生产厂家
        String customerNo =""; //客户代码
        String customerName =""; //客户名称
        String proId = ""; //产品代码
        String proName ="";//产品名称
        String proSpec =""; //产品规格
        String dosageForm = ""; //剂型
        String proUnit = ""; //单位
        String proPh ="";//批号
        String proQty =""; //数量
        String taxPrice = "";//含税单价
        String taxMoney = "";//含税金额
        String purchaseType = "";//出货类型
        String customerCity = "";//客户城市
        String customerAddress = "";//客户地址
        String dealerDeliveryNo = ""; //经销商发货单号
        String approvalnum = ""; //批准文号
        String remark = "";//备注

        public String getSaleTime() {
            return saleTime;
        }

        public void setSaleTime(String saleTime) {
            this.saleTime = saleTime;
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

        public String getApprovalnum() {
            return approvalnum;
        }

        public void setApprovalnum(String approvalnum) {
            this.approvalnum = approvalnum;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public static List<String> createSale() {
            List<String> list = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();

            sb.append("销售时间").append(column_speractor);
            sb.append("生产厂家").append(column_speractor);
            sb.append("客户代码").append(column_speractor);
            sb.append("客户名称").append(column_speractor);//
            sb.append("产品代码").append(column_speractor);
            sb.append("产品名称").append(column_speractor);//
            sb.append("产品规格").append(column_speractor);//
            sb.append("剂型").append(column_speractor);
            sb.append("单位").append(column_speractor);
            sb.append("批号").append(column_speractor);
            sb.append("数量").append(column_speractor);//
            sb.append("含税单价").append(column_speractor);
            sb.append("含税金额").append(column_speractor);
            sb.append("出货类型").append(column_speractor);//
            sb.append("客户城市").append(column_speractor);
            sb.append("客户地址").append(column_speractor);//
            sb.append("经销商发货单号").append(column_speractor);
            sb.append("批准文号").append(column_speractor);
            sb.append("备注");
            list.add(sb.toString());
            return list;
        }

        public String joinStr(){
            StringBuffer str = new StringBuffer();
            str.append(saleTime).append(column_speractor);
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
            str.append(approvalnum).append(column_speractor);
            str.append(remark).append(column_speractor);
            return str.toString();
        }
    }

    /**
     * 库存取数模板(ID)
     */
    public static class StockInfo{
        //库存时间	生产厂家	供应商代码	供应商名称	产品代码	产品名称	产品规格	剂型	单位	批号	生产日期	失效日期	数量	库存状态	入库时间  批准文号
        String inventoryDate =""; //库存日期  指定为查询的日期
        String factoryname=""; //生产厂家
        String supplierId =""; //供应商代码
        String supplierName =""; //供应商名称
        String proId = ""; //产品代码
        String proName ="";//产品名称
        String proSpec =""; //产品规格
        String dosageForm = ""; //剂型
        String proUnit = ""; //单位
        String proPh ="";//批号
        String manufactureDate=""; //生产日期
        String expiryDate=""; //失效日期
        String proQty =""; //数量
        String inventoryStatus = ""; //库存状态
        String purchasDate = ""; //入库时间
        String approvalnum = ""; //批准文号

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

        public String getManufactureDate() {
            return manufactureDate;
        }

        public void setManufactureDate(String manufactureDate) {
            this.manufactureDate = manufactureDate;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getApprovalnum() {
            return approvalnum;
        }

        public void setApprovalnum(String approvalnum) {
            this.approvalnum = approvalnum;
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

        public static List<String> createStock() {
            List<String> list = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();
            sb.append("库存时间").append(column_speractor);//
            sb.append("生产厂家").append(column_speractor);
            sb.append("供应商代码").append(column_speractor);
            sb.append("供应商名称").append(column_speractor);//
            sb.append("产品代码").append(column_speractor);
            sb.append("产品名称").append(column_speractor);//
            sb.append("产品规格").append(column_speractor);//
            sb.append("剂型").append(column_speractor);
            sb.append("单位").append(column_speractor);
            sb.append("批号").append(column_speractor);
            sb.append("生产日期").append(column_speractor);//
            sb.append("失效日期").append(column_speractor);//
            sb.append("数量").append(column_speractor);//
            sb.append("库存状态").append(column_speractor);//
            sb.append("入库时间").append(column_speractor);
            sb.append("批准文号");
            list.add(sb.toString());
            return list;

        }

        public String joinStr(){
            //库存时间	生产厂家	供应商代码	供应商名称	产品代码	产品名称	产品规格	剂型	单位	批号	生产日期	失效日期	数量	库存状态	入库时间  批准文号
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
            str.append(manufactureDate).append(column_speractor);
            str.append(expiryDate).append(column_speractor);
            str.append(proQty).append(column_speractor);
            str.append(inventoryStatus).append(column_speractor);
            str.append(purchasDate).append(column_speractor);
            str.append(approvalnum);
            return str.toString();
        }

    }
    /**
     * 数据处理转换
     * @param rowMap
     * @param resultList
     * @param type 1入库2销售3库存
     */
    public static List<String> operationData(Map<String,Integer> rowMap,List<List<String>> resultList,int type){
        List<String> data = new ArrayList<>();
        if(resultList.size() > 1){
            //第一行表头
            if(type == 1){
                for (int i = 1; i < resultList.size(); i++) {
                    ZDBussinessDataBean.PurchaseInfo info = new ZDBussinessDataBean.PurchaseInfo();
                    if(rowMap.get("purchasDate") != null){
                        String purchasDate = resultList.get(i).get(rowMap.get("purchasDate"));
                        if(StringUtils.isNotBlank(purchasDate)){
                            if(purchasDate.length() > 10){
                                purchasDate = purchasDate.substring(0,10);
                            }
                            purchasDate = purchasDate.replaceAll("/","-");
                        }
                        info.setPurchasDate(purchasDate);
                    }
                    if(rowMap.get("factoryname") != null){
                        info.setFactoryname(resultList.get(i).get(rowMap.get("factoryname")));
                    }
                    if(rowMap.get("supplierId") != null){
                        info.setSupplierId(resultList.get(i).get(rowMap.get("supplierId")));
                    }
                    if(rowMap.get("supplierName") != null){
                        info.setSupplierName(resultList.get(i).get(rowMap.get("supplierName")));
                    }
                    if(rowMap.get("proId") != null){
                        info.setProId(resultList.get(i).get(rowMap.get("proId")));
                    }
                    if(rowMap.get("proName") != null){
                        info.setProName(resultList.get(i).get(rowMap.get("proName")));
                    }
                    if(rowMap.get("proSpec") != null){
                        info.setProSpec(resultList.get(i).get(rowMap.get("proSpec")));
                    }
                    if(rowMap.get("dosageForm") != null){
                        info.setDosageForm(resultList.get(i).get(rowMap.get("dosageForm")));
                    }
                    if(rowMap.get("proUnit") != null){
                        info.setProUnit(resultList.get(i).get(rowMap.get("proUnit")));
                    }else{
                        if(StringUtils.isNotBlank(info.getProSpec())){
                            info.setProUnit(info.getProSpec().substring(info.getProSpec().length()-1));
                        }
                    }

                    if(rowMap.get("proPh") != null){
                        info.setProPh(resultList.get(i).get(rowMap.get("proPh")));
                    }
                    if(rowMap.get("proQty") != null){
                        String qty = resultList.get(i).get(rowMap.get("proQty"));
                        if(qty.indexOf(".") > 0){
                            info.setProQty(qty.substring(0,qty.indexOf(".")));
                        }else{
                            info.setProQty(qty);
                        }
                    }
                    if(rowMap.get("taxPrice") != null){
                        info.setTaxPrice(resultList.get(i).get(rowMap.get("taxPrice")));
                    }
                    if(rowMap.get("taxMoney") != null){
                        info.setTaxMoney(resultList.get(i).get(rowMap.get("taxMoney")));
                    }
                    if(rowMap.get("purchaseType") != null){
                        info.setPurchaseType(resultList.get(i).get(rowMap.get("purchaseType")));
                    }
                    if(rowMap.get("supplierWarehousNo") != null){
                        info.setSupplierWarehousNo(resultList.get(i).get(rowMap.get("supplierWarehousNo")));
                    }
                    if(rowMap.get("approvalnum") != null){
                        info.setApprovalnum(resultList.get(i).get(rowMap.get("approvalnum")));
                    }
                    data.add(info.joinStr());
                }
            }else if(type == 2){
                for (int i = 1; i < resultList.size(); i++) {
                    ZDBussinessDataBean.SaleInfo info = new ZDBussinessDataBean.SaleInfo();
                    if(rowMap.get("saleTime") != null){
                        String saleTime = resultList.get(i).get(rowMap.get("saleTime"));
                        if(StringUtils.isNotBlank(saleTime)){
                            if(saleTime.length() > 10){
                                saleTime = saleTime.substring(0,10);
                            }else if(saleTime.length() == 8){
                                saleTime = saleTime.substring(0,4)+"-"+saleTime.substring(4,6)+"-"+saleTime.substring(6,8);
                            }
                            saleTime = saleTime.replaceAll("/","-");
                        }
                        info.setSaleTime(saleTime);
                    }
                    if(rowMap.get("factoryname") != null){
                        info.setFactoryname(resultList.get(i).get(rowMap.get("factoryname")));
                    }
                    if(rowMap.get("customerNo") != null){
                        info.setCustomerNo(resultList.get(i).get(rowMap.get("customerNo")));
                    }
                    if(rowMap.get("customerName") != null){
                        info.setCustomerName(resultList.get(i).get(rowMap.get("customerName")));
                    }
                    if(rowMap.get("proId") != null){
                        info.setProId(resultList.get(i).get(rowMap.get("proId")));
                    }
                    if(rowMap.get("proName") != null){
                        info.setProName(resultList.get(i).get(rowMap.get("proName")));
                    }
                    if(rowMap.get("proSpec") != null){
                        info.setProSpec(resultList.get(i).get(rowMap.get("proSpec")));
                    }
                    if(rowMap.get("dosageForm") != null){
                        info.setDosageForm(resultList.get(i).get(rowMap.get("dosageForm")));
                    }
                    if(rowMap.get("proUnit") != null){
                        info.setProUnit(resultList.get(i).get(rowMap.get("proUnit")));
                    }else{
                        if(StringUtils.isNotBlank(info.getProSpec())){
                            info.setProUnit(info.getProSpec().substring(info.getProSpec().length()-1));
                        }
                    }
                    if(rowMap.get("proPh") != null){
                        info.setProPh(resultList.get(i).get(rowMap.get("proPh")));
                    }
                    if(rowMap.get("proQty") != null){
                        String qty = resultList.get(i).get(rowMap.get("proQty"));
                        if(qty.indexOf(".") > 0){
                            info.setProQty(qty.substring(0,qty.indexOf(".")));
                        }else{
                            info.setProQty(qty);
                        }
                    }
                    if(rowMap.get("taxPrice") != null){
                        info.setTaxPrice(resultList.get(i).get(rowMap.get("taxPrice")));
                    }
                    if(rowMap.get("taxMoney") != null){
                        info.setTaxMoney(resultList.get(i).get(rowMap.get("taxMoney")));
                    }
                    if(rowMap.get("purchaseType") != null){
                        info.setPurchaseType(resultList.get(i).get(rowMap.get("purchaseType")));
                    }
                    if(rowMap.get("customerCity") != null){
                        info.setCustomerCity(resultList.get(i).get(rowMap.get("customerCity")));
                    }
                    if(rowMap.get("customerAddress") != null){
                        info.setCustomerAddress(resultList.get(i).get(rowMap.get("customerAddress")));
                    }
                    if(rowMap.get("dealerDeliveryNo") != null){
                        info.setDealerDeliveryNo(resultList.get(i).get(rowMap.get("dealerDeliveryNo")));
                    }

                    if(rowMap.get("approvalnum") != null){
                        info.setApprovalnum(resultList.get(i).get(rowMap.get("approvalnum")));
                    }
                    if(rowMap.get("remark") != null){
                        info.setRemark(resultList.get(i).get(rowMap.get("remark")));
                    }
                    data.add(info.joinStr());
                }
            }else if(type == 3){
                for (int i = 1; i < resultList.size(); i++) {
                    ZDBussinessDataBean.StockInfo info = new ZDBussinessDataBean.StockInfo();
                    //库存日期取当天
                    String date = DateUtil.getToday("yyyy-MM-dd");
                    if(rowMap.get("inventoryDate") != null){
                        String inventoryDate = resultList.get(i).get(rowMap.get("inventoryDate"));
                        if(StringUtils.isNotBlank(inventoryDate)){
                            if(inventoryDate.length() > 10){
                                inventoryDate = inventoryDate.substring(0,10);
                            }
                            inventoryDate = inventoryDate.replaceAll("/","-");
                        }
                        info.setInventoryDate(inventoryDate);
                    }else{
                        info.setInventoryDate(date);
                    }
                    if(rowMap.get("factoryname") != null){
                        info.setFactoryname(resultList.get(i).get(rowMap.get("factoryname")));
                    }
                    if(rowMap.get("supplierId") != null){
                        info.setSupplierId(resultList.get(i).get(rowMap.get("supplierId")));
                    }
                    if(rowMap.get("supplierName") != null){
                        info.setSupplierName(resultList.get(i).get(rowMap.get("supplierName")));
                    }
                    if(rowMap.get("proId") != null){
                        info.setProId(resultList.get(i).get(rowMap.get("proId")));
                    }
                    if(rowMap.get("proName") != null){
                        info.setProName(resultList.get(i).get(rowMap.get("proName")));
                    }
                    if(rowMap.get("proSpec") != null){
                        info.setProSpec(resultList.get(i).get(rowMap.get("proSpec")));
                    }
                    if(rowMap.get("dosageForm") != null){
                        info.setDosageForm(resultList.get(i).get(rowMap.get("dosageForm")));
                    }
                    if(rowMap.get("proUnit") != null){
                        info.setProUnit(resultList.get(i).get(rowMap.get("proUnit")));
                    }
                    if(rowMap.get("proPh") != null){
                        info.setProPh(resultList.get(i).get(rowMap.get("proPh")));
                    }
                    if(rowMap.get("manufactureDate") != null){
                        info.setManufactureDate(resultList.get(i).get(rowMap.get("manufactureDate")));
                    }
                    if(rowMap.get("expiryDate") != null){
                        String expiryDate = resultList.get(i).get(rowMap.get("expiryDate"));
                        if(StringUtils.isNotBlank(expiryDate) && expiryDate.length() > 10){
                            expiryDate = expiryDate.substring(0,10);
                        }
                        info.setExpiryDate(expiryDate);
                    }
                    String qty = resultList.get(i).get(rowMap.get("proQty"));
                    if(qty.indexOf(".") > 0){
                        info.setProQty(qty.substring(0,qty.indexOf(".")));
                    }else{
                        info.setProQty(qty);
                    }
                    if(rowMap.get("inventoryStatus") != null){
                        info.setInventoryStatus(resultList.get(i).get(rowMap.get("inventoryStatus")));
                    }
                    if(rowMap.get("purchasDate") != null){
                        info.setPurchasDate(resultList.get(i).get(rowMap.get("purchasDate")));
                    }
                    if(rowMap.get("approvalnum") != null){
                        info.setApprovalnum(resultList.get(i).get(rowMap.get("approvalnum")));
                    }
                    data.add(info.joinStr());
                }
            }

        }
        return data;
    }

}
