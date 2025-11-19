package com.foodgo.backend.module.auth.service.impl;

import com.foodgo.backend.exception.ResourceNotFoundException;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsServiceImpl implements UserDetailsService {
  private final UserAccountRepository userAccountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userAccountRepository
        .findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }
}
