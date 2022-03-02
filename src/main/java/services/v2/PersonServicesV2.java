#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${package}.converter.custom.PersonConverter;
import ${package}.data.vo.PersonVOV2;
import ${package}.repository.PersonRepository;

@Service
public class PersonServicesV2 {

	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PersonConverter converter;
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		var entity = converter.convertVOToEntity(person);
		var vo = converter.convertEntityToVO(repository.save(entity));
		return vo;
	}
	
}
