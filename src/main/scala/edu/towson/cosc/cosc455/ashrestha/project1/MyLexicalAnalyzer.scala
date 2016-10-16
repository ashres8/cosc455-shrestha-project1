package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
class MyLexicalAnalyzer extends LexicalAnalyzer{
  val constant = new CONSTENT
  var listFileContent = Array.empty[Char]
  var charToken = scala.collection.mutable.ArrayBuffer.empty[Char]
  var currentChar: Char = ' '
  var fileIndex = 0

  override def addChar(): Unit = charToken.append(currentChar)

  override def getChar(): Char = {
    var c: Char = listFileContent(fileIndex)
    fileIndex = fileIndex + 1
    c
  }

  override def getNextToken(): Unit = {
    println("Getting Token...")
    currentChar = getChar()
    while(isSpace(currentChar)) {
      currentChar = getChar()
    }
    while(!isSpace(currentChar) && currentChar != '\n'){
      if(constant.SpacialChar.contains(currentChar)){
        println("Found Spacial Char: " + currentChar)
        addChar()
        currentChar = getChar()
      } else {
        println("Found Normal Char: " + currentChar)
        addChar()
        currentChar = getChar()
      }
    }

    println(charToken.mkString(""))
    Compiler.currentToken = charToken.mkString("")
  }

  override def lookup(): Boolean = ???

  def isSpace(c: Char): Boolean = c == ' '
}
