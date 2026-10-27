package com.gen.language.basic


import java.util.ArrayDeque

open class StackCompiler {
  
  val lexer = TokenLexer()
  val applier = TokenApplier()
  
  fun eval(expression: String) =
    compile(expression).evaluate(Context())
  
  fun eval(expression: String, context: Context) =
    compile(expression).evaluate(context)
  
  
  fun compile(code: String): Token {
    val tokens = lexer.lex(code)
    val compilation = compile(tokens)
    return compilation
  }
  
  
  fun compile(tokens: List<Token>): Token {
    val stack = reverseStack(tokens)
    return compileReverseStack(stack)
  }
  
  /**
   * cf https://en.wikipedia.org/wiki/Shunting_yard_algorithm
   */
  protected fun reverseStack(tokens: List<Token>): ArrayDeque<Token> {
    
    val stack = ArrayDeque<Token>()
    val operatorStack = ArrayDeque<Token>()
    
    tokens.forEach { token ->
      if (token.word.operator) {
        while (operatorStack.isNotEmpty() && operatorStack.peek().let { it.word.priority > token.word.priority && it.word != Word.OPENPAR }) {
          stack.push(operatorStack.pop())
        }
        operatorStack.push(token)
      } else if (token.word == Word.OPENPAR)
        operatorStack.push(token)
      else if (token.word == Word.ENDPAR) {
        while (operatorStack.isNotEmpty() && operatorStack.peek().word != Word.OPENPAR) {
          stack.push(operatorStack.pop())
        }
        if (operatorStack.isNotEmpty())
          operatorStack.pop() // discard the open par
      } else {
        stack.push(token)
      }
    }
    while (operatorStack.isNotEmpty())
      stack.push(operatorStack.pop())
    
    return stack
  }
  
  
  private fun compileReverseStack(reversedTokens: ArrayDeque<Token>): Token {
    val currentArgs = ArrayDeque<Token>()
    val tokenStack = ArrayDeque(reversedTokens)
    
    while (tokenStack.size > 1 || currentArgs.isNotEmpty()) {
      
      while (!tokenStack.peekLast().word.operator)
        currentArgs.add(tokenStack.removeLast())
      
      val currentOperator = tokenStack.removeLast()
      val compiledToken = apply(currentOperator, currentArgs)
      tokenStack.addLast(compiledToken)
    }
    // TODO check that tokenStack has only one element
    return tokenStack.removeFirst()
  }
  
  private fun retrieveArguments(arguments: ArrayDeque<Token>): Pair<Token, Token> {
    val second = arguments.pollLast()
    val first = arguments.pollLast()
    return first to second
  }
  
  
  private fun retrieveArgument(arguments: ArrayDeque<Token>) =
    arguments.pollLast()
  
  private fun apply(operator: Token, arguments: ArrayDeque<Token>): Token =
    when (operator.word) {
      Word.MINUS -> {
        if (operator.unary) applier.unaryNegativeToken(operator, retrieveArgument(arguments)) else
          retrieveArguments(arguments).let { applier.subtractTokens(operator, it.first, it.second) }
      }
      Word.PLUS -> {
        if (operator.unary) applier.unaryPositiveToken(operator, retrieveArgument(arguments)) else
          retrieveArguments(arguments).let { applier.addTokens(it.first, it.second) }
      }
      Word.MULTIPLY -> retrieveArguments(arguments).let { applier.multiplyTokens(it.first, it.second) }
      Word.DIVIDE -> retrieveArguments(arguments).let { applier.divideTokens(it.first, it.second) }
      Word.POWER -> retrieveArguments(arguments).let { applier.powerTokens(it.first, it.second) }
      Word.MODULO -> retrieveArguments(arguments).let { applier.moduloTokens(it.first, it.second) }
      Word.ASSIGN -> retrieveArguments(arguments).let { applier.assignExpressionTokens(it.first, it.second) }
      Word.AND -> retrieveArguments(arguments).let { applier.andTokens(it.first, it.second) }
      Word.OR -> retrieveArguments(arguments).let { applier.orTokens(it.first, it.second) }
      Word.EQUAL -> retrieveArguments(arguments).let { applier.equalTokens(it.first, it.second) }
      Word.NOTEQUAL -> retrieveArguments(arguments).let { applier.notEqualTokens(it.first, it.second) }
      Word.LESS -> retrieveArguments(arguments).let { applier.lessTokens(it.first, it.second) }
      Word.LESSOREQUAL -> retrieveArguments(arguments).let { applier.lessOrEqualTokens(it.first, it.second) }
      Word.GREATER -> retrieveArguments(arguments).let { applier.greaterTokens(it.first, it.second) }
      Word.GREATEROREQUAL -> retrieveArguments(arguments).let { applier.greaterOrEqualTokens(it.first, it.second) }
      Word.NOT -> applier.notToken(operator, retrieveArgument(arguments))
      Word.CONCAT -> retrieveArguments(arguments).let { applier.concatenateTokens( it.first, it.second )}
      Word.CONTAINS -> retrieveArguments(arguments).let { applier.containsTokens( it.first, it.second )}
      
      else -> error("$operator is not a registered operator")
    }
}
  

