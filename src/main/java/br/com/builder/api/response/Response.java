package br.com.builder.api.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T data;
	private List<String> errors;

	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void addError(String error) {
		getErrors().add(error);
	}

	public void addErrors(List<String> errors) {
		errors.forEach(e -> {
			addError(e);
		});
	}

	public boolean containErrors() {
		return getErrors().isEmpty() == false;
	}
}
