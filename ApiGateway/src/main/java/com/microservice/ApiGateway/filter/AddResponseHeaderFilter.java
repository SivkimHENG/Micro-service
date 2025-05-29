package com.microservice.ApiGateway.filter;


import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.server.mvc.common.AbstractGatewayDiscoverer;
import org.springframework.stereotype.Component;

@Component
public class AddResponseHeaderFilter extends
        AbstractGatewayFilterFactory<AddResponseHeaderFilter.Config> {


    public AddResponseHeaderFilter(){
        super(Config.class);

    }

    public class Config {
        private String name;
        private String value;

        Config(String  name, String value){
            name = name;
            value = value;
        }

    }
}
