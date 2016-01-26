package org.mdoc.rendering.engines

import java.nio.file.Paths
import org.scalacheck.Prop._
import org.scalacheck.Properties
import Wkhtmltopdf._

class WkhtmltopdfSpec extends Properties("Wkhtmltopdf") {

  property("") = secure {
    execWkhtmltopdf("", Paths.get("test.pdf"))
    true
  }
}
