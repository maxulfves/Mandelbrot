
//SIMPLE COMPLEX NUMBER WITH A FEW METHODS
class Complex(val real: Float, val i: Float) {

  def +(other: Complex): Complex = {
    return new Complex(this.real + other.real, this.i + other.i)
  }

  def -(other: Complex): Complex = {
    return new Complex(this.real - other.real, this.i - other.i)
  }

  def *(other: Complex): Complex = {
    return new Complex(
      (this.real * other.real) - (this.i * other.i),
      (this.real * other.i) + (other.real * this.i))
  }

  def magnitude: Float = math.sqrt(real * real + i * i).toFloat
  override def toString(): String = this.real + " + " + this.i + " i"
}