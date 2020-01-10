package py.org.atom.heart.project.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.model.LazyDataModel;

import py.org.atom.heart.project.FrontendBase;
import py.org.atom.heart.project.service.ServiceBase;

public abstract class ControllerBase<T,V> extends FrontendBase{
	
	@Inject
    protected Conversation conversation;
	protected String screen = Constants.LIST;
	protected T instance;
	private List<FilterField> filters = new ArrayList<FilterField>();
	private List<ListField> listFields = new ArrayList<ListField>();
	private List<FormField> formFields = new ArrayList<FormField>();
	protected Map<String,String> labels = new HashMap<String,String>();
	private Map<String,List<ViewField>> viewFields = new LinkedHashMap<String,List<ViewField>>();
	private LinkedHashMap<String, Boolean> sort = new LinkedHashMap<String,Boolean>(); 
	private String sortKey;
	protected String user;
	protected String baseQuery = Constants.BASE_QUERY;
	protected String baseCount = Constants.BASE_COUNT;
	private LazyDataModel<T> dataList = null;
	private int pageSize=Constants.DATA_TABLE_DEFAULT_PAGE_SIZE;
	protected V service; // The class that extends this shall inject the @EJB stateless
	protected abstract T newInstance();
	public abstract void init();
	public abstract void searchAction();
	public abstract void viewAction();
	public abstract void clearAction();
	public abstract void removeAction();
	public abstract void editAction();
	public abstract void newAction();
	public abstract void updateAction();
    protected ExcelOptions xlsOpts;
    protected PDFOptions pdfOpts;	
	protected Class clazz;
	public void start() {
		if(this.conversation.isTransient())	this.conversation.begin();
		if(this.conversation != null) this.conversation.setTimeout(10800000);
	}	
	protected void search() {
		if(this.baseQuery.trim().equals(Constants.BASE_QUERY)) this.baseQuery = this.baseQuery.replace("[entity]", this.clazz == null ? "" : this.clazz.getCanonicalName());
		if(this.baseCount.trim().equals(Constants.BASE_COUNT)) this.baseCount = this.baseCount.replace("[entity]", this.clazz == null ? "" : this.clazz.getCanonicalName());
		ServiceBase sb = (ServiceBase) this.service;
		this.dataList = new LazyModelBase<T>(sb, this.baseQuery, this.baseCount, filters, sort);
	}
	protected void clear() {
		if(this.screen.trim().equals(Constants.LIST)) {
			this.dataList = null;
			this.filters = new ArrayList<FilterField>();
			this.listFields = new ArrayList<ListField>();
			this.formFields = new ArrayList<FormField>();
			this.viewFields = new LinkedHashMap<String,List<ViewField>>();
			this.sort = new LinkedHashMap<String,Boolean>();
			init();
		}
		if(this.screen.trim().equals(Constants.FORM) || this.screen.trim().equals(Constants.VIEW)) {
			this.instance = null;
			this.screen = Constants.LIST;
		}
	}
	public void addSortAction() {
		String key = this.sortKey;
		if(key == null || key.trim().length() <= 0) return;
		if(this.sort.containsKey(key)) {
			if(this.sort.get(key)) this.sort.put(key,false); else this.sort.put(key,true);
		}else this.sort.put(key, true);
	}
	public void remSortAction() {
		String key = this.sortKey;
		if(key == null || key.trim().length() <= 0) return;
		if(!this.sort.containsKey(key)) return;
		this.sort.remove(key);
	}
	public void sortAction() {
		if(this.sort == null || this.sort.size() <= 0) return;
		this.searchAction();
	}	

	public T getInstance() {
		return instance;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
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
	public List<ListField> getListFields() {
		return listFields;
	}
	public void setListFields(List<ListField> listFields) {
		this.listFields = listFields;
	}
	public void addListField(ListField o) {
		if(o == null) return;
		this.listFields.add(o);
	}
	public List<FormField> getFormFields() {
		return formFields;
	}
	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}
	public void addFormField(FormField o) {
		if(o == null) return;
		this.formFields.add(o);
	}
	public Map<String, List<ViewField>> getViewFields() {
		return viewFields;
	}
	public void setViewFields(Map<String, List<ViewField>> viewFields) {
		this.viewFields = viewFields;
	}
	public void addViewField(String tab, ViewField o) {
		if(o == null) return;
		List<ViewField> vfs = this.viewFields.get(tab);
		if(vfs == null) vfs = new ArrayList<ViewField>();
		vfs.add(o);
		this.viewFields.put(tab, vfs);
	}
	protected void addLabel(String key, String value) {
		if(key == null || value == null) return;
		this.labels.put(key, value);
	}
	protected void remLabel(String key) {
		if(key == null || !this.labels.containsKey(key)) return;
		this.labels.remove(key);
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
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public ExcelOptions getXlsOpts() {
    	if(this.xlsOpts == null) {
	        xlsOpts = new ExcelOptions();
	        xlsOpts.setFacetBgColor("#CCCCCC");
	        xlsOpts.setFacetFontSize("10");
	        xlsOpts.setFacetFontColor("#000000");
	        xlsOpts.setFacetFontStyle("BOLD");
	        xlsOpts.setCellFontColor("#000000");
	        xlsOpts.setCellFontSize("10");
    	}	
		return xlsOpts;
	}
	public void setXlsOpts(ExcelOptions xlsOpts) {
		this.xlsOpts = xlsOpts;
	}
	public PDFOptions getPdfOpts() {
    	if(this.pdfOpts == null) {
	        pdfOpts = new PDFOptions();
	        pdfOpts.setFacetBgColor("#CCCCCC");
	        pdfOpts.setFacetFontSize("10");
	        pdfOpts.setFacetFontColor("#000000");
	        pdfOpts.setFacetFontStyle("BOLD");
	        pdfOpts.setCellFontColor("#000000");
	        pdfOpts.setCellFontSize("10");
    	}		
		return pdfOpts;
	}
	public void setPdfOpts(PDFOptions pdfOpts) {
		this.pdfOpts = pdfOpts;
	}
	public Map<String, String> getLabels() {
		return labels;
	}
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}
}
