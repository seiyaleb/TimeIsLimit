package com.seiya.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

//メインのカフェ機能に関するビジネスロジックをまとめる
@Service
public class MyService {

	//住所をもとに、最終的に店舗一覧の検索結果を返すメソッド
	public List<CafeData> searchResult(String place) {
		
		List<CafeData> cafeList = new ArrayList<>();
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<JsonNode> response = restTemplate.getForEntity(
			//Yahoo！ジオコーディングAPIを使用
			"http://geo.search.olp.yahooapis.jp/OpenLocalPlatform/V1/geoCoder?appid=dj00aiZpPVZidVkzNWg2bERkQSZzPWNvbnN1bWVyc2VjcmV0Jng9MWM-&query=" + place + "&output=json",
			JsonNode.class);
				
			String cordinates = response.getBody().get("Feature").get(0).get("Geometry").get("Coordinates").asText();
			//緯度と経度のデータは,で繋がって抽出されるため、splitで分割する
			String[] split = cordinates.split(",",0);
			
			RestTemplate restTemplate2 = new RestTemplate();
			ResponseEntity<JsonNode> response2 = restTemplate2.getForEntity(
				//ぐるなびのレストラン検索APIを利用（Wi－Fiと電源の設定する）
				"https://api.gnavi.co.jp/RestSearchAPI/v3/"
				+ "?keyid=ac1e268dd324b0237478eb234a082687&outret=1&wifi=1"
				+ "&latitude=" + split[1] + "&longitude=" + split[0] + "&range=3"
				+ "&category_l=RSFST20000,RSFST18000", JsonNode.class);
			
			//CafeDataインスタンスに抽出したデータを毎回格納し、配列へ
			for(int i = 0; i < 10; i++) {
				
			    String shopID = response2.getBody().get("rest").get(i).get("id").asText();
				if(shopID == null) {
					System.out.println("shopIDはnullのため、このループ処理をbreakします。");
					break;
				} 
				String image1 = response2.getBody().get("rest").get(i).get("image_url").get("shop_image1").asText();
				String name = response2.getBody().get("rest").get(i).get("name").asText();
				String station = response2.getBody().get("rest").get(i).get("access").get("station").asText();
				int walk = response2.getBody().get("rest").get(i).get("access").get("walk").asInt();
				
				CafeData cd = new CafeData();
				cd.setShopID(shopID);
				cd.setImage1(image1);
				cd.setName(name);
				cd.setStation(station);
				cd.setWalk(walk);
				
				cafeList.add(cd);
			}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return cafeList;
	}
	
	//searchResultメソッドで取得したShopIDをもとに、店舗の詳細情報を返すメソッド
	public CafeData detailResult1(String shopID, @ModelAttribute CafeData cd) {
		
		try {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(
			//ぐるなびのレストラン検索APIを利用
			"https://api.gnavi.co.jp/RestSearchAPI/v3/"
			+ "?keyid=ac1e268dd324b0237478eb234a082687&id=" + shopID, JsonNode.class);
		
		//CafeDataインスタンスに抽出したデータを格納
		cd.setImage1(response.getBody().get("rest").get(0).get("image_url").get("shop_image1").asText());
		cd.setImage2(response.getBody().get("rest").get(0).get("image_url").get("shop_image2").asText());
		cd.setName(response.getBody().get("rest").get(0).get("name").asText());
		String address = response.getBody().get("rest").get(0).get("address").asText();
		cd.setAddress(address);
		cd.setStation(response.getBody().get("rest").get(0).get("access").get("station").asText());
		cd.setWalk(response.getBody().get("rest").get(0).get("access").get("walk").asInt());
		cd.setTel(response.getBody().get("rest").get(0).get("tel").asText());
		cd.setBusinessDay(response.getBody().get("rest").get(0).get("opentime").asText());
		cd.setHoliday(response.getBody().get("rest").get(0).get("holiday").asText());
		cd.setHp(response.getBody().get("rest").get(0).get("url").asText());
		
	}  catch(Exception e) {
		e.printStackTrace();
	}
		
		return cd;
	}
	
	//searchResultメソッドで取得したShopIDをもとに、店舗の評価情報を返すメソッド
	public List<CafeReviewData> detailResult2(String shopID) {
		
		List<CafeReviewData> list = new ArrayList<CafeReviewData>();
		
		try {
			
			RestTemplate restTemplate2 = new RestTemplate();
			ResponseEntity<JsonNode> response2 = restTemplate2.getForEntity(
				//ぐるなびの応援口コミAPIを利用
				"https://api.gnavi.co.jp/PhotoSearchAPI/v3/"
				+ "?keyid=ac1e268dd324b0237478eb234a082687&shop_id=" + shopID, JsonNode.class);
			
			//CafeReviewDataインスタンスに抽出したデータを毎回格納し、配列へ
			for(int i = 0; i < 5; i++) {
				
				CafeReviewData crd = new CafeReviewData();
				String num = String.valueOf(i);
				crd.setComment(response2.getBody().get("response").get(num).get("photo").get("comment").asText());
				crd.setTotal_score(response2.getBody().get("response").get(num).get("photo").get("total_score").asDouble());
				list.add(crd);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
