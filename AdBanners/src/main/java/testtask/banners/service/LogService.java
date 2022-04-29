package testtask.banners.service;

import java.time.LocalDateTime;
import java.util.List;
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

  public void createLog(Log log) {
    logRepository.save(log);
  }


  /**
   * Gets all logs for current day. For given 'user'.
   * @param userAgent - user agent.
   * @param ipAddress - ip address of user.
   * @param dateStart - current date - 24 hours.
   * @param dateEnd - current date.
   * @return - list of logs.
   */
  public List<Log> getLogsForDay(String userAgent, String ipAddress, LocalDateTime dateStart,
      LocalDateTime dateEnd) {
    return logRepository.getLogsByUserAgentAndIpAddressAndDateBetween(userAgent, ipAddress,
        dateStart, dateEnd);
  }
}
