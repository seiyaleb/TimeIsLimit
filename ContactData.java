package com.seiya.springboot;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.sql.Timestamp;

//お問い合わせ情報に関するデータベースのデータ部分を扱う
@Entity
public class ContactData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	
	@Column(nullable=false)
	@NotEmpty
	private String name;
	@Column(nullable=false)
	@NotEmpty
	private String mail;
	@Column(nullable=false)
	@NotEmpty
	private String context;
	
	//フィールドに現在の日付と時刻を設定
	private Timestamp day = new Timestamp(System.currentTimeMillis());
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public Timestamp getDay() {
		return day;
	}
	
	public void setDay(Timestamp day) {
		this.day = day;
	}
}
