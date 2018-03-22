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

    	//输出部分   输出WraningSys系统存入hashmap中的车辆信息，按要求格式输出
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
