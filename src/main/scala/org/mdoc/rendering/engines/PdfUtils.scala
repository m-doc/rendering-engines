package org.mdoc.rendering.engines

import scodec.bits.ByteVector

object PdfUtils {

  def isPdf(bytes: ByteVector): Boolean = {
    val magicNumber = ByteVector.view("%PDF".getBytes)
    bytes.startsWith(magicNumber)
  }
}
