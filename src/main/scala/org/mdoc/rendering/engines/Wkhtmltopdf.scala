package org.mdoc.rendering.engines

import java.nio.file.{ Path, Paths }
import org.mdoc.fshell.{ ProcessResult, Shell }
import org.mdoc.fshell.Shell.ShellSyntax
import scalaz.NonEmptyList
import scodec.bits.ByteVector

object Wkhtmltopdf {

  def htmlToPdf(bytes: ByteVector): Shell[ByteVector] =
    for {
      pathIn <- Shell.writeToTempFile("mdoc-wkhtmltopdf-", ".html", bytes)
      pathOut = Paths.get(pathIn.toString + ".pdf")
      _ <- execWkhtmltopdf(pathIn.toString, pathOut)
      bytesOut <- Shell.readAllBytes(pathOut)
      _ <- Shell.delete(pathIn)
      _ <- Shell.delete(pathOut)
    } yield bytesOut

  def execWkhtmltoimage(url: String, output: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltoimage", "--quiet", url, output.toString))

  def execWkhtmltopdf(url: String, output: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltopdf", "--quiet", url, output.toString))

  private def execXvfbRun(args: List[String]): Shell[ProcessResult] = {
    val cmd = NonEmptyList("xvfb-run", "--auto-servernum", "-s", "-screen 0 1280x1024x24")
    Shell.readProcess(cmd :::> args).throwOnError
  }
}
