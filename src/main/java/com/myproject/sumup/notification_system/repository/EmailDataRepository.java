package com.myproject.sumup.notification_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.sumup.notification_system.domain.data.EmailData;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, UUID>{

}
