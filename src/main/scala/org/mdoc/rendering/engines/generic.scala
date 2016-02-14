package org.mdoc.rendering.engines

import org.mdoc.common.model.Document
import org.mdoc.common.model.RenderingEngine._
import org.mdoc.common.model.RenderingInput
import org.mdoc.fshell.Shell
import org.mdoc.rendering.engines.util.PathOps

object generic {

  def renderDoc(input: RenderingInput): Shell[Document] = {
    input.config.engine match {
      case LibreOffice => libreoffice.renderDoc(input)
      case Wkhtmltoimage => wkhtmltopdf.renderHtmlToImage(input)
      case Wkhtmltopdf => wkhtmltopdf.renderHtmlToPdf(input)
    }
  }

  def mkRenderFun[T](execEngine: RenderingContext => Shell[T]): RenderingInput => Shell[Document] =
    input => {
      val engineName = input.config.engine.toString.toLowerCase
      val jobId = input.id.self
      val dirPrefix = s"mdoc-$engineName-$jobId-"
      val inputFormat = input.doc.format
      val outputFormat = input.config.outputFormat

      for {
        workingDir <- Shell.createTempDirectory(dirPrefix)
        basePath = workingDir.resolve("doc-" + jobId)
        inputFile = PathOps.withExtension(basePath, inputFormat)
        outputFile = PathOps.withExtension(basePath, outputFormat)
        ctx = RenderingContext(input, workingDir, inputFile, outputFile)
        _ <- Shell.write(inputFile, input.doc.body)
        _ <- execEngine(ctx)
        outputBytes <- Shell.readAllBytes(outputFile)
        _ <- Shell.deleteAll(List(inputFile, outputFile, workingDir))
      } yield Document(outputFormat, outputBytes)
    }
}
