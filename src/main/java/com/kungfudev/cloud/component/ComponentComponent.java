package com.kungfudev.cloud.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * User: Kevin W. Sewell
 * Date: 2015-05-29
 * Time: 12h40
 */
@ComponentScan
@EnableWebSecurity
@EnableEurekaClient
@SpringBootApplication
@EnableRedisHttpSession
public class ComponentComponent {

    public static void main(String[] args) {
        SpringApplication.run(ComponentComponent.class);
    }

    @Configuration
    protected static class RestClientConfiguration {

        @Bean
        @Primary
        public RestTemplate restTemplate() {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate;
        }
    }

    @Configuration
    protected static class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter {

        @Bean
        public Filter shallowETagHeaderFilter() {
            return new ShallowEtagHeaderFilter();
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/META-INF/resources/");
        }
    }

    @Configuration
    protected static class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/resources/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().disable()
                    .headers().cacheControl().disable();
        }
    }
}
