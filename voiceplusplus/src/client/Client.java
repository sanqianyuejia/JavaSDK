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
	 * @param version
	 *            : 引擎版本号
	 * @param type
	 *            : 备用字段
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
	public synchronized int setServer(String host, int port, String version,
			int type) {
		this.version = version;
		this.type = type;
		this.server = "http://" + host + ":" + String.valueOf(port) + "/"
				+ this.version;

		return Constants.RETURN_SUCCESS;
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
	public synchronized int verifyVoiceprint(Person person, Speech speech,
			VerifyRes res) {
		int ret = Constants.RETURN_SUCCESS;

		if (!person.getId().isEmpty()) {
			JSONObject result = getClientService().clientVerifyVoiceprint(
					person.getId(), speech.getCodec(), speech.getSampleRate(),
					speech.getVerify(), speech.getData());

			if (!result.getBoolean(Constants.SUCCESS)) {
				ret = result.getInt(Constants.ERROR_CODE);
				super.setLastErr(result.getString(Constants.ERROR));
				super.setErrCode(ret);
			} else {
				res.setResult(result.getBoolean(Constants.RESULT));
				res.setSimilarity(result.getDouble(Constants.SIMILARITY));
			}
		} else {
			ret = Constants.LOCAL_ID_NULL;
			super.setLastErr("id is empty");
			super.setErrCode(0);
		}

		return ret;
	}

	public synchronized int identifyVoiceprint(Person person, Speech speech,
			VerifyRes res) {
		int ret = Constants.RETURN_SUCCESS;

		JSONObject result = getClientService().clientIdentifyVoiceprint(
				speech.getCodec(), speech.getSampleRate(), speech.getData());

		if (!result.getBoolean(Constants.SUCCESS)) {
			ret = result.getInt(Constants.ERROR_CODE);
			super.setLastErr(result.getString(Constants.ERROR));
			super.setErrCode(ret);
		} else {
			person.setId(result.getString(Constants.ID));
			person.setName(result.getString(Constants.NAME));
			res.setResult(result.getBoolean(Constants.RESULT));
			res.setSimilarity(result.getDouble(Constants.SIMILARITY));
		}

		return ret;
	}

	/**
	 * Update speaker's voiceprint
	 * 
	 * @param voiceprint
	 * @return
	 */
	public synchronized int updateVoiceprint(Person person) {
		int ret = Constants.RETURN_SUCCESS;

		if (!person.getId().isEmpty()) {
			JSONObject result = getClientService().clientRegisterVoiceprint(
					person.getId(), person.getName(), true);

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

	/**
	 * Rgister speaker's voiceprint
	 * 
	 * @param voiceprint
	 * @return
	 */
	public synchronized int registerVoiceprint(Person person) {
		int ret = Constants.RETURN_SUCCESS;

		if (!person.getId().isEmpty()) {
			JSONObject result = getClientService().clientRegisterVoiceprint(
					person.getId(), person.getName(), false);

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

	public void setType(int type) {
		this.type = type;
	}

	public String getType() {
		if (this.type == Constants.TEXT_INDEPENDENT) {
			return Constants.TEXT_INDEPENDENT_STR;
		} else if (this.type == Constants.TEXT_PROMPT) {
			return Constants.TEXT_PROMPT_STR;
		} else {
			return Constants.TEXT_DEPENDENT_STR;
		}
	}
}