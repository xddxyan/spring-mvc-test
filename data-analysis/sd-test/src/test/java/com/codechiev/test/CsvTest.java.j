/**
 * 
 */
package com.codechiev.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechiev.model.ForeignKey;
import com.codechiev.model.metadata.Attribute;
import com.codechiev.model.metadata.Database;
import com.codechiev.model.metadata.Entity;
import com.codechiev.model.metadata.Schema;
import com.codechiev.service.dao.AttributeDaoService;
import com.codechiev.service.dao.DatabaseDaoService;
import com.codechiev.service.dao.EntityDaoService;
import com.codechiev.service.dao.SchemaDaoService;

/**
 * @author james
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/application-dev.xml"})  
@ActiveProfiles("development")
public class CsvTest {
	@Autowired
	EntityDaoService entityDaoService;
	@Autowired
	DatabaseDaoService databaseDaoService;
	@Autowired
	SchemaDaoService schemaDaoService;
	@Autowired
	AttributeDaoService attributeDaoService;
	
	private File csv ;
	//InputStream inputStream = new BufferedInputStream(new FileInputStream(csv));
	//Reader reader = new InputStreamReader(inputStream, Charset.forName( "utf-8"));
	private Reader reader ;
	private Iterable<CSVRecord> records ;
	
	public CsvTest () throws URISyntaxException, IOException {

	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void importAttribute() 
			throws URISyntaxException, IOException, IllegalAccessException, InvocationTargetException {
		List<ForeignKey> fks = attributeDaoService.foreignKey();
		ForeignKey fk = fks.get(0);
		System.out.println(fk.getForeign_table_name());
		
		//attributeDaoService.selectAll(0, 9999);
		csv = new File(CsvTest.class.getResource("/csv/ts_metadata_lm_attribute.csv").toURI());
		reader = new FileReader(csv);
		records = CSVFormat.EXCEL.parse(reader);
		//work with foreign key
		/*int first=0;
		int idPos = -1;
		for (CSVRecord record : records) {
			if(first==0){
				int i=0;
				for(String header : record){
					if(fk.getColumn_name().equals(header.toLowerCase()))
						idPos = i;
					i++;
				}
			}else{
				String id = record.get(idPos);
				Entity en = new Entity();
				en.setEntity_id(id);
				en.setDatabase_id("-1");
			}
			first++;
		}
		*/
		List<String> propertieList = null;
		for (CSVRecord record : records) {
			System.out.println(record.toString());
			
			//headers
			if(null == propertieList){
				propertieList = getHeaders(record);
			}else{
				Map<String,String> properties = assemble(record, propertieList);
				Attribute attr = new Attribute();
				BeanUtils.populate(attr, properties);
				try {
					attributeDaoService.insert(attr);
			    } catch (final DuplicateKeyException e) {
			    	System.out.println("DuplicateKeyException:"+attr.getAttribute_id());
			    } catch (final DataIntegrityViolationException e) {
			    	System.out.println("DataIntegrityViolationException:"+attr.getAttribute_id());
			    }
				
			}
		}
	}
	
	private List<String> getHeaders(CSVRecord record){
		List<String> list = new ArrayList<String>();
		Iterator<String> it = record.iterator();
		while (it.hasNext()) {
			String prop = it.next();
			list.add(prop.toLowerCase());
		}
		return list;
	}
	
	private Map<String,String> assemble(CSVRecord record, List<String> propertieList){
		Map<String,String> properties = new HashMap<String,String>();	
		Iterator<String> propertiesIt = propertieList.iterator();
		Iterator<String> it = record.iterator();
		while (it.hasNext() && propertiesIt.hasNext()) {
			properties.put(propertiesIt.next(), it.next());
		}
		return properties;
	}
	
	public void importEnity() throws URISyntaxException, IOException{
		csv= new File(CsvTest.class.getResource("/csv/entity.csv").toURI());
		reader= new FileReader(csv);
		records= CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
		ArrayList<CSVRecord> list = new ArrayList<CSVRecord>();
		Database db = new Database();
		Entity entity = new Entity();
		Schema schema = new Schema();
		Map<String, CSVRecord> dbmap = new HashMap<String, CSVRecord>();
		Map<String, CSVRecord> scmap = new HashMap<String, CSVRecord>();
		for (CSVRecord record : records) {
			dbmap.put(record.get("DATABASE_ID"), record);
			scmap.put(record.get("SCHEMA"), record);
			list.add(record);
		}
		for(Entry<String, CSVRecord> entry : dbmap.entrySet()){
			try {
				db.setDatabase_id(entry.getKey());
				databaseDaoService.insert(db);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		for(Entry<String, CSVRecord> entry : scmap.entrySet()){
			try {
				schema.setSchema(entry.getKey());
				schema.setDatabase_id(entry.getValue().get("DATABASE_ID"));
				schemaDaoService.insert(schema);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		for (CSVRecord record : list) {
		    entity.setEntity_id(record.get("﻿ENTITY_ID"));
			entity.setEntity_name(record.get("ENTITY_NAME"));
			entity.setTable_name(record.get("TABLE_NAME"));
			entity.setTable_desc(record.get("TABLE_DESC"));
			entity.setSchema(record.get("SCHEMA"));
			entity.setDatabase_id(record.get("DATABASE_ID"));
			entity.setCreate_user(record.get("CREATE_USER"));
			entity.setCreate_time(parseDate(record.get("CREATE_TIME")) );
			entity.setLast_modify_time(parseDate(record.get("LAST_MODIFY_TIME")) );
			entity.setSequence(parseSeq(record.get("SEQ")));
			
		    entityDaoService.insert(entity);/**/
		}
	}
	
	private Integer parseSeq(String seq){
		if(null==seq)
			return 0;
		try {
			return Integer.parseInt(seq);
		}catch (Exception e){}
		return 0;
	}
	
	private long parseDate(String d){
	    String pattern = "yyyy-MM-dd hh:mm:ss";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    try {
	      Date date = format.parse(d);
	      System.out.println(date);
	      return date.getTime();
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
		return 0;
	    
	}
}
