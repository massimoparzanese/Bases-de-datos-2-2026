package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.Impl.ToursServiceImpl;

@Configuration
@ComponentScan(basePackages = "unlp.info.bd2.repositories.Impl")
public class AppConfig {

    @Bean
    @Primary
    public ToursService createService() {
        return new ToursServiceImpl();
    }

}
