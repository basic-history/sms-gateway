package io.github.pleuvoir.kit;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

public class PropertiesWrap extends Properties {
	
	private static final long serialVersionUID = 1148989482896455722L;
	
	public PropertiesWrap(){
		super();
	}
	
	public PropertiesWrap(Properties pro){
		super(pro);
	}

	/**
	 * 获取Integer类型的数据，若数据格式不可转换为Integer类型，将抛出异常{@link NumberFormatException}
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key){
		String val = super.getProperty(key);
		return Integer.valueOf(val);
	}
	
	/**
	 * 获取Integer类型的数据，并提供默认的值，若数据格式不可转换为Integer类型或者为null时，将会返回默认值
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public Integer getInteger(String key, Integer defaultVal){
		Integer i = null;
		try{
			i = getInteger(key);
		}catch(NumberFormatException e){
		}
		if(i == null){
			i = defaultVal;
		}
		return i;
	}
	
	/**
	 * 获取Long类型的数据，若数据格式不可转换为Long类型，将抛出异常{@link NumberFormatException}
	 * @param key
	 * @return
	 */
	public Long getLong(String key){
		String val = super.getProperty(key);
		return Long.valueOf(val);
	}
	
	/**
	 * 获取Long类型的数据，并提供默认的值，若数据格式不可转换为Long类型或者为null时，将会返回默认值
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public Long getLong(String key, Long defaultVal){
		Long i = null;
		try{
			i = getLong(key);
		}catch(NumberFormatException e){
		}
		if(i == null){
			i = defaultVal;
		}
		return i;
	}
	
	/**
	 * 获取Double类型的数据，若数据格式不可转换为Double类型，将抛出异常{@link NumberFormatException}
	 * @param key
	 * @return
	 */
	public Double getDouble(String key){
		String val = super.getProperty(key);
		return Double.valueOf(val);
	}
	
	/**
	 * 获取Double类型的数据，并提供默认的值，若数据格式不可转换为Double类型或者为null时，将会返回默认值
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public Double getDouble(String key, Double defaultVal){
		Double i = null;
		try{
			i = getDouble(key);
		}catch(NumberFormatException e){
		}
		if(i == null){
			i = defaultVal;
		}
		return i;
	}
	
	/**
	 * 获取BigDecimal类型的数据，若数据格式不可转换为BigDecimal类型，将抛出异常{@link NumberFormatException}
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key){
		String val = super.getProperty(key);
		return new BigDecimal(val);
	}
	
	/**
	 * 获取BigDecimal类型的数据，并提供默认的值，若数据格式不可转换为BigDecimal类型或者为null时，将会返回默认值
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal defaultVal){
		BigDecimal i = null;
		try{
			i = getBigDecimal(key);
		}catch(NumberFormatException e){
		}
		if(i == null){
			i = defaultVal;
		}
		return i;
	}
	
	/**
	 * 获取Boolean类型的数据，若数据格式不可转换为Boolean类型，将返回false
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key){
		String val = super.getProperty(key);
		return Boolean.valueOf(val);
	}
	
	/**
	 * 获取Boolean类型的数据，若不存在key，则返回默认值
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public Boolean getBoolean(String key, Boolean defaultVal){
		String val = super.getProperty(key);
		if(StringUtils.isBlank(val)){
			return defaultVal;
		}
		return Boolean.valueOf(val);
	}
	
	/**
	 * 获取String类型的数据
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return super.getProperty(key);
	}
	
	/**
	 * 获取String类型的数据，并提供默认值，若为null时，将返回默认值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue) {
		return super.getProperty(key, defaultValue);
	}

	@Override
	public synchronized void load(Reader reader) throws IOException {
		super.load(reader);
	}

	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		super.load(inStream);
	}

}
