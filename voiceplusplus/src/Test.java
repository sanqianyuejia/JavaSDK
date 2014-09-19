import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		int ret = -1;		
		String idString = "1e24cf708a14ce81";			// 群组编号
		String nameString = "test";			// 说话人别名，同一群组内必须唯一
		String pwdString = "*";	// 口令内容	
		
		// Create server
		Client client = new Client("fd80fa1b9077742eaa517986baff0c00", "fd80fa1b9077742eaa517986baff0c00");
		client.setServer("127.0.0.1", 81, "1", Constants.TEXT_DEPENDENT);
		
		// Delete Person
		Person person = new Person(client, idString, nameString);
		if ((ret = person.delete()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));			
		}
		
		// Create Person
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {
			if ( (ret = person.create()) != Constants.RETURN_SUCCESS) {
				System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			}
		}
		
		// Create Speech
		Speech speech = new Speech("pcm/raw", 16000, true);		
		speech.setRule(pwdString);
		
		// Add Speech to person
		if (person.getFlag() == false) {
			for (String filepath : args) {
				String strRule = filepath.substring(filepath.length()-8, filepath.length()-4);
				speech.setRule(strRule);
				speech.setData(readWavform("wav/"+filepath));		// readWavform是读文件到byte缓冲的函数
				if ((ret = person.addSpeech(speech)) != Constants.RETURN_SUCCESS) {
					System.err.println(person.getLastErr()+":"+String.valueOf(ret));
				}
			}
			
			// Register voiceprint for speaker
			if ((ret = client.registerVoiceprint(person)) != Constants.RETURN_SUCCESS) {
				System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			}
			
			// Output result
			System.out.println(person.getId()+"\t"+person.getName()+": Register voiceprint success.");
		}
		
		// Verify voiceprint for speaker
		VerifyRes res = new VerifyRes();
		speech.setRule("5318"); 
		speech.setData(readWavform("wav/ver_4digits_5318.wav"));
		if ((ret = client.verifyVoiceprint(person, speech, res)) != Constants.RETURN_SUCCESS) {
			System.err.println(client.getLastErr()+":"+String.valueOf(ret));
		}
		
		// Output result
		System.out.println(person.getId()+"\t"+person.getName()+": "+res.getResult()+"-"+res.getSimilarity());
		
		// Identify voiceprint for speaker
		if ((ret = client.identifyVoiceprint_2(person, speech, res)) != Constants.RETURN_SUCCESS) {
			System.err.println(client.getLastErr()+":"+String.valueOf(ret));
		}
		
		// Output result
		System.out.println(person.getId()+"\t"+person.getName()+": "+res.getResult()+"-"+res.getSimilarity());
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