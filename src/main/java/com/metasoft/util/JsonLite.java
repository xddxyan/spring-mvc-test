package com.metasoft.util;

/**
 * @author james
 *simply provide a way of translating a series key-values into a json string 
 */
public class JsonLite {
	public enum Type {Brace, Bracket};
	private Type type;
	private StringBuilder sb;
	private boolean nonempty;
	public JsonLite() {
		type = Type.Brace;
		sb = new StringBuilder("{");
		nonempty = false;
	}
	public JsonLite(Type type) {
		this.type = type;
		if(type == Type.Brace){
			sb = new StringBuilder("{");
		}else if(type == Type.Bracket){
			sb = new StringBuilder("[");
		}else{
			sb = new StringBuilder("{");
		}
		
		nonempty = false;
	}
	
	public void appendNodeString(String str){
		if(nonempty){
			sb.append(",");
		}
		sb.append(str);
		nonempty = true;
	}
	
	public void appendKeyValue(String key, String value){
		if(nonempty){
			sb.append(",");
		}
		sb.append("\"").append(key).append("\":\"").append(value).append("\"");
		nonempty = true;
	}
	
	public void appendValString(String key, String json){
		if(nonempty){
			sb.append(",");
		}
		sb.append("\"").append(key).append("\":").append(json);
		nonempty = true;
	}

	public String convert2String() {
		if(type == Type.Brace){
			return sb.append("}").toString();
		}else if(type == Type.Bracket){
			return sb.append("]").toString();
		}else{
			return sb.append("}").toString();
		}
	}
	
	public static void main(String[] args) {
		long before = System.currentTimeMillis();
		
		JsonLite jl = new JsonLite();
		//Map<String,String> map = new HashMap<String, String>();
		for(int i=0;i<9;i++){
			jl.appendKeyValue(String.valueOf(i), "Test text");
		}
		//String json = JsonUtils.toJson(map);//999999->1893ms
		String json = jl.convert2String();//999999->190ms
		
		long now = System.currentTimeMillis();
		System.out.println(now-before);
		System.out.println(json);
	}
}
