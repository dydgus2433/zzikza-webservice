package com.zzikza.springboot.web.domain.policy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, String> {

    Optional<Policy> findByTermCode(String term);

}
