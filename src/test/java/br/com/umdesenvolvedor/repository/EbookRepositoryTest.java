package br.com.umdesenvolvedor.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.umdesenvolvedor.restteste.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class EbookRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;

	@Autowired
	BookRepository repository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir na base de dados um livro com o isbn informado")
	public void returnTruWhenIsbnExists() {
		String isbn = "123";
		
		boolean exists = repository.existsByIsbn(isbn);
		
		assertThat(exists).isTrue();
	}
}
