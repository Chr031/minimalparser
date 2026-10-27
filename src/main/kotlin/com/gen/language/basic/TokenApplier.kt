package com.gen.language.basic

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow


class TokenApplier() {
  
  
  fun unaryNegativeToken(operator: Token, arg: Token): Token =
    if (arg.isNumber())
      Token(Word.NUMBER, "- (${arg.image()})", false, operator.start, arg.end) { c -> arg.asNumber(c).negate() }
    else
      throw SyntaxErrorException("${arg.image()} at ${arg.start} is not a number")
  
  
  fun subtractTokens(operator: Token, first: Token, second: Token): Token =
    if (first.isNumber() && second.isNumber()) {
      Token(Word.NUMBER, "${first.image()} - ${second.image()}", false, operator.start, second.end) { c -> first.asNumber(c).minus(second.asNumber(c)) }
    } else
      if (!first.isNumber())
        throw SyntaxErrorException("${first.image()} at ${first.start} is not a number")
      else if (!second.isNumber())
        throw SyntaxErrorException("${second.image()} at ${second.start} is not a number")
      else
        throw SyntaxErrorException("${first.image()} at ${first.start} or ${second.image()} at ${second.start} are not numbers")
  
  
  fun unaryPositiveToken(operator: Token, arg: Token): Token =
    if (arg.isNumber())
      Token(Word.NUMBER, "+(${arg.image()})", false, operator.start, arg.end) { arg.asNumber(it) }
    else
      throw SyntaxErrorException("${arg.image()} at ${arg.start} is not a number")
  
  
  fun addTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.NUMBER, "${first.image()} + ${second.image()}", false, first.start, second.end) { c -> first.asNumber(c).add(second.asNumber(c)) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be added to ${second.word} at ${first.start}")
      
    }
  
  
  fun multiplyTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.NUMBER, "${first.image()} * ${second.image()}", false, first.start, second.end) { first.asNumber(it).multiply(second.asNumber(it)) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be multiplied by ${second.word} at ${first.start}")
      
    }
  
  
  fun divideTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.NUMBER, "${first.image()} / ${second.image()}", false, first.start, second.end) { c ->
          
          val dividend = first.asNumber(c)
          val divisor = second.asNumber(c)
          try {
            dividend.divide(divisor)
          } catch (ae: ArithmeticException) {
            if (ae.message?.startsWith("Non-terminating decimal expansion;") ?: false)
              dividend.divide(divisor, 14, RoundingMode.HALF_UP)
            else throw ae
          }
          
        }
      else ->
        throw SyntaxErrorException("${first.word} cannot be divided by ${second.word} at ${first.start}")
      
    }
  
  
  fun powerTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.NUMBER, "${first.image()} ^ ${second.image()}", false, first.start, second.end)
        { c -> BigDecimal.valueOf(first.asNumber(c).toDouble().pow(second.asNumber(c).toDouble())) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be elevated to power of ${second.word} at ${first.start}")
      
    }
  
  
  fun moduloTokens(first: Token, second: Token)
          : Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.NUMBER, "${first.image()} ^ ${second.image()}", false, first.start, second.end) { c -> first.asNumber(c).remainder(second.asNumber(c)) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be moduled by ${second.word} at ${first.start}")
      
    }
  
  
  fun assignExpressionTokens(first: Token, second: Token): Token =
    when {
      first.isVariable() -> Token(Word.VARIABLE, first.varImage(), false, first.start, second.end)
      { c ->
        val eval = second.evaluate(c)
        c.varMap[first.varImage()] = eval
        eval
      }
      else ->
        throw SyntaxErrorException("${first.word} is not a variable and cannot be assigned to ${second.word} at ${first.start}")
      
    }
  
  fun andTokens(first: Token, second: Token)
          : Token =
    when {
      first.isBoolean() && second.isBoolean() ->
        Token(Word.BOOLEAN, "${first.image()} && ${second.image()}", false, first.start, second.end)
        { c -> first.asBoolean(c) && (second.asBoolean(c)) }
      else ->
        throw SyntaxErrorException("${first.word} & ${second.word} at ${first.start} is not possible")
    }
  
  fun orTokens(first: Token, second: Token): Token =
    when {
      first.isBoolean() && second.isBoolean() ->
        Token(Word.BOOLEAN, "${first.image()} || ${second.image()}", false, first.start, second.end)
        { c -> first.asBoolean(c) || (second.asBoolean(c)) }
      else ->
        throw SyntaxErrorException("${first.word} & ${second.word} at position ${first.start} is not possible")
    }
  
  
  fun equalTokens(first: Token, second: Token): Token =
    Token(
      Word.BOOLEAN, "${first.image()} || ${second.image()}", false,
      first.start, second.end,
      evaluator = when {
        first.isNumber() && second.isNumber() ->
          { c -> first.asNumber(c).compareTo(second.asNumber(c)) == 0 }
        else ->
          { c -> first.evaluate(c) == second.evaluate(c) }
      }
    )
  
  
  fun notEqualTokens(first: Token, second: Token): Token =
    Token(
      Word.BOOLEAN, "${first.image()} || ${second.image()}", false, first.start, second.end,
      evaluator = when {
        first.isNumber() && second.isNumber() ->
          { c -> first.asNumber(c).compareTo(second.asNumber(c)) != 0 }
        else ->
          { c -> first.evaluate(c) != second.evaluate(c) }
      })
  
  fun lessTokens(first: Token, second: Token)
          : Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.BOOLEAN, "${first.image()} < ${second.image()}", false, first.start, second.end)
        { c -> first.asNumber(c) < second.asNumber(c) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be compared to ${second.word} at ${first.start}")
      
    }
  
  fun lessOrEqualTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.BOOLEAN, "${first.image()} <= ${second.image()}", false, first.start, second.end)
        { c -> first.asNumber(c) <= second.asNumber(c) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be compared to ${second.word} at ${first.start}")
      
    }
  
  
  fun greaterTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.BOOLEAN, "${first.image()} > ${second.image()}", false, first.start, second.end)
        { c -> first.asNumber(c) > second.asNumber(c) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be compared to ${second.word} at ${first.start}")
      
    }
  
  
  fun greaterOrEqualTokens(first: Token, second: Token): Token =
    when {
      first.isNumber() && second.isNumber() ->
        Token(Word.BOOLEAN, "${first.image()} >= ${second.image()}", false, first.start, second.end)
        { c -> first.asNumber(c) >= second.asNumber(c) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be compared to ${second.word} at ${first.start}")
      
    }
  
  fun notToken(operator: Token, arg: Token): Token =
    when {
      arg.isBoolean() -> Token(Word.BOOLEAN, "!${arg.image()}", false, operator.start, arg.end) { c ->
        !arg.asBoolean(c)
      }
      else ->
        throw SyntaxErrorException("${arg.word} cannot be negated")
    }
  
  fun concatenateTokens(first: Token, second: Token): Token =
    when {
      first.isString() || second.isString() ->
        Token(Word.STRING, "${first.image()}${second.image()}", false, first.start, second.end) { c -> "${first.evaluate(c)}${second.evaluate(c)}" }
      else ->
        throw SyntaxErrorException("${first.word} cannot be concatenated to ${second.word} at ${first.start}")
    }
  
  fun containsTokens(first: Token, second: Token): Token =
    when {
      first.isString() && second.isString() ->
        Token(Word.BOOLEAN, "${first.image()} contains ${second.image()}", false, first.start, second.end)
        { c -> first.asString(c).contains(second.asString(c)) }
      else ->
        throw SyntaxErrorException("${first.word} cannot be compared to ${second.word} at ${first.start}")
    }
  
}