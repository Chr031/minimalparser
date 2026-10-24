package com.gen.com.gen.language.basic

import java.math.BigDecimal
import kotlin.math.max

open class Token(val word: Word, private val image: String, val unary: Boolean, val start: Int = 0, val end: Int = 0, val evaluator: (context: Context) -> Any) {
  
  fun image(): String = image.take(max(3, image.length))
  fun varImage(): String = if (isVariable()) image else throw SyntaxErrorException("Token $word is not a variable at $start")
  
  
  fun evaluate(context: Context): Any = evaluator(context)
  fun asNumber(context: Context): BigDecimal = evaluator(context) as BigDecimal
  fun asBoolean(context: Context): Boolean = evaluator(context) as Boolean
  fun asString(context: Context): String = evaluator(context) as String
  
  fun isNumber() = this.word == Word.NUMBER
  fun isString() = this.word == Word.STRING
  fun isBoolean() = this.word == Word.BOOLEAN
  fun isVariable() = this.word == Word.VARIABLE
  
  
}

@FunctionalInterface
interface Evaluator {
  fun evaluate(c: Context): Any
}
