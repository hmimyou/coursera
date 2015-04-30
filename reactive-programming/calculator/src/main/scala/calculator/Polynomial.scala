package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = Signal {
    val av = a()
    val bv = b()
    val cv = c()
    bv * bv - 4 * av * cv
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = Signal {
    val av = a()
    val bv = b()
    val cv = c()
    val delta = computeDelta(a, b, c)()
    if (delta < 0) {
      Set.empty
    }
    else if (delta > 0.0){
      val result1 = (-0.5/av) * (bv + Math.sqrt(delta)) 
      val result2 = (-0.5/av) * (bv - Math.sqrt(delta))	
      Set(result1, result2)
    }
    else {
      Set(0.0 -  bv/av * 0.5)
    }   
  }
}
