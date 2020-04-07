package com.zzikza.springboot.web.domain.portpolio;

import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.domain.request.UserRequest;
import com.zzikza.springboot.web.domain.request.UserRequestProduct;
import com.zzikza.springboot.web.domain.request.UserRequestProductFile;
import org.junit.Test;

public class PortpolioTest {

    @Test
    public void Test() {
        /*
        스튜디오에 저장하고. 삭제하고
        파일 올리고 삭제하고
        유저요청에 포트폴리오 추가해서 올리고 안올리고
         */
        Studio studio = new Studio();


//        Portfolio portfolio = new Portfolio();
//        PortfolioFile portfolioFile = new PortfolioFile();
//        portfolio.addPortpolioFile(portfolioFile);
//        studio.addPortpolio(portfolio);

        User user = new User();
        UserRequest userRequest = new UserRequest();
        user.addUserRequest(userRequest);


        UserRequestProduct userRequestProduct = new UserRequestProduct();
        UserRequestProductFile addRequesrProductFile = new UserRequestProductFile();
        userRequestProduct.addRequestProductFile(addRequesrProductFile);
//        userRequestProduct.addProtpolio(portfolio);
        studio.addUserRequestProduct(userRequestProduct);


    }
}