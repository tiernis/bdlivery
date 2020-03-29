package ar.edu.unlp.info.bd2.config;

import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;
import ar.edu.unlp.info.bd2.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public DBliveryServiceable createService()
    {
        DBliveryRepository repository = this.createRepository();
        return new DBliveryService(repository);
    }

    @Bean
    public DBliveryRepository createRepository()
    {
        return new DBliveryRepository();
    }
}
