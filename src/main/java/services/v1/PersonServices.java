#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.converter.DozerConverter;
import ${package}.data.model.Person;
import ${package}.data.vo.PersonVO;
import ${package}.exception.ResourceNotFoundException;
import ${package}.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	private PersonRepository repository;
	
	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		var entity = repository.findById(person.getKey())
		.orElseThrow(() -> 
			new ResourceNotFoundException("No records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
	
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
		.orElseThrow(() -> 
			new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
	public PersonVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> 
					new ResourceNotFoundException("No records found for this ID"));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertPersonVO);
	}
	
	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
		var page = repository.findPersonByName(firstName, pageable);
		return page.map(this::convertPersonVO);
	}
	
	private PersonVO convertPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		repository.disablePerson(id);
		var entity = repository.findById(id)
				.orElseThrow(() -> 
					new ResourceNotFoundException("No records found for this ID"));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
}
