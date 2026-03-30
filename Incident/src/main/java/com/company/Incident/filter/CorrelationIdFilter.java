// package com.company.Incident.filter;

// import
// org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
// import org.springframework.stereotype.Component;

// @Component
// public class CorrelationIdFilter extends
// AbstractGatewayFilterFactory<CorrelationIdFilter.Config> {

// public CorrelationIdFilter() {
// super(config.class);
// }

// public static class config {
// // Put the configuration properties
// }

// private static final String HEADER_NAME = "X-Correlation-Id";

// @Override
// public apply(){

// return (exchange, chain)->{

// ServerHttpRequest request = exchange.getRequest();

// String correlationId = request.getHeaders().getFirst(HEADER_NAME);
// if (correlationId == null) {
// correlationId = UUID.randomUUID().toString();
// }
// ServerHttpRequest modifiedRequest = request.mutate()
// .header(HEADER_NAME, correlationId)
// .build();
// return chain.filter(exchange.mutate().request(modifiedRequest).build());

// };
// }
// }
