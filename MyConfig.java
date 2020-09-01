package com.seiya.springboot;

import org.springframework.context.annotation.*;

//構成クラス
@Configuration
public class MyConfig {

	//CafeReviewDataクラスをBeanとして登録
	@Bean
	CafeReviewData crd() {
		return new CafeReviewData();
	}
}
