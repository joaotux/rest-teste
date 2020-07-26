package br.com.umdesenvolvedor.restteste.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

import br.com.umdesenvolvedor.excption.BussinesException;

public class ApiErros {
	private List<String> errors;

	public ApiErros(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
	}

	public ApiErros(BussinesException exception) {
		this.errors = Arrays.asList(exception.getMessage());
	}

	public List<String> getErrors() {
		return errors;
	}

}
