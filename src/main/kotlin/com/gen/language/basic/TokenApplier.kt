package com.gen.com.gen.language.basic

class TokenApplier {
  
  
  fun unaryNegativeToken(t: Token):Token {
  
    if (t.isNumber()) return Token(Word.NUMBER, )
  }
  
  fun Token.isNumber() =
    this.word == Word.NUMBER
  
}