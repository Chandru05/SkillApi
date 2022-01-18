package data;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
	 
//	private Map<String, Person> personMap = new HashMap<>();
	
	private Map<String, String> personMap = new HashMap<>();
	
	private static DataStore instance = new DataStore();
	
	public static DataStore getInstance() {
		return instance;
	}
	
//	private DataStore() {
//		personMap.put("Ada",new Person("Ada","Software Engg",1990));
//		personMap.put("Baba", new Person("Baba","QA Engg",1995));
//	}
	
	private DataStore() {
		personMap.put("Ada","Soft Engg");
		personMap.put("Baba", "QA Engg");
	}
	
	public String getPerson(String name) {
		return personMap.get(name);
	}

}
