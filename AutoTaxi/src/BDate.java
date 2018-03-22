
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

/*BDate curDate=new BDate("2030/09/01");
BDate bDate1=new BDate("2025/04/05");
BDate bDate2=new BDate("2029/10/14");
BDate bDate3=new BDate("2026/08/17");
BDate bDate4=new BDate("2027/11/01");
BDate bDate5=new BDate("2027/01/11");
BDate bDate6=new BDate("2029/07/01");
BDate bDate7=new BDate("2028/04/19");
BDate bDate9=new BDate("2024/10/22");
BDate bDate8=new BDate("2027/07/10");

Taxi ta1=new Taxi("car1",bDate1,"Prosche",10000,'F');
Taxi ta2=new Taxi("car2",bDate2,"Prosche",9000,'F');
Taxi ta3=new Taxi("car3",bDate3,"Prosche",13000,'F');
Taxi ta4=new Taxi("car4",bDate4,"BYD",23000,'T');
Taxi ta5=new Taxi("car5",bDate5,"BYD",19500,'F');
Taxi ta6=new Taxi("car6",bDate6,"Audi",10001,'T');
Taxi ta7=new Taxi("car7",bDate7,"Ford",9800,'F');
Taxi ta9=new Taxi("car9",bDate9,"BYD",90300,'F');
Taxi ta8=new Taxi("car8",bDate8,"Ford",15000,'F');*/
/*BDate bDate1=new BDate("2044/05/01");
BDate bDate2=new BDate("2044/05/03");
BDate bDate3=new BDate("2047/05/02");
BDate bDate4=new BDate("2047/05/03");
BDate bDate5=new BDate("2049/12/10");
BDate bDate6=new BDate("2046/11/15");
BDate bDate7=new BDate("2046/11/16");
Taxi ta1=new Taxi("car1",bDate1,"Volk",65535,'F');
Taxi ta2=new Taxi("car2",bDate2,"BMW",100001,'F');
Taxi ta3=new Taxi("car3",bDate3,"Mercedes-Benz",37789,'T');
Taxi ta4=new Taxi("car4",bDate4,"Honda",59908,'T');
Taxi ta5=new Taxi("car5",bDate5,"Peugeot",49999,'F');
Taxi ta6=new Taxi("car6",bDate6,"Jeep",2000,'F');
Taxi ta7=new Taxi("car7",bDate7,"Jeep",5000,'F');*/


/*List<Taxi> lta=new ArrayList<Taxi>();
lta.add(ta1);
lta.add(ta2);
lta.add(ta3);
lta.add(ta4);
lta.add(ta5);
lta.add(ta6);
lta.add(ta7);
lta.add(ta8);
lta.add(ta9);*/