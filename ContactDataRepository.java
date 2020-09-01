package com.seiya.springboot.repositories;

import com.seiya.springboot.ContactData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import java.util.Optional;

//お問い合わせ情報に関するデータベースアクセスの部分を扱う
@Repository
public interface ContactDataRepository extends JpaRepository<ContactData, Long> {
	
	public Optional<ContactData> findById(Long id);
}
