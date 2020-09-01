package com.seiya.springboot;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;


import com.seiya.springboot.repositories.*;

//メインのカフェ関連の機能を制御するコントローラークラス
@Controller
public class MainController {
	
	@Autowired
	CafeReviewData crd;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CafeDataRepository repository;
	
	@Autowired
	private MyService ms;

	@RequestMapping("/")
	public ModelAndView top(ModelAndView mav) throws Exception {
		mav.setViewName("top");
		//直近5件をデータベースから検索し、配列へ
		Iterable<CafeData> detailList = repository.findTop5AllByOrderByIdDesc();
		mav.addObject("detailList", detailList);
		return mav;
	}
	
	@RequestMapping("/searchresult")
	public ModelAndView searchResult(@RequestParam("place")String place, ModelAndView mav) {
		
		mav.setViewName("searchresult");
		mav.addObject("placeText", "「" + place + "」の検索一覧");
		//detailresultから戻った場合があるため、送信された住所をセッションに格納し、有効期限を設定
		session.setAttribute("place", place);
		session.setMaxInactiveInterval(2400);
		//住所をもとに、最終的に店舗一覧をListへ
		List<CafeData> cafeList = ms.searchResult(place);
		mav.addObject("cafeList", cafeList);
		
		return mav;
	}
	
	//detailresultから再度searchresultに戻った時に呼び出す処理
	@RequestMapping("/searchresult/2")
	public ModelAndView searchResult2(ModelAndView mav) {
		
		mav.setViewName("searchresult");
		//セッションから住所を取得
		String place = (String)session.getAttribute("place");	
		mav.addObject("placeText", "「" + place + "」の検索一覧");
		//住所をもとに、最終的に店舗一覧をListへ
		List<CafeData> cafeList = ms.searchResult(place);
		mav.addObject("cafeList", cafeList);
		
		return mav;
	}
	
	@RequestMapping("/detailresult/{shopID}")
	@Transactional(readOnly=false)
	public ModelAndView detailResult(@PathVariable String shopID, 
			@ModelAttribute CafeData cd, ModelAndView mav) {
		
		mav.setViewName("detailresult");
		//shopIDをもとに生成した店舗データを格納したCafeDataインスタンスをデータベースに永久に保存化
		CafeData cafeData = ms.detailResult1(shopID, cd);
		repository.saveAndFlush(cafeData);
		mav.addObject("object", cafeData);
		mav.addObject("mapURL","https://maps.google.co.jp/maps?output=embed&q=" + cafeData.getAddress());
		//shopIDをもとに、その店舗の評価データ一覧を配列へ
		List<CafeReviewData> list = ms.detailResult2(shopID);
		mav.addObject("reviewList", list);
		
		return mav;
	}
}
