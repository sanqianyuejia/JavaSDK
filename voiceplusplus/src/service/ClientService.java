package service;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;
import utils.HttpURLUtils;
import net.sf.json.JSONObject;

public class ClientService extends BaseService {
	
	public JSONObject clientRegisterVoiceprint(String id, boolean bUpdate) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		parameters.put(Constants.UPDATE, String.valueOf(bUpdate));
		
		String tokenResult = HttpURLUtils.doPost(super.getClient().getServerString()+Constants.URL_MODEL_REGISTER, parameters);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
	
	public JSONObject clientVerifyVoiceprint(String id, String codec, int sr, boolean bVerify, byte[] data) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.API_KEY, super.getClient().getKey());
		parameters.put(Constants.API_SECRET, super.getClient().getSecret());
		parameters.put(Constants.ID, id);
		parameters.put(Constants.CODEC, codec);
		parameters.put(Constants.SAMPLERATE, String.valueOf(sr));
		
		String tokenResult = HttpURLUtils.doUploadFile(super.getClient().getServerString()+Constants.URL_MODEL_VERIFY, parameters, 
				Constants.FILEPARAM, "./testfile.wav", "multipart/form-data;", data);
		JSONObject tokenJson = (JSONObject) JSONObject.fromObject(tokenResult);
		
		return tokenJson;
	}
}
