package org.mdoc.rendering.engines

import org.mdoc.common.model.Document
import org.mdoc.common.model.Format.Pdf
import scodec.bits.ByteVector

package object util {

  def isPdfDocument(doc: Document): Boolean =
    doc.format == Pdf && isPdfByteVector(doc.body)

  def isPdfByteVector(bytes: ByteVector): Boolean = {
    val magicNumber = ByteVector.view("%PDF".getBytes)
    bytes.startsWith(magicNumber)
  }
}
