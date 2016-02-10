package org.mdoc.rendering.engines

import java.nio.file.Paths
import org.mdoc.common.model.Format.{ Pdf, Odt }
import org.mdoc.common.model.Document
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.rendering.engines.LibreOffice._
import org.scalacheck.Prop._
import org.scalacheck.Properties

object LibreOfficeSpec extends Properties("LibreOffice") {

  property("convertTo PDF") = secure {
    val odt = Paths.get("./src/test/resources/test.odt")
    val p = for {
      bytesIn <- Shell.readAllBytes(odt)
      docOut <- convertTo(Document(Odt, bytesIn), Pdf)
    } yield Utils.isPdfDocument(docOut)
    p.yolo
  }

  property("execLowriter") = secure {
    val odt = Paths.get("./src/test/resources/test.odt")
    val pdf = Paths.get("./test.pdf")
    val p = for {
      notExists <- Shell.fileNotExists(pdf)
      _ <- execLowriter(odt, Paths.get("."), Pdf)
      bytes <- Shell.readAllBytes(pdf)
      _ <- Shell.delete(pdf)
    } yield notExists && Utils.isPdfByteVector(bytes)
    p.yolo
  }
}
