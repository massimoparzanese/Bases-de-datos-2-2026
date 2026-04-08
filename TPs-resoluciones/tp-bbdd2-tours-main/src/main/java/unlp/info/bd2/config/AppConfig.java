package unlp.info.bd2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
//import unlp.info.bd2.repositories.*;
//import unlp.info.bd2.services.*;

@Configuration
public class AppConfig {

    // TODO: DESCOMENTAR CUANDO SE CREEN LAS CLASES ToursRepository, ToursRepositoryImpl y ToursServiceImpl
    /*
    @Bean
    @Primary
    public ToursService createService() {
        ToursRepository repository = this.createRepository();
        return new ToursServiceImpl(repository);
    }

    @Bean
    @Primary
    public ToursRepository createRepository() {
        return new ToursRepositoryImpl();
    }
    */
}
