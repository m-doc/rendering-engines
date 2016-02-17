package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.Document
import org.mdoc.common.model.Format.Pdf
import org.mdoc.common.model.RenderingEngine.Wkhtmltopdf
import org.mdoc.common.model.RenderingInput
import org.mdoc.fshell.{ ProcessResult, Shell }
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

  def renderUrlToPdf(url: String): Shell[Document] =
    for {
      workingDir <- Shell.createTempDirectory(generic.tempPrefix(Wkhtmltopdf))
      outputFile = workingDir.resolve("out.pdf")
      _ <- execWkhtmltopdf(url, outputFile, workingDir).throwOnError
      bytes <- Shell.readAllBytes(outputFile)
      _ <- Shell.deleteAll(List(outputFile, workingDir))
    } yield Document(Pdf, bytes)

  def execWkhtmltoimage(url: String, outputFile: Path, workingDir: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltoimage", "--quiet", url, outputFile.toString), workingDir)

  def execWkhtmltopdf(url: String, outputFile: Path, workingDir: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltopdf", "--quiet", url, outputFile.toString), workingDir)

  def execXvfbRun(args: List[String], workingDir: Path): Shell[ProcessResult] = {
    val cmd = NonEmptyList("xvfb-run", "--auto-servernum", "-s", "-screen 0 1280x1024x24")
    Shell.readProcessIn(cmd :::> args, workingDir)
  }
}
