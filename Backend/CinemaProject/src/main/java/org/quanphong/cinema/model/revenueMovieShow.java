package org.quanphong.cinema.model;

import java.sql.Date;
import java.sql.Time;

public class revenueMovieShow {
	private String movieName;
	private String roomName;
	private Date dateShow;
	private Time beginTime;
	
	private Long sumCost;

	public revenueMovieShow(String movieName, String roomName, Date dateShow, Time beginTime, Long sumCost) {
		super();
		this.movieName = movieName;
		this.roomName = roomName;
		this.dateShow = dateShow;
		this.beginTime = beginTime;
		this.sumCost = sumCost;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Date getDateShow() {
		return dateShow;
	}

	public void setDateShow(Date dateShow) {
		this.dateShow = dateShow;
	}

	public Time getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}

	public Long getSumCost() {
		return sumCost;
	}

	public void setSumCost(Long sumCost) {
		this.sumCost = sumCost;
	}
	
	

	

	
	
	
	
	

}
