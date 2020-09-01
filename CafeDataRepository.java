package com.seiya.springboot.repositories;

import com.seiya.springboot.CafeData;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

//カフェ情報に関するデータベースアクセスの部分を扱う
@Repository
public interface CafeDataRepository extends JpaRepository<CafeData, Long> {

	public List<CafeData> findTop5AllByOrderByIdDesc() throws Exception;
}
