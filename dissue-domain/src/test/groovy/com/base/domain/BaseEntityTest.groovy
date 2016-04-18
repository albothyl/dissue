package com.base.domain

import com.base.configuration.ConfigurationPropertiesApplicationContextInitializer
import com.base.configuration.DissueDomainJpaConfig
import com.base.configuration.profiles.DissueProfiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@Transactional
@ActiveProfiles(profiles = DissueProfiles.LOCAL)
@ContextConfiguration(initializers = ConfigurationPropertiesApplicationContextInitializer, classes = [DissueDomainJpaConfig])
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
