package com.gen.language.basic

class Context {
  val varMap: MutableMap<String, Token> = mutableMapOf()
  
  fun get(variableName: String): Token? = varMap[variableName]
}