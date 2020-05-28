package com.hanhan.store;

@org.springframework.boot.autoconfigure.SpringBootApplication
@org.mybatis.spring.annotation.MapperScan({ "com.hanhan.store.mapper", "com.hanhan.store.generated.mapper" })
public class SpringTplApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringTplApplication.class, args);
    }
}
