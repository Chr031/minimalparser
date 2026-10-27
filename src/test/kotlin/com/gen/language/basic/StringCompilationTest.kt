package com.gen.language.basic

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class StringCompilationTest {
  
  companion object {
    @JvmStatic
    fun stringExpressionsParams(): Stream<Arguments> =
      Stream.of(
        Arguments.of("'1'", "1"),
        Arguments.of("'8/2' . 12", "8/212"),
        Arguments.of("'this is a test' . '!'", "this is a test!"),
        Arguments.of(""" 'for \'escape\'' . '!'""", "for 'escape'!"),
        
        )
    
    
  }
  
  val compiler = StackCompiler()
  
  
  @ParameterizedTest
  @MethodSource("stringExpressionsParams")
  fun testStringCompilation(expression: String, resultAsString: String) {
    val evaluation = compiler.eval(expression)
    evaluation.toString() shouldBe resultAsString
  }
  
  
}