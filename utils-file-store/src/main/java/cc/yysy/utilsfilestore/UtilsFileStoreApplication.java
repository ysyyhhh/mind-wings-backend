package cc.yysy.utilsfilestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UtilsFileStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilsFileStoreApplication.class, args);
    }

}
