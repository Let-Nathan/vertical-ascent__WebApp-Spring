package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.ErrorsLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Store logs and bug from webapp.
 *
 * @author Nathan L
 * @version 1.0
 */
public interface ErrorsLogRepository extends JpaRepository<ErrorsLog, Long> {
}
