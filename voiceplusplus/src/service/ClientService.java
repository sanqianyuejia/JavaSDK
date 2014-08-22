package service;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;
import utils.HttpURLUtils;
import net.sf.json.JSONObject;

public class ClientService extends BaseService {
	
	public JSONObject clientRegisterVoiceprint(String id, String name, boolean bUpdate) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		parameters.put(Constants.NAME, name);
		parameters.put(Constants.UPDATE, String.valueOf(bUpdate));
		parameters.put(Constants.TRAIN_MODE, super.getClient().getType());
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_MODEL_REGISTER, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject clientVerifyVoiceprint(String id, String name, String codec, int sr, boolean bVerify, String rule, byte[] data) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		parameters.put(Constants.CODEC, codec);
		parameters.put(Constants.SAMPLERATE, String.valueOf(sr));
		parameters.put(Constants.VERIFY, String.valueOf(bVerify));
		parameters.put(Constants.SPEECH_RULE, rule);
		
		String tokenResult = HttpURLUtils.doUploadFile(super.getClient().getServerString()+Constants.URL_MODEL_VERIFY, parameters, 
				Constants.FILEPARAM, "./testfile.wav", "multipart/form-data;", data);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject clientIdentifyVoiceprint(String id, String codec, int sr, byte[] data) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.CODEC, codec);
		parameters.put(Constants.SAMPLERATE, String.valueOf(sr));
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doUploadFile(super.getClient().getServerString()+Constants.URL_MODEL_IDENTIFY, parameters, 
				Constants.FILEPARAM, "./testfile.wav", "multipart/form-data;", data);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject clientIdentifyVoiceprint_2(String id, String codec, int sr, byte[] data) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.CODEC, codec);
		parameters.put(Constants.SAMPLERATE, String.valueOf(sr));
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doUploadFile(super.getClient().getServerString()+Constants.URL_MODEL_IDENTIFY_2, parameters, 
				Constants.FILEPARAM, "./testfile.wav", "multipart/form-data;", data);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject clientIdentifyVoiceprint_3(String id, String codec, int sr, byte[] data) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.CODEC, codec);
		parameters.put(Constants.SAMPLERATE, String.valueOf(sr));
		parameters.put(Constants.ID, id);
		
		String tokenResult = HttpURLUtils.doUploadFile(super.getClient().getServerString()+Constants.URL_MODEL_IDENTIFY_3, parameters, 
				Constants.FILEPARAM, "./testfile.wav", "multipart/form-data;", data);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject personFindAll(int limit) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.PERSON_LIMIT, limit+"");
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_PERSON_FIND_ALL, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
}
