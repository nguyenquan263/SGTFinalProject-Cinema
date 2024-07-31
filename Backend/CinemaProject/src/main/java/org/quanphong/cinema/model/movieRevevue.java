package org.quanphong.cinema.model;

public class movieRevevue {
	private String movieName;
	private Long Revenue;
	public movieRevevue(String movieName, Long revenue) {
		super();
		this.movieName = movieName;
		Revenue = revenue;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public Long getRevenue() {
		return Revenue;
	}
	public void setRevenue(Long revenue) {
		Revenue = revenue;
	}
	@Override
	public String toString() {
		return "movieRevevue [movieName=" + movieName + ", Revenue=" + Revenue + "]";
	}
	
	
	

}
