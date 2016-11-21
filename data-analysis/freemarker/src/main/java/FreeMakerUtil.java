import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elements.GenBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerUtil {
	private static Logger logger = LoggerFactory.getLogger(FreeMakerUtil.class);

	private static Configuration freemarker_cfg = null;
	private static final String kTplPath = "/ftl/";
	private static final String kOutputPath = "output/";
	
	public static String getPkgPath(String pkg , String basePath){
		return basePath + pkg.replace(".", "/");// 生成路径
	}
	//生成所有文件
	public static void genFiles(List<GenBean> beans, String tpl) {
		for (GenBean bean : beans) {
			HashMap<String, GenBean> prop = new HashMap<String, GenBean>();
			prop.put("vo", bean);
			String pkgOutputPath = getPkgPath(bean.getPkg(), kOutputPath);
			genFile(tpl, kTplPath, prop, pkgOutputPath, bean.getClazz());
		}

	}

	/**
	 * 生成静态文件.
	 * 
	 * @param tplName
	 *            模板文件名,相对htmlskin路径,例如"/tpxw/view.ftl"
	 * @param propMap
	 *            用于处理模板的属性Object映射
	 * @param pkgPath
	 *            要生成的静态文件的路径,相对设置中的根路径,例如 "/tpxw/1/2005/4/"
	 * @param className
	 *            
	 * @param tplPath
	 *            模板路径
	 * @return boolean true代表生成文件成功
	 */
	public static void genFile(String tplName, String tplPath, Map<String, GenBean> propMap,
			String pkgPath, String className) {
		try {

			// 如果根路径存在,则递归创建子目录
			FileUtils.forceMkdir(new File(pkgPath));
			
			//free marker 处理替换标签
			StringWriter strWriter = new StringWriter();
			File tplFilePath = new File(System.getProperty("user.dir")+tplPath) ;
			Template t = getFreeMarkerCFG( tplFilePath ).getTemplate(tplName);
			t.process(propMap, strWriter);
			strWriter.flush();
			strWriter.close();
			
			String outputName = pkgPath + "/" + className + ".java";
			File outputfile = new File(outputName);
			FileUtils.writeStringToFile(outputfile, processCustom(outputfile, strWriter.getBuffer().toString()));
		} catch (TemplateException e) {
			System.out.print(e.getMessage());
		} catch (IOException e) {
			System.out.print(e.getMessage());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	private static String processCustom(File outputfile, String outputData) throws IOException{
		if(outputfile.exists())
		{
			String prev = FileUtils.readFileToString(outputfile);logger.debug(prev);
			final String kImport = "//===YOUR IMPORT===";
			final String kMethod = "//===YOUR METHOD===";
			int begin = prev.indexOf(kImport), end = prev.lastIndexOf(kImport);
			if(end>begin){
				String custom = prev.substring(begin, end-2);//escape /r/n
				outputData = outputData.replaceFirst(kImport, custom);logger.info(custom);
			}
			begin = prev.indexOf(kMethod); end = prev.lastIndexOf(kMethod);
			if(end>begin){
				String custom = prev.substring(begin, end-2);
				outputData = outputData.replaceFirst(kMethod, custom);logger.info(custom);
			}
		}
		return outputData;
	}


	/**
	 * 
	 * 获取freemarker的配置. freemarker本身支持classpath,目录和从ServletContext获取.
	 * 
	 * @param templateFilePath
	 *            获取模板路径
	 * @return Configuration 返回freemaker的配置属性
	 * @throws Exception
	 */
	private static Configuration getFreeMarkerCFG(File templateFilePath)
			throws Exception {
		if (null == freemarker_cfg) {

			try {
				freemarker_cfg = new Configuration();
				freemarker_cfg.setDirectoryForTemplateLoading( templateFilePath);
			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}
		}
		return freemarker_cfg;
	}
}
