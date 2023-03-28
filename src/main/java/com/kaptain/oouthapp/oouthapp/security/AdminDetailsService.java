package com.kaptain.oouthapp.oouthapp.security;

import com.kaptain.oouthapp.oouthapp.entities.Admin;
import com.kaptain.oouthapp.oouthapp.exceptions.AdminNotFoundException;
import com.kaptain.oouthapp.oouthapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(AdminNotFoundException::new);
        return new AdminDetails(admin);
    }
}
