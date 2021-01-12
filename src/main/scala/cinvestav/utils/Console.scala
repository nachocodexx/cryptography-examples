package cinvestav.utils

import cats.effect.IO

import java.util.Calendar
import scala.io.StdIn

trait Console[F[_]]{
  def getLine():F[String]
  def putLine(x:Any):F[Unit]
}

object Console {
  implicit val consoleIO: Console[IO] = new Console[IO] {
    override def getLine(): IO[String] = IO(StdIn.readLine())
    override def putLine(x:Any): IO[Unit] =
      IO(println(
        s"${Calendar.getInstance().getTime}[${Thread.currentThread().getName}]- $x")
      )
  }
}
