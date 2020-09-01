package com.seiya.springboot;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

@Service
public class DealerService extends Human{

	//ディーラが配る山札全体を格納する配列ArrayList作成
	protected ArrayList<Integer> cards = new ArrayList<Integer>();
	//引いたカードを一時的に格納する配列ArrayList作成
	protected ArrayList<Integer> plusCard = new ArrayList<Integer>();
	
	//山札の全シャッフル
	public void shuffle() {
		
		for(int in = 1; in <= 4; in++) {
			
			for(int i = 1; i <= 13; i++) {
				cards.add(i);
			}
		}
		Collections.shuffle(cards);
	}

	
	//自分の手札の合計値を返す
	public int open() {
		int total = 0;
		
		for(int i = 0; i < myCards.size(); i++) {
			
			if(myCards.get(i) > 10) {
				total += 10;
			} else if(myCards.get(i) == 1) {
				total += 11;
			} else {
				total += myCards.get(i);
			}
		}
		return total;
	}
	
	//自分の手札に加える。
	//引数のディールかヒットした数値は一時的なものなため、削除
	public void setCard(ArrayList<Integer> plusCard) {
		myCards.addAll(plusCard);
		plusCard.clear();
	}
	
	//自分の手札と山札をリセット
	public void clearMyCards() {
		myCards.clear();
	}
	
	public void clearCards() {
		cards.clear();
	}
	
	//ヒットするかの判断を返す
	public boolean checkSum() {
		return this.open() < 17;
	}
	
	//一時的に配列に格納された、ディールした2枚を返す
	public ArrayList<Integer> deal() {
		Collections.addAll(plusCard, cards.get(0), cards.get(1));
		cards.removeAll(plusCard);
		return plusCard;
	}
	
	//一時的に配列に格納された、ヒットした1枚を返す（自分用）
	public ArrayList<Integer> hit() {
		plusCard.add(cards.get(0));
		cards.remove(0);
		return plusCard;
	}
	
	//一時的に配列に格納された、ヒットした1枚を返す（プレイヤー用）
	//ヒットを二つに分ける理由は、ディーラ－が一括して自動で、プレイヤーが手動なため。
	public int hit2() {
		int card = cards.get(0);
		cards.remove(0);
		return card;
	}
	
}
