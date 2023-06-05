package com.example.demo.Service;

import com.example.demo.Model.MyUser;
import com.example.demo.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final AuthRepository authRepository;
    @Override
    //لو تسجيل الدخول عندي بالايميل  امرر له الايميل مو اليوزر نيم
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser=authRepository.findMyUserByUsername(username);
        if(myUser==null){
            throw new UsernameNotFoundException("Wrong Username Or password");
        }
        return myUser;
    }
}
