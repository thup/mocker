package org.nico.mocker.model;

import java.util.Map;

import org.nico.mocker.enums.ApiParameterType;

import lombok.Data;

@Data
public class ApiParameter {

	private ApiParameterType type;

	private ApiParameter extra;

	private String name;

	private String alias;

	private String description;

	private boolean required;

	private Map<String, ApiParameter> fields;

	private ApiParameter newFields;

	public static final ApiParameter OBJECT = new ApiParameter(ApiParameterType.OBJECT);
	public static final ApiParameter RANDOM = new ApiParameter(ApiParameterType.RANDOM);

	public ApiParameter(ApiParameterType type) {
		this.type = type;
	}

	public ApiParameter() {
		super();
	}

}
