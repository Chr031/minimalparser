package com.gen.language.basic

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class VariableCompilationTest {
  
  val compiler = StackCompiler()
  
  @Test
  fun testVariableCompilation() {
    
    val context = Context(listOf("a" to 1))
    val expression = "a + 1"
    val evaluation = compiler.eval(expression, context)
    evaluation shouldBe BigDecimal.TWO
    
    
  }
  
  @Test
  fun testVariableMultiContext() {
    val expression = "( a + b )/ c"
    val compiled = compiler.compile(expression)
    val context = Context(listOf("a" to 1, "b" to 2, "c" to 3))
    val evaluation = compiled.evaluate(context)
    evaluation shouldBe BigDecimal("1")
    
    val context2 = Context(listOf("a" to 10, "b" to 2, "c" to 2))
    val evaluation2 = compiled.evaluate(context2)
    evaluation2 shouldBe BigDecimal("6")
  }
  
  @Test
  fun testVariableMultiContext2() {
    val expression = "( a && b ) || c == d"
    val compiled = compiler.compile(expression)
    val context = Context(listOf("a" to true, "b" to false, "c" to 3, "d" to 3))
    val evaluation = compiled.evaluate(context)
    evaluation shouldBe true
    
    val context2 = Context(listOf("a" to true, "b" to true, "c" to 3, "d" to 4))
    val evaluation2 = compiled.evaluate(context2)
    evaluation2 shouldBe true
  }
  
  
  @Test
  fun testVariableMultiContext3() {
    val expression = "x= (a + b) * (c - d)"
    val compiled = compiler.compile(expression)
    val context = Context(listOf("a" to 1, "b" to 2, "c" to 3, "d" to 4))
    val evaluation = compiled.evaluate(context)
    evaluation shouldBe BigDecimal("-3")
    context.get("x") shouldBe BigDecimal("-3")
  }
  
  
  @Test
  fun testVariableStringContext1() {
    val expression = "famousHelloWorld= a .' '. b "
    val compiled = compiler.compile(expression)
    val context = Context(listOf("a" to "hello", "b" to "world"))
    val evaluation = compiled.evaluate(context)
    evaluation shouldBe "hello world"
    context.get("famousHelloWorld") shouldBe "hello world"
    
    val expression2 = "famousHelloWorld ~= 'world'"
    val compiled2 = compiler.compile(expression2)
    val evaluation2 = compiled2.evaluate(context)
    evaluation2 shouldBe true
    
  }
  
  
}