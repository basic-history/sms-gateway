package io.github.pleuvoir.message.gateway.util.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


public class HibernateValidatorUtils {

	private static Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();
	
	private BindingResult bindingResult;
	private boolean existLengthError = true;
	private String resultErrorInfo;
	
	public static HibernateValidatorUtils create(){
		HibernateValidatorUtils validatorUtils = new HibernateValidatorUtils();
		return validatorUtils;
	}
	
	public HibernateValidatorUtils setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
		return this;
	}
	
	public HibernateValidatorUtils generate(){
		StringBuffer buffer = new StringBuffer();
		List<FieldError> fieldErrors = this.bindingResult.getFieldErrors();
		for (FieldError error : fieldErrors) {
			buffer.append("字段：").append(error.getField()).append("，错误原因：").append(error.getDefaultMessage()).append(";");
			if(StringUtils.equals(error.getCode(), "Length")){
				this.existLengthError = false;
			}
		}
		this.resultErrorInfo = buffer.toString();
		return this;
	}

	public boolean isNotExistLengthError() {
		return existLengthError;
	}

	public String getResultErrorInfo() {
		return resultErrorInfo;
	}
	
	public String getRandomFieldError() {
		return this.bindingResult.getFieldError().getField() + this.bindingResult.getFieldError().getDefaultMessage();
	}
	
	/**
	 * 校验某个实体类
	 * @param obj
	 * @return
	 */
	public static <T> ValidationResult validateEntity(T obj){
		ValidationResult result = new ValidationResult();
		 Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);
		 if( !CollectionUtils.isEmpty(set) ){
			 result.setHasErrors(true);
			 Map<String,String> errorMsg = new HashMap<String,String>();
			 for(ConstraintViolation<T> cv : set){
				 errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			 }
			 result.setErrorMsg(errorMsg);
		 }
		 return result;
	}
	
	/**
	 * 按指定的分组校验某个实体
	 * @param obj
	 * @param groups
	 * @return
	 */
	public static <T> ValidationResult validateEntity(T obj, Class<?>... groups){
		ValidationResult result = new ValidationResult();
		 Set<ConstraintViolation<T>> set = validator.validate(obj, groups);
		 if( !CollectionUtils.isEmpty(set) ){
			 result.setHasErrors(true);
			 Map<String,String> errorMsg = new HashMap<String,String>();
			 for(ConstraintViolation<T> cv : set){
				 errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			 }
			 result.setErrorMsg(errorMsg);
		 }
		 return result;
	}
	
	/**
	 * 校验某个实体类中的某个字段
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static <T> ValidationResult validateProperty(T obj,String propertyName){
		ValidationResult result = new ValidationResult();
		 Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName,Default.class);
		 if( !CollectionUtils.isEmpty(set) ){
			 result.setHasErrors(true);
			 Map<String,String> errorMsg = new HashMap<String,String>();
			 for(ConstraintViolation<T> cv : set){
				 errorMsg.put(propertyName, cv.getMessage());
			 }
			 result.setErrorMsg(errorMsg);
		 }
		 return result;
	}
	
	/**
	 * 按指定的分组校验某个实体类中的某个字段
	 * @param obj
	 * @param propertyName
	 * @param groups
	 * @return
	 */
	public static <T> ValidationResult validateProperty(T obj, String propertyName, Class<?>... groups){
		ValidationResult result = new ValidationResult();
		 Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, groups);
		 if( !CollectionUtils.isEmpty(set) ){
			 result.setHasErrors(true);
			 Map<String,String> errorMsg = new HashMap<String,String>();
			 for(ConstraintViolation<T> cv : set){
				 errorMsg.put(propertyName, cv.getMessage());
			 }
			 result.setErrorMsg(errorMsg);
		 }
		 return result;
	}
}
