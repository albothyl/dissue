package com.base.configuration;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;

/**
 * 전체 설정 정보를 가지고 있는 객체. 가능하면 이 객체는 사용하지 않기를 권장함.
 *
 * 과거의 PlatformProperties와 역할이 비슷하지만 여러 문제들을 제거한 버전.
 *
 * 가능하면 getProperty() 메소드 시리즈를 사용하지 말고 @Value("${propertyKey}") 형태로
 * 주입 받도록 처리하는 것이 좋다.
 */
public class ConfigurationProperties {

	public static final String PROFILE_PROPERTY_NAME = AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;
	public static final String NO_PROFILE_ERROR_MESSAGE = "Profile이 지정되지 않았습니다.";
	public static final String TOO_MANY_PROFILES_ERROR_MESSAGE = "Profile이 너무 많이 지정돼 있습니다. 1개만 허용됩니다. 현재 프로파일 : ";

	private Environment environment;

	public ConfigurationProperties(Environment environment) {
		this.environment = environment;
	}

	public String getProperty(String key) {
		return environment.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return environment.getProperty(key, defaultValue);
	}

	public <T> T getProperty(String key, Class<T> targetType) {
		return environment.getProperty(key, targetType);
	}

	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		return environment.getProperty(key, targetType, defaultValue);
	}

	public String getActiveProfile() {
		String[] activeProfiles = environment.getActiveProfiles();
		if (activeProfiles == null || activeProfiles.length == 0) {
			throw new IllegalStateException(NO_PROFILE_ERROR_MESSAGE);
		}

		if (activeProfiles.length > 1) {
			String msg = getTooManyActiveProfileErrorMessage(activeProfiles);
			throw new IllegalStateException(msg);
		}

		return activeProfiles[0];
	}

	private String getTooManyActiveProfileErrorMessage(String[] activeProfiles) {
		StringBuilder sb = new StringBuilder();
		sb.append(TOO_MANY_PROFILES_ERROR_MESSAGE);

		for (int i = 0; i < activeProfiles.length; i++) {
			sb.append(activeProfiles[i]);
			if (i < activeProfiles.length - 1) {
				sb.append(',');
			}
		}

		return sb.toString();
	}
}
