package py.org.atom.heart.project.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.model.SelectItem;

public class FilterField extends DataBase implements Serializable{

	private static final long serialVersionUID = -648042054760834155L;
	public static final String EQ="=";
	public static final String NEQ="<>";
	public static final String GT=">";
	public static final String GE=">=";
	public static final String LT="<";
	public static final String LE="<=";
	public static final String LIKE="like";
	public static final String IS_NOT_NULL="is not null";
	public static final String IS_NULL="is null";
	public static final String IS="is";
	private String label;
	private String key;
	private String operator;
	private String parameter;
	private boolean required = false;
	private boolean caseSensitive = false;
	private boolean param = false;
	private Integer size;
	private List<SelectItem> options = new ArrayList<SelectItem>();
	
	public FilterField() {	
	}
	public FilterField(String label, String key, String operator, @SuppressWarnings("rawtypes") Class c, String parameter) {
		this.label = label;
		this.operator = operator;
		this.type = c;
		this.required = false;
		if(parameter != null && this.type == String.class && !this.caseSensitive && !parameter.contains("upper")) parameter = "upper(:" + parameter + ")";
		this.parameter = parameter;
		if(key != null && this.type == String.class && !this.caseSensitive && !key.contains("upper")) key = "upper(" + key + ")";
		this.key = key;
	}
	public FilterField(String label, String key, String operator, @SuppressWarnings("rawtypes") Class c, boolean required, String parameter) {
		this.label = label;
		this.operator = operator;
		this.type = c;
		this.required = required;
		if(parameter != null && this.type == String.class && !this.caseSensitive && !parameter.contains("upper")) parameter = "upper(:" + parameter + ")";
		this.parameter = parameter;
		if(key != null && this.type == String.class && !this.caseSensitive && !key.contains("upper")) key = "upper(" + key + ")";
		this.key = key;
	}	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		if(key != null && this.type == String.class && !this.caseSensitive && !key.contains("upper")) key = "upper(" + key + ")";
		this.key = key;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public List<SelectItem> getOptions() {
		return options;
	}
	public void setOptions(List<SelectItem> options) {
		this.options = options;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		if(parameter != null && this.type == String.class && !this.caseSensitive && !parameter.contains("upper")) parameter = "upper(:" + parameter + ")";
		this.parameter = parameter;
	}
	public void addOption(SelectItem o) {
		if(o == null) return;
		this.options.add(o);
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	public void convertDate(boolean first) {
		if(this.dateValue == null) return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateValue);
		if(first) {
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 1);
		}else{
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);			
		}
		this.dateValue = cal.getTime();
	}
	public boolean isParam() {
		return param;
	}
	public void setParam(boolean param) {
		this.param = param;
	}	
}
