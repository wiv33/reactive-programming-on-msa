package org.psawesome;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 15. Saturday
 */
public class ForExample {
  public static void main(String[] args) {
    Hooks.onOperatorDebug();
    Flux.just("alpha", "bravo", "charlie")
            .map(String::toUpperCase)
            .flatMap(s -> Flux.fromArray(s.split("")))
            .groupBy(String::toString)
//    .sort((s1, s2) -> s1.key().compareTo(s2.key()));
            .sort(Comparator.comparing(GroupedFlux::key))
            .flatMap(group -> Mono.just(Objects.requireNonNull(group.key())).zipWith(group.count()))
            .map(keyAndCount -> keyAndCount.getT1() + " => " + keyAndCount.getT2())
            .subscribe(System.out::println);
  }
}
