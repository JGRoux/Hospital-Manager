package hopital;

/**
 * Classe permettant de paramétrer chaque champ de recherche
 *
 */
public class MyParameter {
	private String SQLvalue, value, specialParam;
	private boolean istring;

	public MyParameter(String SQLvalue, String value, boolean istring) {
		this.SQLvalue=SQLvalue;
		this.value=value;
		this.istring=istring;
	}
	
	public MyParameter(String SQLvalue, String value, String specialParam, boolean istring) {
		this(SQLvalue,value,istring);
		this.specialParam=specialParam;
	}
	
	public String getValue(){
		return this.value;
	}

	public String getSQLValue(){
		return this.SQLvalue;
	}
	
	public String getSpecialParam(){
		return this.specialParam;
	}
	
	public boolean iString(){
		return this.istring;
	}
}
