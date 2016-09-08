package desuteam.OLXPoster;

import java.util.Date;

public class DateLink {
	private String login;
	private Date date;
	public DateLink(String login, Date date) {
		this.login = login;
		this.date = date;
	}
	public String getLogin() {
		return login;
	}
	public Date getDate() {
		return date;
	}
	
}
