package com.arquivei.config

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner
import pureconfig.error.ConfigReaderException

import scala.concurrent.duration._
import scala.language.postfixOps

@RunWith(classOf[JUnitRunner])
class ArquiveiConfigTest extends AnyFlatSpec with Matchers {

  "apply" should "correctly reflect the environment variables to the configs" in {

    case class DeeplyNested(stringValue: String, durationValue: Duration)
    case class NestedClass(someNestedValue: String, deeplyNestedValue: DeeplyNested)
    case class AnotherNested(value: String)
    case class PipeConfig(
        stringValue: String,
        intValue: Int,
        listOfStringValue: List[String],
        optionalStringValue: Option[String],
        intWithDefaultValue: Int = 5,
        doubleValue: Double,
        nestedClass: NestedClass,
        dbConfig: AnotherNested
    )

    val inputMap = Map[String, String](
      "CONFIG_STRINGVALUE"                                 -> "value",
      "CONFIG_INTVALUE"                                    -> "1",
      "CONFIG_LISTOFSTRINGVALUE_0"                         -> "a",
      "CONFIG_LISTOFSTRINGVALUE_1"                         -> "b",
      "CONFIG_LISTOFSTRINGVALUE_3"                         -> "c",
      "CONFIG_OPTIONALSTRINGVALUE"                         -> "some string",
      "CONFIG_DOUBLEVALUE"                                 -> "1.2",
      "CONFIG_NESTEDCLASS_SOMENESTEDVALUE"                 -> "nested value",
      "CONFIG_NESTEDCLASS_DEEPLYNESTEDVALUE_STRINGVALUE"   -> "str",
      "CONFIG_NESTEDCLASS_DEEPLYNESTEDVALUE_DURATIONVALUE" -> "7 days",
      "CONFIG_DBCONFIG_VALUE"                              -> "localhost"
    )

    val reflectedConfig = ArquiveiConfig[PipeConfig](inputMap)
    val expectedConfig = PipeConfig(
      stringValue = "value",
      intValue = 1,
      listOfStringValue = List("a", "b", "c"),
      optionalStringValue = Some("some string"),
      doubleValue = 1.2,
      nestedClass = NestedClass(
        someNestedValue = "nested value",
        deeplyNestedValue = DeeplyNested(
          stringValue = "str",
          durationValue = 7 days
        )
      ),
      dbConfig = AnotherNested("localhost")
    )
    reflectedConfig shouldBe expectedConfig
  }

  it should "fail if defined type could not be read from the environment variables" in {
    case class PipeConfig(
        stringValue: String,
        intValue: Int
    )

    val inputMap = Map[String, String](
      "CONFIG_STRINGVALUE" -> "value"
    )

    an[ConfigReaderException[PipeConfig]] shouldBe thrownBy {
      val _ = ArquiveiConfig[PipeConfig](inputMap)
    }
  }

  it should "fail if defined case class type has a different type from the envvar" in {
    case class PipeConfig(
        intValue: Int
    )

    val inputMap = Map[String, String](
      "CONFIG_INTVALUE" -> "value"
    )

    an[ConfigReaderException[PipeConfig]] shouldBe thrownBy {
      val _ = ArquiveiConfig[PipeConfig](inputMap)
    }
  }

}
