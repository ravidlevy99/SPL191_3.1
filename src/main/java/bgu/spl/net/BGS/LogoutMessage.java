package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class LogoutMessage extends MessageFromClient {

    public LogoutMessage() {
        super();
    }

    @Override
    public Message decodeNextByte(byte b) {
        return this;
    }

    @Override
    public void process(BidiMessagingProtocolImpl messagingProtocol) {
        messagingProtocol.processMessage(this);
    }
}
