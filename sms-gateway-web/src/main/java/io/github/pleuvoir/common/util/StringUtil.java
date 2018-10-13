package io.github.pleuvoir.common.util;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


public class StringUtil {

	private static final char[] HEX = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static final char[] BINARY = new char[] { '0', '1' };
	
	/**
	 * 按指定的倍数右填充
	 * @param s 字符串
	 * @param fill 填充字符
	 * @param multiple 整数倍
	 * @return
	 */
	public static String fillRight(String s, char fill, int multiple){
		int len = s.length();
		int mo = len % multiple;
		if(mo==0)
			return s;
		len = len + (multiple - mo);
		
		StringBuilder d = new StringBuilder(s);
        while (d.length() < len)
            d.append('0');
        return d.toString();
	}

	/**
	 * 将二进制字符串转换为十六进制字符串
	 * 
	 * @param bin
	 * @return
	 */
	public static String bin2hex(String bin) {
		if (bin == null || "".equals(bin)) {
			return null;
		}
		return Long.toHexString(Long.parseLong(bin, 2));
	}

	/**
	 * 将byte数组转化为String类型的十六进制编码格式 本方法实现的思路是： 1）每位byte数组转换为2位的十六进制数
	 * 2）将字节左移4位取得高四位字节数值，获取对应的char类型数组编码 3）将字节与0x0F按位与，从而获取第四位的字节，同样获取编码
	 */
	public static String hex(byte[] b) {
		char[] rs = new char[b.length * 2];
		for(int i=0; i<b.length; i++){
			rs[i*2] = HEX[b[i] >> 4 & 0x0F];	// &0x0F的目的是为了转换负数
			rs[i*2+1] = HEX[b[i] & 0x0F];
		}
		return new String(rs);
	}
	/**
	 * 将byte数组中指定的位数范围转为String类型的十六进制格式，指定的范围包前不包后
	 * @param b 需要转换的数字
	 * @param begin 开始的数组下标
	 * @param end 截止的数组下标，不包含
	 * @return
	 */
	public static String hex(byte[] b, int begin, int end){
		StringBuilder accum = new StringBuilder();
		for (int i=begin; i<end; i++) {
			accum.append(HEX[b[i] >> 4 & 0x0F]);// &0x0F的目的是为了转换负数
			accum.append(HEX[b[i] & 0x0F]);
		}
		return accum.toString();
	}
	
	public static String binary(byte[] bts) {
		StringBuffer accum = new StringBuffer();
		for (byte bt : bts) {
			accum.append(binary(bt));
		}
		return accum.toString();
	}

	/**
	 * 本方法修改于Integer.toBinaryString
	 * 参数的每个字节都会转化为8位2进制字符串，如1会转换为00000001
	 * @param bt
	 * @return
	 */
    private static String binary(byte bt){
    	int num = bt&0xFF;
    	char[] arrayOfChar = new char[8];
		int i = 8;
		for(int times=0;times<8;times++){
			arrayOfChar[(--i)] = BINARY[(num & 0x01)];
			num >>>= 1;
		}
		return new String(arrayOfChar);
    }

	/**
	 * 左补位
	 * @param origStr 原字符串
	 * @param length 填补后的字符串位数
	 * @param fill 填补的字符
	 * @return
	 */
	public static String padLeft(String origStr,int length, String fill){
		int len = origStr.length();
		if(len != 0 && len==length){
			return origStr;
		}
		if(len < 1 || len > length || fill.length()>1){
			throw new IllegalArgumentException();
		}
		byte[] src = origStr.getBytes();
		byte[] rs = new byte[length];
		
		System.arraycopy(src, 0, rs, length - len, len);
		Arrays.fill(rs, 0, length-len, fill.getBytes()[0]);
		return new String(rs);
    }
    
	/**
	 * 右补位
	 * @param origStr 原字符串
	 * @param length 填补的位数
	 * @param fill 填补的字符
	 * @return
	 */
    public static String padRight(String origStr,int length, String fill){
    	int len = origStr.length();
    	if(len == length){
    		return origStr;
    	}else if(length<1 || len > length || fill.length()>1){
    		throw new IllegalArgumentException();
    	}
    	byte[] src = origStr.getBytes();
    	byte[] rs = new byte[length];
    	
    	System.arraycopy(src, 0, rs, 0, len);
    	Arrays.fill(rs, len, length, fill.getBytes()[0]);
    	return new String(rs);
    }
 
    
    public static String of(String s1, String s2){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5, String s6){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	b.append(s6);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5, String s6, String s7){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	b.append(s6);
    	b.append(s7);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	b.append(s6);
    	b.append(s7);
    	b.append(s8);
    	return b.toString();
    }
    
    
    /**
     * 首字母转小写
     * @return
     */
    public static String lowerCaseInitial(String val){
    	if(val==null){
    		return val;
    	}
    	char[] chars = val.toCharArray();
    	char lowerChar = Character.toLowerCase(chars[0]);
    	chars[0] = lowerChar;
    	return new String(chars);
    }
    
    /**
     * 首字母转大写
     * @param val
     * @return
     */
    public static String upperCaseInitial(String val){
    	if(val==null){
    		return val;
    	}
    	char[] chars = val.toCharArray();
    	char upperChar = Character.toUpperCase(chars[0]);
    	chars[0] = upperChar;
    	return new String(chars);
    }

	/**
	 * 拼接地址
	 * @param url
	 * @param addurl
	 * @return
	 */
	public static String contactUrl(String url, String addurl) {
		StringBuilder b = new StringBuilder();
		if(StringUtils.isNotBlank(url)) {
			b.append(url);
			if(url.endsWith("/")) {
				b.deleteCharAt(url.length()-1);
			}
		}
		if(StringUtils.isNotBlank(addurl)) {
			if(!addurl.startsWith("/")) {
				b.append("/");
			}
			b.append(addurl);
		}
		return b.toString();
	}
    
}
