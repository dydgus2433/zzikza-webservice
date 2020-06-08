package com.zzikza.springboot.web.domain.request;

import com.zzikza.springboot.web.domain.studio.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestProductRepository extends JpaRepository<UserRequestProduct, String> {
    Page<UserRequestProduct> findAllByStudio(Studio studio, Pageable pageable);

    Page<UserRequestProduct> findAllByUserRequest(UserRequest userRequest, Pageable pageable);
}

