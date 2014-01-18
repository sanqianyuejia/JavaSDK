import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import utils.Constants;
import client.Client;
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
		String idString = "10000";			// 群组编号
		String nameString = "test";			// 说话人别名，同一群组内必须唯一
		String pwdString = "0123456789";	// 口令内容	
		
		// Create server
		Client client = new Client("1ee0d9b01e8d92a155597785e0b7074e", "1ee0d9b01e8d92a155597785e0b7074e");
		client.setServer("openapi.shengwenyun.com", 80, "1", Constants.TEXT_DEPENDENT);
		
		// Create person
		Person person = new Person(client, idString, nameString);
		if ( (ret = person.create()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			return;
		}
		
		// Create Speech
		Speech speech = new Speech("pcm/raw", 8000, true);		
		speech.setRule(pwdString);
		
		// Add Speech to person
		for (String filepath : args) {
			speech.setData(readWavform(filepath));		// readWavform是读文件到byte缓冲的函数
			if ((ret = person.addSpeech(speech)) != Constants.RETURN_SUCCESS) {
				System.err.println(person.getLastErr()+":"+String.valueOf(ret));
				return;
			}
		}
		
		// Register voiceprint for speaker
		if ((ret = client.registerVoiceprint(person)) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			return;
		}
		
		// Output result
		System.out.println(person.getId()+"\t"+person.getName()+": Register voiceprint success.");
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