package com.seiya.springboot;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

	@Autowired
	DealerService ds;
	
	@Autowired
	PlayerService ps;
	
	@Autowired
	CheckService cs;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/explanation")
	public String explanation() {
		return "explanation";
	}
	
	@RequestMapping("/dealer")
	public String dealer() {
		return "dealer";
	}
	
	//ディーラー（自動）とプレイヤーのターン（手動）を動作
	@RequestMapping(value="/player", method=RequestMethod.POST)
	public ModelAndView player(ModelAndView mav) {
		
		//ディーラーのターン(まず山札のシャッフルから始める)
		ds.shuffle();
		ds.setCard(ds.deal());
		ds.open();
		
		while(ds.checkSum() == true) {
			ds.setCard(ds.hit());
			ds.open();
		}
		
		session.setAttribute("dealerTotal", ds.open());
		
		//プレイヤーのターン
		ps.setCard(ds.deal());
		ps.open();
		
		//(表示用)
		mav.addObject("playerTotal", ps.open());
		mav.addObject("playerContent", ps.myCards);
		
		//(裏側用)
		session.setAttribute("playerTotal", ps.open());
		
		mav.setViewName("player");
		return mav;
	}
	
	//ヒットした際の、プレイヤーのターンを動作
	@RequestMapping(value="/playerhit", method=RequestMethod.POST)
	public ModelAndView playerhit(ModelAndView mav) {
		
		ps.setCard(ds.hit2());
		ps.open();
		
		//（表示用）
		mav.addObject("playerTotal2", ps.open());
		mav.addObject("playerContent2", ps.myCards);
		
		//（裏側用）
		session.setAttribute("playerTotal", ps.open());
		
		mav.setViewName("playerhit");
		return mav;
	}
	
	//最終結果の返却
	@RequestMapping(value="/result", method=RequestMethod.POST)
	public ModelAndView result(ModelAndView mav) {
	
		int dealerTotal = (int)session.getAttribute("dealerTotal");
		int playerTotal = (int)session.getAttribute("playerTotal");
		
		//セッション情報、山札、それぞれの自分の手札を全てクリア
		session.invalidate();
		ds.clearCards();
		ds.clearMyCards();
		ps.clearMyCards();
		
		//条件分岐された結果を変数へ
		String result = cs.check(dealerTotal, playerTotal);
		
		//（表示用）
		mav.addObject("dealerTotal", dealerTotal);
		mav.addObject("playerTotal",playerTotal);
		mav.addObject("result",result);
		
		mav.setViewName("result");
		return mav;
	}
	
}
