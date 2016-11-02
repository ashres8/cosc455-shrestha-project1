package edu.towson.cosc.cosc455.ashrestha.project1

/**
  * Created by ashres8 on 10/11/2016.
  */
class MySyntaxAnalyzer extends SyntaxAnalyzer{
  val constent = new CONSTENT

  override def gittex(): Unit = {
    if(Compiler.currentToken == constent.DOCB){
      Compiler.analyzedTokens.append("<DOCB>")
      Compiler.Scanner.getNextToken()
      variableDefine()
      title()
      body()
      if(Compiler.currentToken == constent.DOCE){
        Compiler.analyzedTokens.append("<DOCE>")
        println("Done !!")
        println("Array: " + Compiler.analyzedTokens.mkString(", "))
        Compiler.SemanticAna.startConvert()
      } else errorAndQuit("Syntax Error: Document End missing : " + constent.DOCE)
    } else errorAndQuit("Syntax Error: Document Start missing : " + constent.DOCB)
  }

  override def title(): Unit = {
    if(Compiler.currentToken == constent.TITLEB){
      Compiler.analyzedTokens.append("<TITLEB>")
      println("Title Begin Found !!")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.analyzedTokens.append(Compiler.currentToken)
        println("Text Begin Found !!")
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.SQBRACKETE){
          Compiler.analyzedTokens.append("<TITLEE>")
          println("Title End Found !!")
          Compiler.Scanner.getNextToken()
        } else errorAndQuit("Syntax Error: Title End missing : " + constent.SQBRACKETE)
      } else errorAndQuit("Syntax Error: Not Text")
    } else errorAndQuit("Syntax Error: Title Start missing : " + constent.TITLEB + " LIST: " + Compiler.currentToken)
  }

  var bodyfound: Boolean = false

  override def body(): Unit = {
    while(!constent.notBody.contains(Compiler.currentToken)){
      itfound = false
      if(!bodyfound) innerText()
      if(!bodyfound) paragraph()
      if(!bodyfound) newline()
      bodyfound = false
    }
  }

  override def paragraph(): Unit = {
    if (Compiler.currentToken == constent.PARAB){
      bodyfound = true
      Compiler.analyzedTokens.append("<PARAB>")
      Compiler.Scanner.getNextToken()
      itfound = false
      variableDefine()
      innerText()
      if(Compiler.currentToken == constent.PARAE){
        Compiler.analyzedTokens.append("<PARAE>")
        Compiler.Scanner.getNextToken()
      } else errorAndQuit("Syntax Error: Paragraph End Missing" + constent.PARAE)
    }
  }

  var itfound: Boolean = false
  override def innerText(): Unit = {
    while((!constent.notBody.contains(Compiler.currentToken)) && Compiler.currentToken != constent.PARAB && Compiler.currentToken != constent.PARAE && Compiler.currentToken != constent.NEWLINE){
      //println("Inner Text: " + Compiler.currentToken)
      bodyfound = true
      if(!itfound) variableUse()
      if(!itfound) heading()
      if(!itfound) bold()
      if(!itfound) italics()
      if(!itfound) link()
      if(!itfound) image()
      if(!itfound) listItem()
      if(!itfound) newline()
      if(!itfound && isText(Compiler.currentToken)){
        itfound = true
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }
      itfound = false
    }
  }

  override def heading(): Unit = {
    if(Compiler.currentToken == constent.HEADING){
      itfound = true
      Compiler.analyzedTokens.append("<HEADINGB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.analyzedTokens.append("<HEADINGE>")
        Compiler.Scanner.getNextToken()
      } else errorAndQuit("Syntax Error: Text after Heading is missing.")
    }
  }

  override def variableDefine(): Unit = {
    if(Compiler.currentToken == constent.DEFB) {
      Compiler.analyzedTokens.append("<DEFB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.EQSIGN){
          Compiler.analyzedTokens.append("<EQSIGN>")
          Compiler.Scanner.getNextToken()
          if(isText(Compiler.currentToken)){
            Compiler.analyzedTokens.append(Compiler.currentToken)
            Compiler.Scanner.getNextToken()
            if(Compiler.currentToken == constent.SQBRACKETE){
              Compiler.analyzedTokens.append("<DEFE>")
              Compiler.Scanner.getNextToken()
              variableDefine()
            } else errorAndQuit("Variable Def Error, Not " + constent.SQBRACKETE)
          } else errorAndQuit("Variable Def Error, Not value defined")
        } else errorAndQuit("Variable Def Error, Not " + constent.EQSIGN)
      } else errorAndQuit("Variable Def Error, No variable name")
    }
  }

  override def variableUse(): Unit = {
    if(Compiler.currentToken == constent.USEB){
      itfound = true
      innerItemFound = true
      Compiler.analyzedTokens.append("<USEB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.SQBRACKETE) {
          Compiler.analyzedTokens.append("<USEE>")
          Compiler.Scanner.getNextToken()
        } else errorAndQuit("Variable Use Error, No " + constent.SQBRACKETE)
      } else errorAndQuit("Variable Use Error, No variable name")
    }
  }

  override def bold(): Unit = {
    if(Compiler.currentToken == constent.BOLD){
      itfound = true
      innerItemFound = true
      Compiler.analyzedTokens.append("<BOLDB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.BOLD){
          Compiler.analyzedTokens.append("<BOLDE>")
          Compiler.Scanner.getNextToken()
        } else errorAndQuit("Italics ERROR Missing " + constent.BOLD)
      } else errorAndQuit("Italics ERROR Missing TEXT ")
    }
  }

  override def italics(): Unit = {
    if(Compiler.currentToken == constent.ITALICS){
      itfound = true
      innerItemFound = true
      Compiler.analyzedTokens.append("<ITALICSB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)){
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.ITALICS){
          Compiler.analyzedTokens.append("<ITALICSE>")
          Compiler.Scanner.getNextToken()
        } else errorAndQuit("Italics ERROR Missing " + constent.ITALICS)
      } else errorAndQuit("Italics ERROR Missing TEXT ")
    }
  }

  var innerItemFound: Boolean = false
  override def listItem(): Unit = {
    if(Compiler.currentToken == constent.LISTITEM){
      bodyfound = true
      Compiler.analyzedTokens.append("<LISTITEMB>")
      Compiler.Scanner.getNextToken()
      innerItemFound = false
      innerItem()
      Compiler.analyzedTokens.append("<LISTITEME>")
      listItem()
    }
  }

  override def innerItem(): Unit = {
    if(constent.innerItemTokens.contains(Compiler.currentToken) || isText(Compiler.currentToken)){
      //println("innerItem: " + Compiler.currentToken)
      if(!innerItemFound) bold()
      if(!innerItemFound) italics()
      if(!innerItemFound) link()
      if(!innerItemFound) variableUse()
      if(!innerItemFound && isText(Compiler.currentToken)){
        innerItemFound = true
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }
      innerItemFound = false
    }
  }

  override def link(): Unit = {
    if (Compiler.currentToken == constent.LINKB){
      itfound = true
      innerItemFound = true
      Compiler.analyzedTokens.append("<LINKB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)) {
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.SQBRACKETE){
          Compiler.analyzedTokens.append("<SQBRACKETE>")
          Compiler.Scanner.getNextToken()
          if(Compiler.currentToken == constent.ADDRESSB){
            Compiler.analyzedTokens.append("<ADDRESSB>")
            Compiler.Scanner.getNextToken()
            if(isText(Compiler.currentToken)){
              Compiler.analyzedTokens.append(Compiler.currentToken)
              Compiler.Scanner.getNextToken()
              if(Compiler.currentToken == constent.ADDRESSE){
                Compiler.analyzedTokens.append("<ADDRESSE>")
                Compiler.Scanner.getNextToken()
              } else errorAndQuit("Link Use ERROR Missing " + constent.ADDRESSE)
            } else errorAndQuit("Link ERROR Missing URL")
          } else errorAndQuit("Link Use ERROR Missing " + constent.ADDRESSB)
        } else errorAndQuit("Link Use ERROR Missing " + constent.SQBRACKETE)
      } else errorAndQuit("Link ERROR Missing Text")
    }
  }

  override def image(): Unit = {
    if (Compiler.currentToken == constent.IMAGEB){
      itfound = true
      Compiler.analyzedTokens.append("<IMAGEB>")
      Compiler.Scanner.getNextToken()
      if(isText(Compiler.currentToken)) {
        Compiler.analyzedTokens.append(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken == constent.SQBRACKETE){
          Compiler.analyzedTokens.append("<SQBRACKETE>")
          Compiler.Scanner.getNextToken()
          if(Compiler.currentToken == constent.ADDRESSB){
            Compiler.analyzedTokens.append("<ADDRESSB>")
            Compiler.Scanner.getNextToken()
            if(isText(Compiler.currentToken)){
              Compiler.analyzedTokens.append(Compiler.currentToken)
              Compiler.Scanner.getNextToken()
              if(Compiler.currentToken == constent.ADDRESSE){
                Compiler.analyzedTokens.append("<ADDRESSE>")
                Compiler.Scanner.getNextToken()
              } else errorAndQuit("Image Use ERROR Missing " + constent.ADDRESSE)
            } else errorAndQuit("Image ERROR Missing URL")
          } else errorAndQuit("Image Use ERROR Missing " + constent.ADDRESSB)
        } else errorAndQuit("Image Use ERROR Missing " + constent.SQBRACKETE)
      } else errorAndQuit("Image ERROR Missing Text")
    }
  }

  override def newline(): Unit = {
    if(Compiler.currentToken == constent.NEWLINE) {
      bodyfound = true
      Compiler.analyzedTokens.append("<NEWLINE>")
      Compiler.Scanner.getNextToken()
    }
  }

  def errorAndQuit(str: String): Unit = {
    print("Array: " + Compiler.analyzedTokens.mkString(", "))
    println("Syntax Error: " + str)
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
