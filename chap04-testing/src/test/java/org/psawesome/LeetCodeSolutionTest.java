package org.psawesome;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
  String[] inputData;

  @BeforeEach
  void setUp() {
    inputData = new String[]{"flower", "flow", "flight"};
  }

  @AfterEach
  void tearDown() {
    inputData = null;
  }

  @Test
  @DisplayName("첫 번째 글자의 총 사이즈부터 거꾸로 count 테스트")
  void testShouldBeSplitAndAccumulateCharacters() {
    final int size = inputData[0].split("").length;
    final long count = IntStream.iterate(size - 1, s -> s - 1)
//            .peek(System.out::println)
            .limit(size)
            .count();
    Assertions.assertEquals(6, count);
  }


}
