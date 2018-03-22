

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
