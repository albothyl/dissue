package com.common.mail;

import com.util.mail.DissueMailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by jinhyung on 2016. 3. 22..
 */
@Component
public class DissueMailTemplete {

    @Autowired
    DissueMailManager dissueMailManager;

    public Optional<String> contactToAdmin(String sender, String title, String content) {
        String mailSubject = "[문의]" + title;
        String mailContent = "<h1>다음은 고객의 문의입니다</h1><hr />" + content;

        return dissueMailManager.sendToUs(sender, mailSubject, mailContent);
    }
}
