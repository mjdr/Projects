package desuteam.OLXPoster;

public class User {
	private String login;
	private String password;
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return String.format("Login: %s, Password: %s", login, password);
	}
	
}
