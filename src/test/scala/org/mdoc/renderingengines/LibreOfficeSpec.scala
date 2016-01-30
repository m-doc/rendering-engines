package org.mdoc.renderingengines

import java.nio.file.Paths
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.renderingengines.LibreOffice._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class LibreOfficeSpec extends Properties("LibreOffice") {

  property("convertToPdf") = secure {
    val odt = Paths.get("./src/test/resources/test.odt")
    val pdf = Paths.get("./test.pdf")
    val p = for {
      _ <- convertToPdf(odt)
      bytes <- Shell.readAllBytes(pdf)
      _ <- Shell.delete(pdf)
    } yield bytes.size > 1024
    p.yolo
  }
}
