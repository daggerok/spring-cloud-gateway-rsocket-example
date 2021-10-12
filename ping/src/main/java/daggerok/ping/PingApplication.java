package daggerok.ping;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.rsocket.client.BrokerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
@Configuration
class PingClientConfig {

  @PostConstruct
  public void profile() {
    log.info("{} registered.", getClass().getName());
  }

  @Bean
  Mono<RSocketRequester> rsr(RSocketRequester.Builder builder) {
    return builder.connectTcp("gateway1-127-0-0-1.nip.io", 7001)
                  .retry()
                  // .retry(err -> err.toString().contains("Route Id already registered")
                  //     || err.toString().contains("Could not emit tick")
                  //     || err.toString().contains("Connection refused"))
                  .doOnError(throwable -> log.warn(throwable.getLocalizedMessage()));
  }

  @Bean
  AtomicLong requestCounter() {
    return new AtomicLong(0);
  }

  @Bean
  AtomicLong responseCounter() {
    return new AtomicLong(0);
  }

  @Bean
  ApplicationRunner runner(BrokerClient brokerClient,
                           Mono<RSocketRequester> rsr,
                           AtomicLong responseCounter,
                           AtomicLong requestCounter) {
    return args -> {
      rsr.flatMapMany(rr -> rr.route("ping-pong")
                              .data(Flux.just("Max-" + requestCounter.incrementAndGet()))
                              // .data(Flux.interval(Duration.ofSeconds(3))
                              //           .map(aLong -> "Max-" + aLong + "-" + requestCounter.incrementAndGet()))
                              // // .data(Mono.just("Max-" + requestCounter.incrementAndGet()))
                              .metadata(brokerClient.forwarding("pong"))
                              .retrieveFlux(String.class))
         .subscribe(s -> log.info("Ping client received ({}): {}", responseCounter.incrementAndGet(), s));
    };
  }
}

@SpringBootApplication
public class PingApplication {

  public static void main(String[] args) {
    Hooks.onOperatorDebug();
    SpringApplication.run(PingApplication.class, args);
  }
}
