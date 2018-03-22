#入职培训建立本地仓库


## WarningSystem

There are two kinds of car maintenance：Time-related maintenance and Distance-related maintenance. In addition, besides maintenance, the car will be write-off if it reaches certain conditions.

## System Design

### WarningSys
 - warning  
     input ： list of taxis and  submitDate
    process:  if (writeoff true) set in to hashmap-wOff    else if ( distance-Related true)set in to hashmap-disRel else if (time-Related ) set into hashmap-tiemRel

- writeOff    judgment ( input: Taxi andsubmitDate,  output: int)
 - traRelated  judgment  ( input:Taxi , output:boolean)
 - timeRelated  judgment  ( input: Taxi andsubmitDate,  output: int)

 WarningSys.java
 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class WarningSys {
	BDate curDate;
	List<Taxi> lta=new ArrayList<Taxi>();
	HashMap<String, StringBuilder> wOff =new  HashMap<String,StringBuilder>();
	HashMap<String, StringBuilder> transRel =new  HashMap<String, StringBuilder>();
	HashMap<String, StringBuilder> timeRel =new  HashMap<String, StringBuilder>();

 public WarningSys(List<Taxi> lta,BDate curDate) throws ParseException{
		this.lta=lta;
		this.curDate=curDate;
		
		for(Taxi ta:lta){
			warning(ta,curDate);
		} 
	}
 
 //判断传入的taxi对象是需要报废还是保养 报废优先级最高 一万里保养其次 定时保养优先级最低
 public void warning(Taxi ta,BDate curDate) throws ParseException{
	   int woflag=writeOff(ta,curDate);
		if(woflag==1){
			String str=ta.getlogo();
			if(!wOff.containsKey(str)){
				wOff.put(str,new StringBuilder().append(ta.getcarNum()));
				
			}else{
				wOff.get(str).append(","+ta.getcarNum());
			}
			
		}else if(woflag==0){
			
			if(traRelated(ta)){
				String str=ta.getlogo();
				if(!transRel.containsKey(str)){
					transRel.put(str,new StringBuilder().append(ta.getcarNum()));
					
				}else{
					transRel.get(str).append(","+ta.getcarNum());
				}
				
			}else{
				if(timeRelated(ta,curDate)){
					String str=ta.getlogo();
					if(!timeRel.containsKey(str)){
						timeRel.put(str,new StringBuilder().append(ta.getcarNum()));
						
					}else{
						timeRel.get(str).append(","+ta.getcarNum());
					}
				}	
			}
		}
   }
    	
	public int writeOff(Taxi taxi,BDate curDate) throws ParseException{
		int flag=0;
		char rp=taxi.getrepaired();
		BDate bDate=taxi.getbuyDate();
		BDate woffDate=new BDate(curDate.year+"/"+String.valueOf(Integer.valueOf(bDate.month))+"/"+bDate.data);
		int bYear=bDate.year;
		int dyear=curDate.year-bYear;
		int bmonth=bDate.month;
		int curmonth=curDate.month;
		int bdata=bDate.data;
		int curdata=curDate.data;
		int wodays=dertDays(taxi.getbuyDate(),woffDate);
		int ddays=dertDays(taxi.getbuyDate(),curDate);
		
		if(ddays>=2190){
		//大于六年 已报废
			flag=-1;
		}else if(wodays-2190==2){
				if(bdata<4){
					//购买日期在3月份以前  接近六年且小于六年  若购买日期在月初前三天则报废日期在前一个月，则在前两个月提醒
					if(curmonth==bmonth-2 || (curmonth==bmonth-1)){
					flag=1;
				    	}
				}else{
					//购买日期在3月份以前  接近六年且小于六年  若购买日期不在月初前三天则报废日期在当月，则在前一个月至报废日期前提醒
					if(curmonth==bmonth-1 || (curmonth==bmonth && curdata<=bdata)){
						flag=1;
					    }
				}
		}else if(wodays-2190==1){
				if(bdata<3){
					//购买日期在3月份以前  接近六年且小于六年  若购买日期在月初前三天则报废日期在前一个月，则在前两个月提醒
					if(curmonth==bmonth-2 || (curmonth==bmonth-1)){
					flag=1;
				    	}
				}else{
					//购买日期在3月份以前  接近六年且小于六年  若购买日期不在月初前三天则报废日期在当月，则在前一个月至报废日期前提醒
					if(curmonth==bmonth-1 || (curmonth==bmonth && curdata<=bdata)){
						flag=1;
					    }
				}
		}else if(rp=='T'){
			//小于6年且有大修，3年以上报废，3年以下的同理于六年以下无大修的报废方式
			if(ddays>=1095){
				flag=-1;
			}else if(wodays-1095==1){
				if(bdata<3){
					if(curmonth==bmonth-2 || (curmonth==bmonth-1)){
					flag=1;
				    	}
				}else{
					if(curmonth==bmonth-1 || (curmonth==bmonth && curdata<=bdata)){
						flag=1;
					    }
				}
			}else if(wodays-1095==0){
				if(bdata<2){
					if(curmonth==bmonth-2 || (curmonth==bmonth-1)){
					flag=1;
				    	}
				}else{
					if(curmonth==bmonth-1 || (curmonth==bmonth && curdata<=bdata)){
						flag=1;
					    }
				}
		 }
		}
		return flag;
	}
	
	public boolean traRelated(Taxi taxi){
		boolean flag=false;
		int s=taxi.getkiloMeter();
		//里程数每一万公里需要保养，【9500，10000】之间都提醒
		if(((9500<=s%10000)&&(s%10000<10000)) || s%10000==0){
			 flag=true;
		}
		return flag;
	}
	
	public boolean timeRelated(Taxi taxi,BDate curDate){
		boolean flag=false;
		BDate bDate=taxi.getbuyDate();
		int bYear=bDate.year;
		int dyear=curDate.year-bYear;
		int bmonth=bDate.month;
		int curmonth=curDate.month;
		int bdata=bDate.data;
		int curdata=curDate.data;
		char rp=taxi.getrepaired();
		
		if(rp=='T'){
			//有大修  每三个月定期保养 包括保养当月和前一个月的都提醒
			if(((12-bmonth+curmonth)%3==0)&&(bdata>=curdata) || (12-bmonth+curmonth)%3==2){
				 flag=true;
			}
		}else{
			//无大修 
			if(dyear<3){
				//小于三年 每12个月定期保养一次，包括保养当月和前一个月的都提醒
				if(((bmonth==curmonth)&&(bdata>=curdata)) || bmonth-curmonth==1){
					flag=true;
				}
			}else{
				//大于三年 每6个月定期保养一次，包括保养当月和前一个月的都提醒
				if(((12-bmonth+curmonth)%6==0)&&(bdata>=curdata) || (12-bmonth+curmonth)%6==5){
					flag=true;
				}
			}
		}
		return flag;
	}
	
	public int dertDays(BDate preDate,BDate curDate) throws ParseException {
		//计算两个日期之间相差的天数
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        Date fDate=sdf.parse(preDate.year+"/"+preDate.month+"/"+preDate.data);
        Date oDate=sdf.parse(curDate.year+"/"+curDate.month+"/"+curDate.data);
        int days=(int)Math.floor((oDate.getTime()-fDate.getTime())/(1000*3600*24));
        return days;
    }
}






