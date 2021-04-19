package py.org.atom.heart.project.web.controller;

import java.io.Serializable;

public class ViewField extends DataBase implements Serializable{
	private static final long serialVersionUID = -1713120126947830179L;
	private String label;
	public ViewField(String label, String value) {
		this.label = label;
		this.stringValue = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
