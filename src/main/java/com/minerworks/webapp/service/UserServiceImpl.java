package com.minerworks.webapp.service;

import com.minerworks.webapp.configuration.Encoders;
import com.minerworks.webapp.model.Role;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.RoleRepository;
import com.minerworks.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
@Import(Encoders.class)
public
class UserServiceImpl implements  UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder userPasswordEncoder;


	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}


	public List<User> findAll(){return userRepository.findAll();}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(s);
		if(user == null){
			throw new UsernameNotFoundException("Invalid email or password.");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRoles().toString())) );
	}


	public void saveUser(User user) {
		user.setPassword(userPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}


}
