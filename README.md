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

## 开发示例

    func (this *AddController) Post() {
            var user User
            form := this.GetInput(&user)
            if !form.Validates() {
                    return 
            }
            models.UserInsert(&user)
            this.Ctx.Redirect(302, "/admin/index")
    }

## 下载

### 从 release 版本下载

下载地址：https://github.com/sanqianyuejia/JavaSDK/releases

## 许可证

Copyright (c) 2013 快商通信息技术有限公司

基于 MIT 协议发布:

* [www.opensource.org/licenses/MIT](http://www.opensource.org/licenses/MIT)
