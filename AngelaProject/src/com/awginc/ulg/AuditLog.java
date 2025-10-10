package com.awginc.ulg;

public class AuditLog {
	
	private String itemCode;
	private int auditSeqNb;
	private String auditChageDesc;
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public int getAuditSeqNb() {
		return auditSeqNb;
	}
	public void setAuditSeqNb(int auditSeqNb) {
		this.auditSeqNb = auditSeqNb;
	}
	public String getAuditChageDesc() {
		return auditChageDesc;
	}
	public void setAuditChageDesc(String auditChageDesc) {
		this.auditChageDesc = auditChageDesc;
	}
	
	
}
