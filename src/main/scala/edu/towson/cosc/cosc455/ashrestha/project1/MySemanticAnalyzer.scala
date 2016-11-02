package edu.towson.cosc.cosc455.ashrestha.project1

import java.awt.Desktop
import java.io.{File, IOException, PrintWriter}

/**
  * Created by ashres8 on 10/11/2016.
  */
class MySemanticAnalyzer {
  var stack:List[String] = Nil
  var resolved:List[String] = Nil
  var constent = new CONSTENT
  var sIndex = 0
  def startConvert(): Unit ={
    println("Semantic: ")
    stack = Compiler.analyzedTokens.toList.reverse
    println(stack)
    while(stack.nonEmpty){
      stack match{
        case "<DOCB>"::rest =>
          resolved = "<!DOCTYPE html>\n<html>\n<head>\n"::resolved
          stack = rest
        case "<DOCE>"::rest =>
          resolved = "</html>"::resolved
          stack = rest
        case "<TITLEB>"::rest =>
          resolved = "<title>"::resolved
          stack = rest
        case "<TITLEE>"::rest =>
          resolved = "</title>\n</head>\n"::resolved
          stack = rest
        case "<PARAB>"::rest =>
          resolved = "<p>\n"::resolved
          stack = rest
        case "<PARAE>"::rest =>
          resolved = "</p>\n"::resolved
          stack = rest
        case "<ITALICSB>"::rest =>
          resolved = "<em>"::resolved
          stack = rest
        case "<ITALICSE>"::rest =>
          resolved = "</em>"::resolved
          stack = rest
        case "<BOLDB>"::rest =>
          resolved = "<b>"::resolved
          stack = rest
        case "<BOLDE>"::rest =>
          resolved = "</b>"::resolved
          stack = rest
        case "<LISTITEMB>"::rest =>
          resolved = "<li>"::resolved
          stack = rest
        case "<LISTITEME>"::rest =>
          resolved = "</li>\n"::resolved
          stack = rest
        case "<HEADINGB>"::rest =>
          resolved = "<h1>"::resolved
          stack = rest
        case "<HEADINGE>"::rest =>
          resolved = "</h2>"::resolved
          stack = rest
        case "<NEWLINE>"::rest =>
          resolved = "<br>\n"::resolved
          stack = rest
        case "<DEFB>"::rest =>
          stack = rest
        case "<EQSIGN>"::rest =>
          stack = rest.tail
        case "<DEFE>"::rest =>
          stack = rest.tail
        case "<LINKB>"::rest =>
          stack = rest
        case "<IMAGEB>"::rest =>
          stack = rest
        case "<USEB>"::rest =>
          stack = rest
        case "<USEE>"::rest =>
          println("Var: " + rest.head)
          resolved = getvaluefor(rest.head)::resolved
          stack = rest.tail
        case "<ADDRESSE>"::rest =>
          val v = findLinkOrImage(stack)
          println("ADDRESS: " + rest.head)
          val href = rest.head
          val info = rest.drop(3).head
          v match{
            case 0 =>
              resolved = "<a href=" + href +">" + info + "</a>\n"::resolved
            case 1 =>
              resolved = "<img src="+ href +" alt=\"" + info +"\">\n"::resolved
            case _ => errorAndQuit("Cant find Image or Link Start.")
          }
          stack = rest.drop(4)
        case a::rest =>
          resolved = a::resolved
          stack = rest
        case Nil => Nil
      }
    }
    println(resolved.mkString(""))
    createHTMLFile(resolved.mkString(""))
    openHTMLFileInBrowser(Compiler.filename + ".html")
  }

  def findLinkOrImage(list: List[String]): Int = {
    var temp = 0
    var found = false
    while(temp < stack.size - 1 && !found){
      println("tmp: " + temp)
      if(constent.linkOrImage.contains(stack(temp))){
        stack(temp) match{
          case "<LINKB>" => found = true
            return 0
          case "<IMAGEB>" => found = true
            return 1
        }
      }
      temp += 1
    }
    -1
  }

  def getvaluefor(str: String): String = {
    var tmpIndex = 0
    var found = false
    var result = ""
    println(stack)
    while(tmpIndex < stack.size - 1 && !found){
      //println("tmp: " + tmpIndex)
      //println(stack(tmpIndex))
      if(constent.ArrayOfTokensSemEnd.contains(stack(tmpIndex))){
        if(stack(tmpIndex) == "<DEFE>"){
          val dname = stack(tmpIndex + 3).replaceAll("\\s", "")
          //println("Def Name: " + dname)
          if(dname == str.replaceAll("\\s", "")){
            found = true
            result = stack(tmpIndex + 1)
            println(result)
          }
        } else {
          val tokenBeg = stack(tmpIndex).dropRight(2) + "B>"
          //println(tokenBeg)
          while(stack(tmpIndex) != tokenBeg && tmpIndex < stack.size - 1){
            tmpIndex += 1
          }
        }
      }

      tmpIndex += 1
    }
    if(!found){
      errorAndQuit("Can't Find Value for " + str + ".")
    }
    result
  }

  def getprevioustoken(i: Int, list: List[String]): String = {
    list(i-1)
  }

  def nextToken(i: Int, list: List[String]): String = {
    list(i+1)
  }

  def errorAndQuit(str: String): Unit = {
    println("Semantic Error: " + str)
    System.exit(1)
  }

  def createHTMLFile(str: String): Unit ={
    val fw = new PrintWriter(new File(Compiler.filename + ".html"))
    fw.write(str)
    fw.close()
  }

  /* * Hack Scala/Java function to take a String filename and open in default web browswer. */
  def openHTMLFileInBrowser(htmlFileStr : String) = {
    val file : File = new File(htmlFileStr.trim)
    println(file.getAbsolutePath)
    if (!file.exists())
      sys.error("File " + htmlFileStr + " does not exist.")

    try {
      Desktop.getDesktop.browse(file.toURI)
    }
    catch {
      case ioe: IOException => sys.error("Failed to open file:  " + htmlFileStr)
      case e: Exception => sys.error("He's dead, Jim!")
    }
  }
}
