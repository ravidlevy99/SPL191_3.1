package bgu.spl.net.BGS;

public class LogoutMessage extends MessageFromClient {

    public LogoutMessage()
    {
        super();
    }

    public Message decodeNextByte(byte b)
    {
        return this;
    }
}
