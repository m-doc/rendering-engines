package org.mdoc.rendering.engines

import java.nio.file.Paths
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import LibreOffice._
import org.scalacheck.Prop._
import org.scalacheck.Properties

object LibreOfficeSpec extends Properties("LibreOffice") {

  property("convertToPdf") = secure {
    val odt = Paths.get("./src/test/resources/test.odt")
    val pdf = Paths.get("./test.pdf")
    val p = for {
      notExists <- Shell.fileNotExists(pdf)
      _ <- convertToPdf(odt)
      bytes <- Shell.readAllBytes(pdf)
      _ <- Shell.delete(pdf)
    } yield notExists && bytes.size > 1024
    p.yolo
  }
}
