package com.aronim.cloud.component.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter
{
    @Bean
    public Filter shallowETagHeaderFilter()
    {
        return new ShallowEtagHeaderFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/META-INF/resources/");
    }
}