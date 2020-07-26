package br.com.umdesenvolvedor.restteste.service;

import org.springframework.stereotype.Service;

import br.com.umdesenvolvedor.excption.BussinesException;
import br.com.umdesenvolvedor.restteste.interfaces.IBookService;
import br.com.umdesenvolvedor.restteste.model.Book;
import br.com.umdesenvolvedor.restteste.repository.BookRepository;

@Service
public class BookService implements IBookService {

	private BookRepository repository;

	public BookService(BookRepository repository) {
		this.repository = repository;
	}

	public Book save(Book book) {
		if(repository.existsByIsbn(book.getIsbn())) {
			throw new BussinesException("ISBN já cadastrado");
		}
		return repository.save(book);
	}

}
