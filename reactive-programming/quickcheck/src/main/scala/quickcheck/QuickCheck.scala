package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }
  
  property("min2") = forAll { (a: Int, b: Int, c: Int) =>
    val h = insert(a, empty)
    val h1 = insert(b, h)
    val h2 = insert(c, h1)
    findMin(h2) == Math.min(a, Math.min(b, c))
  }
  
  
  property("meld1") = forAll { (h1: H, h2:H) =>
  	val h3 = meld(h1, h2)
  	findMin(h3) == (if (findMin(h1) < findMin(h2)) findMin(h1) else findMin(h2))
  }
  
  property("deleteMin") = forAll { (a: Int, b: Int, c: Int) =>
    val h = insert(a, empty)
    val h1 = insert(b, h)
    val h2 = insert(c, h1)
    val sorted = Seq(a, b, c).sorted
    val h3 = deleteMin(h2)
    findMin(h3) == sorted(1)
  }
  

  lazy val genHeap: Gen[H] = for {
    v <- arbitrary[A]
    h <- oneOf(const(empty), genHeap)
  } yield insert(v, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
