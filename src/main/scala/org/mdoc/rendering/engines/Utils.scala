package org.mdoc.rendering.engines

import java.nio.file.{ Path, Paths }
import org.mdoc.common.model.Document
import org.mdoc.common.model.Format
import org.mdoc.common.model.Format._
import scodec.bits.ByteVector

object Utils {

  def dotFormatExtension(format: Format): String =
    "." + format.toExtension

  def pathWithExtension(path: Path, format: Format): Path =
    Paths.get(path.toString + dotFormatExtension(format))

  def isPdfDocument(doc: Document): Boolean =
    doc.format == Pdf && isPdfByteVector(doc.body)

  def isPdfByteVector(bytes: ByteVector): Boolean = {
    val magicNumber = ByteVector.view("%PDF".getBytes)
    bytes.startsWith(magicNumber)
  }
}
