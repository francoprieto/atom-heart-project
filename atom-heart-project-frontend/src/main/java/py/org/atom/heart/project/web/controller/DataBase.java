package py.org.atom.heart.project.web.controller;

import java.math.BigDecimal;
import java.util.Date;

import py.org.atom.heart.project.FrontendBase;

public class DataBase extends FrontendBase{

	@SuppressWarnings("rawtypes")
	protected Class type;
	protected String stringValue;
	protected Integer integerValue;
	protected Long longValue;
	protected BigDecimal bigDecimalValue;
	protected Double doubleValue;
	protected Character charValue;
	protected Date dateValue;
	@SuppressWarnings("rawtypes")
	public Class getType() {
		return type;
	}
	@SuppressWarnings("rawtypes")
	public void setType(Class type) {
		this.type = type;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public Integer getIntegerValue() {
		return integerValue;
	}
	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}
	public Long getLongValue() {
		return longValue;
	}
	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}
	public BigDecimal getBigDecimalValue() {
		return bigDecimalValue;
	}
	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		this.bigDecimalValue = bigDecimalValue;
	}
	public Double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public Character getCharValue() {
		return charValue;
	}
	public void setCharValue(Character charValue) {
		this.charValue = charValue;
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	public Object getValue() {
		if(this.type == String.class) return this.stringValue;
		else if(this.type == Integer.class) return this.integerValue;
		else if(this.type == Long.class) return this.longValue;
		else if(this.type == BigDecimal.class) return this.bigDecimalValue;
		else if(this.type == Double.class) return this.doubleValue;
		else if(this.type == Character.class) return this.charValue;
		else if(this.type == Date.class) return this.dateValue;
		return null;
	}
}
