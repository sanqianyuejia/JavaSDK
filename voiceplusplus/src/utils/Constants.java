package utils;

public class Constants {
	public static final int TEXT_DEPENDENT = 0;	// Text dependent
	public static final int TEXT_INDEPENDENT = 1;	// Text independent
	public static final int TEXT_PROMPT = 2;	// Text prompt
	
    public static final String API_SECRET = "api_secret";
    public static final String API_KEY = "api_key";
    public static final String ID = "id";
    public static final String IDENTY = "identy";
    public static final String MD5 = "md5";
    public static final String NAME = "name";
    public static final String TAG = "tag";
    public static final String CODEC = "codec";
    public static final String SAMPLERATE = "sr";
    public static final String SAMPLE_RATE = "samplerate";
    public static final String FILEPARAM = "file";
    public static final String UPDATE = "update";    
    public static final String VERIFY = "verify";
    public static final String SUCCESS = "success";
    public static final String RESULT = "result";
    public static final String SIMILARITY = "similarity";
    public static final String ERROR = "error";
    public static final String ERROR_CODE = "error_code";
    
    // server: http://192.168.1.253:8888/1/
    public static final String URL_PERSON_CREATE = "/person/create.json";
    public static final String URL_PERSON_DELETE = "/person/delete.json";
    
    public static final String URL_SPEECH_ADD = "/speech/add.json";
    public static final String URL_SPEECH_FIND = "/speech/find.json";
    public static final String URL_SPEECH_FIND_PERSON = "/speech/find_person.json";
    public static final String URL_SPEECH_DELETE = "/speech/delete.json";
    public static final String URL_SPEECH_DELETE_PERSON = "/speech/delete_person.json";
    
    public static final String URL_MODEL_REGISTER = "/model/train.json";
    public static final String URL_MODEL_VERIFY = "/model/verify.json";
}
