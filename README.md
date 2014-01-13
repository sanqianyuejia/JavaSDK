# 声纹识别开发平台Java SDK
作为一种核心的语音技术，声纹识别技术在通信技术、语音技术得到飞速发展的今天有着巨大的应用潜力，相关产品已经应用于公共安全、社保身份认证、社区矫正，金融投资服务领域的身份辨认，智能手机移动商务和行业用户满意度调查等不同领域。

声纹识别开发平台由『快商通信息技术有限公司』研发，平台提供一个分布式声纹识别解决方案，可根据应用规模适当增减服务器，灵活性非常强大，在以下领域有成功案例：

- 司法社区矫正
- 远程教育身份认证
- 高校教学实验平台

![](voiceplusplus.png)

## 平台特征介绍

- 文本相关，方便快捷。快商通声纹识别系统基于文本相关声纹识别，用户只需要一句简短口令，即可实时验证身份。
- 领先算法，精准识别。采用独特的声纹识别处理算法，声纹验证准确率高达99.5%，支持防录音攻击、环境降噪、动态数字口令。
- 分布式部署，扩展性强。分布式部署声纹识别服务器，支持上百万的声纹容量，易容扩展，而且可以根据应用规模适当调整硬件配置。
- 简约API，快速集成。提供一组RestFul风格API，支持多终端、多平台集成调用。可针对特定语言定制SDK开发包。

## 快速上手
请参考以下步骤进行开发

1. 申请api key和secret（请发邮件至lixm@kuaishang.cn，邮件标题置为“快商通声纹识别开发平台：API调用申请”）
2. 参照example目录中的例子，编写应用程序
3. 上线运营。上线之前，请联系管理员（lixm@kuaishang.cn）

## 开发示例——注册说话人声纹

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
        
        // Create server
        Client client = new Client("1ee0d9b01e8d92a155597785e0b7074e", "1ee0d9b01e8d92a155597785e0b7074e");
        client.setServer("openapi.shengwenyun.com", 80, "1", Constants.TEXT_DEPENDENT);
        
        // Create person
        Person person = new Person(client, "123456789", "test");
        if ( (ret = person.create()) != Constants.RETURN_SUCCESS) {
            System.err.println(person.getLastErr()+":"+String.valueOf(ret));
            return;
        }
        
        // Create Speech
        Speech speech = new Speech("pcm/raw", 8000, true);      
        speech.setRule("123456789");
        
        // Add Speech to person
        for (String filepath : args) {
            speech.setData(readWavform(filepath));      // readWavform是读文件到byte缓冲的函数
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
        System.out.println(person.getId()+": Register voiceprint success.");
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

## 错误代码对照表
<table cellpadding="0" cellspacing="1" border="0" style="width:100%" class="tableborder">
<tbody><tr>
<th>错误代码</th>
<th>错误信息</th>
<th>详细描述</th>
</tr>

<tr>
<td class="td"><strong>1001</strong></td>
<td class="td">Auth fail</td>
<td class="td">Key或Secret错误</td>
</tr>

<tr>
<td class="td"><strong>1002</strong></td>
<td class="td">network error</td>
<td class="td">网络错误</td>
</tr>

<tr>
<td class="td"><strong>1003</strong></td>
<td class="td">internal error</td>
<td class="td">未知错误</td>
</tr>

<tr>
<td class="td"><strong>1004</strong></td>
<td class="td">argument error</td>
<td class="td">参数错误</td>
</tr>

<tr>
<td class="td"><strong>1005</strong></td>
<td class="td">database error</td>
<td class="td">数据库错误</td>
</tr>

<tr>
<td class="td"><strong>1006</strong></td>
<td class="td">system file lost</td>
<td class="td">系统文件丢失</td>
</tr>

<tr>
<td class="td"><strong>2001</strong></td>
<td class="td">person already exist</td>
<td class="td">说话人已存在</td>
</tr>

<tr>
<td class="td"><strong>2002</strong></td>
<td class="td">person not exist</td>
<td class="td">说话人不存在</td>
</tr>

<tr>
<td class="td"><strong>2003</strong></td>
<td class="td">has no persons</td>
<td class="td">没有任何说话人</td>
</tr>

<tr>
<td class="td"><strong>2004</strong></td>
<td class="td">person has no speeches</td>
<td class="td">该说话人没有登记语音</td>
</tr>

<tr>
<td class="td"><strong>3001</strong></td>
<td class="td">voiceprint state error</td>
<td class="td">声纹训练状态错误</td>
</tr>

<tr>
<td class="td"><strong>3002</strong></td>
<td class="td">voiceprint already registered</td>
<td class="td">说话人声纹已注册</td>
</tr>

<tr>
<td class="td"><strong>3003</strong></td>
<td class="td">voiceprint has no speeches</td>
<td class="td">该说话人不存在登记语音</td>
</tr>

<tr>
<td class="td"><strong>3004</strong></td>
<td class="td">verify error</td>
<td class="td">验证异常</td>
</tr>

<tr>
<td class="td"><strong>3005</strong></td>
<td class="td">voiceprint not trained</td>
<td class="td">没有登记声纹</td>
</tr>

<tr>
<td class="td"><strong>3006</strong></td>
<td class="td">identify error</td>
<td class="td">声纹辨认发生错误</td>
</tr>

<tr>
<td class="td"><strong>4001</strong></td>
<td class="td">speech too short</td>
<td class="td">语音太短</td>
</tr>

<tr>
<td class="td"><strong>4002</strong></td>
<td class="td">speech too long</td>
<td class="td">语音太长</td>
</tr>

<tr>
<td class="td"><strong>4003</strong></td>
<td class="td">speech sample rate error</td>
<td class="td">语音采样率错误，目前仅支持8k</td>
</tr>

<tr>
<td class="td"><strong>4004</strong></td>
<td class="td">speech already exist</td>
<td class="td">语音已存在</td>
</tr>

<tr>
<td class="td"><strong>4005</strong></td>
<td class="td">speech not exist</td>
<td class="td">语音不存在</td>
</tr>

<tr>
<td class="td"><strong>4006</strong></td>
<td class="td">speech process error</td>
<td class="td">语音处理错误</td>

<tr>
<td class="td"><strong>5001</strong></td>
<td class="td">asr recognize error</td>
<td class="td">语音识别错误</td>
</tr>

<tr>
<td class="td"><strong>5002</strong></td>
<td class="td">asr not matched</td>
<td class="td">语音内容不匹配</td>

</tr>

</tbody></table>
	

## 下载

### 从 release 版本下载

下载地址：https://github.com/sanqianyuejia/JavaSDK/releases

## 许可证

Copyright (c) 2013 快商通信息技术有限公司

基于 MIT 协议发布:

* [www.opensource.org/licenses/MIT](http://www.opensource.org/licenses/MIT)
