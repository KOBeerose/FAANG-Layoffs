package com.jee.faang;

import com.jee.faang.models.Role;
import com.jee.faang.models.metier.*;
import com.jee.faang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.jee.faang.models.ERole.*;

@SpringBootApplication
public class SpringBootSecurityJwtApplication {
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	GuestRepo guestRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	EnrollmentRepo enrollmentRepo;




	public static void main(String[] args) {
    SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner() {
		return (args) -> {
			roleRepository.save(new Role(ROLE_ADMIN));
			roleRepository.save(new Role(ROLE_GUEST));
			roleRepository.save(new Role(ROLE_EMPLOYEE));
			Admin user1 = new Admin("jeegroup@gmail.com","jeegroup@gmail.com", encoder.encode("password123"),"Group J2EE");
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByName(ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			user1.setRoles(roles);
			adminRepo.save(user1);

			Employee employee1 = new Employee("boukhris.imane.pro@gmail.com","boukhris.imane.pro@gmail.com",encoder.encode("scrapingishard"),"Imane Boukhris");
			Set<Role> roles2 = new HashSet<>();
			roles2.add(roleRepository.findByName(ROLE_EMPLOYEE).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			employee1.setRoles(roles2);
			employeeRepo.save(employee1);

			Guest guest1 = new Guest("guest@gmail.com","guest@gmail.com",encoder.encode("password123"),"guest");
			Set<Role> roles3 = new HashSet<>();
			roles3.add(roleRepository.findByName(ROLE_GUEST).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
			guest1.setRoles(roles3);
			guestRepo.save(guest1);


			Company Meta = new Company(null,"Meta","Technology conglomerate based in Menlo Park, California", LocalDate.of(2022,10,10),LocalDate.of(2022,11,22),null,employee1);
			Company Amazon = new Company(null,"Amazon","technology company focusing on e-commerce, cloud computing, online advertising",LocalDate.of(2022,10,11),LocalDate.of(2022,11,23),null,employee1);
			Company Netflix = new Company(null,"Netflix","subscription video on-demand over-the-top streaming service and production",LocalDate.of(2022,10,11),LocalDate.of(2022,11,15),null,employee1);
			Company Google = new Company(null,"Google","Technology company focusing on search engine technology",LocalDate.of(2022,10,12),LocalDate.of(2022,11,19),null,employee1);
			Company Apple = new Company(null,"Apple", "technology company headquartered in Cupertino, California, United States.", LocalDate.of(2022,10,07),LocalDate.of(2022,11,17),null,employee1);

			companyRepo.save(Meta);
			companyRepo.save(Amazon);
			companyRepo.save(Apple);
			companyRepo.save(Netflix);
			companyRepo.save(Google);
			employee1.setCompanies(Arrays.asList(Meta,Amazon));
			userRepository.save(employee1);
			Enrollment enrollment = new Enrollment(null,guest1,Meta,LocalDate.of(2022,10,15));
			enrollmentRepo.save(enrollment);
		};
	};


}
