package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rotarysource.boot.Application;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles(profiles = "SepEngine-Ram")
public class ApplicationTests {

	@Test
	public void contextLoads() {

	}

}
