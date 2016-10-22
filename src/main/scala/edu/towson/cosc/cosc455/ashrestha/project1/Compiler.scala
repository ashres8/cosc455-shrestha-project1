package edu.towson.cosc.cosc455.ashrestha.project1

import scala.io.Source._

/**
  * Created by ashres8 on 10/11/2016.
  */
object Compiler {

  var fileContent: String = ""
  var currentToken: String = ""

  val Scanner = new MyLexicalAnalyzer
  val Parser = new MySyntaxAnalyzer
  val SemanticAna = new MySemanticAnalyzer

  def main(args: Array[String]): Unit = {
    fileCheck(args)
    //println(fileContent)

    println(fileContent.toArray.length)

    Scanner.listFileContent = fileContent.toArray

    while(true){
      Scanner.getNextToken()
      println("Compiler: " + currentToken)
    }
  }

  def fileCheck(args: Array[String]): Unit ={
    if (args.length != 1){
      println("Error: There are too many arguments. Please enter a *.mkd File.")
      System.exit(1)
    } else {
      if(!args(0).endsWith(".mkd")){
        println("Error: Please enter a *.mkd File.")
        System.exit(1)
      } else {
        readFile(args(0))
      }
    }
  }

  def readFile(file : String): Unit ={
    val source = scala.io.Source.fromFile(file);
    fileContent = try source.mkString finally source.close()
  }
}
