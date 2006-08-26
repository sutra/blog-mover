/**
 * Created on 2006-8-27 上午12:13:31
 */
package com.redv.blogmover.bsps.csdn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.BooleanHolder;
import javax.xml.rpc.holders.ByteArrayHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogMoverRuntimeException;

import net.csdn.passport.UserLoginService;
import net.csdn.passport.UserLoginServiceSoap;

/**
 * @author <a href="zhoushuqun@gmail.com">Shutra Zhou</a>
 * 
 */
public class CSDNLoginByUserLoginService {
	private Log log = LogFactory.getLog(getClass());

	private UserLoginServiceSoap userLoginServiceSoap;

	private URL url;

	private StringHolder clientKey = new StringHolder();

	private String loginName;

	private String password;

	private String verifyCode;

	public CSDNLoginByUserLoginService() {
		try {
			url = new URL(
					"http://passport.csdn.net/WebService/UserLoginService.asmx");
		} catch (MalformedURLException e) {
			throw new BlogMoverRuntimeException(e);
		}
		init();
	}

	public CSDNLoginByUserLoginService(String url) throws MalformedURLException {
		this.url = new URL(url);
		init();
	}

	private void init() {
		UserLoginService userLoginService = new net.csdn.passport.UserLoginServiceLocator();
		try {
			userLoginServiceSoap = userLoginService
					.getUserLoginServiceSoap(url);
		} catch (ServiceException e) {
			throw new BlogMoverRuntimeException(e);
		}
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param verifyCode
	 *            the verifyCode to set
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public byte[] getIdentifyingCodeImage() throws BlogMoverException {
		ByteArrayHolder userPreLoginResult = new ByteArrayHolder();
		try {
			this.userLoginServiceSoap.userPreLogin("CSDN 用户登录Demo 1.0", userPreLoginResult,
					clientKey);
			log.debug("clientKey: " + clientKey.value);
		} catch (RemoteException e) {
			throw new BlogMoverException(e);
		}
		return userPreLoginResult.value;
	}

	public void login() throws BlogMoverException {
		BooleanHolder userLoginResult = new BooleanHolder();
		StringHolder errorInfo = new StringHolder();
		StringHolder warningInfo = new StringHolder();
		try {
			log.debug("clientKey: " + clientKey.value);
			userLoginServiceSoap.userLogin(loginName, password, verifyCode,
					clientKey.value, userLoginResult, errorInfo, warningInfo);
			if (!userLoginResult.value) {
				throw new BlogMoverException("登录失败。Error: " + errorInfo.value
						+ ", warning: " + warningInfo.value);
			}
		} catch (RemoteException e) {
			throw new BlogMoverException(e);
		}
	}

	public static void main(String[] args) throws BlogMoverException,
			IOException {
		CSDNLoginByUserLoginService login = new CSDNLoginByUserLoginService();
		byte[] bytes = login.getIdentifyingCodeImage();
		FileOutputStream fos = new FileOutputStream("/tmp/csdn-login.png");
		fos.write(bytes);
		fos.close();
		System.out.print("Enter the verify code: ");
		int ch;
		StringBuffer verifyCode = new StringBuffer();
		while ((ch = System.in.read()) != '\n') {
			verifyCode.append(ch);
		}
		System.out.println("verifyCode: " + ch);
		login.setLoginName("redv");
		login.setPassword("wangjing");
		login.setVerifyCode(verifyCode.toString());
		login.login();
	}
}
