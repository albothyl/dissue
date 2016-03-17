package com.base.application;

import com.base.domain.Base;
import com.base.domain.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class DissueTestController {

	@Autowired
	private BaseRepository repository;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		log.info("come in indexController");

		List<Base> baseList = repository.findAll();
		mav.addObject("base", baseList.get(0));

		return mav;
	}
}
