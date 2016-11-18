package com.codechiev.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/application-core.xml"})  
@ActiveProfiles("development")
public class SpringJdbcTest {

	@Autowired
	private DataSourceTransactionManager txManager;//PlatformTransactionManager at least
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;

	public void setTxManager(DataSourceTransactionManager txManager) {
		this.txManager = txManager;
		this.txManager.setDefaultTimeout(3);
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Test
	public void initJdbc(){
		// DriverManagerDataSource only for testing, returns a new Connection every time.
		// In contrast, SingleConnectionDataSource reuses the same connection all the time
		/*DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClass("org.postgresql.Driver");
		dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1/metadata");
		dataSource.setUser("postgres");
		dataSource.setPassword("1212");*/
		/*
		 * spring handles the creation and release of resources
		 * you provide SQL and extract results. 
		 * */
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		//test close
		//dataSource.close();
		
		/*Initialize database ,make sure sql file encode in ansi? */
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("/sql/db.sql"));
        databasePopulator.addScript(new ClassPathResource("/sql/entity.sql"));
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	}

	@Test
	public void test() throws Exception {
		
		System.out.println("Mapper");
		List<User> users = jdbcTemplate.query("select * from p_users", new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
		for(User u:users){
			System.out.println("user id:"+u.getId()+", username:"+u.getUsername()+", passwd:"+u.getPassword());
		}
		
		System.out.println("queryForList");
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from p_users");
		for(Map<String, Object> map:list){
			System.out.println("user id:"+map.get("id")+", username:"+map.get("username")+", passwd:"+map.get("password"));
		}
	}
	
	@Test
	public void testTrasactionIsolationSerialization1() throws Exception{
		testTrasactionIsolationSerialization("126",10000l, "");
	}
	@Test
	public void testTrasactionIsolationSerialization2() throws Exception{
		testTrasactionIsolationSerialization("126",0, "");
	}
	public void testTrasactionIsolationSerialization(String uname,long millis, String tb) throws Exception{
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);// //without transaction: PROPAGATION_NOT_SUPPORTED
		def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
		def.setReadOnly(false);//requeir postgres jdbc 9.4 as opposed to 9.1 
		def.setTimeout(5);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			//对于ISOLATION_SERIALIZABLE级别,相同约束(where后面相同)的sql即使是readonly事务无效.所以下面查询已有用户名其实不必抛错
			//System.out.println("test object");
			//int count = jdbcTemplate.queryForObject("select count(*) from p_users where username='"+uname+"'", Integer.class);
			//if(count>0)
				//throw new Exception();//canel insert action by throw an exception
			//System.out.println(count);			
			//System.out.println("test insert");//test insert 
			//int insertnum = jdbcTemplate.update("insert into p_users values(nextval('p_users_seq'),'"+uname+"','hehe','xxx',1,1)");
			//long insertid = jdbcTemplate.queryForObject("insert into p_users"+tb+" values(nextval('p_users_seq'),'"+uname+"','hehe','"+millis+"',1,1) returning id", Long.class);
			//System.out.println(insertid);
			//对于ISOLATION_SERIALIZABLE级别,相同约束会触发"由于同步更新而无法串行访问"并阻塞,不相同约束不阻塞但更新也无效?!,此时改为ISOLATION_REPEATABLE_READ即可,或约束字段为unique也行
			int updatenum = jdbcTemplate.update("update p_users"+tb+" set power="+millis+" where username='"+uname+"'");
			System.out.println(updatenum);
			Thread.sleep(millis);
			txManager.commit(status);
		} catch (DataAccessException e) {
	        txManager.rollback(status);
	        throw e;
	    } catch (Exception e) {
			txManager.rollback(status);
			throw e;
		}
	}
}
class User {
	private long id;
	private String username;
	private String password;

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}