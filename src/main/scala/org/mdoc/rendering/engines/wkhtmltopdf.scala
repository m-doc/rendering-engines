package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.Document
import org.mdoc.common.model.Format
import org.mdoc.common.model.Format.Pdf
import org.mdoc.common.model.RenderingEngine
import org.mdoc.common.model.RenderingEngine.{ Wkhtmltoimage, Wkhtmltopdf }
import org.mdoc.common.model.RenderingInput
import org.mdoc.fshell.ProcessResult
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.rendering.engines.util.PathOps
import scalaz.NonEmptyList

object wkhtmltopdf {

  val renderDoc: RenderingInput => Shell[Document] =
    generic.mkRenderFun { ctx =>
      execWkhtmltoX(
        ctx.input.config.engine,
        ctx.inputFile.toString,
        ctx.outputFile,
        ctx.workingDir
      ).throwOnError
    }

  def renderUrl(url: String, format: Format): Shell[Document] = {
    val engine = getEngine(format)
    for {
      workingDir <- Shell.createTempDirectory(generic.tempPrefix(engine))
      outputFile = PathOps.withExtension(workingDir.resolve("out"), format)
      _ <- execWkhtmltoX(engine, url, outputFile, workingDir).throwOnError
      bytes <- Shell.readAllBytes(outputFile)
      _ <- Shell.deleteAll(List(outputFile, workingDir))
    } yield Document(format, bytes)
  }

  def execWkhtmltoX(engine: RenderingEngine, url: String, outputFile: Path, workingDir: Path): Shell[ProcessResult] =
    execXvfbRun(List(getCommand(engine), "--quiet", url, outputFile.toString), workingDir)

  def execXvfbRun(args: List[String], workingDir: Path): Shell[ProcessResult] = {
    val cmd = NonEmptyList("xvfb-run", "--auto-servernum", "-s", "-screen 0 1280x1024x24")
    Shell.readProcessIn(cmd :::> args, workingDir)
  }

  def getEngine(format: Format): RenderingEngine =
    format match {
      case Pdf => Wkhtmltopdf
      case _ => Wkhtmltoimage
    }

  def getCommand(engine: RenderingEngine): String =
    engine match {
      case Wkhtmltoimage => "wkhtmltoimage"
      case _ => "wkhtmltopdf"
    }
}
