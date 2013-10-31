package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import client.Client;
import service.PersonService;
import utils.Constants;
import model.Speech;
import Object.Object;


public class Person extends Object {
	private Client client;
	private String id;
	private String name;
	private String tag;
	private boolean flag;
	
	private PersonService ps;
	
	public Person(Client client, String id, String name) {
		this.client = client;
		this.id = id;
		this.name = name;
	}
	
	public Person(Client client) {
		this.client = client;
	}
	
	public Person(Client client, String id) {
		this.client = client;
		this.id = id;
	}
	
	public PersonService getPersonService() {
		if (ps == null) {
			ps = (PersonService) new PersonService().setClient(this.client);
		}
		
		return ps;
	}
	
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getTag() {
		return this.tag;
	}
	public boolean getFlag() {
		return this.flag;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public int delete() {
		int ret = Constants.RETURN_SUCCESS;
		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personRemove(this.id);
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			}
		} else {
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}

		return ret;
	}
	
	public int create() {
		int ret = Constants.RETURN_SUCCESS;
		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personCreate(this.id, this.name);			
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			}
		} else {		
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);		
		}
		
		return ret;
	}
	
	public int getInfo() {
		int ret = Constants.RETURN_SUCCESS;
		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personGetInfo(this.id);			
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(result.getInt(Constants.ERROR_CODE));
			} else {
				JSONArray person = (JSONArray) result.getJSONArray("person");
				if (person.size() > 0) {
					JSONObject p = (JSONObject) person.get(0);
					this.setId(p.getString(Constants.IDENTY));
					this.setName(p.getString(Constants.NAME));
					this.setFlag(p.getBoolean(Constants.FLAG));
				}
			}
		} else {		
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}		
		
		return ret;
	}
	
	public List<Speech> getSpeeches() {
		List<Speech> speechList = new ArrayList<Speech>();
		int ret = Constants.RETURN_SUCCESS;
		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personGetSpeeches(this.id);
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			} else {
				JSONArray speeches = (JSONArray) result.getJSONArray("speech");
				Iterator<JSONObject> it = speeches.iterator();
				while (it.hasNext()) {
					JSONObject object = (JSONObject) it.next();
					Speech speech = new Speech();
					speech.setId(object.getString(Constants.IDENTY));
					speech.setMD5(object.getString(Constants.MD5));
					speech.setSampleRate(object.getInt(Constants.SAMPLE_RATE));
					speech.setCodec(object.getString(Constants.CODEC));
					
					speechList.add(speech);
				}
			}
		} else {
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}
		
		return speechList;
	}
	
	public int addSpeech(Speech speech) {
		int ret = Constants.RETURN_SUCCESS;
		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personAddSpeech(this.id, speech.getCodec(), 
					speech.getSampleRate(), speech.getVerify(), speech.getData());
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			} else {
				speech.setMD5(result.getString(Constants.MD5));
			}
		} else {		
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);		
		}
		
		return ret;
	}
	
	public int removeSpeeches() {
		int ret = Constants.RETURN_SUCCESS;
		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personRemoveSpeeches(this.id);			
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			}
		} else {		
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);		
		}
		
		return ret;
	}
	
	public int removeSpeech(Speech speech) {
		int ret = Constants.RETURN_SUCCESS;
		
		if (!speech.getMD5().isEmpty()) {
			JSONObject result = getPersonService().personRemoveSpeech(speech.getMD5());
			
			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			}
		} else {		
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);		
		}
		
		return ret;
	}
}