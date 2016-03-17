package com.base.configuration;

import com.base.application.DissueDomainApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( {DissueDomainJpaConfig.class} )
@ComponentScan(basePackageClasses = { DissueDomainApplication.class })
public class DissueDomainApplicationContextConfig {
}
