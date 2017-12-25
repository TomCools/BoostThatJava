package be.tomcools.javaboost;

import be.tomcools.javaboost.commands.GatttoolCommandWrapper;
import be.tomcools.javaboost.commands.Motor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaBoostApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBoostApplication.class, args);
	}

	@Bean
	CommandLineRunner runOnStartup() {
		return args -> {
            GatttoolCommandWrapper wrapper = new GatttoolCommandWrapper();
            wrapper.startKeepAlive();
            while(true) {
                Thread.sleep(2000);
                wrapper.motorTime(Motor.AB, 2, 100);
                Thread.sleep(2000);
                wrapper.motorTime(Motor.AB, 2, -100);
            }
        };
		//return (String[] args) -> new HcitoolCommandWrapper().scanLowEnergyDevices();
	}
}