### Taxi
encapsulated taxi,include car number, date of purchase ,Car brand，kKM ，flag of repaired

taxi.java

public class Taxi {
    private String carNum;
    private BDate buyDate;
    private String logo;
    private int kiloMeter;
    private char repaired;
    
    //构造函数 通过参数列表传各个属性值
    public Taxi(String carNum,BDate buyDate,String logo,int kiloMeter,char repaired){
    	this.carNum=carNum;
    	this.buyDate=buyDate;
    	this.logo=logo;
    	this.kiloMeter=kiloMeter;
    	this.repaired=repaired;
    }
    
    //各属性的get方法
    public String getcarNum(){
    	return carNum;
    }
    
    public BDate getbuyDate(){
    	return buyDate;
    }
    
    public String getlogo(){
    	return logo;
    }
    
    public int getkiloMeter(){
    	return kiloMeter;
    }
    
    public char getrepaired(){
    	return repaired;
    }
    
}


### BDate
The element Date that encapsulate by oneself.

BDate.java

public class BDate {
  private String date;
  public int year;
  public int month;
  public int data;
  
  //构造函数 传入日期字符串
  public BDate(String date){
	  this.date=date;
	  String[] str=date.split("/");
	  this.year=Integer.valueOf(str[0]);
      this.month =Integer.valueOf(str[1]);	
      this.data =Integer.valueOf(str[2]);	
  }
 
}

