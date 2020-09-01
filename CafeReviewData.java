package com.seiya.springboot;

//カフェ店舗の評価に関するBeanクラス
public class CafeReviewData {

	private String comment;
	private double total_score;
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public double getTotal_score( ) {
		return total_score;
	}
	
	public void setTotal_score(double total_score) {
		this.total_score = total_score;
	}
}
