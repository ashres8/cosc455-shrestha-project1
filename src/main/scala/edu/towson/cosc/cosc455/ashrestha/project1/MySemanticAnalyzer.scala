package edu.towson.cosc.cosc455.ashrestha.project1

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
    while(sIndex < Compiler.analyzedTokens.size){
      stack = Compiler.analyzedTokens(sIndex) :: stack
      if(constent.ArrayOfTokensSemEnd.contains(stack.head)){
        val endToken = stack.head.dropRight(2) + "B>"
        //println(endToken +" :")
        println(stack.head)
        stack = stack.tail
        while(stack.head != endToken){
          println(stack.head)
          stack = stack.tail
        }
        println(stack.head)
        stack = stack.tail
      }
      sIndex += 1
    }
    println(stack)
  }
}
