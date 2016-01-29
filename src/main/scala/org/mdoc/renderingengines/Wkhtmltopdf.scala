package org.mdoc.renderingengines

import java.nio.file.Path
import org.mdoc.fshell.{ ProcessResult, Shell }

object Wkhtmltopdf {

  def execWkhtmltopdf(url: String, output: Path): Shell[ProcessResult] =
    Shell.readProcess(
      "xvfb-run",
      List(
        "--auto-servernum",
        "-s", "-screen 0 1280x1024x24",
        "wkhtmltopdf",
        "--quiet",
        url,
        output.toString
      )
    )
}
