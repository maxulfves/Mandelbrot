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

  val depth = 10

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

  
  val mandelbrot = new JuliaSet();
  val julia = new JuliaSet();
  
  var img = mandelbrot.render;
  
  
  var time = 0.0
  
  var myPoint = new Complex(0, 0)
  
  def top = new MainFrame {

    title = "Demo"
    size = new Dimension(150, 150)
    minimumSize = new Dimension(WIDTH * 2, HEIGHT + 50)
    centerOnScreen()
    
    contents = new Panel {
      listenTo(mouse.clicks)
      listenTo(mouse.wheel)
      listenTo(keys)

      var space = false
      reactions += {
        case KeyPressed(a, Key.Space, b, c) =>
          space = true
        case KeyReleased(a, Key.Space, b, c) =>
          space = false
          
        case MousePressed(_, event, a, b, c) => {
          myPoint = new Complex(
              mandelbrot.rangeReal._1 + event.x.toFloat / WIDTH  * (mandelbrot.rangeReal._2 - mandelbrot.rangeReal._1),
              mandelbrot.rangeImag._2 - event.y.toFloat / HEIGHT * (mandelbrot.rangeImag._2 - mandelbrot.rangeImag._1)
          )
          println(myPoint)
          julia.setZ0(myPoint)
          
        }
        case MouseDragged(a, b, c) => {      }
        case MouseReleased(a, b, c, d, e) => { }
        case MouseWheelMoved(a, b, c, d) => {      }
      }

      override def paint(g: Graphics2D) {

        g.clearRect(0, 0, WIDTH, HEIGHT)
        g.drawImage(img, null, 0, 0)
        g.drawImage(julia.render, null, img.getWidth, 0)
        
        img = mandelbrot.render
        
        g.drawRect(
            (WIDTH.toFloat/2.0f + (myPoint.real * WIDTH.toFloat / (mandelbrot.rangeReal._2 - mandelbrot.rangeReal._1))).toInt - 2, 
            (HEIGHT.toFloat/2.0f - (myPoint.i * HEIGHT.toFloat / (mandelbrot.rangeImag._2 - mandelbrot.rangeImag._1))).toInt - 2, 
            4, 4
        )
        
        time += 0.01
        repaint()
      }

    }

  }

}