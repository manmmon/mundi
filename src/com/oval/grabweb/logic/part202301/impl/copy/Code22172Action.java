package com.oval.grabweb.logic.part202301.impl.copy;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.Constant;
import com.oval.util.WebUtil;

/**
 * 宁夏众欣联合方泽医药有限公司
 * 
 * @author youzi
 *
 */
public class Code22172Action extends ChongQingYiYaoAction {
	
	public Code22172Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		return getStock(paramHttpClient, "22172", "R7");
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		return getPurchas(paramHttpClient, "22172", "R7");
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		return getSale(paramHttpClient, "22172", "R7");
	}
	
	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "5403");
		params.put("callId", "5050");
		params.put("uuid", "07DF621E-1313-484C-8BC3-C74488D72806");
		try { // 22172 宁夏众欣联合方泽医药有限公司
			(new Code22172Action()).exec(client, params, "22172", "宁夏众欣联合方泽医药有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
