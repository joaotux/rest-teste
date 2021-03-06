package br.com.umdesenvolvedor.restteste.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.umdesenvolvedor.excption.BussinesException;
import br.com.umdesenvolvedor.restteste.exceptions.ApiErros;
import br.com.umdesenvolvedor.restteste.interfaces.IBookService;
import br.com.umdesenvolvedor.restteste.model.Book;
import br.com.umdesenvolvedor.restteste.model.BookDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/books")
@Api("Book API")
@Slf4j
public class BookController {

	@Autowired
	private IBookService service;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Cria um novo livro")
	@ApiResponses({
		@ApiResponse(code = 401, message = "Você não tem autorização para realizar esta operação")
	})
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		Book entity = modelMapper.map(dto, Book.class);
		entity = service.save(entity);
		log.info("Criado book com o código {}", entity.getId());
		return modelMapper.map(entity, BookDTO.class);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return new ApiErros(bindingResult);
	}
	
	@ExceptionHandler(BussinesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleBussinesException(BussinesException exception) {
		return new ApiErros(exception);
	}
}
