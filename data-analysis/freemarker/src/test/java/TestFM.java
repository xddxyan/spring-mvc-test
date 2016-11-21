import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elements.GenBean;

public class TestFM {
	private static final String kDaoPkg = "com.metasoft.model.metadata";
	private static final String kMapperPkg = "com.metasoft.model.mapper";
	private static final String kDaoServicPkg = "com.metasoft.service.dao";
	private static final String kSrcPath = "../../src/main/java/";
	public List<GenBean> daos = new ArrayList<GenBean>();
	public List<GenBean> mappers = new ArrayList<GenBean>();
	
	public static void main(String[] args) {
		final Logger log = LoggerFactory.getLogger(TestFM.class);
		new TestFM(){{
	    	File dir = new File(FreeMakerUtil.getPkgPath(kDaoPkg, kSrcPath));
	        File[] files = dir.listFiles(); 
	        for (File f : files) { 
	            if (f.isDirectory()) { 
	                continue;
	            } else {
	            	String clzFile = FilenameUtils.getBaseName(f.getName());
	                String clzName = kDaoPkg +"."+clzFile;
	    			try {
	    				Class<?> clz = Class.forName(clzName);
	    				addDao(clz);
	    				addMapper(clz);
	    				log.info("clzName: {}", clz);
	    			} catch (ClassNotFoundException e) {
	    				e.printStackTrace();
	    			}           
	            } 
	        }
			//FreeMakerUtil.genFiles(daos,"dao.ftl");
			FreeMakerUtil.genFiles(mappers,"mapper.ftl");
		}};
	}
	
	public void addDao( Class<?> clz ){
		GenBean bean = new GenBean();
		bean.setPkg(kDaoServicPkg);//clz.getPackage().getName()
		bean.setClazz(clz.getSimpleName()+"DaoService");
		bean.setClazzDesc(clz.getSimpleName());
		
		daos.add(bean);
	}
	public void addMapper( Class<?> clz ){
		GenBean bean = new GenBean();
		bean.setPkg(kMapperPkg);
		bean.setClazz(clz.getSimpleName()+"Mapper");
		bean.setClazzDesc(clz.getSimpleName());
		mappers.add(bean);
	}

}