### Main
The main part. Include the part of input and output.

Main.java

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		//输入部分 以EOF Ctrl+z结束输入
		Scanner sc=new Scanner(System.in);
		String[] in=sc.nextLine().split(" ");
		BDate curDate=new BDate(in[1].toString());
		List<Taxi> lta=new ArrayList<Taxi>();
		while(sc.hasNextLine()){
			String[] strs=sc.nextLine().split("\\|");
			BDate bdate=new BDate(strs[1].toString());
			char c=strs[4].toString().charAt(0);
			Taxi ta=new Taxi(strs[0].toString(),bdate,strs[2].toString(),Integer.valueOf(strs[3].toString()),c);
			lta.add(ta);
		}
    	WarningSys ws=new WarningSys(lta,curDate);

    	//输出WraningSys系统存入hashmap中的车辆信息，按要求格式输出
    	 System.out.println("* Time-Related maintenance coming soon...");
         Collection<String> keyset3= ws.timeRel.keySet();  
         List<String> ltimeRel = new ArrayList<String>(keyset3);  
         Collections.sort(ltimeRel); 
         
         for (int i = 0; i < ltimeRel.size(); i++) { 
        	 String[] strs=ws.timeRel.get(ltimeRel.get(i)).toString().split(",");
         	int count=strs.length;
             System.out.println(ltimeRel.get(i)+": "+ count+" ("+ws.timeRel.get(ltimeRel.get(i))+")");  
          } 
 		System.out.println();
 
 		
        System.out.println("* Trans-Related maintenance coming soon...");
        Collection<String> keyset2= ws.transRel.keySet();  
        List<String> ltransRel = new ArrayList<String>(keyset2);  
        Collections.sort(ltransRel); 
        
        for (int i = 0; i < ltransRel.size(); i++) {  
        	String[] strs=ws.transRel.get(ltransRel.get(i)).toString().split(",");
        	int count=strs.length;
            System.out.println(ltransRel.get(i)+": "+ count+" ("+ws.transRel.get(ltransRel.get(i))+")");  
         } 
        System.out.println();

        
        System.out.println("* Write-off coming soon...");
        Collection<String> keyset1= ws.wOff.keySet();  
        List<String> lwOff = new ArrayList<String>(keyset1);  
        Collections.sort(lwOff); 
       
        for (int i = 0; i < lwOff.size(); i++) {  
        	String[] strs=ws.wOff.get(lwOff.get(i)).toString().split(",");
        	int count=strs.length;
            System.out.println(lwOff.get(i)+": "+ count+" ("+ws.wOff.get(lwOff.get(i))+")");  
         } 
        sc.close();
		}
	
}



### Unit test by JUnit

Taxitest.java

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

WarningSysTest.java

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
		 System.out.println("不考虑报废.是否一万公里保养");
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
		 System.out.println("不考虑报废和一万公里保养,是否定时保养");
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





