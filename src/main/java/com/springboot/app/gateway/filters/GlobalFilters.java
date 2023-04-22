package com.springboot.app.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilters implements GlobalFilter{
	
	private final Logger logger = LoggerFactory.getLogger(GlobalFilters.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("ejecutando filtro pre");
		exchange.getRequest().mutate().headers(h -> h.add("token","test"));
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("ejecutando filtro post");
			exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_XML);
		}));
	}

}
