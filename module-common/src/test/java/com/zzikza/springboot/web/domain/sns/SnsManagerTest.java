package com.zzikza.springboot.web.domain.sns;

import com.zzikza.springboot.web.domain.enums.ESnsConnectStatus;
import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.user.User;
import org.junit.Test;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SnsManagerTest {


    @Test
    public void SNS_연결() {
        //페이스북 연결
        //흠... 막연하네 ;;

        String snsId = "dd";
        User user = User.builder().userId(snsId).
                snsConnectStatus(ESnsConnectStatus.Y).snsType(ESnsType.FACEBOOK).build();

        //Sns의 개수를 0개이면 일반이라고 리턴하면 될 듯?

    }

    @Test
    public void SNS_로그인() {


    }

    @Test
    public void SNS_일반구분() {


    }

    @Test
    public void SNS_연결해제() {


    }


    @Test
    public void 스튜디오가입댓글은_SNS연동해야이용가능_유도() {


    }
}
