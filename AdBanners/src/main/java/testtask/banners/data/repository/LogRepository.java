package testtask.banners.data.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Log;

public interface LogRepository extends CrudRepository<Log, Long> {

  List<Log> getLogsByUserAgentAndIpAddressAndDateBetween(String userAgent, String ipAddress, LocalDateTime dateStart,
      LocalDateTime dateEnd);

}
