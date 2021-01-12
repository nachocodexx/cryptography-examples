package cinvestav
import cats.effect.{ExitCode, IO, IOApp}
import cinvestav.utils.Console
import cinvestav.crypto.{Crypto, MessageDigestAlgorithm}
import cinvestav.crypto.CryptoInterpreter._

object Application extends  IOApp {
  def program()(implicit C:Console[IO], CR:Crypto[IO]):IO[ExitCode] =
    for {
      _<- C.putLine("Please write somehting: ")
      text<- C.getLine()
      digestHex <- CR.digest(text,MessageDigestAlgorithm.SHA256)
      _<- C.putLine(digestHex)
      result <- IO.unit.as(ExitCode.Success)
    } yield result
  override def run(args: List[String]): IO[ExitCode] = program()
}
