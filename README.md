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

```bash
./mvnw

SPRING_PROFILES_ACTIVE=gateway1 java -jar ./gateway/target/*.jar
SPRING_PROFILES_ACTIVE=gateway2 java -jar ./gateway/target/*.jar

java -jar ./ping-pong/pong/target/*.jar
java -jar ./ping-pong/ping/target/*.jar
```

## Versions

* `spring-cloud` -> `Hoxton.RELEASE`
* `spring-cloud-gateway-rsocket` -> `2.2.0.M2`
* `spring-cloud-rsocket-broker` -> `0.2.0.BUILD-SNAPSHOT`
* `spring-cloud-rsocket-client` -> `0.2.0.BUILD-SNAPSHOT`

## Resources

* https://www.infoq.com/presentations/rsocket-spring-cloud-gateway
* https://github.com/spencergibb/rsocket-routing-sample
