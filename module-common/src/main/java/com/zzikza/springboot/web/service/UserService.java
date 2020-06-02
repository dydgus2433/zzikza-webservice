package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.certification.Certification;
import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.user.UserRepository;
import com.zzikza.springboot.web.dto.UserRequestDto;
import com.zzikza.springboot.web.dto.UserResponseDto;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Value("${service.fileupload.basedirectory}")
    private String FILE_PATH;
    @Value("${service.fileupload.thumb.directory}")
    private String FILE_THUMB_PATH;
    @Value("${service.fileupload.midsize.directory}")
    private String FILE_MIDSIZE_PATH;
    @Value("${service.fileupload.large.directory}")
    private String FILE_LARGE_PATH;
    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;

    public UserResponseDto findByUserIdAndPassword(UserRequestDto params) {
        Optional<User> user =  userRepository.findByUserIdAndPassword(params.getUserId(), params.getEncodingPassword());
        return new UserResponseDto(user.orElseThrow(()-> new IllegalArgumentException("유저 정보가 없습니다.")));
    }

    public Optional<User> findByCertification(Certification certification1) {
        return userRepository.findByTel(certification1.getTel());

    }

    public Optional<User> findByUserIdAndSnsType(String userId, ESnsType snsType) {
        return userRepository.findByUserIdAndSnsType(userId, snsType);
    }

    public void updatePassword(UserResponseDto userVo, UserRequestDto request) {
        if(userVo.getSnsType().equals(ESnsType.NORMAL)){
            User oUser = userRepository.findById(userVo.getId()).orElseThrow(()-> new IllegalArgumentException("해당 회원이 없습니다."));
            oUser.update(request);
            userRepository.save(oUser);
        }else{
            throw new IllegalArgumentException("SNS 회원은 비밀번호 변경이 불가합니다.");
        }
    }

    public void update(UserResponseDto userVo, UserRequestDto request) {
            User oUser = userRepository.findById(userVo.getId()).orElseThrow(()-> new IllegalArgumentException("해당 회원이 없습니다."));
            oUser.update(request);
            userRepository.save(oUser);
    }
}
