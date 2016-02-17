package org.mdoc.rendering.engines

import org.mdoc.common.model._
import org.mdoc.common.model.Format.{ Latex, Pdf }
import org.mdoc.common.model.RenderingEngine.Pandoc
import org.mdoc.fshell.Shell.ShellSyntax
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scodec.bits.ByteVector

object PandocSpec extends Properties("pandoc") {

  property("renderDoc Pdf") = secure {
    val body = ByteVector.view("\\documentclass{article}\\begin{document}Hello, world!\\end{document}".getBytes)
    val input = RenderingInput(JobId(""), RenderingConfig(Pdf, Pandoc), Document(Latex, body))
    generic.renderDoc(input).map(util.isPdfDocument).yolo
  }
}
