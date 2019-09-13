

package eventlog;

import com.lv.cloud.eventlog.EventLog;
import com.lv.cloud.eventlog.EventLogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(locations={"classpath:application.properties"})
public class EventLogTest {

	private final static EventLog LOG = EventLogFactory.getLog(EventLogTest.class);
	

	@Test
	public void testAdd(){
		LOG.errorLogicEventLog("bussiness_code","error_code","11111","bussinessTag","test","test detail.");
	}

}

