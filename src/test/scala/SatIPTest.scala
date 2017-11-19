import com.biasedbit.efflux.packet.{ByePacket, ControlPacket, DataPacket}
import com.biasedbit.efflux.participant.{RtpParticipant, RtpParticipantInfo}
import com.biasedbit.efflux.session.{MultiParticipantSession, RtpSession, RtpSessionDataListener, SingleParticipantSession}
import io.netty.buffer.ByteBuf
import org.junit.Assert._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by pierr on 21.10.2016.
  */
object SatIPTest {
  def main(args: Array[String]): Unit = {
    val local1: RtpParticipant = RtpParticipant.createReceiver("192.168.1.5", 1400, 1401)
    val session1 = new MultiParticipantSession("id", 127, local1)

    session1.addDataListener(new RtpSessionDataListener() {
      def dataPacketReceived(session: RtpSession, participant: RtpParticipantInfo, packet: DataPacket) {
        System.err.println("Session 1 received packet: " + packet + "(session: " + session.getId + ")")
      }
    })

    session1.sendControlPacket(new ByePacket())

    Thread.sleep(10000)
  }
}
