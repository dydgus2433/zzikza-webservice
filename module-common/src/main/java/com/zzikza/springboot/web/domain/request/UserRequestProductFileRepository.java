package com.zzikza.springboot.web.domain.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRequestProductFileRepository extends JpaRepository<UserRequestProductFile, String> {
    List<UserRequestProductFile> findAllByUserRequestProduct(UserRequestProduct product);
}

