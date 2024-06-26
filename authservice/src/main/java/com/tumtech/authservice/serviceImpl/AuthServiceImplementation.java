package com.tumtech.authservice.serviceImpl;

import com.tumtech.authservice.dto.ApiResponse;
import com.tumtech.authservice.dto.LoginRequest;
import com.tumtech.authservice.dto.UserDto;
import com.tumtech.authservice.enums.Roles;
import com.tumtech.authservice.exception.UserNameNotFoundException;
import com.tumtech.authservice.model.Users;
import com.tumtech.authservice.repository.UserRepository;
import com.tumtech.authservice.service.AuthService;
import com.tumtech.authservice.util.JwtAuthenticationFilter;
import com.tumtech.authservice.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthServiceImplementation implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private FileDataService fileDataService;
    private JwtUtils jwtUtils;
@Autowired
    public AuthServiceImplementation(UserRepository userRepository,FileDataService fileDataService, PasswordEncoder passwordEncoder){
this.userRepository = userRepository;
this.fileDataService = fileDataService;
this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

//public String register (UserDto userDto) throws IOException {
//        Users users = userRepository.findByEmail(userDto.getEmail()).orElseThrow(()-> new UsernameNotFoundException("user not found"));
//
//if(users!=null && !users.getEnabled()){
//    return "check your email for confirmation";
//} else if (users == null){
//    Users users1 = new Users();
//    users1.setUserRoles(Roles.CUSTOMER);
//    users1.setDob(userDto.getDob());
//    users1.setPhone(userDto.getPhone());
//    users1.setEmail(userDto.getEmail());
//    users1.setFirstName(userDto.getFirstName());
//    users1.setGender(userDto.getGender());
//    users1.setLastName(userDto.getLastName());
//    users1.setOtherName(userDto.getOtherName());
//    users1.setPassword(passwordEncoder.encode(userDto.getPassword()));
//    users1.setEnabled(false);
////    users1.setProfilepic(fileDataService.upload(file));
//    if(Objects.equals(userDto.getPassword(), userDto.getConfirmPassword())) {
//        Users savedUser = userRepository.save(users1);
//        return "User Registration Successful";
//    }
//    return "Password and confirm password not the same";
//}
//return " user already registered";
//}

    public ApiResponse register(UserDto userDto) throws IOException {
        // Find user by email
        Optional<Users> optionalUser = userRepository.findByEmail(userDto.getEmail());

        // Check if user already exists
        if (optionalUser.isPresent()) {
            Users users = optionalUser.get();
            if (!users.getEnabled()) {
                return  new ApiResponse("Check your email for confirmation", "200", null);
            }
            return new ApiResponse("User already registered","200",null);
        }

        // Create new user
        if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
            Users newUser = new Users();
            newUser.setUserRoles(Roles.CUSTOMER);
            newUser.setDob(userDto.getDob());
            newUser.setPhone(userDto.getPhone());
            newUser.setEmail(userDto.getEmail());
            newUser.setFirstName(userDto.getFirstName());
            newUser.setGender(userDto.getGender());
            newUser.setLastName(userDto.getLastName());
            newUser.setOtherName(userDto.getOtherName());
            newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            newUser.setEnabled(false);
            // newUser.setProfilepic(fileDataService.upload(file)); // Uncomment and use this line if file upload is needed

            userRepository.save(newUser);
            return new ApiResponse("User registration successful", "201","user name is "+ newUser.getEmail() );

        } else {
            return new ApiResponse("Password and confirm password do not match", "403", null);
        }
    }



    @Override
    public UserDto getUser(Long id) {
    UserDto userDto = new UserDto();
    Users users = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    userDto.setFirstName(users.getFirstName());
    userDto.setLastName(users.getLastName());
    userDto.setDob(users.getDob());
    userDto.setOtherName(users.getOtherName());
    userDto.setEmail(users.getEmail());
    userDto.setUserRoles(users.getUserRoles());
    userDto.setCreatedAt(users.getCreatedAt());
    userDto.setGender(users.getGender());
    userDto.setUpdated(users.getUpdated());
    userDto.setPhone(users.getPhone());
    userDto.setProfilepic(users.getProfilepic());
        return userDto;
    }

    @Override

    public Page<Users> getAllUsers(int pageSize, int pageNo, String sortParam) {
    try {
        String[] sortParams = sortParam.split(",");
        Sort sort = Sort.by(sortParams[0]);
        if (sortParams.length == 2) {
            sort = sortParams[0].equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        Page<Users> getAllUsers = userRepository.getAllUsers();
        return userRepository.findAll(pageable);
    }catch (Exception e){
        e.printStackTrace();
        throw new UsernameNotFoundException(e.getMessage());
    }

    }
public ApiResponse Login (LoginRequest loginRequest){
    /*
    # fill form
    #checks if user exists
    #checks if user is enabled
    #checks if password is correct
    #checks if password is correct
    Then returns response
     */
    if (loginRequest!=null){
        if(userRepository.existsByEmail(loginRequest.getUsername())) {
            Users user = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(()-> new UserNameNotFoundException("user not found"));
        if(user.getEnabled()){
           if( passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
               return new ApiResponse("Login successful", "200",jwtUtils.createJwt.apply(user));
           };
           return new ApiResponse("Username or password not correct","403",null);
        }
        return new ApiResponse("Check your email for Validation message","403", null);
        }
       return new ApiResponse("You don't have an account; create one", "403", null);
    }
    return new ApiResponse("Give your login details", "403", null);
}
/*
#is the user logged in
#fetch from security context
#delete from security context
*/

public  ApiResponse logout (HttpServletRequest request ){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if(authentication!=null){
    SecurityContextHolder.getContext().setAuthentication(null);
    SecurityContextHolder.clearContext();
    request.getSession().invalidate();
    if(SecurityContextHolder.getContext().getAuthentication()==null) {
        return new ApiResponse("Logout successful","200",null);
    }

}
return new ApiResponse("You are not logged in", "404", null);
}

}
