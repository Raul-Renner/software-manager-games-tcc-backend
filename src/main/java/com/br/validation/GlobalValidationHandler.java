//package com.br.validation;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.web.bind.support.WebExchangeBindException;
//import org.springframework.web.client.ResourceAccessException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.validation.ConstraintViolationException;
//import javax.validation.ValidationException;
//
//import static io.vavr.API.Option;
//import static io.vavr.API.Try;
//import static io.vavr.collection.Stream.ofAll;
//import static java.util.Locale.getDefault;
//import static java.util.ResourceBundle.getBundle;
//import static java.util.stream.Collectors.toSet;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static reactor.core.publisher.Mono.just;
//
//@Slf4j
//@Order(-2)
//@Configuration
//public class GlobalValidationHandler implements ErrorWebExceptionHandler {
//
//    @Autowired
//    @Qualifier("objectMapper")
//    private ObjectMapper mapper;
//
//    @Override
//    public Mono<Void> handle(ServerWebExchange server, Throwable t) {
//        server.getResponse().setStatusCode(BAD_REQUEST);
//        server.getResponse().getHeaders().setContentType(APPLICATION_JSON);
//        var factory = server.getResponse().bufferFactory();
//        var bundle = getBundle("Messages", getDefault());
//        if (t instanceof WebExchangeBindException) {
//            var value = ofAll(((WebExchangeBindException) t).getAllErrors()).map(MessageValidation::new).collect(toSet()).toArray();
//            return process(factory, server, value);
//        } else if (t instanceof ConstraintViolationException) {
//            var value = ofAll(((ConstraintViolationException) t).getConstraintViolations()).map(MessageValidation::new).collect(toSet()).toArray();
//            return process(factory, server, value);
//        } else if (t instanceof ValidationException) {
//            var value = Option((ValidationException) t).map(i -> new MessageValidation("Something went wrong during validation.")).get();
//            if(t.getCause() instanceof ResourceAccessException) {
//                value = Option((ValidationException) t).map(i -> new MessageValidation("The connection to the api used for validation failed.")).get();
//                log.error(value.getMessage());
//            } else {
//                t.printStackTrace();
//                log.error(t.getMessage());
//            }
//            return process(factory, server, value);
//        } else if(t instanceof RuntimeException && !(t instanceof NullPointerException)) {
//            log.error(t.getMessage(), t);
//            return process(factory, server, t.getMessage());
//        }
//        log.error(t.getMessage(), t);
//        return server.getResponse().writeWith(just(factory.wrap(bundle.getString("Desculpe, ocorreu um erro.").getBytes())));
//    }
//
//    private Mono<Void> process(DataBufferFactory factory, ServerWebExchange server, Object value) {
//        var buffer = Try(() -> mapper.writeValueAsBytes(value))
//                .map(factory::wrap)
//                .onFailure(i -> factory.wrap(i.getMessage().getBytes())).get();
//        return server.getResponse().writeWith(just(buffer));
//    }
//}
