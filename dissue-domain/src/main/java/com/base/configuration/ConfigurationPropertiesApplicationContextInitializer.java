package com.base.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

/**
 * 프로젝트 설정파일을 로딩한다.
 *
 * 시스템 프라퍼티 <code>config.override</code>를 통해 자기만의 프라퍼티 파일을 지정하여 설정을 오버라이드 할 수 있다.
 * 단, <code>config.override</code>에 지정하는 값은 classpath:로 시작하면 클래스패스 리소스로 읽고, 그렇지 않으면
 * 파일로 간주한다.
 *
 */
@Slf4j
public class ConfigurationPropertiesApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static final String CONFIGURATION_PROPERTIES_SOURCE_NAME = "_dissue_configuration_properties_property_source_";

	/** 프로젝트별 설정 프라퍼티 파일 위치 */
	public static final String PROJECT_PROPERTIES_LOCATION = "classpath:dissue/properties/project-${" + ACTIVE_PROFILES_PROPERTY_NAME + "}.xml";

	/** 오버라이드할 프라퍼티 파일을 지정하는 시스템 프라퍼티 이름. */
	public static final String OVERRIDE_PROPERTIES_LOCATION_PROPERTY_NAME = "config.override";

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		String profile = getActiveProfile(applicationContext);

		List<String> paths = new ArrayList<>();

		addPropertiesPaths(applicationContext, profile, paths);
		populateConfigOverride(paths);

		Properties configurationProperties = loadPropertiesFromPaths(applicationContext, paths);

		PropertiesPropertySource propertySource = new PropertiesPropertySource(CONFIGURATION_PROPERTIES_SOURCE_NAME, configurationProperties);
		applicationContext.getEnvironment().getPropertySources().addLast(propertySource);

		log.info("Configuration : Project Configuration loaded for profile {}", profile);
	}

	/**
	 * 설정 파일들의 경로를 paths에 추가한다.
	 * @param profile Spring Profile
	 * @param paths 설정 파일 경로 모음
	 */
	protected void addPropertiesPaths(ResourceLoader resourceLoader, String profile, List<String> paths) {
		addProjectPropertiesPath(profile, paths);
	}

	private void addProjectPropertiesPath(String profile, List<String> paths) {
		paths.add(getLocationWithProfile(PROJECT_PROPERTIES_LOCATION, profile));
	}

	private String getActiveProfile(ApplicationContext applicationContext) {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

		if (ArrayUtils.isEmpty(activeProfiles)) {
			throw new IllegalStateException(String.format("-D%s=[profile] 옵션으로 활성 프로필을 지정해주세요.", ACTIVE_PROFILES_PROPERTY_NAME));
		}

		if (activeProfiles.length > 1) {
			throw new IllegalStateException(String.format("활성 프로필은 하나만 지정할 수 있습니다. 현재 활성 프로필 : %s", StringUtils.join(activeProfiles, ", ")));
		}

		return activeProfiles[0];
	}

	/** location에서 profile placeholder를 실제 프로필로 전환한 문자열을 리턴한다. */
	protected String getLocationWithProfile(String location, String profile) {
		return location.replaceAll("\\$\\{" + ACTIVE_PROFILES_PROPERTY_NAME + "}", profile);
	}

	private void populateConfigOverride(List<String> paths) {
		String overridePropertyResourcePaths = System.getProperty(OVERRIDE_PROPERTIES_LOCATION_PROPERTY_NAME);

		log.info("config.override : {}", overridePropertyResourcePaths);
		if (StringUtils.isEmpty(overridePropertyResourcePaths)) {
			return;
		}

		for (String overridePropertyResourcePath : overridePropertyResourcePaths.split(",")) {
			addConfigOverridePropertiesToPaths(paths, overridePropertyResourcePath);
		}
	}

	private void addConfigOverridePropertiesToPaths(List<String> paths, String overridePropertyResourcePath) {
		if (overridePropertyResourcePath.contains("classpath:")) {
			paths.add(overridePropertyResourcePath);
		} else {
			String overridePropertyFileFullPath = new File(overridePropertyResourcePath).getAbsolutePath();
			log.info("Configuration : config.override Full Path : {}", overridePropertyFileFullPath);
			paths.add("file:" + overridePropertyFileFullPath);
		}
	}

	protected Properties loadPropertiesFromPaths(ResourceLoader resourceLoader, List<String> paths) {
		Properties configurationProperties = new Properties();

		log.info("Configuration : property file paths to load - {}", paths);

		for (String path : paths) {
			log.info("Configuration : Processing properties file - {}", path);

			Resource resource = resourceLoader.getResource(path);

			InputStream is = null;
			try {
				is = resource.getInputStream();
				Properties properties = new Properties();
				properties.loadFromXML(is);
				configurationProperties.putAll(properties);
			} catch (IOException ex) {
				log.error("Failed to load configuration properties. Resource - " + path, ex);
				throw new IllegalStateException("Failed to load configuration properties. Resource - " + path, ex);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}

		log.info("Configuration : Loaded configuration properties : {}", configurationProperties);
		return configurationProperties;
	}
}
