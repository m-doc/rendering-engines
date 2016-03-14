package org.mdoc.rendering.engines

import org.mdoc.common.model._
import org.mdoc.common.model.Format.{ Latex, Pdf, Txt }
import org.mdoc.common.model.RenderingEngine.Pandoc
import org.mdoc.fshell.Shell.ShellSyntax
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scodec.bits.ByteVector

object PandocSpec extends Properties("pandoc") {

  property("renderDoc: Latex -> Pdf") = secure {
    val body = ByteVector.view("\\documentclass{article}\\begin{document}Hello, world!\\end{document}".getBytes)
    val input = RenderingInput(JobId(""), RenderingConfig(Pdf, Pandoc), Document(Latex, body))
    generic.renderDoc(input).map(util.isPdfDocument).yolo
  }

  property("renderDoc: Txt -> Txt") = secure {
    val doc = Document(Txt, ByteVector.view("Hello\n".getBytes))
    val input = RenderingInput(JobId(""), RenderingConfig(Txt, Pandoc), doc)
    val rendered = generic.renderDoc(input).yolo
    rendered.body.containsSlice(doc.body)
  }
}
