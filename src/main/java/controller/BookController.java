#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.data.vo.BookVO;
import ${package}.services.v1.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Book Endpoint", description = "Description for a book", tags = {"BookEndpoint"})
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

	@Autowired
	private BookServices services;

	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;
	
	@ApiOperation(value = "Create a book")
	@PostMapping(consumes = {"application/json", "application/xml", "application/x-yaml"}, 
			produces = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVO = services.create(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}
	@ApiOperation(value = "Update a book")
	@PutMapping(consumes = {"application/json", "application/xml", "application/x-yaml"}, 
			produces = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO update(@RequestBody BookVO book) {		
		BookVO bookVO = services.update(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Delete a book")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Find a book")
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO findById(@PathVariable(value = "id") Long id) {
		BookVO bookVO = services.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Find all books")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer	limit, 
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"title"));
		
		Page<BookVO> books = services.findAll(pageable);
		books.stream()
			.forEach(p -> p.add(linkTo(methodOn(BookController.class)
					.findById(p.getKey()))
					.withSelfRel()));
		
		PagedResources<?> resources = assembler.toResource(books);
		return new ResponseEntity<>(resources, HttpStatus.OK);	}
	
}
