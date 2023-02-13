package sevenphase.verifycode;



import com.oval.grabweb.logic.part201905.impl.HttpClientUtil;
import com.oval.grabweb.logic.part201905.impl.SSLClient;

public abstract  class HttpsVerifyAbstractAction extends VerifyAbstractAction{
	protected HttpClientUtil https = new HttpClientUtil();
	public HttpsVerifyAbstractAction() throws Exception {
		client = new SSLClient();
	}
	
	public  void exec() throws Exception{
		try {
			doExec();
		}finally {
			if(client == null) {
				return;
			}
			client.getConnectionManager().shutdown();
			client = null;
		}
	}
	
}
