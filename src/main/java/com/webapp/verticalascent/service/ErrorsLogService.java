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
	
	private final ErrorsLogRepository eLogRepository;
	
	@Autowired
	public ErrorsLogService(ErrorsLogRepository eLogRepository){
		this.eLogRepository = eLogRepository;
	}
	
	/**
	 * Save ErrorsLog into database
	 *
	 * @param eLog Take Errors log entity.
	 */
	public void storeLogs(ErrorsLog eLog){
		eLogRepository.save(eLog);
	}
	
	/**
	 * Binding Exception into ErrorsLog entity
	 *
	 * @param e Take an Exception object
	 */
	public void errorLogTraitment(Exception e){
		ErrorsLog eLog = new ErrorsLog();
		eLog.setErrorMessage(e.getMessage());
		eLog.setErrorType(e.getClass().getSimpleName());
		storeLogs(eLog);
	}
}
