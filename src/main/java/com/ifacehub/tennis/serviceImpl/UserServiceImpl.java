package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Role;
import com.ifacehub.tennis.entity.User;
import com.ifacehub.tennis.repository.RoleRepository;
import com.ifacehub.tennis.repository.UserRepository;
import com.ifacehub.tennis.requestDto.UserDto;
import com.ifacehub.tennis.service.UserService;
import com.ifacehub.tennis.util.Paging;
import com.ifacehub.tennis.util.ResponseObject;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Qualifier("userInfoUserDetailsService")
    @Autowired
    private UserDetailsService userService;
    @Autowired
    private JwtService jwtService;
    @Override
    public ResponseObject saveUser(UserDto userDTO) {
        try {
            User user = User.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setBitDeletedFlag((byte) 0);
            user.setCreatedBy(String.valueOf(1));
            user.setCreatedOn(LocalDateTime.now());
            // Fetch the Role entity by ID
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);

            Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
            if(byUsername.isPresent()){
                return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "UserName Already Exist!: ");
            }
            User savedUser = userRepository.save(user);

            return new ResponseObject(savedUser, "SUCCESS", HttpStatus.OK, "User registered successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to register user: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject authenticateUser(UserDto loginUser) {
            try {
                // Load the user by username
                UserDetails userDetails = userService.loadUserByUsername(loginUser.getUsername());

                // Authenticate the user credentials
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
                );

                // Generate JWT token upon successful authentication
                String jwtToken = jwtService.generateToken(userDetails.getUsername());

                // Load the full User entity to get additional details (role, department, etc.)
                User user = userRepository.findByUsername(loginUser.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("token", jwtToken);
                responseData.put("userDetails", user);
                // Return the response with the JWT token and user
                return new ResponseObject(responseData, "SUCCESS", HttpStatus.OK, "Login successful");
            } catch (AuthenticationException e) {
                // Handle authentication failure
                return new ResponseObject(HttpStatus.UNAUTHORIZED, "ERROR", "Invalid username/password");
            }
        }

    @Override
    public ResponseObject getUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findByIdAndBitDeletedFlag(id, (byte) 0);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return new ResponseObject(user, "SUCCESS", HttpStatus.OK, "User retrieved successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the user");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @Override
    public ResponseObject updateUser(Long id, UserDto updatedUser) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Update basic user fields
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setUpdatedOn(LocalDateTime.now());
            existingUser.setUpdatedBy(String.valueOf(1)); // Hardcoded for now, can be updated based on the logged-in user

            // Condensed ternary logic to fetch and set role, department, and designation
            existingUser.setRole(
                    updatedUser.getRoleId() != null ?
                            roleRepository.findById(updatedUser.getRoleId())
                                    .orElseThrow(() -> new RuntimeException("Role not found"))
                            : existingUser.getRole()
            );


            // Save updated user
            User savedUser = userRepository.save(existingUser);

            return new ResponseObject(savedUser, "SUCCESS", HttpStatus.OK, "User updated successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Error updating user: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllUsers(Paging paging, String username, String email, String roleName) {
        try {
            Pageable pageable = PageRequest.of(paging.getPage() - 1, paging.getLimit());
            Page<User> userPage = userRepository.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                // Add predicates using ternary operator to check conditions
                predicates.add(StringUtils.hasLength(username) ?
                        cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%") : null);

                predicates.add(StringUtils.hasLength(email) ?
                        cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%") : null);

                predicates.add(StringUtils.hasLength(roleName) ?
                        cb.like(cb.lower(root.get("role").get("name")), "%" + roleName.toLowerCase() + "%") : null);
                // Always filter by bitDeletedFlag to exclude soft-deleted users
                predicates.add(cb.equal(root.get("bitDeletedFlag"), 0)); // Only include users where bitDeletedFlag = 0

                // Remove null predicates
                predicates.removeIf(Objects::isNull);

                return cb.and(predicates.toArray(new Predicate[0]));
            }, pageable);

            // Set the total count in Paging object
            paging.setCount(userPage.getTotalElements());

            // Collect User entities directly into a list
            List<User> users = userPage.getContent();

            // Create result map with users and pagination info
            Map<String, Object> result = new HashMap<>();
            result.put("users", users);
            result.put("paging", paging);

            // Create and return the ResponseObject
            return new ResponseObject(result, "SUCCESS", HttpStatus.OK, "Users fetched successfully.");

        } catch (IllegalArgumentException e) {
            // Handle specific exception for illegal arguments
            return new ResponseObject(null, "ERROR", HttpStatus.BAD_REQUEST, "Invalid input parameters: " + e.getMessage());
        } catch (Exception e) {
            // Handle generic exceptions
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }

    }
}
