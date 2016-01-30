package org.mdoc.renderingengines

import java.nio.file.Path
import org.mdoc.fshell.{ ProcessResult, Shell }
import scalaz.NonEmptyList

object LibreOffice {

  def convertToPdf(input: Path): Shell[ProcessResult] = {
    val cmd = NonEmptyList("lowriter", "--headless", "--convert-to", "pdf", input.toString)
    Shell.readProcess(cmd)
  }
}
