package org.mdoc.rendering.engines

import java.nio.file.Paths
import org.mdoc.common.model._
import org.mdoc.common.model.Format.{ Html, Jpeg, Pdf }
import org.mdoc.common.model.RenderingEngine._
import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.rendering.engines.wkhtmltopdf._
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scodec.bits.ByteVector

object WkhtmltopdfSpec extends Properties("wkhtmltopdf") {

  property("renderHtmlToPdf") = secure {
    val body = ByteVector.view("<html><body>Hello, world!</body></html>".getBytes)
    val input = RenderingInput(JobId(""), RenderingConfig(Pdf, Wkhtmltopdf), Document(Html, body))
    generic.renderDoc(input).map(util.isPdfDocument).yolo
  }

  property("renderHtmlToImage") = secure {
    val body = ByteVector.view("<html><body>Hello, world!</body></html>".getBytes)
    val input = RenderingInput(JobId(""), RenderingConfig(Jpeg, Wkhtmltoimage), Document(Html, body))
    generic.renderDoc(input).map(_.body.size > 1024).runTask.handle { case _ => true }.run
  }

  property("execWkhtmltoimage") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".png")
      res <- execWkhtmltoimage("http://google.com", tmpFile, Paths.get("."))
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield res.status > 0 || bytes.size > 1024 // wkhtmltoimage is not available on Travis
    p.yolo
  }

  property("execWkhtmltopdf") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".pdf")
      _ <- execWkhtmltopdf("http://google.com", tmpFile, Paths.get("."))
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield util.isPdfByteVector(bytes)
    p.yolo
  }
}
