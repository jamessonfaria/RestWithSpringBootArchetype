#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ${package}.converter.DozerConverter;
import ${package}.data.model.Book;
import ${package}.data.vo.BookVO;
import ${package}.exception.ResourceNotFoundException;
import ${package}.repository.BookRepository;

@Service
public class BookServices {
	
	@Autowired
	private BookRepository repository;

	public BookVO create(BookVO book) {
		var entity = DozerConverter.parseObject(book, Book.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public BookVO update(BookVO book) {
		var entity = repository.findById(book.getKey())
		.orElseThrow(() -> 
			new ResourceNotFoundException("No records found for this ID"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());	
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		
		return vo;
	}

	public void delete(Long id) {
		Book entity = repository.findById(id)
		.orElseThrow(() -> 
			new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);		
	}

	public BookVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> 
					new ResourceNotFoundException("No records found for this ID"));
		
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public Page<BookVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertBookVO);
	}

	private BookVO convertBookVO(Book entity) {
		return DozerConverter.parseObject(entity, BookVO.class);
	}

}
