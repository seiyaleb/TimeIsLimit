package com.seiya.springboot;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class PlayerService extends Human{

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
		//引数のディールした数値は一時的なものなため、削除
		public void setCard(ArrayList<Integer> plusCard) {
			myCards.addAll(plusCard);
			plusCard.clear();
		}
		
		//自分の手札に加える。
		//引数はヒットした数値
		public void setCard(int plusCard) {
			myCards.add(plusCard);
		}
		
		//自分の手札をリセット
		public void clearMyCards() {
			myCards.clear();
		}
}
