package org.mdoc.rendering.engines

import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.rendering.engines.Wkhtmltopdf._
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scodec.bits.ByteVector

object WkhtmltopdfSpec extends Properties("Wkhtmltopdf") {

  property("htmlToPdf") = secure {
    val html = ByteVector.view("<html><body>Hello, world!</body></html>".getBytes)
    htmlToPdf(html).map(PdfUtils.isPdf).yolo
  }

  property("wkhtmltoimage non-empty file") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".png")
      res <- execWkhtmltoimage("http://google.com", tmpFile)
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield res.status > 0 || bytes.size > 1024 // wkhtmltoimage is not available on Travis
    p.yolo
  }

  property("wkhtmltopdf non-empty file") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".pdf")
      _ <- execWkhtmltopdf("http://google.com", tmpFile)
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield PdfUtils.isPdf(bytes)
    p.yolo
  }
}
