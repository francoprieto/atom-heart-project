package py.org.atom.heart.project.web.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import py.org.atom.heart.project.service.ServiceBase;

public class LazyModelBase<T> extends LazyDataModel<T>{

	private static final long serialVersionUID = -5024269911529228249L;
	
	protected ServiceBase<T> service;
	protected String query;
	protected String count;
	protected List<FilterField> filters;
	protected LinkedHashMap<String, Boolean> sort;
	
	public LazyModelBase(ServiceBase<T> service, String query, List<FilterField> filters, LinkedHashMap<String, Boolean> sort) {
		this.service = service;
		this.filters = filters;
		this.sort = sort;
		this.query = query;
	}
	
	public List<T> load(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta){
		Map<String,Map<String,Object>> cq = this.getFullQuery(true);
		Map<String,Map<String,Object>> rq = this.getFullQuery(false);
		Long count = null;
		for(String k : cq.keySet()) count = this.service.count(k, cq.get(k));
		if(count == null) return null;
		this.setRowCount(count.intValue());
		this.setPageSize(pageSize);
		String fullQuery = null;
		for(String k: rq.keySet()) fullQuery = k;
		if(count.intValue() > pageSize) {
			try {
				return this.service.getList(fullQuery, rq.get(fullQuery), first, first + pageSize);
			}catch(IndexOutOfBoundsException ex) {
				return this.service.getList(fullQuery, rq.get(fullQuery), first, first + (count.intValue() % pageSize));
			}
		}else {
			first = 0;
			return this.service.getList(fullQuery, rq.get(fullQuery), first, count.intValue());
		}
	}
	
	protected Map<String,Map<String,Object>> getFullQuery(boolean count) {
		String q = count ? this.count : this.query;
		String w = "";
		Map<String,Object> prms = new HashMap<String, Object>();
		for(FilterField ff : this.filters) {
			if(ff.getOperator().trim().equals(FilterField.IS_NOT_NULL)
					|| ff.getOperator().trim().equals(FilterField.IS_NULL)) 
				prms.put(ff.getParmameter(), ff.getValue());
			if(w.trim().length() > 0) w += " and ";
			w += ff.getKey() + " " + ff.getOperator() + " " + ff.getParmameter();
		}
		if(w.trim().length() > 0) w = " Where " + w;
		String o = "";
		for(String k : this.sort.keySet()) {
			if(o.trim().length() > 0) o += ", ";
			o += k + " " + (this.sort.get(k) ? "asc" : "desc");
		}
		if(o.trim().length() > 0) o = " Order by " + o;
		Map<String,Map<String,Object>> out = new HashMap<String,Map<String,Object>>();
		if(count) out.put(q + " " + w, prms); else out.put(q + " " + w + " " + o, prms);
		return out;
	}
	
}
