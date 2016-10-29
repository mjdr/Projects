package desuteam.ru.equipnet;

public class CompanyInfo {
	private String kind;
	private String name;
	private String description;
	private String address;
	private String phone;
	private String face;
	private String site;
	private String email;

	public CompanyInfo(String kind, String name, String description, String address, String phone, String face,
			String site, String email) {
		this.kind = kind;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.face = face;
		this.site = site;
		this.email = email;
	}

	public String getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getFace() {
		return face;
	}

	public String getSite() {
		return site;
	}

	public String getEmail() {
		return email;
	}

}
