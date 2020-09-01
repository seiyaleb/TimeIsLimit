package com.seiya.springboot;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//カフェ情報に関するデータベースのデータ部分を扱う
@Entity
public class CafeData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	
	private String image1;
	private String image2;
	private String name;
	private String station;
	private int walk;
	private String shopID;
	private String address;
	private String tel;
	private String businessDay;
	private String holiday;
	private String hp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	
	public int getWalk() {
		return walk;
	}
	public void setWalk(int walk) {
		this.walk = walk;
	}
	
	public String getShopID() {
		return shopID;
	}
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getBusinessDay() {
		return businessDay;
	}
	public void setBusinessDay(String businessDay) {
		this.businessDay = businessDay;
	}
	
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
}
