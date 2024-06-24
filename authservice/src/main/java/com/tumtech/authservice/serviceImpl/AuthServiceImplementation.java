package com.tumtech.authservice.serviceImpl;

import com.tumtech.authservice.dto.UserDto;
import com.tumtech.authservice.enums.Roles;
import com.tumtech.authservice.model.Users;
import com.tumtech.authservice.repository.UserRepository;
import com.tumtech.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class AuthServiceImplementation implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private FileDataService fileDataService;
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

public String register (UserDto userDto, MultipartFile file) throws IOException {
        Users users = userRepository.findByEmail(userDto.getEmail()).orElseThrow(()-> new UsernameNotFoundException("user not found"));

if(users!=null && !users.getEnabled()){
    return "check your email for confirmation";
} else if (users == null){
    Users users1 = new Users();
    users1.setUserRoles(Roles.CUSTOMER);
    users1.setDob(userDto.getDob());
    users1.setPhone(userDto.getPhone());
    users1.setEmail(userDto.getEmail());
    users1.setFirstName(userDto.getFirstName());
    users1.setGender(userDto.getGender());
    users1.setLastName(userDto.getLastName());
    users1.setOtherName(userDto.getOtherName());
    users1.setPassword(passwordEncoder.encode(userDto.getPassword()));
    users1.setEnabled(false);
    users1.setProfilepic(fileDataService.upload(file));
    if(Objects.equals(userDto.getPassword(), userDto.getConfirmPassword())) {
        Users savedUser = userRepository.save(users1);
        return "User Registration Successful";
    }
    return "Password and confirm password not the same"
}
return " user already registered";
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
    public Page<Users> getAllUsers(int pagesize, int pageNo, String sortParam) {
String[] sortParams = Sort.


        Page<Users> getAllUsers = userRepository.getAllUsers();

    }


}
