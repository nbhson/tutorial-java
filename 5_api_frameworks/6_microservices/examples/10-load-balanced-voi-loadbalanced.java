@Configuration
public class RestClientConfig {

    @Bean
    @LoadBalanced  // Tự động resolve service name qua Eureka
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
