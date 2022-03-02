#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.converter.custom;

import org.springframework.stereotype.Service;

import ${package}.data.model.Book;
import ${package}.data.vo.BookVO;

@Service
public class BookConverter {

	public BookVO convertEntityToVO(Book book) {
		var vo = new BookVO();
		vo.setKey(book.getId());
		vo.setAuthor(book.getAuthor());
		vo.setLaunchDate(book.getLaunchDate());
		vo.setPrice(book.getPrice());
		vo.setTitle(book.getTitle());
		return vo;
	}
	
	public Book convertVOToEntity(BookVO bookVO) {
		var entity = new Book();
		entity.setId(bookVO.getKey());
		entity.setAuthor(bookVO.getAuthor());
		entity.setLaunchDate(bookVO.getLaunchDate());
		entity.setPrice(bookVO.getPrice());
		entity.setTitle(bookVO.getTitle());;
		return entity;
	}

	
}
