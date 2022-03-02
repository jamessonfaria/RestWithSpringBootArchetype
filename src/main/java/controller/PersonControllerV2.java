#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.data.vo.PersonVOV2;
import ${package}.services.v2.PersonServicesV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Person Endpoint", description = "Description for person", tags = {"PersonEndpointV2"})
@RestController
@RequestMapping("/api/person/v2")
public class PersonControllerV2 {

	@Autowired
	private PersonServicesV2 services;
	
	@ApiOperation(value = "Create a person v2")
	@PostMapping
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
		return services.createV2(person);
	}

}
