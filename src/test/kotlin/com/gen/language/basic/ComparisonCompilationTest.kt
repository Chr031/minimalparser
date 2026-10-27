package com.gen.language.basic

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ComparisonCompilationTest {
  
  companion object {
    @JvmStatic
    fun comparisonExpressionsParams(): Stream<Arguments> =
      Stream.of(
        Arguments.of("1<2", "true"),
        Arguments.of("1<=1", "true"),
        Arguments.of("12>4.1", "true"),
        Arguments.of("12-3 >=10", "false"),
        Arguments.of("13 == 2 || 12<31", "true"),
        Arguments.of("13 == 9+ 4", "true"),
        Arguments.of("49 == 7^2", "true"),
        Arguments.of("49 < 7^2", "false"),
        Arguments.of("49 > 7^2", "false"),
        Arguments.of("18 != 7^2", "true"),
        Arguments.of("36 != 6^2", "false"),
        Arguments.of("12.8 - 0.8 != 12.1", "true"),
        Arguments.of("12.8 - 0.8 != 12", "false"),
        Arguments.of("true != true", "false"),
        Arguments.of("false != false", "false"),
        Arguments.of("true != false", "true"),
        Arguments.of("'abc' == 'ab' . 'c'", "true"),
        Arguments.of("'abc' == 'ab' . 'cd'", "false"),
        Arguments.of("'' == false", "false"),
        Arguments.of("'' == ''", "true"),
        Arguments.of("'' != ''", "false"),
        Arguments.of("'12' == 12", "false"),
        Arguments.of("'12' != '12'", "false"),
        Arguments.of("36 == 6 + 6 * 2 + 6 * 3", "true"),
        
        Arguments.of("'abc' ~= 'abc'", "true"),
        Arguments.of("'normally' ~= 'norm'", "true"),
        Arguments.of("!('basic' ~= 'base')", "true"),
        Arguments.of("'bas'.'ic' ~= 'basic'", "true")
      
      )
  }
  
  val compiler = StackCompiler()
  
  
  @ParameterizedTest
  @MethodSource("comparisonExpressionsParams")
  fun testComparisonCompilation(expression: String, resultAsString: String) {
    val evaluation = compiler.eval(expression)
    evaluation.toString() shouldBe resultAsString
  }
  
  
}