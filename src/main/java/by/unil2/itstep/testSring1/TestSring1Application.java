package by.unil2.itstep.testSring1;

import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TestSring1Application extends SpringBootServletInitializer {


	public static void main(String[] args) {

		SpringApplication.run(TestSring1Application.class, args);
		}

		//17_03_2022

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TestSring1Application.class);
	}






    }
