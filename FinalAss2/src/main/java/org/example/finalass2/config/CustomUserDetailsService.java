package org.example.finalass2.config;

import org.example.finalass2.entity.NguoiDung;
import org.example.finalass2.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + email));

        return new CustomUserDetails(nguoiDung);
    }
}