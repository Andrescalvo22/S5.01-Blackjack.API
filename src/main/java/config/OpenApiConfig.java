package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blackJackOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("BlackJack Api(WebFlux+MongoDB-+MySQL")
                        .version("1.0.0")
                        .description("Reactive Api designed to manage blackjack games, create new players, view, ranking, delete players, etc. It uses MongoDB to games and MySQL to manage players"));
    }
}
