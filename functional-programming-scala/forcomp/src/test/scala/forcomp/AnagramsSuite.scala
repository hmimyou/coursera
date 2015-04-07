package forcomp

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Anagrams._

@RunWith(classOf[JUnitRunner])
class AnagramsSuite extends FunSuite {

  test("wordOccurrences: abbed") {
    assert(wordOccurrences("abbed") === List(('a', 1), ('b', 2), ('d', 1), ('e',1)))
  }

  test("wordOccurrences: Robert") {
    assert(wordOccurrences("Robert") === List(('b', 1), ('e', 1), ('o', 1), ('r', 2), ('t', 1)))
  }



  test("sentenceOccurrences: abbed dc e") {
    assert(sentenceOccurrences(List("abbed","dc", "e")) === List(('a', 1), ('b', 2), ('c', 1), ('d', 2), ('e', 2)))
  }
  
  test("sentenceOccurrences: []") {
    assert(sentenceOccurrences(List()) === List())
  }


  test("dictionaryByOccurrences.get: eat") {
    assert(dictionaryByOccurrences.get(List(('a', 1), ('e', 1), ('t', 1))).map(_.toSet) === Some(Set("ate", "eat", "tea")))
  }



  test("word anagrams: married") {
    assert(wordAnagrams("married").toSet === Set("married", "admirer"))
  }

  test("word anagrams: player") {
    assert(wordAnagrams("player").toSet === Set("parley", "pearly", "player", "replay"))
  }



  test("subtract: lard - l") {
    val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
    val r = List(('l', 1))
    val lad = List(('a', 1), ('d', 1), ('r', 1))
    assert(subtract(lard, r) === lad)
  }
  
  test("subtract: lard - lard") {
    val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
    //val r = List(('r', 1))
    val emp = List()
    assert(subtract(lard, lard) === emp)
    assert(subtract(lard, emp) === lard)
  }
  
  



  test("combinations: []") {
    assert(combinations(Nil) === List(Nil))
  }

  test("combinations: abba") {
    val abba = List(('a', 2), ('b', 2))
    val abbacomb = List(
      List(),
      List(('a', 1)),
      List(('a', 2)),
      List(('b', 1)),
      List(('a', 1), ('b', 1)),
      List(('a', 2), ('b', 1)),
      List(('b', 2)),
      List(('a', 1), ('b', 2)),
      List(('a', 2), ('b', 2))
    )
    assert(combinations(abba).toSet === abbacomb.toSet)
  }
  
  test("combinations: abbaccddeedd") {
    val abba = List(('a', 2), ('b', 2), ('c',2), ('d',4), ('e',2))
    //println(combinations(abba))
  }

  test("sentence anagrams: []") {
    val sentence = List()
    assert(sentenceAnagrams(sentence) === List(Nil))
  }
  
  test("sentence anagrams: player") {
    val sentence = List("Linux","rulez")
   // println(sentenceAnagrams(sentence))

  }
  
  

  test("sentence anagrams: Linux rulez") {
    val sentence = List("Linux", "rulez")
    val anas = List(
      List("Rex", "Lin", "Zulu"),
      List("nil", "Zulu", "Rex"),
      List("Rex", "nil", "Zulu"),
      List("Zulu", "Rex", "Lin"),
      List("null", "Uzi", "Rex"),
      List("Rex", "Zulu", "Lin"),
      List("Uzi", "null", "Rex"),
      List("Rex", "null", "Uzi"),
      List("null", "Rex", "Uzi"),
      List("Lin", "Rex", "Zulu"),
      List("nil", "Rex", "Zulu"),
      List("Rex", "Uzi", "null"),
      List("Rex", "Zulu", "nil"),
      List("Zulu", "Rex", "nil"),
      List("Zulu", "Lin", "Rex"),
      List("Lin", "Zulu", "Rex"),
      List("Uzi", "Rex", "null"),
      List("Zulu", "nil", "Rex"),
      List("rulez", "Linux"),
      List("Linux", "rulez")
    )
    
  //  println(sentenceAnagrams(sentence))
    
    assert(sentenceAnagrams(sentence).toSet === anas.toSet)
  }  

}
