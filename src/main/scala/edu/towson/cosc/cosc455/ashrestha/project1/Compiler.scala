package edu.towson.cosc.cosc455.ashrestha.project1

import scala.collection.mutable.ArrayBuffer
import scala.io.Source._

/**
  * Created by ashres8 on 10/11/2016.
  */
object Compiler {

  var fileContent: String = ""
  var currentToken: String = ""
  var analyzedTokens: ArrayBuffer[String] = new ArrayBuffer[String]()
  var filename: String = ""

  val Scanner = new MyLexicalAnalyzer
  val Parser = new MySyntaxAnalyzer
  val SemanticAna = new MySemanticAnalyzer

  def main(args: Array[String]): Unit = {
    fileCheck(args)
    println(fileContent)
    Scanner.listFileContent = fileContent.toArray

    Scanner.getNextToken()
    //println("Compiler: " + currentToken)
    Parser.gittex()
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
        filename = args(0).dropRight(4)
        println(filename)
        readFile(args(0))
      }
    }
  }

  //Reads the file and puts it in 'fileContent'
  def readFile(file : String): Unit ={
    val source = scala.io.Source.fromFile(file)
    fileContent = try source.mkString finally source.close()
  }
}
