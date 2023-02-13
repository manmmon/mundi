package com.oval.grabweb.logic.part202301.impl.copy;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.Constant;
import com.oval.util.WebUtil;

/**
 * 重庆医药合川医药有限责任公司
 * 
 * @author youzi
 *
 */
public class Code31011Action extends ChongQingYiYaoAction {
	
	public Code31011Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		return getStock(paramHttpClient, "31011", "29");
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		return getPurchas(paramHttpClient, "31011", "29");
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		return getSale(paramHttpClient, "31011", "29");
	}
	
	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "5403");
		params.put("callId", "5050");
		params.put("uuid", "07DF621E-1313-484C-8BC3-C74488D72806");
		try { // 31011 重庆医药合川医药有限责任公司
			(new Code31011Action()).exec(client, params, "31011", "重庆医药合川医药有限责任公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
