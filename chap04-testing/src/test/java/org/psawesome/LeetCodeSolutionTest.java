package org.psawesome;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 27. Thursday
 */
class LeetCodeSolutionTest {
  String[] strs;
  int size;

  @BeforeEach
  void setUp() {
    strs = new String[]{"flower", "flow", "flight"};
    size = strs[0].split("").length;
  }

  @AfterEach
  void tearDown() {
    strs = null;
    size = 0;
  }

  @Test
  @DisplayName("첫 번째 글자의 총 사이즈부터 거꾸로 count 테스트")
  void testShouldBeSplitAndAccumulateCharacters() {
    final long count = IntStream.iterate(size, s -> s - 1)
//            .peek(System.out::println)
            .limit(size)
            .count();
    assertEquals(6, count);
  }

  @Test
  void testSubstringByDiscount() {
//    if (strs.length == 0 || strs[0].isEmpty()) return "";
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
    final int size = strs[0].split("").length;
    final String actual = IntStream.iterate(size, s -> s - 1)
            .limit(size)
            .mapToObj(i -> strs[0].substring(0, i))
            .filter(s -> Arrays.stream(strs).allMatch(f -> f.startsWith(s)))
            .findFirst()
            .orElse("");

    assertEquals("fl", actual);
  }

  @Test
  void testSecondFindLongestCommonPrefix() {
    final var integer = new AtomicInteger(this.size);
// System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");

    final String actual = Stream.generate(() -> strs[0].substring(0, integer.getAndDecrement()))
            .filter(s -> Arrays.stream(strs).skip(1).allMatch(f -> f.startsWith(s)))
//            .findFirst()
            .findAny()
            .orElse("");

    assertEquals("fl", actual);
  }
}
