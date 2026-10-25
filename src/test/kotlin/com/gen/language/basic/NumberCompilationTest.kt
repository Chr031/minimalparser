package com.gen.language.basic

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class NumberCompilationTest {
  
  companion object {
    @JvmStatic
    fun numberExpressionsParams(): Stream<Arguments> =
      Stream.of(
        Arguments.of("1 -2", "-1"),
        Arguments.of("8/2", "4"),
        Arguments.of("3+ -1", "2"),
        Arguments.of("3+ -(10-3*4)", "5"),
        Arguments.of("3.3^1.2", "4.1900260963770135"),
        Arguments.of("1/3", "0.33333333333333"),
        Arguments.of("3 % 1", "0"),
        Arguments.of("3%2", "1"),
        Arguments.of("1+3%2", "2"),
        Arguments.of("(1+3)%2", "0"),
        Arguments.of("+1", "1"),
        Arguments.of("+1 + 10", "11"),
        Arguments.of("+(1 - 10) - (+1-10)", "0"),
      )
  }
  
  val compiler = StackCompiler()
  
  
  @ParameterizedTest
  @MethodSource("numberExpressionsParams")
  fun testNumberCompilation(expression: String, resultAsString: String) {
    val evaluation = compiler.eval(expression)
    evaluation.toString() shouldBe resultAsString
  }
}