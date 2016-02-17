package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.{ Document, Format, RenderingInput }
import org.mdoc.fshell.ProcessResult
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import scalaz.NonEmptyList

object pandoc {

  val renderDoc: RenderingInput => Shell[Document] =
    generic.mkRenderFun { ctx =>
      execPandoc(ctx.inputFile, ctx.outputFile, ctx.workingDir).throwOnError
    }

  def execPandoc(inputFile: Path, outputFile: Path, workingDir: Path): Shell[ProcessResult] = {
    val cmd = NonEmptyList("pandoc", s"--output=${outputFile.toString}", inputFile.toString)
    Shell.readProcessIn(cmd, workingDir)
  }
}
