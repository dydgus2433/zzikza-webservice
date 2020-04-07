package com.zzikza.springboot.web.domain.request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest, String> {
}
