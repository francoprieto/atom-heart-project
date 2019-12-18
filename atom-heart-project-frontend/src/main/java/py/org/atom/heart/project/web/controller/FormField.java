package py.org.atom.heart.project.web.controller;

import java.math.BigDecimal;
import java.util.Date;

public class FormField extends DataBase{
	private String label;
	private String key;
	private boolean required;
	private Integer size; 
	public FormField() {	
	}
	public FormField(String label, String key, String value, boolean required) {
		this.label = label;
		this.key = key;
		this.stringValue = value;
		this.type = String.class;
	}	
	public FormField(String label, String key, String operator, Integer value) {
		this.label = label;
		this.key = key;
		this.integerValue = value;
		this.type = Integer.class;
	}
	public FormField(String label, String key, String operator, Long value) {
		this.label = label;
		this.key = key;
		this.longValue = value;
		this.type = Long.class;
	}	
	public FormField(String label, String key, String operator, Double value) {
		this.label = label;
		this.key = key;
		this.doubleValue = value;
		this.type = Double.class;
	}
	public FormField(String label, String key, String operator, BigDecimal value) {
		this.label = label;
		this.key = key;
		this.bigDecimalValue = value;
		this.type = BigDecimal.class;
	}
	public FormField(String label, String key, String operator, Character value) {
		this.label = label;
		this.key = key;
		this.charValue = value;
		this.type = Character.class;
	}
	public FormField(String label, String key, String operator, Date value) {
		this.label = label;
		this.key = key;
		this.dateValue = value;
		this.type = Date.class;
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
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}	
}
