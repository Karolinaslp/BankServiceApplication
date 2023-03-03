package org.kaczucha;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "currency-exchange")
public class GlobalConfigurationProperties {

    private String ratesUrl;

    private String exchangeUrl;


}
