package ggPackage1;

public class CommonPlant {
	
	protected String commonName;
	protected boolean vegetable;
	protected double space;
	protected int daysToMature;
	private String companion;
	private String anticompanion;



public CommonPlant(String name, boolean vegetable, double space, int daysToMature){
	this.commonName = name;
	this.vegetable = vegetable;
	this.space = space;
	this.daysToMature = daysToMature;
	this.companion = "None";
	this.anticompanion = "None";
}

public String getName(){
	return this.commonName;
}

public String getCompanion(){
	return this.companion;
}

public String getAnticompanion(){
	return this.anticompanion;
}

public double getSize(){
	return this.space;
}

public int getDays(){
	return this.daysToMature;
}
public void addCompanion(String plants){
	if(plants.length() < 3){
		this.companion = this.companion;
	}
	else if(this.companion == "None"){
		this.companion = plants;
	}
	else {
		this.companion = this.companion +", "+ plants;
	}

}

public void addAnticompanion(String plants){
	if(plants.length() < 3){
		this.anticompanion = this.anticompanion;
	}
	else if(this.anticompanion == "None"){
		this.anticompanion = plants;
	}
	else {
		this.anticompanion = this.anticompanion +", "+ plants;
	}
}

public String vegeOrHerb(){
	if(this.vegetable == true){
		return "Vegetable";
	}
	else {
		return "Herb";
	}
}
public String vegeOrHerbIcon(){
	if(this.vegetable == true){
		return "V";
	}
	else {
		return "H";
	}
}



public String toString(){
	return "Name: "+this.commonName+"("+this.vegeOrHerb()+") Helped by: "+this.companion+" Hurt by: "+this.anticompanion;
}

public static void main(String args[]){
	CommonPlant Tomato = new CommonPlant("Tomato", false, .25, 80);
	Tomato.addCompanion("Peppers, Borage, Basil");

	Tomato.addCompanion("Radish");
	System.out.println(Tomato.toString());
	}
}