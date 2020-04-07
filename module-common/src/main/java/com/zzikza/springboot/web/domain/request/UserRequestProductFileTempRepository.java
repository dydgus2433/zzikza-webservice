package com.zzikza.springboot.web.domain.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRequestProductFileTempRepository extends JpaRepository<UserRequestProductFileTemp, String> {
    List<UserRequestProductFileTemp> findAllByTempKey(String tempKey);
}
