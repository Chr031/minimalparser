package com.gen.com.gen.language.basic


enum class Word(val lexable: Boolean, val regexImage: String, val operator: Boolean = false, val priority: Int = 0, val final:Boolean = false) {
  
  BOOLEAN(true, "true|false"),
  NUMBER(true, "[0-9]+(\\.[0-9]+)?"),
  STRING(true, "'(?:[^'\\\\]|\\\\.)*'"), // string is always between ', and \' for escape
  VARIABLE(true, "[a-zA-Z_][a-zA-Z0-9_]*"),
  PLUS(true, "\\+", true, 60),
  MINUS(true, "-", true, 60),
  MULTIPLY(true, "\\*", true, 70),
  DIVIDE(true, "/", true, 70),
  POWER(true, "\\^", true, 80),
  EQUAL(true, "==", true, 50),
  NOT_EQUAL(true, "!=", true, 50),
  LESS_OR_EQUAL(true, "<=", true, 50),
  GREATER_OR_EQUAL(true, ">=", true, 50),
  LESS(true, "<", true, 50),
  GREATER(true, ">", true, 50),
  AND(true, "&&", true, 40),
  OR(true, "\\|\\|", true, 30),
  NOT(true, "!", true, 91),
  OPEN_PAR(true, "\\("),
  END_PAR(true, "\\)"),
  ASSIGN(true, "=", true, 10),
  
  CR(true, "\\n"),
  
}