package com.example.user_service.repository;

import com.example.user_service.model.AuditLog;
import org.hibernate.boot.model.relational.AuxiliaryDatabaseObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUsernameOrderByTimestampDesc(String username);
    List<AuditLog> findByTargetUserOrderByTimestampDesc(String username);
    List<AuditLog> findByActionOrderByTimestampDesc(String action);


    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :startDate AND :endDate ORDER BY a.timestamp DESC ")
    List<AuditLog> findByTimestampBetween(@Param("startLocalDateTime") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM AuditLog a WHERE a.username = :username AND a.action = :action ORDER BY a.timestamp DESC ")
    List<AuditLog> findByUsernameAndAction(@Param("username")  String username, @Param("action") String action);


}

