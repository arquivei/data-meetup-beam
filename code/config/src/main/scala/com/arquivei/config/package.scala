package com.arquivei

import pureconfig.ConfigReader
import pureconfig.Exported
import pureconfig.generic.ExportMacros
import pureconfig.generic.ProductHint
import scala.language.experimental.macros

/** This package object needs to be imported to provide the implicits of generic ConfigReader[T]
  */
package object config {
  implicit def hint[A]: ProductHint[A] = ProductHint[A]((fieldName: String) => fieldName.toUpperCase)
  implicit def exportReader[A]: Exported[ConfigReader[A]] = macro ExportMacros.exportDerivedReader[A]
}
