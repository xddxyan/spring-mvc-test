import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import elements.GenBean;

public class TestFM {
	private static final String kMapperPkg = "com.metasoft.model.mapper";
	public List<GenBean> beans = new ArrayList<GenBean>();
	
	public static void main(String[] args) {
		
    	File dir = new File(FreeMakerUtil.getPkgPath(kMapperPkg, "../../src/main/java/"));
        File[] files = dir.listFiles(); 
        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
                continue;
            } else { 
                //String strFileName = files[i].getAbsolutePath().toLowerCase();
                String strFileName = files[i].getName();
                String[] strs = strFileName.split("\\.");
                filelist.add(packageName + strs[0]);                    
            } 
        }
		//TestFM fm = new TestFM();
		//fm.addBean(schema.getDoaminModel());
		//FreeMakerUtil.genFiles(fm.beans);
	}
	
	public void addBean( ){
		GenBean bean = new GenBean();
		bean.setPkg("");//com.pkg
		bean.setClazz("name");//getClass().getSimpleName();
		bean.setParentClazz("SuperClazz");//getClass().getSuperclass().getSimpleName()
		bean.setClazzDesc("clazz annotation");
		bean.setEvent(null);
		
		//for(ModelAttribute attr : model.getAttributes()){
			//bean.addItem(attr.getType().getName(), attr.getName(), null);
		//}
		
		//beans.add(bean);
	}

}
