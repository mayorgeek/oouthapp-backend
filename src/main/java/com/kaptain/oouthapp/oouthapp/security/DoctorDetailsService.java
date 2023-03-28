package com.kaptain.oouthapp.oouthapp.security;

import com.kaptain.oouthapp.oouthapp.entities.Doctor;
import com.kaptain.oouthapp.oouthapp.exceptions.DoctorNotFoundException;
import com.kaptain.oouthapp.oouthapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DoctorDetailsService implements UserDetailsService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByDoctorId(email)
                .orElseThrow(DoctorNotFoundException::new);
        return new DoctorDetails(doctor);
    }
}
