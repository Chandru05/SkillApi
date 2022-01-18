package data;

public class Person {
	
	private String name;
	private String prof;
	private int year;
	
	public Person(String name, String profession, int year) {
		this.name = name;
		this.prof = profession;
		this.year = year;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getProfession() {
		return prof;
	}
	
	public int getYear() {
		return year;
	}

}
