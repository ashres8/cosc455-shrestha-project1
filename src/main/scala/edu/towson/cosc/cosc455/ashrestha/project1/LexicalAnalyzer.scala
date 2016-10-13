package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
trait LexicalAnalyzer {
  def addChar() : Unit
  def getChar() : Char
  def getNextToken() : Unit
  def lookup() : Boolean
}
