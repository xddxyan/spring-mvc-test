package elements;

import java.util.ArrayList;
import java.util.List;

public class GenBean {
    private String pkg;
    private String clazz;
    private String clazzDesc ="";
    private String parentClazz;
    private String idSeq;
    private String event="";
    private String table ="";
    private List<Attribute> attributes;

    public GenBean(){
        attributes = new ArrayList<Attribute>();
    }
    public GenBean(String pkg,String name,String desc){
        this.pkg = pkg;
        this.clazz = name;
        this.clazzDesc = desc;
        attributes = new ArrayList<Attribute>();
    }

    public void addItem(String type,String name,String desc){
        attributes.add(new Attribute(type,name,desc));
    }
    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getClazzDesc() {
		return clazzDesc;
	}
	public void setClazzDesc(String clazzDesc) {
		this.clazzDesc = clazzDesc;
	}
	public String getParentClazz() {
		return parentClazz;
	}
	public void setParentClazz(String parentClazz) {
		this.parentClazz = parentClazz;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getIdSeq() {
		return idSeq;
	}
	public void setIdSeq(String idSeq) {
		this.idSeq = idSeq;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
}