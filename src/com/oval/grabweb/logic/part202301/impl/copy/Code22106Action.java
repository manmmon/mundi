package com.oval.grabweb.logic.part202301.impl.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;

import com.alibaba.fastjson.JSON;
import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.WebUtil;

/**
 * 山东海王银河医药有限公司
 * @author youzi
 *
 */
public class Code22106Action extends AbstractAction{
	
	public Code22106Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}
	
	@Override
	protected String[] createPurchas(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] createStock(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] createSale(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 重新定义库存模板
	 */
	protected List<String> createStock() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("库存时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("供应商代码").append(column_speractor);
		sb.append("供应商名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
        sb.append("批号").append(column_speractor);
        sb.append("生产日期").append(column_speractor);
        sb.append("失效日期").append(column_speractor);
        sb.append("数量").append(column_speractor);
        sb.append("库存状态").append(column_speractor);
        sb.append("入库时间").append(column_speractor);
		list.add(sb.toString());
		return list;

	}
	
	/**
	 * 重新定义采购模板
	 */
	protected List<String> createPurchas() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();

		sb.append("入库时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("供应商代码").append(column_speractor);
		sb.append("供应商名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
        sb.append("批号").append(column_speractor);
        sb.append("数量").append(column_speractor);
        sb.append("含税单价").append(column_speractor);
        sb.append("含税金额").append(column_speractor);
        sb.append("进货类型").append(column_speractor);
        sb.append("供应商出库单号").append(column_speractor);
		list.add(sb.toString());

		return list;
	}
	
	/**
	 * 重新定义销售模板
	 */
	protected List<String> createSale() {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("出库时间").append(column_speractor);
		sb.append("生产厂家").append(column_speractor);
		sb.append("客户代码").append(column_speractor);
		sb.append("客户名称").append(column_speractor);
		sb.append("产品代码").append(column_speractor);
		sb.append("产品名称").append(column_speractor);
		sb.append("产品规格").append(column_speractor);
		sb.append("剂型").append(column_speractor);
		sb.append("单位").append(column_speractor);
        sb.append("批号").append(column_speractor);
        sb.append("数量").append(column_speractor);
        sb.append("含税单价").append(column_speractor);
        sb.append("含税金额").append(column_speractor);
        sb.append("出货类型").append(column_speractor);
        sb.append("客户城市").append(column_speractor);
		sb.append("客户地址").append(column_speractor);
		sb.append("经销商发货单号").append(column_speractor);
		sb.append("备注").append(column_speractor);

		list.add(sb.toString());
		return list;
	}

	@Override
	protected void login(HttpClient paramHttpClient, Map<String, String> paramMap) throws Exception {
		String username = "010315@hwygjk.com";
		String password = "Md202207";
		WebUtil.repeatGet(paramHttpClient, "http://219.146.120.3:8889/ashx/loginhandler.ashx?username="+username+"&password="+password);
		WebUtil.repeatGet(paramHttpClient, "http://219.146.120.3:8889/ashx/MenuData.ashx");
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String url = "http://219.146.120.3:8889/ashx/exportexcel.aspx?t1=sr&json={}";
		String str = WebUtil.repeatGet(paramHttpClient, url);
		str = str.substring(str.indexOf("<table"), str.indexOf("</table>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "库存时间","生产厂家","供应商代码","供应商名称","产品代码","产品名称","产品规格","剂型","单位","批号","生产日期","失效日期","数量","库存状态","入库时间" };
		String[] contents = new String[] { "商品名","产品名称","库存时间","分销商名","批号","产品规格","单位","供应商名称","数量","不合格数量","生产日期","失效日期","业务员","生产厂家","商品编码","产品代码" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createStock();
		for (int i = 2; i < trs.length; i++) {

			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if(tds != null && tds[3].equals("山东海王银河医药有限公司")) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						sb.append(tds[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
			
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		Map<String,Object> map = new HashMap<>();
		map.put("BeginDate", startdate);
		map.put("endDate", enddate);
		map.put("BillType", "TAL");
		String json=JSON.toJSONString(map);
		String url = "http://219.146.120.3:8889/ashx/exportexcel.aspx?t1=sdf&json="+json;
		String str = WebUtil.repeatGet(paramHttpClient, url);
		str = str.substring(str.indexOf("<table"), str.indexOf("</table>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "入库时间","生产厂家","供应商代码","供应商名称","产品代码","产品名称","产品规格","剂型","单位","批号","数量","含税单价","含税金额","进货类型","供应商出库单号" };
		String[] contents = new String[] { "RowIndex","入库时间","商品名","产品名称","分销商名","客户名","批号","产品规格","单位","供应商名称","数量","生产日期","效期","业务员","进货类型","生产厂家","商品编码","产品代码","客户编码","备注" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createPurchas();
		for (int i = 2; i < trs.length; i++) {

			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if(tds != null && tds[4].equals("山东海王银河医药有限公司")) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						sb.append(tds[indexs[j]]).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
			
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		String startdate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String enddate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		Map<String,Object> map = new HashMap<>();
		map.put("BeginDate", startdate);
		map.put("endDate", enddate);
		map.put("BillType", "SAL");
		String json=JSON.toJSONString(map);
		String url = "http://219.146.120.3:8889/ashx/exportexcel.aspx?t1=sdf&json="+json;
		String str = WebUtil.repeatGet(paramHttpClient, url);
		str = str.substring(str.indexOf("<table"), str.indexOf("</table>"));
		String[] trs = str.split("<tr");
		String[] title = new String[] { "出库时间","生产厂家","客户代码","客户名称","产品代码","产品名称","产品规格","剂型","单位","批号","数量","含税单价","含税金额","出货类型","客户城市","客户地址","经销商发货单号","备注" };
		String[] contents = new String[] { "RowIndex","出库时间","商品名","产品名称","分销商名","客户名称","批号","产品规格","单位","供应商名","数量","生产日期","效期","业务员","出货类型","生产厂家","商品编码","产品代码","客户代码","备注" };
		int[] indexs = getContentsIndexs(title, contents);
		List<String> data = createSale();
		for (int i = 2; i < trs.length; i++) {

			String[] tds = StringUtils.substringsBetween(trs[i], "<td>", "</td>");
			if(tds != null && tds[4].equals("山东海王银河医药有限公司")) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < indexs.length; j++) {
					if (indexs[j] == -1) {
						sb.append("").append(column_speractor);
					} else {
						String s = tds[indexs[j]].indexOf("&nbsp;") < 0 ? tds[indexs[j]] : "";
						sb.append(s).append(column_speractor);
					}
				}
				data.add(sb.toString());
			}
			
		}
		return data.toArray(new String[1]);
	}

}
