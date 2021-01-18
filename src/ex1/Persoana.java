package ex1;

public class Persoana {
	private String id;
	private String nume;
	private String varsta;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getVarsta() {
		return varsta;
	}
	public void setVarsta(String varsta) {
		this.varsta = varsta;
	}
	
	Persoana(String id, String nume, String varsta){
		this.id=id;
		this.nume=nume;
		this.varsta=varsta;
	}
	
}
