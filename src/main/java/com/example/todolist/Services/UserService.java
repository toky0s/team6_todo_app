package com.example.todolist.Services;

import com.example.todolist.Common.Enums.Status;
import com.example.todolist.Exceptions.UserReadlyExistExeption;
import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Entities.User;
import com.example.todolist.Models.Requests.RequestUser;
import com.example.todolist.Models.Requests.UserRequest;
import com.example.todolist.Models.Responses.UserResponse;
import com.example.todolist.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(s);
        if (user.isEmpty()){
            throw new UsernameNotFoundException(s);
        }
        return new CustomUserDetail(user.get());
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return new CustomUserDetail(user);
    }

    public UserResponse createUser(UserRequest userRequest){
        Optional<User> userOptional = userRepository.findByName(userRequest.getUsername());
        if (userOptional.isPresent()){
            throw new UserReadlyExistExeption(userRequest.getUsername());
        }
        else{
            User user = new User();
            user.setName(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRepository.save(user);
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponse.setStatus(true);
            return userResponse;
        }
    }

    public Integer getUserId(String userName){
        Optional<User> user = userRepository.findByName(userName);
        if (user.isPresent()){
            return user.get().getId();
        }
        else {
            throw new UsernameNotFoundException(userName);
        }
    }
}
