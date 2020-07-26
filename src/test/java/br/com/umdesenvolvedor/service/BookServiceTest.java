package br.com.umdesenvolvedor.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.umdesenvolvedor.excption.BussinesException;
import br.com.umdesenvolvedor.restteste.interfaces.IBookService;
import br.com.umdesenvolvedor.restteste.model.Book;
import br.com.umdesenvolvedor.restteste.repository.BookRepository;
import br.com.umdesenvolvedor.restteste.service.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

	IBookService service;

	@MockBean
	BookRepository repository;

	private Book book;

	@BeforeEach
	public void init() {
		this.service = new BookService(repository);
		book = Book.builder().author("Carlos").title("As Aventuras").isbn("123456").build();
	}

	@Test
	@DisplayName("Tem que salvar um livro")
	public void saveBook() {
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
		Mockito.when(service.save(book))
				.thenReturn(Book.builder().id(101L).author("Carlos").title("As Aventuras").isbn("123456").build());

		Book bookSave = service.save(book);

		assertThat(bookSave.getId()).isNotNull();
		assertThat(bookSave.getAuthor()).isEqualTo(book.getAuthor());
		assertThat(bookSave.getTitle()).isEqualTo(book.getTitle());
		assertThat(bookSave.getIsbn()).isEqualTo(book.getIsbn());
	}

	@Test
	@DisplayName("Lança erro ao tentar cadastrar livro com ISPN já cadastrado")
	public void notCreateBookWithIsbnDuplicate() {
		//cenario
		Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
		
		//execução
		Throwable exception = Assertions.catchThrowable(() -> service.save(book));
		
		//verificações
		assertThat(exception)
		.isInstanceOf(BussinesException.class)
		.hasMessage("ISBN já cadastrado");
		
		Mockito.verify(repository, Mockito.never()).save(book);
	}
}
