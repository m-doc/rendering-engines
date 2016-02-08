package org.mdoc.rendering.engines

import java.nio.file.{ Path, Paths }
import org.mdoc.common.model._
import scodec.bits.ByteVector

object Utils {

  def dotFormatExtension(format: Format): String =
    "." + formatExtension(format)

  def formatExtension(format: Format): String =
    format match {
      case Docx => "docx"
      case Html => "html"
      case Jpeg => "jpg"
      case Latex => "tex"
      case Odt => "odt"
      case Pdf => "pdf"
      case Png => "png"
    }

  def pathWithExtension(path: Path, format: Format): Path =
    Paths.get(path.toString + dotFormatExtension(format))

  def isPdfDocument(doc: Document): Boolean =
    doc.format == Pdf && isPdfByteVector(doc.body)

  def isPdfByteVector(bytes: ByteVector): Boolean = {
    val magicNumber = ByteVector.view("%PDF".getBytes)
    bytes.startsWith(magicNumber)
  }
}
