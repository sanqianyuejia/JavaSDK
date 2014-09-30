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
		String idString 	= "632cd5c21aa8dcc0";	// Ⱥ����
		String nameString 	= "ipanel";				// ˵���˱�����ͬһȺ���ڱ���Ψһ
		
		// ����������ʵ��
		Client client = new Client("5aac06153cda21f07aa28269a505c565", "5aac06153cda21f07aa28269a505c565");
		client.setServer("113.98.233.209", 11888, "1");
		
		// ɾ��ָ��˵����
		Person person = new Person(client, idString, nameString);
		person.setPassType(Constants.VOICEPRINT_TYPE_FIXED_TEXT);	// ���ÿ�������Ϊ��������ֿ���
		if ((ret = person.delete()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));			
		}
		
		// ����˵����
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {	// �Ȳ�ѯ�Ƿ����
			if ( (ret = person.create()) != Constants.RETURN_SUCCESS) {
				System.err.println(person.getLastErr()+":"+String.valueOf(ret));
			}
		}
		
		// ��ȡ˵���˶�Ӧ��ϵͳ��Ϣ
		if ((ret = client.getSysInfo(person)) != Constants.RETURN_SUCCESS) {
			// ��һ��δ��ȡ�ɹ�ʱ���ٵ���һ�Σ���������ʱ��������ϵ���δ��ȡ�ɹ�
			if ((ret = client.getSysInfo(person)) != Constants.RETURN_SUCCESS) {
				System.err.println(client.getLastErr()+":"+String.valueOf(ret));
			}
		}
		
		// ��������ʵ��
		Speech speech = new Speech("pcm/raw", 16000, true);		
		
		// Ϊ˵����������������֮ǰ�Ȼ�ȡ˵���˵�ǰ��Ϣ
		if ( (ret = person.getInfo()) != Constants.RETURN_SUCCESS) {
			System.err.println(person.getLastErr()+":"+String.valueOf(ret));
		} else if (person.getFlag() == false) {	// ���δע������
			System.out.println(person.getId()+"\t"+person.getName()+"\t"+person.getStep()+"/"+client.getRegSteps());
			for (String filepath : args) {	// �����ļ��б�
				if ( (ret = person.getAuthCode()) != Constants.RETURN_SUCCESS) {
					System.err.println(person.getLastErr()+":"+String.valueOf(ret));
				} else {
					speech.setRule(person.getAuthCodeString());
					speech.setData(readWavform("wav/"+filepath));		// readWavform�Ƕ��ļ���byte����ĺ���
					if ((ret = person.addSpeech(speech)) != Constants.RETURN_SUCCESS) {
						System.err.println(person.getLastErr()+":"+String.valueOf(ret));
					}
				}
			}
			
			// ע������
			if ((ret = client.registerVoiceprint(person)) != Constants.RETURN_SUCCESS) {
				System.err.println(client.getLastErr()+":"+String.valueOf(ret));
			}
			
			System.out.println(person.getId()+"\t"+person.getName()+": Register voiceprint success.");
		}
		
		// ����ȷ��
		VerifyRes res = new VerifyRes();
		speech.setRule("5318"); 
		speech.setData(readWavform("wav/ver_4digits_5318.wav"));
		if ((ret = client.verifyVoiceprint(person, speech, res)) != Constants.RETURN_SUCCESS) {
			System.err.println(client.getLastErr()+":"+String.valueOf(ret));
		}

		System.out.println(person.getId()+"\t"+person.getName()+": "+res.getResult()+"-"+res.getSimilarity());
		
		// ���Ʊ���
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