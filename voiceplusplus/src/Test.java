import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import utils.Constants;
import client.Client;
import client.VerifyRes;
import model.Person;
import model.Speech;

public class Test {
	
	/**
     * @param args
     * @throws IOException 
     * @throws ParseException 
     */
	public static void main(String[] args) {
		int ret = Constants.RETURN_FAIL;
		
		// Create server
		Client client = new Client("1ee0d9b01e8d92a155597785e0b7074e", "1ee0d9b01e8d92a155597785e0b7074e");
		client.setServer("115.28.177.226", 11638, "1", Constants.TEXT_DEPENDENT);
		
		// Create Person object
		Person person = new Person(client, "18959211638", "¿Óœ°√Ù");
		
		// Create Speech
		Speech speech = new Speech("wav/pcm", 8000, true);		
		speech.setData(readWavform("./wav/0-9.4.wav"));
		speech.setVerify(false);
		
		// Create Person
//		ret = person.create();
		// Delete Person
//		ret = person.delete();
		// Get Information
//		ret = person.getInfo(); System.out.println("Register:"+String.valueOf(person.getFlag()));
		// Add Speech to person
//		ret = person.addSpeech(speech);
		// Remove Speech from person
//		ret = person.removeSpeech(speech);
		// Remove all speeches from person
//		ret = person.removeSpeeches();
		// Get all speeches from person
//		List<Speech> list = person.getSpeeches(); System.out.println(list);
		// Register voiceprint for speaker
//		ret = client.registerVoiceprint(person); // asynchronize
		// Update voiceprint for speaker
//		ret = client.updateVoiceprint(person);	// asynchronize		
		// Verify speaker's voiceprint;
		VerifyRes res = new VerifyRes();
		ret = client.verifyVoiceprint(person, speech, res); 
		System.out.println("Result:"+String.valueOf(res.getResult())+"\tSimilarity:"+String.valueOf(res.getSimilarity()));

		System.out.println(person.getId() + ":" + String.valueOf(ret));
		System.out.println(person.getId() + ":" + person.getLastErr() + ":" + client.getLastErr());
	}	
	
	public static byte[] readWavform(String filename) {

		int regLen = 0;	
		byte[] regbuffer = null;
		try {
			FileInputStream inputsteam = new FileInputStream(new File(filename));			
			inputsteam.skip(100);
			
			regLen = inputsteam.available() - 100;
			regbuffer = new byte[regLen];
			if ((regLen = inputsteam.read(regbuffer, 0, regLen))<0) {
				System.out.println("error when read pcm file.");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return regbuffer;
	}
}