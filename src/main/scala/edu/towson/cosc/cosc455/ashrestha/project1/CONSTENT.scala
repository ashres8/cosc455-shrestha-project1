package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
class CONSTENT {
  val DOCB: String = "\\BEGIN"
  val DOCE: String = "\\END"
  val TITLEB: String = "\\TITLE["
  val SQBRACKETE: String = "]"
  val HEADING: String = "#"
  val PARAB: String = "\\PARAB"
  val PARAE: String = "\\PARAE"
  val BOLD = "**"
  val ITALICS = "*"
  val LISTITEM = "+"
  val NEWLINE = "\\\\"
  val LINKB = "["
  val ADDRESSB = "("
  val ADDRESSE = ")"
  val IMAGEB = "!["
  val DEFB = "\\DEF["
  val EQSIGN = "="
  val USEB = "\\USE["
  val NEXTLINE = "\n"
  val ArrayOfTokens: Array[String] = Array(DOCB, DOCE, TITLEB, SQBRACKETE, PARAB, PARAE, NEWLINE, DEFB, USEB, LINKB, IMAGEB, EQSIGN, ADDRESSE, ADDRESSB, LISTITEM, ITALICS, BOLD, HEADING)
  val SpacialChar: Array[Char] = Array('\\', '*', '!', '[', ']', '(', ')', '+', '#', '=')
}
