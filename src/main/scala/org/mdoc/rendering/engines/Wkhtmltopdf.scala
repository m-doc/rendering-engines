package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.fshell.{ ProcessResult, Shell }
import scalaz.NonEmptyList

object Wkhtmltopdf {

  def wkhtmltoimage(url: String, output: Path): Shell[ProcessResult] =
    xvfbRun(List("wkhtmltoimage", "--quiet", url, output.toString))

  def wkhtmltopdf(url: String, output: Path): Shell[ProcessResult] =
    xvfbRun(List("wkhtmltopdf", "--quiet", url, output.toString))

  private def xvfbRun(args: List[String]): Shell[ProcessResult] = {
    val cmd = NonEmptyList("xvfb-run", "--auto-servernum", "-s", "-screen 0 1280x1024x24")
    Shell.readProcess(cmd :::> args)
  }
}
