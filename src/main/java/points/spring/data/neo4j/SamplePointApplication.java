package points.spring.data.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("points.spring.data.neo4j.domain")
public class SamplePointApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamplePointApplication.class, args);
	}
}
