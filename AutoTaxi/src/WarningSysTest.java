import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WarningSysTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("开始测试");  
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("测试结束"); 
	}

	@Test
	public void testWriteOff() throws ParseException {
		 System.out.println("是否需要报废");
		 BDate curDate=new BDate("2030/09/01");
		 BDate bDate=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate,"Prosche",10000,'F');
		 List<Taxi> lta=new ArrayList<Taxi>();
		 lta.add(ta);
		 WarningSys test=new WarningSys(lta,curDate);
		assertEquals(0,test.writeOff(ta,curDate));  
	}

	@Test
	public void testTraRelated() throws ParseException {
		 System.out.println("不考虑报废是否一万公里保养");
		 BDate curDate=new BDate("2030/09/01");
		 BDate bDate=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate,"Prosche",10000,'F');
		 List<Taxi> lta=new ArrayList<Taxi>();
		 lta.add(ta);
		 WarningSys test=new WarningSys(lta,curDate);
		assertEquals(true,test.traRelated(ta)); 
	}

	@Test
	public void testTimeRelated() throws ParseException {
		 System.out.println("不考虑报废和一万公里保养是否定时保养");
		 BDate curDate=new BDate("2030/09/01");
		 BDate bDate=new BDate("2025/04/05");
		 Taxi ta = new Taxi("",bDate,"Prosche",10000,'F');
		 List<Taxi> lta=new ArrayList<Taxi>();
		 lta.add(ta);
		 WarningSys test=new WarningSys(lta,curDate);
		assertEquals(true,test.timeRelated(ta, curDate)); 
	}

	@Test
	public void testDertDays() throws ParseException {
		System.out.println("两个日期间相差的天数");
		 BDate bDate=new BDate("2044/05/01");
		 BDate curDate=new BDate("2050/05/01");
		 Taxi ta = new Taxi("",bDate,"Prosche",10000,'F');
		 List<Taxi> lta=new ArrayList<Taxi>();
		 lta.add(ta);
		 WarningSys test=new WarningSys(lta,curDate);
		assertEquals(2191,test.dertDays(bDate, curDate)); 
	}

}
