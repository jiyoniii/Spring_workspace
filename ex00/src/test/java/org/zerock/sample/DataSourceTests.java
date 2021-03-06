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
//root-context.xml에서 히카리 입력했던걸 인식 시켜주는 작업임.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTests {
	/*
	 * @Setter(onMethod_ = {@Autowired}) private Restaurant restaurant; //주입.
	 * private Restaurant restaurant = new Restaurant();와 같은 역할임.
	 * 
	 *의존성 주입 테스트 ///////// 
	 * @Test public void testExist() { assertNotNull(restaurant);
	 * 
	 * log.info(restaurant); log.info(restaurant.getChef()); //restaurant.java에선
	 * getter/setter가 없음.롬복을 사용해서 gettter가 자동생성됨. }
	 */
	
//	oracle 연동 테스트(61페이지)
	
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
//		//close를 자동으로 수행해줌.finally 블럭에서 close(); 작성이 필요 없음.
//		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:myoracle","ora_user","hong")){
//			log.info(con);
//			log.info("DB연결됨");
//		}catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
	
	//root context를 사용해서 id/pw입력했기 때문에 여기서 별도로 입력 할 필요가 없음.
//	@Setter(onMethod_= {@Autowired})
//		private DataSource dataSource;
//	@Test
//	public void testConnection() {
//		
//		//try(){} 형태로 작성된건 따로 close() 를 작성할 필요가 없음!
//		try(Connection con = dataSource.getConnection()){
//			log.info(con);
//			log.info("db연결됨");
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
