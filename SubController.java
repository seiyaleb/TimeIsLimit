package com.seiya.springboot;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.*;

import org.springframework.transaction.annotation.*;

import com.seiya.springboot.repositories.*;

//メインのカフェ機能以外を制御するコントローラークラス
@Controller
public class SubController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ContactDataRepository repository;

	@RequestMapping("/about")
	public ModelAndView about(ModelAndView mav) {
		mav.setViewName("about");
		return mav;
	}
	
	//contactのGETアクセス時
	@RequestMapping("/contact")
	public ModelAndView contact(@ModelAttribute("formModel")
	ContactData contactData ,ModelAndView mav) {
		
		mav.setViewName("contact");
		return mav;
	}
	
	//contactのPOSTアクセス時（contactconfirmから戻った時）
	@RequestMapping(value="/contact", method=RequestMethod.POST)
	public ModelAndView contact2(ModelAndView mav) {
		
		mav.setViewName("contact");
		//セッションから送信されたデータを格納するContactDataインスタンスを取得
		ContactData contactData = (ContactData)session.getAttribute("contactData");
		mav.addObject("formModel", contactData);
		return mav;
	}
	
	@RequestMapping(value="/contactconfirm", method=RequestMethod.POST)
	public ModelAndView contactConfirm(@ModelAttribute("formModel")
		ContactData contactData, ModelAndView mav) {
		
		//送信された情報が未入力かequalsメソッドで条件分岐
	    if(contactData.getName().equals("") || contactData.getMail().equals("")
	    		|| contactData.getContext().equals("")) {
	    	
	    	mav.setViewName("contact");
	    	mav.addObject("emptyError", "＊再度入力して下さい。");
	    	
	    } else {
	    	
	    	mav.setViewName("contactconfirm");
	    	//未入力でなければ、送信された情報をセッションに設定
			session.setAttribute("contactData", contactData);
	    }
	    
		return mav;
	}
	
	@RequestMapping(value="/contactresult", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView contactResult(ModelAndView mav) {
		
		//セッションから取得したContactDataインスタンスをデータベースに永久に保存化
		ContactData contactData = (ContactData)session.getAttribute("contactData");
		repository.saveAndFlush(contactData);
		
		//contactresult(GET時)にリダイレクト
		return new ModelAndView("redirect:/contactresult");
	}
	
	@RequestMapping(value="/contactresult", method=RequestMethod.GET)
	public ModelAndView contactResult2(ModelAndView mav) {
		
		mav.setViewName("contactresult");
		
		//セッションから取得したContactDataインスタンスのID情報をもとに、データベースに検索し、それをOptional型へ
		ContactData contactData = (ContactData)session.getAttribute("contactData");
		Optional<ContactData> data = repository.findById(contactData.getId());
		mav.addObject("contactData", data.get());
		
		session.invalidate();
		
		return mav;
	}
	
}
