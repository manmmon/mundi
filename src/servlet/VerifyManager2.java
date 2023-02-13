package servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import sevenphase.verifycode.VerifyAbstractAction2;

import com.oval.util.DateUtil;
import com.oval.util.GlobalInfo;
import com.oval.util.ReadXMLUtil;


public class VerifyManager2 {

	private int todoMax = 5;
	private Boolean busy = false;
	public static String MultiVerCode="";
	public static String[] MultiStockInfo=null;
	public static String[] MultiSaleInfo=null;
	public static String[] MultiPurchasInfo=null;
	public static int MStockArray=0;
	public static int MSaleArray=0;
	public static int MPurchasArray=0;

	private Map<String, VerifyAbstractAction2> todoActors = new HashMap<String, VerifyAbstractAction2>();
	private Map<String, VerifyAbstractAction2> verifyActors = new HashMap<String, VerifyAbstractAction2>();

	private static VerifyManager2 instance;
	
	private VerifyManager2() {
		verifyActors = new HashMap<String, VerifyAbstractAction2>();
	}
	
	public static synchronized VerifyManager2 getInstance() throws Exception {
		if (instance == null) {
			instance = new VerifyManager2();
			System.out.println("--------加载验证码网站列表--------");
			instance.loadVerifyActors();
			System.out.println("--------加载完成-------");
		}
		
		return instance;
	}
	
	public void loadVerifyActors() throws Exception {
//		SAXReader reader = new SAXReader();
//		String xmlparse = this.getClass().getResource("/").getPath();
//		Document doc = reader.read(xmlparse + "350Web2.xml");
//		List<Element> customers = doc.selectNodes("//customer[@verify='Y']");
//		for (Element element : customers) {
//			String className = ReadXMLUtil.getChildValue(element, "classname");
//			System.out.println("---->" + className);
//			VerifyAbstractAction2 actor = (VerifyAbstractAction2)Class.forName(className).newInstance();			
//			String orgCode = ReadXMLUtil.getChildValue(element, "orgcode");
//			actor.setOrgCode(orgCode);
//			String orgName = ReadXMLUtil.getChildValue(element, "orgname");
//			actor.setOrgName(orgName);
//			actor.setStatus(actor.HasExistsFileList(orgCode, orgName));
//			System.out.println(orgName);
//			
//			Node loginNode = element.selectSingleNode("login");
//			List<Element> params = loginNode.selectNodes("pram");
//			for (Element param : params) {
//				actor.addLoginParam(param.attributeValue("name").trim(),param.getText().trim());
//			}
//			
//			verifyActors.put(orgCode, actor);
//		}
	}
	
	public void addActor(VerifyAbstractAction2 actor) {
		//在此处添加测试Action
		verifyActors.put(actor.getOrgCode(), actor);		
	}
	
	
	public synchronized VerifyAbstractAction2 getTesterByCode(String code) {
		return verifyActors.get(code);
	}

	public synchronized boolean isBusy() {
		return busy;
	}	
	
	public void getVerifyCode(String orgCode) throws Exception{
		VerifyAbstractAction2 verifyActor= verifyActors.get(orgCode);
		if(orgCode.equals("W700692")||orgCode.equals("W700692TWO")){    //浙江惠仁
		   /*verifyActor.getVerifyCode("1");*/
		}else{
		  verifyActor.getVerifyCode();
		}
		
		
	}
	
	public void addActor(String orgCode,String verifyCode,String DataMerger){
		VerifyAbstractAction2 verifyActor= verifyActors.get(orgCode);
		verifyActor.addVerifyCodeParam(verifyCode);
		DataMerger = "false";//页面去掉了单选框合并，手动改为false
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
					VerifyManager2.MultiVerCode=s[1];
					todoActors.get(orgCode).exec();//orgCode
//					todoActors.get(orgCode).setStatus(todoActors.get(orgCode).HasExistsFileList(orgCode, todoActors.get(orgCode).getOrgName()));
//					todoActors.get(orgCode).exec();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			VerifyManager2.MultiSaleInfo=null;
			VerifyManager2.MultiStockInfo=null;
			VerifyManager2.MultiPurchasInfo=null;
			VerifyManager2.MSaleArray=0;
			VerifyManager2.MStockArray=0;
			VerifyManager2.MPurchasArray=0;
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
	
	public Map<String, VerifyAbstractAction2> getVerifyActors() {
		return verifyActors;
	}

}
