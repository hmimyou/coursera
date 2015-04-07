package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
	if (c>r) 0 
	else if (c==0 || c == r) 1
	else pascal(c-1,r-1)+pascal(c,r-1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    
    def balanceHelper(numberOfClosing:Int, chars:List[Char]):Boolean = {
      if (chars.isEmpty) {
        numberOfClosing <= 0
      }
      else if (chars.head == '(') balanceHelper(numberOfClosing + 1, chars.tail)
      else if (chars.head == ')') {
        if (numberOfClosing == 0) false
        else balanceHelper(numberOfClosing -1, chars.tail)
      }
      else {
        balanceHelper(numberOfClosing, chars.tail)
      }
    }
    
    balanceHelper(0,chars)
  }
  

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (money < 0 || coins.isEmpty)
      0
    else 
      countChange(money-coins.head, coins) + countChange(money, coins.tail)
  }
}
