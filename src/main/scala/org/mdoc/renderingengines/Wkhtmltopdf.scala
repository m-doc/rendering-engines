package org.mdoc.renderingengines

import java.nio.file.Path
import org.mdoc.fshell.{ ProcessResult, Shell }

object Wkhtmltopdf {

  def execWkhtmltoimage(url: String, output: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltoimage", "--quiet", url, output.toString))

  def execWkhtmltopdf(url: String, output: Path): Shell[ProcessResult] =
    execXvfbRun(List("wkhtmltopdf", "--quiet", url, output.toString))

  private def execXvfbRun(args: List[String]): Shell[ProcessResult] =
    Shell.readProcess(
      "xvfb-run",
      List("--auto-servernum", "-s", "-screen 0 1280x1024x24") ++ args
    )
}
