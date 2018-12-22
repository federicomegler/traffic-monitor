package utente;

public class Credenziali {
	private String nome;
	private String cognome;
	private String nickname;
	private String password;
	private int isAdmin;
	
	public Credenziali(String nome, String cognome, String nickname, String password, int isAdmin) {
		this.nome = nome;
		this.cognome = cognome;
		this.nickname = nickname;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	//getter e setter
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}		
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
