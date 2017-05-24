////接口类型：互亿无线触发短信接口，支持发送验证码短信、订单通知短信等。
//// 账户注册：请通过该地址开通账户http://sms.ihuyi.com/register.html
//// 注意事项：
////（1）调试期间，请用默认的模板进行测试，默认模板详见接口文档；
////（2）请使用APIID（查看APIID请登录用户中心->验证码、通知短信->帐户及签名设置->APIID）及 APIkey来调用接口，APIkey在会员中心可以获取；
////（3）该代码仅供接入互亿无线短信接口参考使用，客户可根据实际需要自行编写；
//package com.fkjslee.schoolv3.student.function;
//import java.io.IOException;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//
//public class SendSms {
//
//	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
//
//	/**
//	 * 输入参数为手机号码
//	 * 返回的是验证码
//	 * author:suoxin
//	 * date: 2017年4月17日下午9:59:11
//	 * return: int
//	 */
//	public int Sendsms(String mobile,int mobile_code) {
//
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod(Url);
//
//		client.getParams().setContentCharset("GBK");
//		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");
//
//
//		String content = "您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。";
//
//		NameValuePair[] data = {//提交短信
//				new NameValuePair("account", "C83514418"), //查看用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
//				new NameValuePair("password", "acb658e2bc36ffd4164e0afd6677c959"),  //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
//				new NameValuePair("mobile", mobile),
//				new NameValuePair("content", content),
//		};
//		method.setRequestBody(data);
//
//		try {
//			client.executeMethod(method);
//			String SubmitResult =method.getResponseBodyAsString();
//			Document doc = DocumentHelper.parseText(SubmitResult);
//			Element root = doc.getRootElement();
//			String code = root.elementText("code");
//			if("2".equals(code)){
//				System.out.println("短信提交成功");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mobile_code;
//	}
//
//}