package com.mrbt.orbit.security.core.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import com.mrbt.orbit.security.infrastructure.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

		return new InternalUserDetails(user.getEmail(), user.getPassword());
	}

	@RequiredArgsConstructor
	public static class InternalUserDetails implements UserDetails {
		private final String email;
		private final String password;

		public InternalUserDetails(com.mrbt.orbit.security.core.model.User user) {
			this.email = user.getEmail();
			this.password = user.getPassword();
		}

		@Override
		public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
			return java.util.Collections.emptyList();
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return email;
		}
	}
}
