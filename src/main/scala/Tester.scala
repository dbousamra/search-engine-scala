import java.util._
import scala.collection.JavaConversions._

object Tester {

  val t: SortedMap[String, Int] = new TreeMap[String, Int]() // produces an empty java.util.SortedMap
  t("a") = 1; t("b") = 2; t("A") = 3; t("0") = 4

  def foreachTest() = {
    t foreach { case (k, v) => printf("(%s,%d)%n", k, v) } // also prints the (k,v) pairs in the same TreeMap sorted order
  }

  def forTest() = {
    for ((k, v) <- t) printf("(%s,%d)%n", k, v)
  }

  def main(args: Array[String]) = {
    foreachTest()
    println()
    forTest()
  }

}