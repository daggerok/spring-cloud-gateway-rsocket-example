package daggerok.pong;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

import java.time.Duration;

@Log4j2
@Controller
class PongServerController {

  @MessageMapping("ping-pong")
  Flux<String> pingPongStream(String ping) {
    log.info("Pong server received: {}", ping);
    return Flux.interval(Duration.ofSeconds(2))
               .map(aLong -> "ping-" + aLong + "-pong")
               .doOnNext(log::info);
  }
}

@SpringBootApplication
public class PongApplication {

  public static void main(String[] args) {
    Hooks.onOperatorDebug();
    SpringApplication.run(PongApplication.class, args);
  }
}
