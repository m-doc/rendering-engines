package org.mdoc.renderingengines

import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.renderingengines.Wkhtmltopdf._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class WkhtmltopdfSpec extends Properties("Wkhtmltopdf") {

  property("wkhtmltoimage non-empty file") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".png")
      res <- wkhtmltoimage("http://google.com", tmpFile)
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield res.status > 0 || bytes.size > 1024 // wkhtmltoimage is not available on Travis
    p.yolo
  }

  property("wkhtmltopdf non-empty file") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".pdf")
      _ <- wkhtmltopdf("http://google.com", tmpFile)
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield bytes.size > 1024
    p.yolo
  }
}
