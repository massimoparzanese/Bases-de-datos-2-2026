package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.services.*;
import unlp.info.bd2.services.Impl.ToursServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public ToursService toursService() {
        return new ToursServiceImpl();
    }
}