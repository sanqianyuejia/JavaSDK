package client;

import net.sf.json.JSONObject;
import service.ClientService;
import utils.Constants;
import model.Speech;
import model.Person;
import Object.Object;
import client.VerifyRes;

public class Client extends Object {	
	private String key;
	private String secret;
	private String version;	
	private int type;
	private String server;
	
	private ClientService cs;
	
	/**
	 * Constructor
	 * 
	 * @param key
	 * @param secret
	 * @param version: 引擎版本号
	 * @param type: 备用字段
	 */
	public Client(String key, String secret) {
		this.key = key;
		this.secret = secret;
	}
	
	public ClientService getClientService() {
		if (cs == null) {
			cs = (ClientService) new ClientService().setClient(this);
		}
		
		return cs;
	}
	
	/**
	 * Set Server's parameters
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public synchronized boolean setServer(String host, int port, String version, int type) {
		this.version = version;
		this.type = type;
		this.server = "http://"+host+":"+String.valueOf(port)+"/"+version;
		
		return true;
	}
	
	public String getServerString() {
		return this.server;
	}
	
	/**
	 * Verify speaker's voiceprint
	 * 
	 * @param person
	 * @param speech
	 * @return
	 */
	public synchronized boolean verifyVoiceprint(Person person, Speech speech, VerifyRes res) {
		boolean isOK = false;
		
		if (!person.getId().isEmpty()) {
			JSONObject result = getClientService().clientVerifyVoiceprint(person.getId(), 
					speech.getCodec(), speech.getSampleRate(), speech.getVerify(), speech.getData());
			isOK = result.getBoolean(Constants.SUCCESS);
			
			if (!isOK) {
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(result.getInt(Constants.ERROR_CODE));
			} else {
				res.setResult(result.getBoolean(Constants.RESULT));
				res.setSimilarity(result.getDouble(Constants.SIMILARITY));
			}
		} else {
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}
		
		return isOK;
	}
	
	/**
	 * Update speaker's voiceprint
	 * 
	 * @param voiceprint
	 * @return
	 */
	public synchronized boolean updateVoiceprint(Person person) {
		boolean isOK = false;
		
		if (!person.getId().isEmpty()) {
			JSONObject result = getClientService().clientRegisterVoiceprint(person.getId(), true);
			isOK = result.getBoolean(Constants.SUCCESS);
			
			if (!isOK) {
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(result.getInt(Constants.ERROR_CODE));
			}
		} else {
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}
		
		return isOK;
	}
	
	
	/**
	 * Rgister speaker's voiceprint
	 * 
	 * @param voiceprint
	 * @return
	 */
	public synchronized boolean registerVoiceprint(Person person) {
		boolean isOK = false;
		
		if (!person.getId().isEmpty()) {
			JSONObject result = getClientService().clientRegisterVoiceprint(person.getId(), false);
			isOK = result.getBoolean(Constants.SUCCESS);
			
			if (!isOK) {				
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(result.getInt(Constants.ERROR_CODE));				
			}
		} else {
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}
		
		return isOK;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getSecret() {
		return this.secret;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
}