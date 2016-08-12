package com.aronim.cloud.component.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * User: Kevin W. Sewell
 * Date: 2015-06-03
 * Time: 15h41
 */
@Profile("cloud")
@EnableRedisHttpSession
@EnableEurekaClient
@EnableAutoConfiguration
public class SpringCloudConfiguration
{
}
