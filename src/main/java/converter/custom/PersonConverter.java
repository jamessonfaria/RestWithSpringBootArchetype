#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.converter.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import ${package}.data.model.Person;
import ${package}.data.vo.PersonVOV2;

@Service
public class PersonConverter {

	public PersonVOV2 convertEntityToVO(Person person) {
		var vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthday(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		return vo;
	}
	
	public Person convertVOToEntity(PersonVOV2 person) {
		var entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}
	
}
