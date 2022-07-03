package hz.spring.breweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
    private final Integer maxTotalConnection;
    private final Integer defaultMaxPerRoute;
    private final Integer maxConnTimeout;
    private final Integer maxSocketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${hz.client.maxTotalConnection}") Integer maxTotalConnection,
                                          @Value("${hz.client.defaultMaxPerRoute}") Integer defaultMaxPerRoute,
                                          @Value("${hz.client.maxConnTimeout}") Integer maxConnTimeout,
                                          @Value("${hz.client.maxSocketTimeout}") Integer maxSocketTimeout) {
        this.maxTotalConnection = maxTotalConnection;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.maxConnTimeout = maxConnTimeout;
        this.maxSocketTimeout = maxSocketTimeout;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnection);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(maxConnTimeout)
                .setSocketTimeout(maxSocketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
