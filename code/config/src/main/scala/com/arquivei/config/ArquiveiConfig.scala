package com.arquivei.config

import com.typesafe.config.ConfigFactory
import pureconfig.ConfigReader
import pureconfig.ConfigSource
import pureconfig.Derivation

import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

/** ArquiveiConfig provides a function to reflect the environment variables to a defined case class
  * ----------------- IMPORTANT -------------------
  * You need to have the implicits in scope, by doing:
  * import com.arquivei.config._
  * -----------------------------------------------
  *
  * Environment variables must be set with the CONFIG_ prefix and have all chars in UPPER CASE
  * The underscore "_" will expect a case class nesting.
  * an underscore "_" and a number at the end will represent a list.
  *
  * ie:
  *
  * case class NestedConfig(list: List[String])
  *
  * case class MyConfig(
  *     nested: NestedConfig,
  *     example: String
  * )
  *
  * will reflect the following environment variables:
  *
  * CONFIG_EXAMPLE=blablabla
  * CONFIG_NESTED_LIST_0=hello
  * CONFIG_NESTED_LIST_1=world
  */
object ArquiveiConfig {

  private val configPrefix              = "CONFIG_"
  private val envVarNestingSeparator    = "_"
  private val lightbendNestingSeparator = "."

  def apply[T: ClassTag](
      envVars: Map[String, String] = System.getenv.asScala.toMap
  )(implicit reader: Derivation[ConfigReader[T]]): T = {

    val configs = envVars
      .filter(_._1.startsWith(configPrefix))
      .map { case (k, v) =>
        val newKey = k
          .replaceFirst(configPrefix, "")
          .replace(envVarNestingSeparator, lightbendNestingSeparator)
        (newKey, v)
      }
      .asJava
    val config = ConfigFactory.parseMap(configs)

    ConfigSource.fromConfig(config).loadOrThrow[T]
  }

}
