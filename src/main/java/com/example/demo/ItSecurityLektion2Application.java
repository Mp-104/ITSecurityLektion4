package com.example.demo;

import com.example.demo.web.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItSecurityLektion2Application {

	public static void main(String[] args) {
		SpringApplication.run(ItSecurityLektion2Application.class, args);

		Controller controller = new Controller(new MyService(), new MyComponent(), new MyBean());

		controller.welcome();
	}

}
