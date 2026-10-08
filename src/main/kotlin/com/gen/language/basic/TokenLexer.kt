package com.gen.com.gen.language.basic

import java.util.regex.Pattern

class TokenLexer {
  
  val lexerRegex = Word.entries.filter { it.lexable }.joinToString("|") { "(?<${it.name}>${it.regexImage})" }
  
  // println(lexerRegex)
  val p = Pattern.compile(lexerRegex)
  
  fun lexExpression(expression: String): List<Token> {
    
    val m = p.matcher(expression)
    val tokens = mutableListOf<Token>()
    while (m.find()) {
      val word = Word.entries.first { m.group(it.name) != null }
      val unary = tokens.isEmpty() || tokens.last().word.let { it.operator || it == Word.OPEN_PAR }
      val token = Token(word, m.group(word.name).trim(), unary, m.start(), m.end())
      tokens.add(token)
    }
    
    return tokens
  }
}