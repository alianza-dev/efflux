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
import com.biasedbit.efflux.packet.DataPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author <a href="http://bruno.biasedbit.com/">Bruno de Carvalho</a>
 */
public class DataPacketDecoder extends OneToOneDecoder {

    // constants ------------------------------------------------------------------------------------------------------

    protected static final Logger LOG = Logger.getLogger(OneToOneDecoder.class);

    // OneToOneDecoder ------------------------------------------------------------------------------------------------

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ByteBuf)) {
            return null;
        }

        try {
            return DataPacket.decode((ByteBuf) msg);
        } catch (Exception e) {
            LOG.debug("Failed to decode RTP packet.", e);
            return null;
        }
    }
}
