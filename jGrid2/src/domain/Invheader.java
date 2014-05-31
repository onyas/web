package domain;

public class Invheader {

	private String invid;
	private String invdate;
	private String client_id;
	private String amount;
	private String tax;
	private String total;
	private String note;
	
	
	public Invheader() {
	}
	
	
	public Invheader(String invid, String invdate, String clientId,
			String amount, String tax, String total, String note) {
		this.invid = invid;
		this.invdate = invdate;
		client_id = clientId;
		this.amount = amount;
		this.tax = tax;
		this.total = total;
		this.note = note;
	}
	public String getInvid() {
		return invid;
	}
	public void setInvid(String invid) {
		this.invid = invid;
	}
	public String getInvdate() {
		return invdate;
	}
	public void setInvdate(String invdate) {
		this.invdate = invdate;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String clientId) {
		client_id = clientId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
