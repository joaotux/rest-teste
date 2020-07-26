package br.com.umdesenvolvedor.restteste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.umdesenvolvedor.restteste.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsByIsbn(String isbn);

}
