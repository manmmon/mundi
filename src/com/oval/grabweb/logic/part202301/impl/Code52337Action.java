package com.oval.grabweb.logic.part202301.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;

import com.oval.grabweb.action.Constant;
import com.oval.util.WebUtil;

/**
 * 重药控股(四川)有限公司
 * 
 * @author youzi
 *
 */
public class Code52337Action extends ChongQingYiYaoAction {
	
	public Code52337Action() {
		file_lastfix = Constant.FILE_LASTFIX_MD;
	}

	@Override
	protected String[] getStock(HttpClient paramHttpClient, String paramString) throws Exception {
		return getStock(paramHttpClient, "52337", "PA");
	}

	@Override
	protected String[] getPurchas(HttpClient paramHttpClient, String paramString) throws Exception {
		return getPurchas(paramHttpClient, "52337", "PA");
	}

	@Override
	protected String[] getSale(HttpClient paramHttpClient, String paramString) throws Exception {
		return getSale(paramHttpClient, "52337", "PA");
	}
	
	public static void main(String[] args) {
		HttpClient client = WebUtil.getDefaultHttpClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("distId", "10840");
		params.put("callId", "5104");
		params.put("uuid", "0D8842A9-311C-4CA8-A3AD-4E7EAB314A69");
		try { // 52337 重药控股(四川)有限公司
			(new Code52337Action()).exec(client, params, "52337", "重药控股(四川)有限公司", new StringBuffer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

}
