package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
class MySyntaxAnalyzer extends SyntaxAnalyzer{
  val constent = new CONSTENT

  override def gittex(): Unit = {
    if(Compiler.currentToken == constent.DOCB){
      Compiler.Scanner.getNextToken()
      //variableDefine()
      title()
      //body()
      if(Compiler.currentToken == constent.DOCE){
        println("Done !!")
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
          println("Text End Found !!")
          Compiler.Scanner.getNextToken()
        } else {
          errorAndQuit("Syntax Error: Title End missing : " + constent.SQBRACKETE)
        }
      } else {
        errorAndQuit("Syntax Error: Not Text")
      }
    } else {
      errorAndQuit("Syntax Error: Title Start missing : " + constent.TITLEB)
    }
  }

  override def body(): Unit = ???

  override def paragraph(): Unit = ???

  override def innerText(): Unit = ???

  override def heading(): Unit = ???

  override def variableDefine(): Unit = ???

  override def variableUse(): Unit = ???

  override def bold(): Unit = ???

  override def italics(): Unit = ???

  override def listItem(): Unit = ???

  override def innerItem(): Unit = ???

  override def link(): Unit = ???

  override def image(): Unit = ???

  override def newline(): Unit = ???

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
