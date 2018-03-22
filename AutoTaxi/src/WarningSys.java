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
