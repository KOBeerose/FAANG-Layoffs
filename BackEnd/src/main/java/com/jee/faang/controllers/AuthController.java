package com.jee.faang.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.jee.faang.models.metier.Admin;
import com.jee.faang.models.metier.Guest;
import com.jee.faang.models.metier.Employee;
import com.jee.faang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jee.faang.models.ERole;
import com.jee.faang.models.Role;
import com.jee.faang.payload.request.LoginRequest;
import com.jee.faang.payload.request.SignupRequest;
import com.jee.faang.payload.response.JwtResponse;
import com.jee.faang.payload.response.MessageResponse;
import com.jee.faang.security.jwt.JwtUtils;
import com.jee.faang.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  AdminRepo adminRepo;

  @Autowired
  GuestRepo guestRepo;

  @Autowired
  EmployeeRepo employeeRepo;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getFullName(),
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    System.out.println("signUpRequest = " + signUpRequest);
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    // Create new user's account


    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
      System.out.println("role is null");
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Admin admin = new Admin(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()),signUpRequest.getFullName());
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          admin.setRoles(roles);
          adminRepo.save(admin);

          break;

        case "guest":
          Guest guest = new Guest(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()),signUpRequest.getFullName());
          Role guestRole = roleRepository.findByName(ERole.ROLE_GUEST)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(guestRole);
          guest.setRoles(roles);
          guestRepo.save(guest);

          break;

        case "employee":
          Employee employee = new Employee(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()),signUpRequest.getFullName());
          Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE )
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(employeeRole);
          employee.setRoles(roles);
          employeeRepo.save(employee);
          break;

        default:
          System.out.println("role not found");
          Role userRole = roleRepository.findByName(ERole.ROLE_GUEST)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }



    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
