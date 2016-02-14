package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.{ Document, Format, RenderingInput }
import org.mdoc.fshell.ProcessResult
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import scalaz.NonEmptyList

object libreoffice {

  val renderDoc: RenderingInput => Shell[Document] =
    generic.mkRenderDoc { ctx =>
      execLowriter(ctx.inputFile, ctx.workingDir, ctx.input.config.outputFormat).throwOnError
    }

  def execLowriter(input: Path, outputDir: Path, outputFormat: Format): Shell[ProcessResult] = {
    val cmd = NonEmptyList(
      "lowriter",
      "--headless",
      "--convert-to", outputFormat.toExtension,
      "--outdir", outputDir.toString,
      input.toString
    )
    Shell.readProcessIn(cmd, outputDir)
  }
}
