package spider.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpiderWebApplication {
    public static void main(String args[]){
        new SpringApplicationBuilder(SpiderWebApplication.class).run();
    }
}
