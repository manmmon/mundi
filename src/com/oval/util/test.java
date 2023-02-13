package com.oval.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Decoder;

public class test {

	/**
     * 转换私钥
     *
     * @param key 字符串的私钥
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 转换公钥
     *
     * @param key 字符串的公钥
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 加密
     *
     * @param str_plaintext 需要加密的数据
     * @param str_publicKey 加密公钥--字符串
     * @param str_priK      加密私钥--字符串
     */
    public static String encrypt(String str_plaintext, String str_publicKey) throws Exception {
        PublicKey publicKey = getPublicKey(str_publicKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bt_plaintext = str_plaintext.getBytes();
        byte[] bt_cipher = cipher.doFinal(bt_plaintext);
        return Base64.encodeBase64(bt_cipher).toString();//encodeBase64String(bt_cipher);
    }

    /**
     * 解密
     *
     * @param str_plaintext  需要解密的数据
     * @param str_pubK       解密公钥--字符串
     * @param str_privateKey 解密私钥--字符串
     */
    public static String decrypt(String str_cipher, String str_privateKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(str_privateKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // return Base64.encodeBase64String(b);
        byte[] bt_encrypted = Base64.decodeBase64(str_cipher.getBytes());//.decodeBase64(str_cipher);
        byte[] bt_original = cipher.doFinal(bt_encrypted);
        String str_original = new String(bt_original);
        return str_original;
    }
    
	public static String invoke(String address, String method,String xml) {
		String result = StringEscapeUtils.unescapeXml(Webserclient
				.sendSoapPost(address, xml,
						"http://webservice.com" + method));
		Document doc;
		try {
			doc = DocumentHelper.parseText(result);
			result = doc.getRootElement().element("Body")
					.element(method+"Response").element("return").getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static void main(String[] args) {
		Map<String, String> params =new HashMap<String,String>();
//		params.put("distId", "2688");
//		params.put("callId", "2086");
//		params.put("uuid", "110FBB5B-AED3-4F5C-803F-400C1CDC27E5");
//		7827	2098	1	FEFEB7B8-3AC9-477F-A21A-AF75F612B640
//		7830	2100	1	B1CD8E80-D875-4F62-A811-3262112CA2A5
//		7837	2110	1	D3FFD582-A5CB-4DF0-AB59-5035A263290F
//		3957	2115	0	723E0FB2-78FC-41D6-8582-0509BB926621

//		2786	2120	1	2BDB99CE-652C-4E34-9BE6-724C64660C41
//		7842	2122	1	29275EC3-BDB1-43F1-BA23-B41AC6D0A4A1
//		5412	2142	1	2641E069-836B-47AA-92E9-27EDA0D66285
//		7794	2119	1	4B2B94C7-6557-4D3D-AE76-EBEA9D491730
//		7822	2091	1	B1E82BC2-1222-46D8-A33F-E5BE3798CF7B
//		2688	2086	1	110FBB5B-AED3-4F5C-803F-400C1CDC27E5
		
//		3033	1952	1	8BB7FAD6-9785-4506-B4F2-3E172B90DF7F  10422602/10422602@10
//		7702	1940	1	CA522573-E93E-4152-A4A2-1666C478FC3F   118118/123456
//		9801	3985	1	07FDE8B2-D23C-4658-9354-93845290AA19
//		7423	1611	1	66694988-8A8A-4A23-9963-6D02284011DD
//		9999	4145	1	FF1D5947-3FDD-4924-86AA-1C019DB59EAE
//		10223	4415	1	83EB8F5D-8A77-4F23-BF1C-C69158F4E237  JT061/123456
//		7423	1611	1	66694988-8A8A-4A23-9963-6D02284011DD  13154/20230614
//		10590	4855	1	DBF72D77-4B38-4AB1-91CB-B3DF04BEFAD5
//		9166	4856	1	28D4C7A6-C6B1-4975-B31D-C516117C14C1

		params.put("distId", "9166");
		params.put("callId", "4856");
		params.put("uuid", "28D4C7A6-C6B1-4975-B31D-C516117C14C1");
		String baseUrl = "fnmfddwTaZvyIEr65so2Sp8AopiwpSW1Bj7dyl+NgQWhjKwOsIpgdOZqwVBLabNzREHjpOw/zMI=#";
		String queryXml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.com\"><soapenv:Header/><soapenv:Body><web:query><web:param>#param#</web:param></web:query></soapenv:Body></soapenv:Envelope>";
		// MD5解密并连接对应的具体地址
		baseUrl = Md5.decrypt(baseUrl);
		baseUrl = baseUrl.concat("GetCallWs2nd");
		String distId =params.get("distId");
	    String callId=params.get("callId");
		/**
		 * 提交的参数
		 */
		JSONObject json = new JSONObject();
		json.put("apiId", "50");
		json.put("type", "1");
//		json.put("type", "0");
		json.put("distId", params.get("distId"));
		json.put("callId", params.get("callId"));
		json.put("uuid", params.get("uuid"));
		String strParamAgain = CodeUtil.encodeString(json.toJSONString()); // 加密参数
		queryXml = queryXml.replace("#param#", strParamAgain);
		String strResult = invoke(baseUrl,"query",queryXml);
		String strResultAgain = CodeUtil.decodeString(strResult); // 解密返回
		System.out.println(strResultAgain);
		JSONObject object = JSONObject.parseObject(strResultAgain);
		String strSuccess = object.getString("success");
		if (strSuccess.equals("true")) {
			JSONObject resultData = JSONObject.parseObject(object
					.getString("resultData"));
			params.put("apiId", resultData.getString("apiId"));
			params.put("username", resultData.getString("account"));
			params.put("password", StringEscapeUtils.escapeXml(resultData
					.getString("password")));
			params.put("privilegekey", resultData.getString("privilegekey"));// 私钥
			params.put("specialstring", resultData.getString("specialstring"));// 特殊字符串
			params.put("bak1", resultData.getString("bak1"));// 备用字段1
			params.put("bak2", resultData.getString("bak2"));// 备用字段2
			params.put("bak3", resultData.getString("bak3"));// 备用字段3
			
		} else {
			System.out.println("执行失败，请确认参数正确与否");
			
		}

	}

}
