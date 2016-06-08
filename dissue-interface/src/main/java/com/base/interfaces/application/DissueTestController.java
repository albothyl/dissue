package com.base.interfaces.application;

import com.base.domain.BaseRepository;
import com.base.interfaces.share.mail.Mail;
import com.base.interfaces.share.mail.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class DissueTestController {

	@Autowired
	private BaseRepository repository;

	@Autowired
	private MailSender mailSender;

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("index");
		log.info("come in indexController");

//		List<Base> baseList = repository.findAll();
//		mav.addObject("base", baseList.get(0));

		mailSender.send(generateMail());

		return mav;
	}

	public ModelAndView mailCertificate() {
		return null;
	}

	private Mail generateMail() {
		return Mail.create("jjhwqqq5@gmail.com", "jjhwqqq@naver.com", "testSubject", "testContent");
	}
}
