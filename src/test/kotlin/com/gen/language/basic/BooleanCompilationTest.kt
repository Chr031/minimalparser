package com.gen.language.basic

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class BooleanCompilationTest {
  
  companion object {
    @JvmStatic
    fun booleanExpressionsParams(): Stream<Arguments> =
      Stream.of(
        Arguments.of("true", "true"),
        Arguments.of("false", "false"),
        Arguments.of("false && true", "false"),
        Arguments.of("false || true", "true"),
        Arguments.of("(true || false) && false", "false"),
        Arguments.of("true || (false && false)", "true"),
        Arguments.of("true || false && false", "true"),
        Arguments.of("!true", "false"),
        Arguments.of("!true || true", "true"),
      )
  }
  
  val compiler = StackCompiler()
  
  
  @ParameterizedTest
  @MethodSource("booleanExpressionsParams")
  fun testBooleanCompilation(expression: String, resultAsString: String) {
    val evaluation = compiler.eval(expression)
    evaluation.toString() shouldBe resultAsString
  }
  
  
}