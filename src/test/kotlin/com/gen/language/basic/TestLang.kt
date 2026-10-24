package com.gen.language.basic

import com.gen.com.gen.language.basic.Context
import com.gen.com.gen.language.basic.StackCompiler
import com.gen.com.gen.language.basic.TokenLexer
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TestLang {
  val lexer = TokenLexer()
  
  val compiler = StackCompiler()
  
  @Test
  fun testLexAndCompile1() {
    
    val expression = "2+1"
    
    val tokens = lexer.lex(expression)
    val compiled = compiler.compile(tokens)
    
    val a1 = compiled.evaluate(Context())
    a1 shouldBeTypeOf BigDecimal::class
    a1 shouldBe BigDecimal(3)
  }
  
  @Test
  fun testLexAndCompile2() {
    
    val expression = "2*(1+3)"
    
    val tokens = lexer.lex(expression)
    val compiled = compiler.compile(tokens)
    
    val a1 = compiled.evaluate(Context())
    a1 shouldBeTypeOf BigDecimal::class
    a1 shouldBe BigDecimal(8)
  }
  
  
  
 
}