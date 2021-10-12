# spring-cloud-gateway-rsocket example
Polyglot RSocket clients communication via RSocket gateway cluster...

```
                                +------+       +------+
                     +~-*-~--~> | gw 2 | <~-~> | pong |
                     |          +------+       +------+
   Client            V              |           Server
  +------+       +------+       +------+
  | ping | <~-~> | gw 1 | <~-~> | gw 3 |
  +------+       +------+       +------+
```

* no service mesh
* no sidecar
* no circuit breaker

## getting started

start _r-socket gateway cluster_ run _ping-pong_ apps

```bash
./mvnw compile spring-boot:start -f gateway -Pgw1
./mvnw compile spring-boot:start -f gateway -Pgw2
./mvnw compile spring-boot:start -f gateway -Pgw3
./mvnw compile spring-boot:start -f pong
./mvnw compile spring-boot:start -f ping
```

after all _tear down_ everything

```bash
#./mvnw spring-boot:stop -f pong
#./mvnw spring-boot:stop -f ping
#./mvnw spring-boot:stop -f gateway -Pgw3
#./mvnw spring-boot:stop -f gateway -Pgw2
#./mvnw spring-boot:stop -f gateway -Pgw1
killall java
```

## Versions

* `spring-cloud` -> `Hoxton.RELEASE`
* `spring-cloud-gateway-rsocket` -> `2.2.0.M2`
* `spring-cloud-rsocket-broker` -> `0.2.0.BUILD-SNAPSHOT`
* `spring-cloud-rsocket-client` -> `0.2.0.BUILD-SNAPSHOT`

## Resources

* https://www.infoq.com/presentations/rsocket-spring-cloud-gateway
* https://github.com/spencergibb/rsocket-routing-sample
