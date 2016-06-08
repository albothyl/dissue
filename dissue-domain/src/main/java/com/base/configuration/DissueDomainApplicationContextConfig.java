package com.base.configuration;

import com.base.application.DissueDomainApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import({ DissueDomainJpaConfig.class })
@ComponentScan(basePackageClasses = { DissueDomainApplication.class })
public class DissueDomainApplicationContextConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
