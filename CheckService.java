package com.seiya.springboot;

import org.springframework.stereotype.Service;

@Service
public class CheckService {

	public String check(int dealerTotal, int playerTotal) {
		
		String result = null;
		
		if(playerTotal == 21 && dealerTotal != 21) {
			result = "ナチュラルブラックジャック達成より、プレイヤーのあなたの勝利！！";
			
		} else if(playerTotal != 21 && dealerTotal == 21) {
			result = "ナチュラルブラックジャック達成より、ディーラーの勝利！！";
			
		} else if(dealerTotal > 21 && playerTotal > 21) {
			result = "両者ともバーストしたので、両者とも敗北";
			
		} else if(dealerTotal < 21 && playerTotal > 21) {
			result = "プレイヤーのあなたがバーストしたので、ディーラーの勝利！！";
			
		} else if(dealerTotal > 21 && playerTotal < 21) {
			result = "ディーラーがバーストしたので、プレイヤーのあなたの勝利！！";
			
		} else if(playerTotal < dealerTotal) {
			result = "大小の差より、ディーラーの勝利！！";
			
		} else if(playerTotal > dealerTotal) {
			result = "大小の差より、プレイヤーのあなたの勝利！！";
			
		} else if(playerTotal == dealerTotal) {
			result = "引き分け！！";
		}
		
		return result;
	}
}
