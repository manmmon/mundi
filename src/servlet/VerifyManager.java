package servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import sevenphase.verifycode.VerifyAbstractAction;

import com.oval.util.DateUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.ReadXMLUtil;


public class VerifyManager {

	private int todoMax = 5;
	private Boolean busy = false;
	public static String MultiVerCode="";
	public static String[] MultiStockInfo=null;
	public static String[] MultiSaleInfo=null;
	public static String[] MultiPurchasInfo=null;
	public static int MStockArray=0;
	public static int MSaleArray=0;
	public static int MPurchasArray=0;

	private Map<String, VerifyAbstractAction> todoActors = new HashMap<String, VerifyAbstractAction>();
	private Map<String, VerifyAbstractAction> verifyActors = new HashMap<String, VerifyAbstractAction>();

	private static VerifyManager instance;
	
	private VerifyManager() {
		verifyActors = new HashMap<String, VerifyAbstractAction>();
	}
	
	public static synchronized VerifyManager getInstance() throws Exception {
		if (instance == null) {
			instance = new VerifyManager();
			System.out.println("--------加载验证码网站列表--------");
			instance.loadVerifyActors();
			System.out.println("--------加载完成-------");
		}
		
		return instance;
	}
	
	public void loadVerifyActors() throws Exception {
		SAXReader reader = new SAXReader();
		String xmlparse = this.getClass().getResource("/").getPath();
		Document doc = reader.read(xmlparse + "350Web.xml");
		List<Element> customers = doc.selectNodes("//customer[@verify='Y']");
		for (Element element : customers) {
			String className = ReadXMLUtil.getChildValue(element, "classname");
			System.out.println("---->" + className);
			VerifyAbstractAction actor = (VerifyAbstractAction)Class.forName(className).newInstance();			
			String orgCode = ReadXMLUtil.getChildValue(element, "orgcode");
			actor.setOrgCode(orgCode);
			String orgName = ReadXMLUtil.getChildValue(element, "orgname");
			actor.setOrgName(orgName);
			System.out.println(orgName);
			
			Node loginNode = element.selectSingleNode("login");
			List<Element> params = loginNode.selectNodes("pram");
			for (Element param : params) {
				actor.addLoginParam(param.attributeValue("name").trim(),param.getText().trim());
			}
			
			verifyActors.put(orgCode, actor);
		}
	}
	
	public void addActor(VerifyAbstractAction actor) {
		verifyActors.put(actor.getOrgCode(), actor);		
	}
	
	
	public synchronized VerifyAbstractAction getTesterByCode(String code) {
		return verifyActors.get(code);
	}

	public synchronized boolean isBusy() {
		return busy;
	}	
	
	public void getVerifyCode(String orgCode) throws Exception{
		VerifyAbstractAction verifyActor= verifyActors.get(orgCode);
		verifyActor.getVerifyCode();
	}
	
	public void addActor(String orgCode,String verifyCode,String DataMerger){
		VerifyAbstractAction verifyActor= verifyActors.get(orgCode);
		verifyActor.addVerifyCodeParam(verifyCode);
		todoActors.put(orgCode+"@"+DataMerger, verifyActor);
	}
	
	public  void execTasks() {
		System.out.println("busy1---" + busy);
		synchronized(busy) {
		   busy = true;
		}
		System.out.println("busy2---" + busy);
		try {
			GlobalInfo.beginDate = DateUtil.getBeforeDayAgainstToday(60, "yyyy-MM-dd");
			GlobalInfo.endDate = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
			for (String orgCode : todoActors.keySet()) {
				try {
					String[] s=orgCode.split("@");
					System.out.println(s.toString());
					VerifyManager.MultiVerCode=s[1];
					todoActors.get(orgCode).exec();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			VerifyManager.MultiSaleInfo=null;
			VerifyManager.MultiStockInfo=null;
			VerifyManager.MultiPurchasInfo=null;
			VerifyManager.MSaleArray=0;
			VerifyManager.MStockArray=0;
			VerifyManager.MPurchasArray=0;
		}finally{
			synchronized(busy) {
				   busy = false;
				}
			System.out.println("111111");
			todoActors.clear();
		}
	}	
	
	
	public boolean isTodoListFull(){
		return todoActors.size() > todoMax;
	}
	
	public Map<String, VerifyAbstractAction> getVerifyActors() {
		return verifyActors;
	}

}
