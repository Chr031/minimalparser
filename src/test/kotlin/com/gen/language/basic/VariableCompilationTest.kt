package com.gen.language.basic

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class VariableCompilationTest {
  
  val compiler = StackCompiler()
  
  @Test
  fun testVariableCompilation() {
   
    val context = Context(listOf("a" to 1))
    val expression = "a + 1"
    val evaluation = compiler.eval(expression, context)
    evaluation shouldBe 2
  }
}