package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
class MySyntaxAnalyzer extends SyntaxAnalyzer{
  val constent = new CONSTENT

  override def gittex(): Unit = {
    if(Compiler.currentToken == constent.DOCB){
      Compiler.Scanner.getNextToken()
      variableDefine()
      title()
      body()
      if(Compiler.currentToken == constent.DOCE){
        println("Done !!")
      }else {
        errorAndQuit("Syntax Error: Document End missing : " + constent.DOCE)
      }
    } else {
      errorAndQuit("Syntax Error: Document Start missing : " + constent.DOCB)
    }
  }

  override def title(): Unit = {
    if(Compiler.currentToken == constent.TITLEB){
      println("Title Begin Found !!")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        println("Text Begin Found !!")
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.SQBRACKETE){
          println("Title End Found !!")
          Compiler.Scanner.getNextToken()
        } else errorAndQuit("Syntax Error: Title End missing : " + constent.SQBRACKETE)
      } else errorAndQuit("Syntax Error: Not Text")
    } else errorAndQuit("Syntax Error: Title Start missing : " + constent.TITLEB + " LIST: " + Compiler.currentToken)
  }

  var bodyfound: Boolean = false

  override def body(): Unit = {
    while(!constent.notBody.contains(Compiler.currentToken)){
      if(!bodyfound) innerText()
      if(!bodyfound) paragraph()
      if(!bodyfound) newline()
      bodyfound = false
    }
  }

  override def paragraph(): Unit = {
    if (Compiler.currentToken == constent.PARAB){
      bodyfound = true
      Compiler.Scanner.getNextToken()
      variableDefine()
      innerText()
      if(Compiler.currentToken == constent.PARAE){
        Compiler.Scanner.getNextToken()
      } else errorAndQuit("Syntax Error: Paragraph End Missing" + constent.PARAE)
    }
  }

  var itfound: Boolean = false
  override def innerText(): Unit = {
    while((!constent.notBody.contains(Compiler.currentToken)) && Compiler.currentToken != constent.PARAB){
      //println("Inner Text: " + Compiler.currentToken)
      if(!itfound) heading()
      if(!itfound) bold()
      if(!itfound) variableUse()
      Compiler.Scanner.getNextToken()
      itfound = false
    }
  }

  override def heading(): Unit = {
    if(Compiler.currentToken == constent.HEADING){
      itfound = true
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.Scanner.getNextToken()
      } else errorAndQuit("Syntax Error: Text after Heading is missing.")
    }
  }

  override def variableDefine(): Unit = {
    if(Compiler.currentToken == constent.DEFB) {
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.EQSIGN){
          Compiler.Scanner.getNextToken()
          if(isText(Compiler.currentToken)){
            Compiler.Scanner.getNextToken()
            if(Compiler.currentToken == constent.SQBRACKETE){
              Compiler.Scanner.getNextToken()
              variableDefine()
            } else errorAndQuit("Syntax Error: Variable Def Error, Not " + constent.SQBRACKETE)
          } else errorAndQuit("Syntax Error: Variable Def Error, Not value defined")
        } else errorAndQuit("Syntax Error: Variable Def Error, Not " + constent.EQSIGN)
      } else errorAndQuit("Syntax Error: Variable Def Error, No variable name")
    }
  }

  override def variableUse(): Unit = {
    if(Compiler.currentToken == constent.USEB){
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.Scanner.getNextToken()
      }else errorAndQuit("Syntax Error: Variable Use Error, No variable name")
    }
  }

  override def bold(): Unit = {
    if(Compiler.currentToken == constent.BOLD){
      itfound = true
    }
  }

  override def italics(): Unit = ???

  override def listItem(): Unit = ???

  override def innerItem(): Unit = ???

  override def link(): Unit = ???

  override def image(): Unit = ???

  override def newline(): Unit = {
    if(Compiler.currentToken == constent.NEWLINE) {
      println("Yay a new line")
      Compiler.Scanner.getNextToken()
    }
  }

  def errorAndQuit(str: String): Unit = {
    println(str)
    System.exit(1)
  }

  def isText(str: String): Boolean = {
    for(spacialChar <- constent.SpacialChar){
      if(str.contains(spacialChar))
        return false
    }
    true
  }
}
