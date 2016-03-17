package com.base.domain

import com.base.configuration.DissueDomainJpaConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@ContextConfiguration(classes = [DissueDomainJpaConfig])
class BaseEntityTest extends Specification {
	@Autowired
	def BaseRepository baseRepository

	def "기본 JPA 테스트"() {
		given:
		def Base base = Base.create("content")
		expect:
		baseRepository.save(base) != null
	}
}
