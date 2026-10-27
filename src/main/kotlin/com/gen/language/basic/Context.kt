package com.gen.language.basic

import java.math.BigDecimal

class Context {
  
  
  val varMap: MutableMap<String, Any> = mutableMapOf()
  
  constructor(values: List<Pair<String, Any>> = listOf()) {
    values.forEach { register(it.first, it.second) }
  }
  
  fun get(variableName: String): Any? = varMap[variableName]
  
  fun register(variableName: String, value: Any) {
    if (value is Number) {
      varMap[variableName] = BigDecimal(value.toString())
    } else if (value is String || value is Boolean) {
      varMap[variableName] = value
    } else {
      throw Exception("Invalid variable type $value. Only Numbers, String and Boolean can be accepted !")
    }
  }
  
}