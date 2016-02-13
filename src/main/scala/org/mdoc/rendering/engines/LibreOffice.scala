package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.{ Document, Format }
import org.mdoc.fshell.ProcessResult
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import scalaz.NonEmptyList

object LibreOffice {

  def convertTo(doc: Document, outputFormat: Format): Shell[Document] = {
    val prefix = "doc"
    for {
      dir <- Shell.createTempDirectory("mdoc-libreoffice-")
      pathIn = dir.resolve(prefix + Utils.dotFormatExtension(doc.format))
      pathOut = dir.resolve(prefix + Utils.dotFormatExtension(outputFormat))
      _ <- Shell.write(pathIn, doc.body)
      _ <- execLowriter(pathIn, dir, outputFormat).throwOnError
      bytesOut <- Shell.readAllBytes(pathOut)
      _ <- Shell.deleteAll(List(pathIn, pathOut, dir))
    } yield Document(outputFormat, bytesOut)
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
