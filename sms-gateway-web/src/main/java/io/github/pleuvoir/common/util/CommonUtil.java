package io.github.pleuvoir.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 公共调取方法
 */
public class CommonUtil {
	
	public static final String SEPARATOR_POINT = ".";
	
	public static final String ASC = "asc";
	
	/**
	 * 截取文件名后缀
	 * @param originalFilename
	 * @return
	 */
	public static String getExtensionName(String originalFilename) {   
		String fileSuffix = "";
        if (StringUtils.isNotBlank(originalFilename)) {   
            int dot = originalFilename.lastIndexOf(SEPARATOR_POINT);   
            if ((dot >-1) && (dot < (originalFilename.length() - 1))) {   
            	fileSuffix = originalFilename.substring(dot + 1);   
            }   
        }   
        return fileSuffix;   
    }   
	
	
	/**
	 * 是否正序
	 */
	public static boolean isAsc(String arg) {
		return StringUtils.equals(ASC, arg);
	}
	
}
