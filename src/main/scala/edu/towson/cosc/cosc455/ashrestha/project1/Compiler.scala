package edu.towson.cosc.cosc455.ashrestha.project1

import scala.collection.mutable.ArrayBuffer
import scala.io.Source._

/**
  * Main Program that makes the program ready to run and checks the files
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

  //Checks the file if it is an *.mkd file or has too many arguments
  def fileCheck(args: Array[String]): Unit ={
    if (args.length != 1){
      println("Error: There are too many/low arguments. Please enter a *.mkd File.")
      System.exit(1)
    } else {
      if(!args(0).endsWith(".mkd")){
        println("Error: Please enter a *.mkd File.")
        System.exit(1)
      } else {
        //Only gets the name of the file, so without the extension
        filename = args(0).dropRight(4)
        //println(filename)
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
