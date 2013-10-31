package service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.Constants;
import utils.HttpURLUtils;

public class PersonService extends BaseService {
	
	public JSONObject personCreate(String id, String name) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		parameters.put(Constants.NAME, name);
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_PERSON_CREATE, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personRemove(String id) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_PERSON_DELETE, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personGetInfo(String id) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_PERSON_GETINFO, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personAddSpeech(String id, String codec, int sr, boolean bVerify, byte[] data) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		parameters.put(Constants.CODEC, codec);
		parameters.put(Constants.SAMPLERATE, String.valueOf(sr));
		parameters.put(Constants.VERIFY, String.valueOf(bVerify));
		
		String tokenResult = HttpURLUtils.doUploadFile(super.getClient().getServerString()+Constants.URL_SPEECH_ADD, parameters, 
				Constants.FILEPARAM, "./testfile.wav", "multipart/form-data;", data);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personRemoveSpeeches(String id) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_SPEECH_DELETE_PERSON, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personRemoveSpeech(String md5) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.MD5, md5);
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_SPEECH_DELETE, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personGetSpeeches(String id) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_SPEECH_FIND_PERSON, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}

}
