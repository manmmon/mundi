package com.oval.grabweb.logic.part202301.impl.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.AbstractAction;
import com.oval.grabweb.action.Constant;
import com.oval.util.DateUtil;
import com.oval.util.WebUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 国药集团临汾有限公司
 * 
 * @author youzi
 *
 */
public class Code22366Action extends AbstractAction {
	
	public Code22366Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}
	
	List<String> dataStock = createStock();
	List<String> dataPurchas = createPurchas();
	List<String> dataSale = createSale();

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
		params.put("mkbh", "M1");
		params.put("UserName", "2222");
		params.put("isphone", "0");
		params.put("Password", "Md123456");
		String str = WebUtil.repeatPost(paramHttpClient, params, "http://60.221.64.77:8020/Loginhome");
		str = WebUtil.repeatGet(paramHttpClient, "http://60.221.64.77:8020/gnmenus");
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		String startDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
		String endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");

		Map<String, String> params = new HashMap<String, String>();
		params.put("querydata[0][con]", "nwg");
		params.put("querydata[0][elementName]", "dsmain");
		params.put("querydata[0][data][0][start_rq]", startDate);
		params.put("querydata[0][data][0][end_rq]", endDate);
		params.put("querydata[0][data][0][spbh]", "");
		params.put("querydata[0][data][0][spmch]", "");
		params.put("querydata[0][data][0][spid]", "");
		params.put("querydata[0][fields][0][field]", "start_rq");
		params.put("querydata[0][fields][0][type]", "date");
		params.put("querydata[0][fields][0][title]", "起始日期");
		params.put("querydata[0][fields][0][datatype]", "varchar");
		params.put("querydata[0][fields][0][length]", "10");
		params.put("querydata[0][fields][0][dec]", "0");
		params.put("querydata[0][fields][0][isprimarykey]", "false");
		params.put("querydata[0][fields][1][field]", "end_rq");
		params.put("querydata[0][fields][1][type]", "date");
		params.put("querydata[0][fields][1][title]", "终止日期");
		params.put("querydata[0][fields][1][datatype]", "varchar");
		params.put("querydata[0][fields][1][length]", "10");
		params.put("querydata[0][fields][1][dec]", "0");
		params.put("querydata[0][fields][1][isprimarykey]", "false");
		params.put("querydata[0][fields][2][field]", "spbh");
		params.put("querydata[0][fields][2][type]", "search");
		params.put("querydata[0][fields][2][title]", "商品编号");
		params.put("querydata[0][fields][2][datatype]", "varchar");
		params.put("querydata[0][fields][2][length]", "20");
		params.put("querydata[0][fields][2][dec]", "0");
		params.put("querydata[0][fields][2][isprimarykey]", "false");
		params.put("querydata[0][fields][3][field]", "spmch");
		params.put("querydata[0][fields][3][type]", "search");
		params.put("querydata[0][fields][3][title]", "商品名称");
		params.put("querydata[0][fields][3][datatype]", "varchar");
		params.put("querydata[0][fields][3][length]", "80");
		params.put("querydata[0][fields][3][dec]", "15");
		params.put("querydata[0][fields][3][isprimarykey]", "false");
		params.put("querydata[0][regUniqueIdentifier]", "3581a4df-1ec9-4c09-9418-9e05d392b1a2");
		params.put("querydata[1][applyLink]", "false");
		params.put("querydata[1][con]", "cms6");
		params.put("querydata[1][elementName]", "table_1");
		params.put("querydata[1][sqls][0][sqls]", "");
		params.put("querydata[1][sqls][0][name]", "");
		params.put("querydata[1][sqls][0][type]", "selectsql");
		params.put("querydata[1][sqls][0][sql]",
				"declare @srq varchar(10),@erq varchar(10),@status  int select  @srq=start_rq,@erq=end_rq,@status=status from app_zl_dw where dwbh=@getjgbsh \r\n"
						+ "  if @status=0\r\n" + " select '单位状态停用，不允许查询' as '系统提示'\r\n" + " else\r\n"
						+ "exec msp_lxcx @dsmain_start_rq,@dsmain_end_rq,@dsmain_spmch,@srq,@erq,@getjgbsh,@dsmain_spid");
		params.put("querydata[1][sqls][0][tempid]", "1000000");
		params.put("querydata[1][showData]", "true");
		params.put("querydata[1][regUniqueIdentifier]", "3581a4df-1ec9-4c09-9418-9e05d392b1a2");
		String str = WebUtil.repeatPost(paramHttpClient, params, "http://60.221.64.77:8020/designquerydata");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		json = JSONObject.fromObject(json.getJSONArray("result").get(0));
		JSONArray datas = json.getJSONArray("data");
		for (int i = 0; i < datas.size(); i++) {
			json = datas.getJSONObject(i);
			StringBuilder sb = new StringBuilder();
			switch (json.get("flowname").toString()) {
			case "库存：":
				sb = getKC(sb, json);
				dataStock.add(sb.toString());
				break;
			case "正常销售订单":
				sb = getXS(sb, json);
				dataSale.add(sb.toString());
				break;
			case "采购订单":
				sb = getCG(sb, json);
				dataPurchas.add(sb.toString());
				break;
			}
		}
		return dataStock.toArray(new String[1]);
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		return dataPurchas.toArray(new String[1]);
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		return dataSale.toArray(new String[1]);
	}

	protected StringBuilder getKC(StringBuilder sb, JSONObject json) {
		String today = DateUtil.getBeforeDayAgainstToday(0, "yyyy-MM-dd");
		sb.append(today).append(column_speractor);// 库存时间
		sb.append(json.get("producer")).append(column_speractor);// 生产厂家
		sb.append(column_speractor);// 供应商代码
		sb.append(column_speractor);// 供应商名称
		sb.append(json.get("goods")).append(column_speractor);// 商品编码
		sb.append(json.get("name")).append(column_speractor);// 名称
		sb.append(json.get("spec")).append(column_speractor);// 规格
		sb.append(column_speractor);// 剂型
		sb.append(json.get("msunitno")).append(column_speractor);// 单位
		sb.append(json.get("lotno")).append(column_speractor);// 批号
		sb.append(json.get("prddate")).append(column_speractor);// 生产日期
		sb.append(json.get("enddate")).append(column_speractor);// 有效期
		sb.append(json.get("allo_qty")).append(column_speractor);// 库存数量
		sb.append(column_speractor);// 库存状态
		sb.append(column_speractor);// 入库时间
		return sb;
	}

	protected StringBuilder getCG(StringBuilder sb, JSONObject json) {
		// 入库时间 生产厂家 供应商代码 供应商名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 进货类型 供应商出库单号
		sb.append(json.get("bizcreatedate")).append(column_speractor);// 订单日期
		sb.append(json.get("producer")).append(column_speractor);// 生产厂家
		sb.append(column_speractor);// 供应商代码
		sb.append(json.get("dname")).append(column_speractor);// 供应商名称
		sb.append(json.get("goods")).append(column_speractor);// 商品编码
		sb.append(json.get("name")).append(column_speractor);// 名称
		sb.append(json.get("spec")).append(column_speractor);// 产品规格
		sb.append(column_speractor);// 剂型
		sb.append(json.get("msunitno")).append(column_speractor);// 单位
		sb.append(json.get("lotno")).append(column_speractor);// 批号
		sb.append(json.get("rkshl")).append(column_speractor);// 数量
		sb.append(column_speractor);// 含税单价
		sb.append(column_speractor);// 含税金额
		sb.append(json.get("flowname")).append(column_speractor);// 进货类型
		sb.append(column_speractor);// 经销商发货单号
		sb.append(column_speractor);// 备注
		return sb;
	}

	protected StringBuilder getXS(StringBuilder sb, JSONObject json) {
		// 出库时间 生产厂家 客户代码 客户名称 产品代码 产品名称 产品规格 剂型 单位 批号 数量 含税单价 含税金额 出货类型 客户城市 客户地址
		sb.append(json.get("bizcreatedate")).append(column_speractor);// 出库时间
		sb.append(json.get("producer")).append(column_speractor);// 生产厂家
		sb.append(column_speractor);// 客户代码
		sb.append(json.get("dname")).append(column_speractor);// 客户名称
		sb.append(json.get("goods")).append(column_speractor);// 商品编码
		sb.append(json.get("name")).append(column_speractor);// 名称
		sb.append(json.get("spec")).append(column_speractor);// 产品规格
		sb.append(column_speractor);// 剂型
		sb.append(json.get("msunitno")).append(column_speractor);// 单位
		sb.append(json.get("lotno")).append(column_speractor);// 批号
		sb.append(json.get("ckshl")).append(column_speractor);// 数量
		sb.append(column_speractor);// 含税单价
		sb.append(column_speractor);// 含税金额
		sb.append(json.get("flowname")).append(column_speractor);// 出货类型
		sb.append(column_speractor);// 客户城市
		sb.append(column_speractor);// 客户地址
		sb.append(column_speractor);// 经销商发货单号
		sb.append(column_speractor);// 备注
		return sb;
	}

	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "4217");
		params.put("callId", "5044");
		params.put("uuid", "5F40516E-5F25-4984-AEEE-751BF32FBB42");
		try { // 22366 国药集团临汾有限公司
			(new Code22366Action()).exec(client, params, "22366", "国药集团临汾有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
