package org.mdoc.rendering.engines.util

import java.nio.file.{ Path, Paths }
import org.mdoc.common.model.Format

object PathOps {

  def withExtension(path: Path, format: Format): Path =
    Paths.get(path.toString + "." + format.toExtension)
}
