package edu.towson.cosc.cosc455.ashrestha.project1

import scala.io.Source._

/**
  * Created by ashres8 on 10/11/2016.
  */
object Compiler {

  var fileContent: String = ""

  def main(args: Array[String]): Unit = {
    val flname : String = args(0)
    println(flname)

    readFile(flname)

    println(fileContent)
  }

  def fileCheck(args: Array[String]): Unit ={
    if (args.length != 1){

    }
  }

  def readFile(file : String): Unit ={
    val source = scala.io.Source.fromFile(file);
    fileContent = try source.mkString finally source.close()
  }
}
