package com.oval.grabweb.logic.part202301.impl.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.StringUtil;
import com.oval.util.WebUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 国药集团山西有限公司
 * 
 * @author youzi
 *
 */
public class Code22364Action extends AbstractAction {
	public Code22364Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}

	String cookie = "";

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
		Map<String, String> params = new HashMap<String, String>();
		params.put("Account", "ghs729");
		params.put("Password", "a4a176912c6dcb4f85ade96fe4491e31");
		HttpResponse response = WebUtil.repeatPost1(paramHttpClient, params,
				"http://lxcx.sxsinopharm.net/Login/CheckLogin");
		Header cookieHeader = response.getHeaders("Set-Cookie")[0];
		String value = cookieHeader.getValue();
		cookie = value.split(";")[0].trim();

	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String today = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		long l = System.currentTimeMillis();
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("Content-Type", "application/json;charset=utf-8");
		String url = "http://lxcx.sxsinopharm.net/ScmModule/PubStmCardGrp/GridStorePageListJson?_search=false&nd=" + l
				+ "&rows=1000&page=1&sidx=goodscode&sord=asc";
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headMap, url);
		JSONObject json = JSONObject.fromObject(str);
		JSONArray datas = json.getJSONArray("rows");
		System.out.println(datas);
		List<String> data = createStock();
		// 库存时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 生产日期 失效日期 数量 库存状态 入库时间
		for (int i = 0; i < datas.size(); i++) {
			json = datas.getJSONObject(i);
			StringBuilder sb = new StringBuilder();
			sb.append(today).append(column_speractor);
			sb.append(json.get("producer")).append(column_speractor);// 生产厂家
			sb.append(column_speractor);// 供应商代码
			sb.append(json.get("cstname")).append(column_speractor);// 供应商名称
			sb.append(json.get("goodscode")).append(column_speractor);// 产品代码
			sb.append(json.get("goodsname")).append(column_speractor);// 产品名称
			sb.append(json.get("spec")).append(column_speractor);// 产品规格
			sb.append(column_speractor);// 剂型
			sb.append(json.get("msunit")).append(column_speractor);// 单位
			sb.append(column_speractor);// 批号
			sb.append(column_speractor);// 生产日期
			sb.append(column_speractor);// 失效日期
			sb.append(json.get("goodsqty")).append(column_speractor);// 数量
			sb.append(column_speractor);// 库存状态
			sb.append(column_speractor);// 入库时间
			data.add(sb.toString());
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
		long nd = System.currentTimeMillis();
		String parameterJson = "[{\"paramName\":\"confirmindate\",\"paramValue\":\"" + startDate
				+ "\",\"Operation\":\"GreaterThan\",\"Logic\":\"AND\"},{\"paramName\":\"confirmindate\",\"paramValue\":\""
				+ endDate + "\",\"Operation\":\"LessThan\"}]";
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("Content-Type", "application/json;charset=utf-8");
		String url = "http://lxcx.sxsinopharm.net/ScmModule/PubStmCardGrp/GridPurbillPageListJson?" + "ParameterJson="
				+ parameterJson + "&_search=false&nd=" + nd + "&rows=1000&page=1&sidx=id&sord=desc";
		System.out.println("采购：" + url);
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headMap, url);
		JSONObject json = JSONObject.fromObject(str);
		JSONArray datas = json.getJSONArray("rows");
		System.out.println(datas);
		List<String> data = createPurchas();
		// 入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号
		for (int i = 0; i < datas.size(); i++) {
			json = datas.getJSONObject(i);
			StringBuilder sb = new StringBuilder();
			sb.append(json.get("createdate")).append(column_speractor);// 进仓确认日期
			sb.append(json.get("producer")).append(column_speractor);// 生产厂家
			sb.append(json.get("cstcode")).append(column_speractor);// 供应商代码
			sb.append(json.get("dname")).append(column_speractor);// 供应商名称
			sb.append(json.get("goodscode")).append(column_speractor);// 产品代码
			sb.append(json.get("goodsname")).append(column_speractor);// 产品名称
			sb.append(json.get("spec")).append(column_speractor);// 产品规格
			sb.append(column_speractor);// 剂型
			sb.append(json.get("msunitno")).append(column_speractor);// 单位
			String bth = StringUtil.getValue(json.get("bthdesc").toString(), "批号:", "效期", 1);
			sb.append(bth).append(column_speractor);// 批号
			sb.append(json.get("billqty")).append(column_speractor);// 数量
			sb.append(json.get("prc")).append(column_speractor);// 含税单价
			sb.append(column_speractor);// 含税金额
			sb.append(column_speractor);// 进货类型
			sb.append(column_speractor);// 经销商发货单号
			data.add(sb.toString());
		}
		return data.toArray(new String[1]);
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		long nd = System.currentTimeMillis();
		String parameterJson = "[{\"paramName\":\"wmsdate\",\"paramValue\":\"" + startDate
				+ "\",\"Operation\":\"GreaterThan\",\"Logic\":\"AND\"},{\"paramName\":\"wmsdate\",\"paramValue\":\""
				+ endDate + "\",\"Operation\":\"LessThan\"}]";
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("Content-Type", "application/json;charset=utf-8");
		String url = "http://lxcx.sxsinopharm.net/ScmModule/PubStmCardGrp/GridPageListJson2?" + "ParameterJson="
				+ parameterJson + "&_search=false&nd=" + nd + "&rows=1000&page=1&sidx=cstcode,rowid&sord=asc";
		System.out.println("销售：" + url);
		String str = WebUtil.repeatGetByHeader(paramHttpClient, headMap, url);
		JSONObject json = JSONObject.fromObject(str);
		JSONArray datas = json.getJSONArray("rows");
		System.out.println(datas);
		List<String> data = createSale();
		// 出库时间 生产厂家 客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 出货类型 客户城市 客户地址 经销商发货单号 备注
		for (int i = 0; i < datas.size(); i++) {
			json = datas.getJSONObject(i);
			StringBuilder sb = new StringBuilder();
			sb.append(json.get("wmsdate")).append(column_speractor);// 出库时间
			sb.append(json.get("producer")).append(column_speractor);// 生产厂家
			sb.append(json.get("cstcode")).append(column_speractor);// 客户代码
			sb.append(json.get("cstname")).append(column_speractor);// 客户名称
			sb.append(json.get("goodscode")).append(column_speractor);// 产品代码
			sb.append(json.get("goodsname")).append(column_speractor);// 产品名称
			sb.append(json.get("spec")).append(column_speractor);// 产品规格
			sb.append(column_speractor);// 剂型
			sb.append(json.get("msunitno")).append(column_speractor);// 单位
			sb.append(json.get("lotno")).append(column_speractor);// 批号
			sb.append(json.get("lotqty")).append(column_speractor);// 数量
			sb.append(json.get("prc")).append(column_speractor);// 含税单价
			sb.append(json.get("sumvalue")).append(column_speractor);// 含税金额
			sb.append(column_speractor);// 出货类型
			sb.append(column_speractor);// 客户城市
			sb.append(json.get("addressname")).append(column_speractor);// 客户地址
			sb.append(column_speractor);// 经销商发货单号
			sb.append(column_speractor);// 备注
			data.add(sb.toString());
		}
		return data.toArray(new String[1]);
	}

	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "4101");
		params.put("callId", "5043");
		params.put("uuid", "1343FACE-9D07-40A2-8E9A-DA55938A566C");
		try { // 22364 国药集团山西有限公司
			(new Code22364Action()).exec(client, params, "22364", "国药集团山西有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}

	}

}
