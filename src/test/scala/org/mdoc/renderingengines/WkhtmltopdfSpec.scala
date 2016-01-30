package org.mdoc.renderingengines

import org.mdoc.fshell.Shell
import org.mdoc.fshell.Shell.ShellSyntax
import org.mdoc.renderingengines.Wkhtmltopdf._
import org.scalacheck.Prop._
import org.scalacheck.Properties

class WkhtmltopdfSpec extends Properties("Wkhtmltopdf") {

  property("execWkhtmltoimage non-empty file") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".png")
      res <- execWkhtmltoimage("http://google.com", tmpFile)
      bytes <- {
        println(res)
        Shell.readAllBytes(tmpFile)
      }
      _ <- {
        println(bytes)
        Shell.delete(tmpFile)
      }
    } yield bytes.size > 1024
    p.yolo
  }

  property("execWkhtmltopdf non-empty file") = secure {
    val p = for {
      tmpFile <- Shell.createTempFile("google", ".pdf")
      _ <- execWkhtmltopdf("http://google.com", tmpFile)
      bytes <- Shell.readAllBytes(tmpFile)
      _ <- Shell.delete(tmpFile)
    } yield bytes.size > 1024
    p.yolo
  }
}
