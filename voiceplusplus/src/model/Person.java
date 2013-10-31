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
	
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public boolean update() {
		
		
		return true;
	}
	
	public boolean delete() {
		boolean isOK = false;		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personRemove(this.id);
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
	
	public boolean create() {
		boolean isOK = false;		
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personCreate(this.id, this.name);			
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
	
	public List<Speech> getSpeeches() {
		List<Speech> speechList = new ArrayList<Speech>();
		
		boolean isOK = false;
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personGetSpeeches(this.id);			
			isOK = result.getBoolean(Constants.SUCCESS);
			
			if (!isOK) {
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(result.getInt(Constants.ERROR_CODE));
				
				return null;
			} else {
				JSONArray speeches = (JSONArray) result.getJSONArray("speech");
				Iterator<JSONObject> it = speeches.iterator();
				while (it.hasNext()) {
					JSONObject ret = (JSONObject) it.next();
					Speech speech = new Speech();
					speech.setId(ret.getString(Constants.IDENTY));
					speech.setMD5(ret.getString(Constants.MD5));
					speech.setSampleRate(ret.getInt(Constants.SAMPLE_RATE));
					speech.setCodec(ret.getString(Constants.CODEC));
					
					speechList.add(speech);
				}
				
				return speechList;
			}
		} else {		
			super.setLastErr("id is empty");
			super.setErrCode(0);
			
			return null;
		}
	}
	
	public boolean addSpeech(Speech speech) {
		boolean isOK = false;
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personAddSpeech(this.id, speech.getCodec(), 
					speech.getSampleRate(), speech.getVerify(), speech.getData());			
			isOK = result.getBoolean(Constants.SUCCESS);
			
			if (!isOK) {
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(result.getInt(Constants.ERROR_CODE));
			} else {
				speech.setMD5(result.getString(Constants.MD5));
			}
		} else {		
			super.setLastErr("id is empty");
			super.setErrCode(0);		
		}
		
		return isOK;
	}
	
	public boolean removeSpeeches() {
		boolean isOK = false;
		if (!this.id.isEmpty()) {
			JSONObject result = getPersonService().personRemoveSpeeches(this.id);			
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
	
	public boolean removeSpeech(Speech speech) {
		boolean isOK = false;
		if (!speech.getMD5().isEmpty()) {
			JSONObject result = getPersonService().personRemoveSpeech(speech.getMD5());			
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
}