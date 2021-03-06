/*
 * Copyright 2010 Bruno de Carvalho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.biasedbit.efflux.network;

import com.biasedbit.efflux.logging.Logger;
import com.biasedbit.efflux.packet.CompoundControlPacket;
import com.biasedbit.efflux.packet.ControlPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://bruno.biasedbit.com/">Bruno de Carvalho</a>
 */
public class ControlPacketDecoder extends SimpleChannelInboundHandler<DatagramPacket> {

    // constants ------------------------------------------------------------------------------------------------------

    protected static final Logger LOG = Logger.getLogger(ControlPacketDecoder.class);

    // ChannelInboundHandlerAdapter -----------------------------------------------------------------------------------

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buffer = msg.content();
        if ((buffer.readableBytes() % 4) != 0) {
            LOG.debug("Invalid RTCP packet received: total length should be multiple of 4 but is {}",
                      buffer.readableBytes());
            return;
        }

        // Usually 2 packets per UDP frame...
        List<ControlPacket> controlPacketList = new ArrayList<ControlPacket>(2);

        // While there's data to read, keep on decoding.
        while (buffer.readableBytes() > 0) {
            try {
                final ControlPacket packet = ControlPacket.decode(buffer);
                if (packet != null) {
                    packet.setRemoteAddress(msg.sender());
                    controlPacketList.add(packet);
                }
            } catch (Exception e1) {
                LOG.debug("Exception caught while decoding RTCP packet.", e1);
            }
        }

        if (!controlPacketList.isEmpty()) {
            // Only send upwards when there were more than one valid decoded packets.
            // TODO shouldn't the whole compound packet be discarded when one of them has errors?!
            ctx.fireChannelRead(new CompoundControlPacket(controlPacketList));
        }
    }
}
