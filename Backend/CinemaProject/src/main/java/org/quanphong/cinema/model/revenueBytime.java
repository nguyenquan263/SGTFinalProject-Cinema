package org.quanphong.cinema.model;

import java.sql.Date;

public class revenueBytime {
	private String cusFirstname;
	private String cusLastname;
	private Date ordDate;
	private Long sumCost;
	public revenueBytime(String cusFirstname, String cusLastname, Date ordDate, Long sumCost) {
		super();
		this.cusFirstname = cusFirstname;
		this.cusLastname = cusLastname;
		this.ordDate = ordDate;
		this.sumCost = sumCost;
	}
	public String getCusFirstname() {
		return cusFirstname;
	}
	public void setCusFirstname(String cusFirstname) {
		this.cusFirstname = cusFirstname;
	}
	public String getCusLastname() {
		return cusLastname;
	}
	public void setCusLastname(String cusLastname) {
		this.cusLastname = cusLastname;
	}
	public Date getOrdDate() {
		return ordDate;
	}
	public void setOrdDate(Date ordDate) {
		this.ordDate = ordDate;
	}
	public Long getSumCost() {
		return sumCost;
	}
	public void setSumCost(Long sumCost) {
		this.sumCost = sumCost;
	}
	
	
	
	

	
}
