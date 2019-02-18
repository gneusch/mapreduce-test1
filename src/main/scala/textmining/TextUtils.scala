package textmining

object TextUtils {

  def wordPairs(corpus: List[String]) = corpus flatMap { a =>
      corpus flatMap { b =>
        if(a!=b) Option(a, b)
        else None
    }
  }

  def genBiGrams(wordList: List[String]): List[(String,String)] = wordList match {
    case x :: Nil => Nil
    case x :: xs => (x, xs.head) :: genBiGrams(xs)
    case _ => Nil
  }

  def genTriGrams(wordList: List[String]): List[(String, String, String)] = wordList match {
    case x :: y :: Nil => Nil
    case x :: y :: xs => (x, y, xs.head) :: genTriGrams(y :: xs)
    case _ => Nil
  }

  def strListToLower(wordList: List[String]) = wordList map { _.toLowerCase }

}
