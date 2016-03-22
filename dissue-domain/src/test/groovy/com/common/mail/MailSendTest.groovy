package com.common.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by jinhyung on 2016. 3. 22..
 */
@Transactional
@ContextConfiguration
//@ContextConfiguration(classes = [DissueDomainJpaConfig])
class MailSendTest extends Specification{

    @Autowired
    def DissueMailTemplete mailTemplete

    def "메일 전송 테스트"() {
        given:
        def result = mailTemplete.contactToAdmin("haneu89@naver.com", "운영자시끼야", "반가워")
        expect:
        result != null

    }

}