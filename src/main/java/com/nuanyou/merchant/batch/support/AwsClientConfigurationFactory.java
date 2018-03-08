package com.nuanyou.merchant.batch.support;

import com.amazonaws.ClientConfiguration;
import org.apache.http.conn.ssl.SSLSocketFactory;

import java.security.NoSuchAlgorithmException;

public class AwsClientConfigurationFactory {

    public static ClientConfiguration getConfiguration() throws NoSuchAlgorithmException {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.getApacheHttpClientConfig().setSslSocketFactory(SSLSocketFactory.getSocketFactory());
        return configuration;
    }
}
