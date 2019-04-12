package springbootcommon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.imc")
@MapperScan("com.imc.dao")
public class SpringbootCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCommonApplication.class, args);
	}

}
