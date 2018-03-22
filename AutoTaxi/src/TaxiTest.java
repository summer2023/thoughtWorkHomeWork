import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TaxiTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("开始测试");  
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("测试结束");  
	}

	//测试个get方法
	@Test
	public void testGetcarNum() {
		System.out.println("GetcarNum");
		 BDate bDate1=new BDate("2025/04/05");
		 Taxi ta = new Taxi("CAR001",bDate1,"Prosche",10000,'F');
		assertEquals("CAR001",ta.getcarNum());  
	}

	@Test
	public void testGetbuyDate() {
		System.out.println("GetbuyDate");
		 BDate bDate1=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate1,"Prosche",10000,'F');
		assertEquals(bDate1,ta.getbuyDate());  
	}

	@Test
	public void testGetlogo() {
		System.out.println("Getlogo");
		 BDate bDate1=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate1,"Prosche",10000,'F');
		assertEquals("Prosche",ta.getlogo());  
	}

	@Test
	public void testGetkiloMeter() {
		System.out.println("GetkiloMeter");
		 BDate bDate1=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate1,"Prosche",10000,'F');
		assertEquals(10000,ta.getkiloMeter());  
	}

	@Test
	public void testGetrepaired() {
		System.out.println("Getrepaired");
		 BDate bDate1=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate1,"Prosche",10000,'F');
		assertEquals('F',ta.getrepaired());  
	}

}
