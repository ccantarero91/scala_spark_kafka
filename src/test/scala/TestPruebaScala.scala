import com.pruebas.PruebaScala
import org.scalatest._

class TestPruebaScala extends FlatSpec with Matchers {

  "PruebaScala.main" should "lanzar el main" in {
    PruebaScala.main(Array("pepe"))
  }

  "PruebasScala.devuelveMayusculas" should "paco  in mayus" in {
    PruebaScala.devuelveMayusculas("paco") shouldBe "PACO"
  }
}