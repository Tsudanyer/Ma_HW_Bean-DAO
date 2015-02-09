package model;

public class CustomerBean {
	private String custid;
	private byte[] password;
	private String email;
	private java.util.Date birth;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "custid: " + custid + ", email: "+email+ ", birth: " + birth+"password: "+Integer.parseInt(Byte.toString(password[0]), 10);
	}
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.util.Date getBirth() {
		return birth;
	}
	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}
	
}
