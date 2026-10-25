package com.gen.language.basic

import java.math.BigDecimal
import java.util.regex.Pattern

class TokenLexer {
  
  private val lexerRegex = Word.entries.filter { it.lexable }.joinToString("|") { "(?<${it.name}>${it.regexImage})" }
  
  // println(lexerRegex)
  private val p = Pattern.compile(lexerRegex)
  
  fun lex(expression: String): List<Token> {
    
    val m = p.matcher(expression)
    val tokens = mutableListOf<Token>()
    while (m.find()) {
      val word = Word.entries.first { m.group(it.name) != null }
      val unary = tokens.isEmpty() || tokens.last().word.let { it.operator || it == Word.OPENPAR }
      val image = m.group(word.name).trim()
      val token = Token(word, m.group(word.name).trim(), unary, m.start(), m.end(), defineBaseEvaluator(word, image))
      
      tokens.add(token)
    }
    
    return tokens
  }
  
  fun defineBaseEvaluator(word: Word, image: String): (c: Context) -> Any {
    val evaluator = when (word) {
      Word.NUMBER -> numberEvaluator(image)
      Word.STRING -> stringEvaluator(image)
      Word.BOOLEAN -> booleanEvaluator(image)
      Word.VARIABLE -> variableEvaluator(image)
      else -> { _: Context -> Unit }
    }
    
    return evaluator
  }
  
  fun numberEvaluator(image: String) = { _: Context -> BigDecimal(image) }
  fun booleanEvaluator(image: String) = { _: Context -> image.toBoolean() }
  fun stringEvaluator(image: String) = { _: Context -> image }
  fun variableEvaluator(image: String) = { c: Context -> c.get(image)?.evaluate(c) ?: Unit }
  
}