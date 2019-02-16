package textmining

object TextUtils {

  def wordPairs(corpus: List[String]) = corpus flatMap { a =>
      corpus flatMap { b =>
        if(a!=b) Option(a, b)
        else None
    }
  }

  def genBiGrams(wordList: List[String]): List[Option[(String,String)]] = wordList match {
    case x :: Nil => Nil
    case x :: xs => Some(x, xs.head) :: genBiGrams(xs.tail)
    case _ => Nil
  }

  def genTriGrams(wordList: List[String]): List[Option[(String, String, String)]] = wordList match {
    case x :: y :: Nil => Nil
    case x :: y :: xs => Some(x, y, xs.head) :: genTriGrams(y :: xs)
    case _ => Nil
  }

}
