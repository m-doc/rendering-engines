package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.{ Document, Html, Pdf }
import org.mdoc.fshell.ProcessResult
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import scalaz.NonEmptyList
import scodec.bits.ByteVector

object Wkhtmltopdf {

  def htmlToPdf(bytes: ByteVector): Shell[Document] = {
    val formatOut = Pdf
    val suffixIn = Utils.dotFormatExtension(Html)
    for {
      pathIn <- Shell.writeToTempFile("mdoc-wkhtmltopdf-", suffixIn, bytes)
      pathOut = Utils.pathWithExtension(pathIn, formatOut)
      _ <- execWkhtmltopdf(pathIn.toString, pathOut).throwOnError
      bytesOut <- Shell.readAllBytes(pathOut)
      _ <- Shell.delete(pathIn)
      _ <- Shell.delete(pathOut)
    } yield Document(formatOut, bytesOut)
  }

  def execWkhtmltoimage(url: String, output: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltoimage", "--quiet", url, output.toString))

  def execWkhtmltopdf(url: String, output: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltopdf", "--quiet", url, output.toString))

  private def execXvfbRun(args: List[String]): Shell[ProcessResult] = {
    val cmd = NonEmptyList("xvfb-run", "--auto-servernum", "-s", "-screen 0 1280x1024x24")
    Shell.readProcess(cmd :::> args)
  }
}
