package entità;

public class Utente 
{
	private String Username; 
	private String email; 
	private String nome;
	private String cognome; 
	private String password; 
	
	public Utente() {}
	
	public Utente (String newUser, String newMail, String newNome, String newCogn, String newPassword)
	{
		this.Username = newUser; 
		this.email = newMail; 
		this.nome = newNome; 
		this.cognome = newCogn; 
		this.password = newPassword; 
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return "L'utente " + getNome() + " " + getCognome() + "registrato con la mail " + getEmail() + "Ha username: " + getUsername();
	}
	
	
	
}
