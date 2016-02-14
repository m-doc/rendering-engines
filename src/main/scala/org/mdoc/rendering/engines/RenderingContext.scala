package org.mdoc.rendering.engines

import java.nio.file.Path
import org.mdoc.common.model.RenderingInput

final case class RenderingContext(input: RenderingInput, workingDir: Path, inputFile: Path, outputFile: Path)
