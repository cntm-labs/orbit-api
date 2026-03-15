package com.mrbt.orbit.security.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.security.infrastructure.entity.UserAddressEntity;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, UUID> {

}
