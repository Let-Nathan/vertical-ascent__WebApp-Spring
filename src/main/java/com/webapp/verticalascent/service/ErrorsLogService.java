package com.webapp.verticalascent.service;

import com.webapp.verticalascent.entity.ErrorsLog;
import com.webapp.verticalascent.repository.ErrorsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation with logic to manage webapp log.
 *
 * @author Nathan L.
 * @version 1.0
 */
@Service
public class ErrorsLogService {
	
	private final ErrorsLogRepository errorsLogRepository;
	
	@Autowired
	public ErrorsLogService(ErrorsLogRepository errorsLogRepository) {
		this.errorsLogRepository = errorsLogRepository;
	}
	
	/**
	 * Bind Exception into ErrorsLog entity before store it into database.
	 *
	 * @param e Take Exception object.
	 */
	public void storeLogs(Exception e) {
		ErrorsLog errorsLog = new ErrorsLog();
		errorsLog.setErrorMessage(e.getMessage());
		errorsLog.setErrorType(e.getClass().getSimpleName());
		errorsLogRepository.save(errorsLog);
	}
	
}
