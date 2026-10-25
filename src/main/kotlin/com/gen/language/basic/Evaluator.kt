package com.gen.language.basic

@FunctionalInterface
interface Evaluator {
  fun evaluate(c: Context): Any
}