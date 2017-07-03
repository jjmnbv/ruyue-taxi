package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	//合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "";
	
	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOjV55GV7qTtZnD76N/I8wSz8RtLUtFT9k3RISi6SwVtMQQvwHNr/Pe+gN0MW5eOqzikRFbQ3B16dW1QUVu5cY7XkYlIldAK1JQn4sio5sqJgxA0Club2cxKGgUNioj2y4kPYhN4ZIrhTdz9pPuDhuOpbxXQ1QsD1WWADwYakwHXAgMBAAECgYAxnvy2Ez0D2zBc3eL4ZmwcUXkN9xSUVg+E8A/gDSvV4Tp0CPU75ATKi8gM1AhlGVu2O5Pw6JwwkBuci2R7Zt8jSQqyLL6z4jdmuWluZCTaKhaVAu1kGwVIPR1l5ranOvajKQzQ5WNoqOJVzRUEAqTXPeOSmn5q+zzCZ6WOKAUbaQJBAPjgl2J1KSkQN4PMbn/ZYVAkAn0rvFjDaHlqnNpD2nJc6pZ85ulSDnQqt8w/h9qoVWF+GdnDv5VhWSSBPSqgBBMCQQDvf8hk6TIEqPrEmZdCKJZQeGtBe92Ekc/kvmGnouCP8fGGu+X4cIflFPI2a/mmeJNmVYcUk6QmVXIOrt9JvNutAkAn0hemw0RAs72OMwmDH074uapESNksAqgWtT4/lhe/sKpARd/UeTKi16rs3UVpcQGoRbrxIubmidrvglY9GblNAkEAyDuFRxjQAKVmQshGdcGJKm4C/hSY9yURMqUY8BZ0uOQGkia19ife9d+1QVq0tkFIut32uXVWX9ZALZZ2iCelYQJBAJ/WU9KgCvsfNuhNFIiBOxNhE/0QddTV+32bgwCSOHnWTksFJMpgnTIoEfFMhXkxYmVBGxLquJzDEQecPIe5ifg=";
	
	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="C://";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "UTF-8";

	// 接收通知的接口名
//	public static String service = "mobile.securitypay.pay";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

