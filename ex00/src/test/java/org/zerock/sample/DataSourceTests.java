package org.zerock.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//root-context.xml���� ��ī�� �Է��ߴ��� �ν� �����ִ� �۾���.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTests {
	/*
	 * @Setter(onMethod_ = {@Autowired}) private Restaurant restaurant; //����.
	 * private Restaurant restaurant = new Restaurant();�� ���� ������.
	 * 
	 *������ ���� �׽�Ʈ ///////// 
	 * @Test public void testExist() { assertNotNull(restaurant);
	 * 
	 * log.info(restaurant); log.info(restaurant.getChef()); //restaurant.java����
	 * getter/setter�� ����.�Һ��� ����ؼ� gettter�� �ڵ�������. }
	 */
	
//	oracle ���� �׽�Ʈ(61������)
	
//	static {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testConnection() {
//		//close�� �ڵ����� ��������.finally �������� close(); �ۼ��� �ʿ� ����.
//		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:myoracle","ora_user","hong")){
//			log.info(con);
//			log.info("DB�����");
//		}catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
	
	//root context�� ����ؼ� id/pw�Է��߱� ������ ���⼭ ������ �Է� �� �ʿ䰡 ����.
//	@Setter(onMethod_= {@Autowired})
//		private DataSource dataSource;
//	@Test
//	public void testConnection() {
//		
//		//try(){} ���·� �ۼ��Ȱ� ���� close() �� �ۼ��� �ʿ䰡 ����!
//		try(Connection con = dataSource.getConnection()){
//			log.info(con);
//			log.info("db�����");
//		}catch(Exception e) {
//			fail(e.getMessage());
//		}
//		
//		
//	}
	
	@Setter(onMethod_=@Autowired)
	private TimeMapper timeMapper;
	
	
	@Test
	public void testGetTime2() {
		
		log.info("getTime2");
		log.info(timeMapper.getTime2());
	}
	
	
}