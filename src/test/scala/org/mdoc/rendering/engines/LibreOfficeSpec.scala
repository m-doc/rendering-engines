package org.mdoc.rendering.engines

import java.nio.file.Paths
import org.mdoc.common.model._
import org.mdoc.common.model.Format.{ Odt, Pdf, Txt }
import org.mdoc.common.model.RenderingEngine.LibreOffice
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.rendering.engines.libreoffice._
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scodec.bits.ByteVector

object LibreOfficeSpec extends Properties("libreoffice") {

  property("renderDoc: Odt -> Pdf") = secure {
    val odt = Paths.get("./src/test/resources/test.odt")
    val p = for {
      inputBytes <- Shell.readAllBytes(odt)
      input = RenderingInput(JobId(""), RenderingConfig(Pdf, LibreOffice),
        Document(Odt, inputBytes))
      doc <- generic.renderDoc(input)
    } yield util.isPdfDocument(doc)
    p.yolo
  }

  property("renderDoc: Txt -> Txt") = secure {
    val doc = Document(Txt, ByteVector.view("Hello\n".getBytes))
    val input = RenderingInput(JobId(""), RenderingConfig(Txt, LibreOffice), doc)
    val rendered = generic.renderDoc(input).yolo
    rendered.body.containsSlice(doc.body)
  }

  property("execLowriter") = secure {
    val odt = Paths.get("./src/test/resources/test.odt")
    val pdf = Paths.get("./test.pdf")
    val p = for {
      notExists <- Shell.fileNotExists(pdf)
      _ <- execLowriter(odt, Paths.get("."), Pdf)
      bytes <- Shell.readAllBytes(pdf)
      _ <- Shell.delete(pdf)
    } yield notExists && util.isPdfByteVector(bytes)
    p.yolo
  }
}
