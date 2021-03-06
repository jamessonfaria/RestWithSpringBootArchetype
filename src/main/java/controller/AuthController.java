#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.repository.UserRepository;
import ${package}.security.AccountCredentialsVO;
import ${package}.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "AuthenticationEndpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Authentication a user by credentals")
	@PostMapping(value = "/signin", consumes = {"application/json", "application/xml", "application/x-yaml"}, 
			produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity create(@RequestBody AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			var user = userRepository.findByUsername(username);
			var token = "";
			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			}else {
				throw new UsernameNotFoundException("Username " + username + " not found");
			}
			
			Map<Object,Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			return ok(model);
		}catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}

		
	}
	
	
}
