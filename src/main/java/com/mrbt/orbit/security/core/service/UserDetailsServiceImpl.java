package com.mrbt.orbit.security.core.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import com.mrbt.orbit.security.infrastructure.repository.UserRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

		return new InternalUserDetails(user.getId(), user.getEmail(), user.getPassword());
	}

	@Getter
	@RequiredArgsConstructor
	public static class InternalUserDetails implements UserDetails {
		private final UUID userId;
		private final String email;
		private final String password;

		public InternalUserDetails(com.mrbt.orbit.security.core.model.User user) {
			this.userId = user.getId();
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
