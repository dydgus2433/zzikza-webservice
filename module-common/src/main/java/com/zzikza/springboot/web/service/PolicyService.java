package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.policy.Policy;
import com.zzikza.springboot.web.domain.policy.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PolicyService {
    final PolicyRepository policyRepository;

    public Policy findByTermCode(String term) {
        return policyRepository.findByTermCode(term).orElseThrow(()-> new IllegalArgumentException("해당 정책이 없습니다."));
    }
}
