package br.com.umdesenvolvedor.restteste;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.umdesenvolvedor.excption.BussinesException;
import br.com.umdesenvolvedor.restteste.interfaces.IBookService;
import br.com.umdesenvolvedor.restteste.model.Book;
import br.com.umdesenvolvedor.restteste.model.BookDTO;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

	static String BOOK_API = "/api/books";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	IBookService service;

	private BookDTO dto;
	
	@BeforeEach
	public void init() {
		dto = BookDTO.builder().author("Carlos").title("As Aventuras").isbn("123456").build();
	}
	
	@Test
	@DisplayName("cria um livro com sucesso.")
	public void createBookTest() throws Exception {
		Book saveBook = Book.builder().id(101L).author("Carlos").title("As Aventuras").isbn("123456").build();
		
		BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(saveBook);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(builder)
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(saveBook.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("title").value(saveBook.getTitle()))
		.andExpect(MockMvcResultMatchers.jsonPath("author").value(saveBook.getAuthor()))
		.andExpect(MockMvcResultMatchers.jsonPath("isbn").value(saveBook.getIsbn()));
	}
	
	@Test
	@DisplayName("Deve lançar um erro ao tentar cadastrar um livro faltando informações.")
	public void createInvalidBook() throws Exception {
		String json = new ObjectMapper().writeValueAsString(new BookDTO());
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(builder)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors", Matchers.hasSize(3)));
	}
	
	@Test
	@DisplayName("Lança um erro ao tentar cadastrar um livro com isbn já cadastrado")
	public void createBookWithDuplicateIsbn() throws Exception {
		String json = new ObjectMapper().writeValueAsString(dto);
		String mensagem = "ISBN já cadastrado";
		BDDMockito.given(service.save(Mockito.any(Book.class))).willThrow(new BussinesException(mensagem));
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post(BOOK_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(builder)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors", Matchers.hasSize(1)))
		.andExpect(jsonPath("errors[0]").value(mensagem));
	}
}
