package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.{ Document, RenderingInput }
import org.mdoc.fshell.ProcessResult
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import scalaz.NonEmptyList

object wkhtmltopdf {

  val renderHtmlToPdf: RenderingInput => Shell[Document] =
    generic.mkRenderFun { ctx =>
      execWkhtmltopdf(ctx.inputFile.toString, ctx.outputFile, ctx.workingDir).throwOnError
    }

  val renderHtmlToImage: RenderingInput => Shell[Document] =
    generic.mkRenderFun { ctx =>
      execWkhtmltoimage(ctx.inputFile.toString, ctx.outputFile, ctx.workingDir).throwOnError
    }

  def execWkhtmltoimage(url: String, outputFile: Path, workingDir: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltoimage", "--quiet", url, outputFile.toString), workingDir)

  def execWkhtmltopdf(url: String, outputFile: Path, workingDir: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltopdf", "--quiet", url, outputFile.toString), workingDir)

  def execXvfbRun(args: List[String], workingDir: Path): Shell[ProcessResult] = {
    val cmd = NonEmptyList("xvfb-run", "--auto-servernum", "-s", "-screen 0 1280x1024x24")
    Shell.readProcessIn(cmd :::> args, workingDir)
  }
}
