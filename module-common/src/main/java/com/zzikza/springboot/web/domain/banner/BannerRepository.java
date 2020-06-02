package com.zzikza.springboot.web.domain.banner;

import com.zzikza.springboot.web.domain.enums.EBannerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, String> {
    List<Banner> findAllByCategory(EBannerCategory bannerCategory);
}
