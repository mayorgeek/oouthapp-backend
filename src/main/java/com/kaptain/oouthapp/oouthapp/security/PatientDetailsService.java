package com.kaptain.oouthapp.oouthapp.security;

import com.kaptain.oouthapp.oouthapp.entities.Patient;
import com.kaptain.oouthapp.oouthapp.exceptions.PatientNotFoundException;
import com.kaptain.oouthapp.oouthapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientDetailsService implements UserDetailsService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String patientId) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByPatientId(patientId)
                .orElseThrow(PatientNotFoundException::new);
        return new PatientDetails(patient);
    }
}
