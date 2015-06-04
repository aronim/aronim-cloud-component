
package com.kungfudev.cloud.component;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

@RestController
public class ComponentServiceController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/api/components")
    public Object registry() {

        @SuppressWarnings("unchecked")
        Map<String, Map<String, ?>> applications = restTemplate.getForObject("http://localhost:8761/eureka/apps", Map.class);

        return applications.get("applications").get("application");
    }

    @RequestMapping(value = "/api/components/restart", method = RequestMethod.POST)
    public void restart(@RequestParam("hostname") String hostname, @RequestParam("port") String port) {

        String url = String.format("http://user:password@%s:%s/restart", hostname, port);

        post(url);
    }

    @RequestMapping(value = "/api/components/shutdown", method = RequestMethod.POST)
    public void shutdown(@RequestParam("hostname") String hostname, @RequestParam("port") String port) {

        String url = String.format("http://user:password@%s:%s/shutdown", hostname, port);

        post(url);
    }

    private void post(String url) {

        HttpHeaders httpHeaders = new HttpHeaders() {{
            String auth = "user:password";
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            set("Authorization", "Basic " + new String(encodedAuth));
        }};

        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(httpHeaders), Void.class);
    }
}