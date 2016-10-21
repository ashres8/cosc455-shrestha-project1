package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
class MyLexicalAnalyzer extends LexicalAnalyzer{
  val constant = new CONSTENT
  var listFileContent = Array.empty[Char]
  var strToken = ""
  var currentChar: Char = ' '
  var fileIndex = 0

  override def addChar(): Unit = {
    strToken = strToken + currentChar
  }

  override def getChar(): Char = {
    var c: Char = listFileContent(fileIndex)
    fileIndex = fileIndex + 1
    c
  }

  override def getNextToken(): Unit = {
    println("Getting Token...")
    strToken = ""
    currentChar = getChar()
    while(isSpace(currentChar)) {
      currentChar = getChar()
    }

    if(constant.SpacialChar.contains(currentChar)){
      spacialChar()
    } else {
      println("Found Normal Char: " + currentChar)
      addChar()
      currentChar = getChar()
    }

    println(strToken)
    Compiler.currentToken = strToken
  }

  override def lookup(): Boolean = {
    for (word <- constant.ArrayOfTokens){
      if(word.equalsIgnoreCase(strToken)){
        return true
      }
    }
    false
  }

  def spacialChar(): Unit ={
    while(!isSpace(currentChar) && currentChar != '\n') {
      println("Found Char: " + currentChar)
      addChar()
      currentChar = getChar()
    }
    //println(strToken)
    if(currentChar == '\n'){
      strToken = strToken.dropRight(1)
    }

    if(!lookup()){
      println("Lexical Error:: can't find '" + strToken + "'")
      System.exit(1)
    }
  }

  def isSpace(c: Char): Boolean = c == ' '
}
