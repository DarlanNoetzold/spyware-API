package tech.noetzold.spyware.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class TokenApp {

    private String token;

    public String getToken() {
        return token;
    }
}
