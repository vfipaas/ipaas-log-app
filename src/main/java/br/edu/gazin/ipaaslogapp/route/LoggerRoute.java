package br.edu.gazin.ipaaslogapp.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author luan.gazin@oruspay.com.br
 * @year 2021
 */
@Component
public class LoggerRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    rest()
        .post("/log")
        .route()
        .routeId("logger-post-api-route")
        .to("seda:log-async-route?waitForTaskToComplete=Never")
        .setBody(constant("{\"code\": 0,\"message\": \"log accepted\"}"))
        .removeHeaders("*")
        .setHeader(Exchange.HTTP_METHOD, constant(HttpStatus.ACCEPTED))
        .setHeader(HttpHeaders.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE));

    from("seda:log-async-route")
        .routeId("log-async-route")
        .transform(body().regexReplaceAll("(\\n)|( +)", ""))
        .log("${body}");
  }
}
