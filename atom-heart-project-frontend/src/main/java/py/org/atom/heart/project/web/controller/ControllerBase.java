package py.org.atom.heart.project.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;

import py.org.atom.heart.project.FrontendBase;

public abstract class ControllerBase<T> extends FrontendBase{
	@Inject
    	protected Conversation conversation;
	public static final char FORM = 'f';
	public static final char LIST = 'l';
	public static final int DATA_TABLE_DEFAULT_PAGE_SIZE=15;
	private char screen = LIST;
	private T instance;
	private List<FilterField> filters = new ArrayList<FilterField>();
	private LinkedHashMap<String, Boolean> sort = new LinkedHashMap<String,Boolean>(); 
	private String user;
	private LazyDataModel<T> dataList = null;
	private int pageSize=DATA_TABLE_DEFAULT_PAGE_SIZE;
	public void searchAction() {
		// TODO: do search
	}
	public void clearAction() {
		// TODO: do clear
	}
	public void removeAction() {
		// TODO: do remove
	}
	public void editAction() {
		// TODO: do edit
	}
	public void newAction() {
		// TODO: do new
	}
	public void updateAction() {
		// TODO: do update
	}
	protected abstract T newInstance();
	
	public void addSortAction(String key) {
		if(key == null || key.trim().length() <= 0) return;
		if(this.sort.containsKey(key)) {
			if(this.sort.get(key)) this.sort.put(key,false); else this.sort.put(key,true);
		}else this.sort.put(key, true);
	}
	public void remSortAction(String key) {
		if(key == null || key.trim().length() <= 0) return;
		if(!this.sort.containsKey(key)) return;
		this.sort.remove(key);
	}
	public void sortAction() {
		if(this.sort == null || this.sort.size() <= 0) return;
		this.searchAction();
	}	
	public void start() {
		if(this.conversation.isTransient()) this.conversation.begin();
		if(this.conversation != null) this.conversation.setTimeout(10800000);		
	}
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
	
	public T getInstance() {
		return instance;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}

	public char getScreen() {
		return screen;
	}

	public void setScreen(char screen) {
		this.screen = screen;
	}
	
	public LazyDataModel<T> getDataList() {
		return dataList;
	}

	public void setDataList(LazyDataModel<T> dataList) {
		this.dataList = dataList;
	}
	protected void addFilter(FilterField o){
		if(o == null) return;
		this.filters.add(o);
	}

	public List<FilterField> getFilters() {
		return filters;
	}
	public void setFilters(List<FilterField> filters) {
		this.filters = filters;
	}
	public LinkedHashMap<String, Boolean> getSort() {
		return sort;
	}
	public void setSort(LinkedHashMap<String, Boolean> sort) {
		this.sort = sort;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	private void showMsg(Severity s, String msg){
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
	
}
