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

  //Adds the last character to the string
  override def addChar(): Unit = {
    strToken = strToken + currentChar
  }

  //Gets a new char form the file string
  override def getChar(): Char = {
    if(fileIndex >= listFileContent.length) {
      return '\n'
    }

    val c: Char = listFileContent(fileIndex)
    fileIndex = fileIndex + 1
    c
  }

  //Creates a token for the syntax analyser to use
  override def getNextToken(): Unit = {
    //println("Getting Token...")
    strToken = ""
    while(isSpace(currentChar) || currentChar == '\n' || currentChar == '\t' || currentChar == '\r') {
      currentChar = getChar()
    }

    if(constant.SpacialChar.contains(currentChar)){
      spacialChar()
    } else {
      textChar()
    }

    println("Lexical: " + strToken)
    //redoes the getNextToken function if the token is "\r"
    if(strToken == "\r"){
      getNextToken()
    }
    Compiler.currentToken = strToken
  }

  //Looks up the Current token if it is valid
  override def lookup(): Boolean = {
    println(strToken)
    for (word <- constant.ArrayOfTokens){
      if(word.equalsIgnoreCase(strToken)){
        return true
      }
    }
    false
  }

  //Loops and gets every char as text
  def textChar() = {
    do{
      //println("Found Normal Char: " + currentChar)
      addChar()
      currentChar = getChar()
    } while (!constant.SpacialChar.contains(currentChar) && currentChar != '\n' && currentChar != '\r')
  }

  //Function to check spacial tokens
  def spacialChar(): Unit ={
    /* while(!isSpace(currentChar) && currentChar != '\n') {
      println("Found Char: " + currentChar)
      addChar()
      currentChar = getChar()
    }
    //println(strToken)
    if(currentChar == '\n'){
      strToken = strToken.dropRight(1)
    } */

    if(currentChar == '\\'){
      do{
        addChar()
        currentChar = getChar()
      } while (currentChar != '[' && currentChar != '\n' && !isSpace(currentChar) && currentChar != 0)

      if(currentChar == '\n'){
        if(fileIndex != listFileContent.length)
          strToken = strToken.dropRight(1)
      } else {
        addChar()
        currentChar = getChar()
      }
    } else if(currentChar == '*'){
      addChar()
      currentChar = getChar()
      if(currentChar == '*'){
        addChar()
        currentChar = getChar()
      }
    } else if(currentChar == '!'){
      addChar()
      currentChar = getChar()
      if (currentChar == '['){
        addChar()
        currentChar = getChar()
      } else {
        errorAndQuit("Lexical Error:: Can't find '" + currentChar + "'")
      }
    } else {
      addChar()
      currentChar = getChar()
    }

    if(!lookup()){
      errorAndQuit("Lexical Error:: can't find '" + strToken + "'")
    }
  }

  def isSpace(c: Char): Boolean = c == ' '

  //Shows an error and quits :D
  def errorAndQuit(str: String): Unit = {
    println(str)
    System.exit(1)
  }
}
