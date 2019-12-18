package py.org.atom.heart.project.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class FilterField extends DataBase{

	public static final String EQ="=";
	public static final String NEQ="<>";
	public static final String GT=">";
	public static final String GE=">=";
	public static final String LT="<";
	public static final String LE="<=";
	public static final String LIKE="like";
	public static final String IS_NOT_NULL="is not null";
	public static final String IS_NULL="is null";
	
	private String label;
	private String key;
	private String operator;
	private String parmameter;
	private boolean required = false;
	private Integer size;
	private List<SelectItem> options = new ArrayList<SelectItem>();
	
	public FilterField() {	
	}
	public FilterField(String label, String key, String operator, @SuppressWarnings("rawtypes") Class c, String parameter) {
		this.label = label;
		this.key = key;
		this.operator = operator;
		this.type = c;
		this.required = false;
		this.parmameter = parameter;
	}
	public FilterField(String label, String key, String operator, @SuppressWarnings("rawtypes") Class c, boolean required, String parameter) {
		this.label = label;
		this.key = key;
		this.operator = operator;
		this.type = c;
		this.required = required;
		this.parmameter = parameter;
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
	public String getParmameter() {
		return parmameter;
	}
	public void setParmameter(String parmameter) {
		this.parmameter = parmameter;
	}
	public void addOption(SelectItem o) {
		if(o == null) return;
		this.options.add(o);
	}
	
}
