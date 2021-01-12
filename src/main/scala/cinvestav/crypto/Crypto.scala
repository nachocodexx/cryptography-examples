package cinvestav.crypto

import cats.implicits._
import cats.effect.implicits._
import cats.effect.IO
import cinvestav.crypto.MessageDigestAlgorithm.MessageDigestAlgorithm

import java.security.MessageDigest

trait Crypto[F[_]]{
  def getInstance(messageDigestAlgorithm: MessageDigestAlgorithm):F[MessageDigest]
  def digest(data:String,messageDigestAlgorithm: MessageDigestAlgorithm):F[String]
}

object CryptoInterpreter {
  implicit val cryptoIO:Crypto[IO] = new Crypto[IO] {
    override def getInstance(messageDigestAlgorithm: MessageDigestAlgorithm): IO[MessageDigest] =
      IO(
        MessageDigest.getInstance(messageDigestAlgorithm.toString)
      )

    override def digest(data: String, messageDigestAlgorithm: MessageDigestAlgorithm): IO[String] = {

     for {
       messageDigest <- getInstance(messageDigestAlgorithm)
       bytes<-data.getBytes.pure[IO]
       _<-messageDigest.update(bytes).pure[IO]
       digest <- messageDigest.digest().pure[IO]
       result <- digest.map(x=>Integer.toHexString(0xFF & x)).fold("")(_+_).pure[IO]
     } yield result
    }
  }
}
