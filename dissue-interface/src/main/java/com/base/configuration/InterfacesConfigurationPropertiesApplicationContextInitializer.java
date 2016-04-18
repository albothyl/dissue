package com.base.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

/**
 * interfaces(Web) 프로젝트용 설정 파일인 <code>WEB-INF/vitamin/properties/interfaces-${spring.profiles.active}.xml</code> 파일을
 * Spring PropertySource에 추가한다.
 *
 * 이 설정파일은 그 앞의 parent와 project 설정 파일을 덮어쓴다(override).
 *
 * 단, interfaces 전용 프라퍼티 파일이 없을 수도 있는 경우에 대비하여, 해당 설정파일이 존재하지 않으면 에러 없이 로그만 남기고
 * 넘어간다.
 */
@Slf4j
public class InterfacesConfigurationPropertiesApplicationContextInitializer extends ConfigurationPropertiesApplicationContextInitializer {

	/** Interfaces 설정 프라퍼티 파일 위치 */
	public static final String INTERFACES_PROPERTIES_LOCATION = "WEB-INF/vitamin/properties/interfaces-${" + ACTIVE_PROFILES_PROPERTY_NAME + "}.xml";

	@Override
	protected void addPropertiesPaths(ResourceLoader resourceLoader, String profile, List<String> paths) {
		super.addPropertiesPaths(resourceLoader, profile, paths);

		addInterfacesPropertiesPath(resourceLoader, profile, paths);
	}

	private void addInterfacesPropertiesPath(ResourceLoader resourceLoader, String profile, List<String> paths) {
		String interfacesPropertiesLocation = getLocationWithProfile(INTERFACES_PROPERTIES_LOCATION, profile);

		Resource resource = resourceLoader.getResource(interfacesPropertiesLocation);

		if (!resource.exists()) {
			log.warn("interfaces 설정 파일 {} 가 존재하지 않습니다.", interfacesPropertiesLocation);
			return;
		}
		paths.add(interfacesPropertiesLocation);
	}
}
