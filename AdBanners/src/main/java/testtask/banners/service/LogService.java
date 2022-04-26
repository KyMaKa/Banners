package testtask.banners.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testtask.banners.data.models.Log;
import testtask.banners.data.repository.LogRepository;

@Service
public class LogService {

  public final LogRepository logRepository;

  @Autowired
  public LogService(LogRepository logRepository) {
    this.logRepository = logRepository;
  }

  public Log createLog(Log log) {
    logRepository.save(log);
    return log;
  }

  public Optional<Log> getLog(Log log) {
    return logRepository.findById(log.getId());
  }

  public List<Log> getLogsForDay(String userAgent, String ipAddress, LocalDateTime dateStart,
      LocalDateTime dateEnd) {
    return logRepository.getLogsByUserAgentAndIpAddressAndDateBetween(userAgent, ipAddress,
        dateStart, dateEnd);
  }
}
