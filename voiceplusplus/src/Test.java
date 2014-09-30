import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.kuaishangtong.utils.Constants;
import com.kuaishangtong.client.Client;
import com.kuaishangtong.client.VerifyRes;
import com.kuaishangtong.model.Person;
import com.kuaishangtong.model.Speech;

public class Test {
	
	/**
     * @param args
     * @throws IOException 
     * @throws ParseException 
     */
	public static void main(String[] args) {
		int ret 			= -1;		
		String idString 	= "632cd5c21aa8dcc0";	// 群组编号
		String nameString 	= "ipanel";				// 说话人别名，同一群组内必须唯一
		
		// 创建服务器实例
		Client client = new Client("5aac06153cda21f07aa28269a505c565", "5aac06153cda21f07aa28269a505c565");
		client.setServer("113.98.233.209", 11888, "1");
		
		// 删除指定说话人
		Person person = new Person(client, idString, nameString);
		person.setPassType(Constants.VOICEPRINT_TYPE_FIXED_TEXT);	// 设置口令类型为：随机数字口令
		if ((ret = person.delete()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));			
		}
		
		// 创建说话人
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {	// 先查询是否存在
			if ( (ret = person.create()) != Constants.RETURN_SUCCESS) {
				System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			}
		}
		
		// 获取说话人对应的系统信息
		if ((ret = client.getSysInfo(person)) != Constants.RETURN_SUCCESS) {
			// 第一次未获取成功时，再调用一次，避免因暂时性网络故障导致未获取成功
			if ((ret = client.getSysInfo(person)) != Constants.RETURN_SUCCESS) {
				System.err.println(client.getLastErr()+":"+String.valueOf(ret));
			}
		}
		
		// 创建语音实例
		Speech speech = new Speech("pcm/raw", 16000, true);		
		
		// 为说话人添加语音，添加之前先获取说话人当前信息
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));
		} else if (person.getFlag() == false) {	// 如果未注册声纹
			System.out.println(person.getId()+"\t"+person.getName()+"\t"+person.getStep()+"/"+client.getRegSteps());
			for (String filepath : args) {	// 语音文件列表
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
			
			// 注册声纹
			if ((ret = client.registerVoiceprint(person)) != Constants.RETURN_SUCCESS) {
				System.err.println(client.getLastErr()+":"+String.valueOf(ret));
			}
			
			System.out.println(person.getId()+"\t"+person.getName()+": Register voiceprint success.");
		}
		
		// 声纹确认
		VerifyRes res = new VerifyRes();
		speech.setRule("5318"); 
		speech.setData(readWavform("wav/ver_4digits_5318.wav"));
		if ((ret = client.verifyVoiceprint(person, speech, res)) != Constants.RETURN_SUCCESS) {
			System.err.println(client.getLastErr()+":"+String.valueOf(ret));
		}

		System.out.println(person.getId()+"\t"+person.getName()+": "+res.getResult()+"-"+res.getSimilarity());
		
		// 声纹辨认
		if ((ret = client.identifyVoiceprint_2(person, speech, res)) != Constants.RETURN_SUCCESS) {
			System.err.println(client.getLastErr()+":"+String.valueOf(ret));
		}
		
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