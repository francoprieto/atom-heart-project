package py.org.atom.heart.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FrontendBase {

	protected String user;
	protected Map<String,String> labels = new HashMap<String,String>();
	
	protected void error(String msg) {
		this.showMsg(FacesMessage.SEVERITY_ERROR, msg);
	}
	protected void info(String msg) {
		this.showMsg(FacesMessage.SEVERITY_INFO, msg);
	}	
	protected void warn(String msg) {
		this.showMsg(FacesMessage.SEVERITY_WARN, msg);
	}
	protected void fatal(String msg) {
		this.showMsg(FacesMessage.SEVERITY_FATAL, msg);
	}	
	protected void fatal(Exception ex) {
		this.fatal(ex.getMessage());
		ex.printStackTrace();
	}
	protected void showMsg(Severity s, String msg){
		if(msg == null)	return;
		String type = "";
		if(s.equals(FacesMessage.SEVERITY_ERROR)) type = "ERROR: ";
		if(s.equals(FacesMessage.SEVERITY_INFO)) type = "INFO: ";
		if(s.equals(FacesMessage.SEVERITY_WARN)) type = "WARN: ";
		if(s.equals(FacesMessage.SEVERITY_FATAL)) type = "FATAL ERROR: ";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		FacesMessage fm = new FacesMessage(s, type + (this.user == null ? "" : this.user + " ") + sdf.format(new Date()), msg);
		FacesContext.getCurrentInstance().addMessage(null,fm);		
	}		
	public Map<String, String> getLabels() {
		return labels;
	}
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}	
}
