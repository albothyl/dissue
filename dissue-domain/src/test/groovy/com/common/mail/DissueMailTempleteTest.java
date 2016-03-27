package com.common.mail;

import com.base.application.DissueDomainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jinhyung on 2016. 3. 22..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissueDomainApplication.class)
public class DissueMailTempleteTest {

    @Autowired
    DissueMailTemplete dissueMailTemplete;

    @Test
    public void contactToAdmin() throws Exception {
        dissueMailTemplete.contactToAdmin("haneu89@naver.com", "운영자시끼야", "반가워");
    }
}