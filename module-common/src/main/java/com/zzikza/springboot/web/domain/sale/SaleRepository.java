package com.zzikza.springboot.web.domain.sale;

import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository  extends JpaRepository<Sale, String> {
    List<Sale> findAllByExhibition(Exhibition exhibition);
}
