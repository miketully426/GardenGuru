package ggPackage1;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Plant extends CommonPlant {
	
	private String name;
	private String planted;
	private String mature;
	private int numberPlanted;
	
	public Plant (String name, String commonName, boolean vegetable, int daysToMature, String planted, double space, int numberPlanted) {
		super(commonName, vegetable, space, daysToMature);
		this.name = name;
		this.planted = planted;
		this.mature = this.maturityDate(planted);
		this.numberPlanted = numberPlanted;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getNumberPlanted(){
		return this.numberPlanted;
	}
	
	public String getFamily(){
		return this.commonName;
	}
	
	public String getPlanted(){
		return this.planted;
	}
	
	public int getdaysToMature(){
		return this.daysToMature;
	}
	
	public String getMature(){
		return this.mature;
	}
	
	public double getSpace(){
		return this.space;
			}
	
	public String maturityDate(String date){
		Calendar c = Calendar.getInstance();
		String format = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(c.DAY_OF_MONTH, this.daysToMature);
		return sdf.format(c.getTime());		
	}
	
	
	public String toString(){
		return "Plant: "+this.name+" Family: "+this.commonName+" Days to Maturity: "+this.daysToMature;
	}
	
	public static void main(String args[]){
		Plant Radish = new Plant("Radish", "Radish", true, 70, "06/15/2016", .3, 5);
		System.out.println(Radish.getMature());
		System.out.println(Radish.toString());
		
	}

}

