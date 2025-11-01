package org.example.finalass2.config;

import org.example.finalass2.entity.NguoiDung;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final NguoiDung nguoiDung;

    public CustomUserDetails(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return nguoiDung.getVaiTros().stream()
                .map(vaiTro -> new SimpleGrantedAuthority("ROLE_" + vaiTro.getTen()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return nguoiDung.getMatKhau();
    }

    @Override
    public String getUsername() {
        return nguoiDung.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public Integer getId() {
        return nguoiDung.getId();
    }

    public String getTen() {
        return nguoiDung.getTen();
    }
}