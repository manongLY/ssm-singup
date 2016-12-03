package cn.ly.util.encrypt;

import cn.ly.util.MD5Code;

public class MyPasswordEncrypt {
	private static final String salt = "bWxkbmphdmE=";
	
	
	//加密     加密形式password{{salt}}
	public static String encryptPassword(String password){
		return new MD5Code().getMD5ofStr(password+"({"+salt+"})");
	}
}
