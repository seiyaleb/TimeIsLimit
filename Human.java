package com.seiya.springboot;

import java.util.ArrayList;

//ディーラとプレイヤーのサービスに継承される抽象クラス
abstract public class Human {

	//自身の手札を格納する配列ArrayList作成
	protected ArrayList<Integer> myCards = new ArrayList<Integer>();
	
	//抽象メソッド
	abstract public int open();
	abstract public void setCard(ArrayList<Integer> plusCard);
	abstract public void clearMyCards();
}
