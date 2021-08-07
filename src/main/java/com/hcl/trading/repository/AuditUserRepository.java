package com.hcl.trading.repository;

import com.hcl.trading.entity.AuditUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditUserRepository extends JpaRepository<AuditUserLogin,Long> {
    AuditUserLogin findByUserId(Long userId);
}
