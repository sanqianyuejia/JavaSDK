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
		String idString = "6db60c3d588f3f40";			// 群组编号
		String nameString = "lixm";			// 说话人别名，同一群组内必须唯一
		String pwdString = "*";	// 口令内容	
		
		// Create server
		Client client = new Client("53de3f4d0d34cb2f86d01d0cfd21f138", "53de3f4d0d34cb2f86d01d0cfd21f138");
		client.setServer("114.215.103.99", 11638, "1", Constants.TEXT_DEPENDENT);
		
		// Delete Person
		Person person = new Person(client, idString, nameString);
		person.setPassType(Constants.VOICEPRINT_TYPE_RANDOM_DIGITS);	// 随机数字口令
		if ((ret = person.delete()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));			
		}
		
		// Create Person
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {
			if ( (ret = person.create()) != Constants.RETURN_SUCCESS) {
				System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			}
		}
		
		// 获取person对应的系统信息
		if ((ret = client.getSysInfo(person)) != Constants.RETURN_SUCCESS) {
			System.err.println(client.getLastErr()+":"+String.valueOf(ret));			
		}
		
		// Create Speech
		Speech speech = new Speech("pcm/raw", 16000, true);		
		speech.setRule(pwdString);
		
		// Add Speech to person
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));
		} else if (person.getFlag() == false) {
			System.out.println(person.getId()+"\t"+person.getName()+"\t"+person.getStep()+"/"+client.getRegSteps());
			for (String filepath : args) {
				if ( (ret = person.getAuthCode()) != Constants.RETURN_SUCCESS) {
					System.err.println(person.getLastErr()+":"+String.valueOf(ret));
				} else {
					speech.setRule(person.getAuthCodeString());
					speech.setData(readWavform("wav/"+filepath));		// readWavform是读文件到byte缓冲的函数
					if ((ret = person.addSpeech(speech)) != Constants.RETURN_SUCCESS) {
						System.err.println(person.getLastErr()+":"+String.valueOf(ret));
					}
				}
			}
			
			// Register voiceprint for speaker
			if ((ret = client.registerVoiceprint(person)) != Constants.RETURN_SUCCESS) {
				System.err.println(client.getLastErr()+":"+String.valueOf(ret));
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