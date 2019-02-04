package utils

import scala.util.Try

trait SupportTools {
  def tryToInt( int: String ): Option[Int] = Try( int.toInt ).toOption
  def tryToDouble( double: String ): Option[Double] = Try( double.toDouble ).toOption
}
