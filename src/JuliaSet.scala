import java.awt.image.BufferedImage
import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame
import java.awt.Dimension
import scala.swing.Panel
import scala.swing._
import scala.swing.event._
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.event.MouseWheelEvent
import java.awt.Color
import scala.swing.event._

class JuliaSet {
  val WIDTH = 600
  val HEIGHT = 400
  val depth = 10
  var rangeReal = (-2.0f, 2.0f)
  var rangeImag = (-2.0f, 2.0f)

  var z_0 = new Complex(0, 0)
  
  def converges(c: Complex): Int = {
    var n = 0
    var z = z_0
    
    while ((z).magnitude <= 2.0f && (n < depth)) {
      z = (z * z) + c
      n += 1
    }

    return n

  }
  
  def setZ0(c:Complex){
    z_0 = c
  }

  def render: BufferedImage = {
    val buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
    val g = buffer.getGraphics.asInstanceOf[Graphics2D]

    for (y <- 0 to HEIGHT) {
      val y_c = (rangeImag._1 + (y.toFloat / HEIGHT) * (rangeImag._2 - rangeImag._1))

      for (x <- 0 to WIDTH) {
        val x_c = rangeReal._1 + (x.toFloat / WIDTH) * (rangeReal._2 - rangeReal._1)

        val c = new Complex(
          x_c,
          y_c)

        val level = converges(c)

        val color2 = if (level == depth) {
          new Color(100, 100, 100)
        } else {
          (level % 3) match {
            case 0 => new Color(255, 100, 100)
            case 1 => new Color(100, 255, 100)
            case 2 => new Color(100, 100, 255)
          }
        }

        val color = new Color((250 * (level.toFloat / depth.toFloat)).toInt, 10, 10)

        g.setColor(color2)
        g.drawRect(x, y, 1, 1)

      }
    }

    return buffer
  }

}