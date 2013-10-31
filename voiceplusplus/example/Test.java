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
		boolean isOK = false;
		
		// Create server
		Client client = new Client("598956a741545703342f5c6895654570", "598956a741545703342f5c6895654570");
		client.setServer("192.168.1.2", 8888, "1", Constants.TEXT_DEPENDENT);
		
		// Create Person object
		Person person = new Person(client, "18959200000", "Mobile");
		
		// Create Speech
		Speech speech = new Speech("wav/pcm", 8000, true);		
		speech.setData(readWavform("./wav/0-9.5.wav"));
		speech.setVerify(false);
		
		// Create Person
//		isOK = person.create();
		// Delete Person
//		isOK = person.delete();
		// Add Speech to person
//		isOK = person.addSpeech(speech);
		// Remove Speech from person
//		isOK = person.removeSpeech(speech);
		// Remove all speeches from person
//		isOK = person.removeSpeeches();
		// Get all speeches from person
//		List<Speech> list = person.getSpeeches(); System.out.println(list);
		// Register voiceprint for speaker
//		isOK = client.registerVoiceprint(person); // asynchronize
		// Update voiceprint for speaker
//		isOK = client.updateVoiceprint(person);	// asynchronize		
		// Verify speaker's voiceprint
		VerifyRes res = new VerifyRes();
		isOK = client.verifyVoiceprint(person, speech, res); 
		System.out.println("Result:"+String.valueOf(isOK)+"\tSimilarity:"+String.valueOf(res.getSimilarity()));

		System.out.println(person.getId() + ":"+String.valueOf(isOK));
		System.out.println(person.getId() + ":"+client.getLastErr());
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