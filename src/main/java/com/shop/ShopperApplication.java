package com.shop;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shop.model.Role;
import com.shop.model.Users;
import com.shop.repository.RoleRepository;

@SpringBootApplication
public class ShopperApplication implements CommandLineRunner{

	@Autowired 
	private com.shop.repository.UserRepository userRepository; 
	@Autowired 
	private RoleRepository roleRepository;
	@Autowired 
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	public static void main(String[] args) {
		SpringApplication.run(ShopperApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		 HashSet<Role> roles=new HashSet<Role>();
		 Role role_1=new Role(1, "USER");
		 Role role_2=new Role(2, "DBA");
		 Role role_3=new Role(3, "ADMIN");
		 
		 roles.add(role_1);
		 roles.add(role_2);
		 roles.add(role_3);
		 
		if(roleRepository.findAll().isEmpty()){			
			roleRepository.saveAll(roles);
		}
		
		if(!userRepository.getByEmail("admin@email.com").isPresent()) {
			Users user=new Users();
			user.setFname("Admin");
			user.setLname("Manager");
			user.setEmail("admin@email.com");
			user.setPassword("123");
			user.setPassword2("123");			
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	        user.setActive(1);
	        user.setRoles(new HashSet<Role>(roles));
	        userRepository.save(user);
	       
		}
	}
}
