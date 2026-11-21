// Programmatic configuration (alternative)
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r
                .path("/api/users/**")
                .uri("lb://USER-SERVICE"))
            .route("task-service", r -> r
                .path("/api/tasks/**")
                .uri("lb://TASK-SERVICE"))
            .build();
    }
}
