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

object SwingDemo extends SimpleSwingApplication {

  var rangeReal = (-2.0f, 2.0f)
  var rangeImag = (-2.0f, 2.0f)

  val WIDTH = 600
  val HEIGHT = 400

  val depth = 30

  var z_0 = new Complex(0, 0) // z_0

  //TEST FOR CONVERGENCE
  def converges(zn: Complex, c: Complex): Int = {
    var n = 0
    var z = zn

    while (z.magnitude <= 2.0f && (n < depth)) {
      z = z * z + c
      n += 1
    }

    return n

  }

  var cc = new Complex(0, 0)
  var aa = new Complex(0.0f, 0.0f)

  val mandelbrot = new MandelbrotSet()

  val julia = new JuliaSet();
  julia.setZ0(aa)

  var img = mandelbrot.render;
  var time = 0.0

  /*
  def evaluate(): List[Complex] = {
    val ret = scala.collection.mutable.Buffer[Complex]()

    var n = 0
    var z = cc

    while ((z).magnitude <= 2.0f && (n < depth)) {

      z = (z * z) + aa
      n += 1
      ret.append(z)
    }

    return ret.toList

  }*/

  def complexToPoint(c: Complex): (Int, Int) = {
    return ((WIDTH.toFloat / 2.0f + (c.real * WIDTH.toFloat / (mandelbrot.rangeReal._2 - mandelbrot.rangeReal._1))).toInt - 2,
      (HEIGHT.toFloat / 2.0f - (c.i * HEIGHT.toFloat / (mandelbrot.rangeImag._2 - mandelbrot.rangeImag._1))).toInt - 2)
  }

  def top = new MainFrame {

    title = "Demo"
    size = new Dimension(150, 150)
    minimumSize = new Dimension(WIDTH * 2, HEIGHT + 50)
    centerOnScreen()

    contents = new Panel {
      listenTo(mouse.clicks)
      listenTo(mouse.wheel)
      listenTo(mouse.moves)
      listenTo(keys)

      var space = false
      reactions += {
        case KeyPressed(a, Key.Space, b, c) =>
          space = true
        case KeyReleased(a, Key.Space, b, c) =>
          space = false

        case MousePressed(_, event, a, b, c) => {
          aa = new Complex(
            mandelbrot.rangeReal._1 + event.x.toFloat / WIDTH * (mandelbrot.rangeReal._2 - mandelbrot.rangeReal._1),
            mandelbrot.rangeImag._2 - event.y.toFloat / HEIGHT * (mandelbrot.rangeImag._2 - mandelbrot.rangeImag._1))

        }
        case MouseDragged(a, b, c) => {
          println("hi")
        }
        case MouseMoved(a, b, c) => {
          println(b)
          aa = new Complex(
            mandelbrot.rangeReal._1 + b.x.toFloat / WIDTH * (mandelbrot.rangeReal._2 - mandelbrot.rangeReal._1),
            mandelbrot.rangeImag._2 - b.y.toFloat / HEIGHT * (mandelbrot.rangeImag._2 - mandelbrot.rangeImag._1))
          println(aa)
          julia.setZ0(aa)
        }
        case MouseReleased(a, b, c, d, e) => {

        }
        case MouseWheelMoved(a, b, c, d) => {}
      }

      override def paint(g: Graphics2D) {

        g.clearRect(0, 0, WIDTH, HEIGHT)
        g.drawImage(img, null, 0, 0)
        g.drawImage(julia.render, null, img.getWidth, 0)

        img = mandelbrot.render

        val p_cc = complexToPoint(cc)
        val p_aa = complexToPoint(aa)

        g.setColor(Color.WHITE)
        g.fillRect(p_aa._1, p_aa._2, 4, 4)

        var prev = aa

        time += 0.05
        println("rp")
        /*
        aa = (new Complex(
          -0.5f + (math.sin(time * 2).toFloat),
          0 //( math.cos(time * 2).toFloat)
        ))
        julia.setZ0(aa)
				*/
        
        g.drawLine(
            WIDTH, 0, 
            WIDTH, HEIGHT)
        repaint()
      }

    }

  }

}