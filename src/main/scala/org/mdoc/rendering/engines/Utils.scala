package org.mdoc.rendering.engines

import java.nio.file.{ Path, Paths }
import org.mdoc.common.model.Format

object Utils {

  def dotFormatExtension(format: Format): String =
    "." + format.toExtension

  def pathWithExtension(path: Path, format: Format): Path =
    Paths.get(path.toString + dotFormatExtension(format))
}
