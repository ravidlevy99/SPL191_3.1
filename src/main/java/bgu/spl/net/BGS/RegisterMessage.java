package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RegisterMessage extends MessageFromClient {

    private int zeroCounter;
    private String username, password;
    private short opcode = 1;
    private boolean isDone;

    public RegisterMessage()
    {
        super();
        zeroCounter = 0;
        username = "";
        password = "";
        isDone = false;
    }

    @Override
    public Message decodeNextByte(byte b)
    {
        if (currentByte >= bytes.length) {
            bytes = Arrays.copyOf(bytes, currentByte * 2);
        }
        if(b == '\0')
        {
            zeroCounter++;
            if(zeroCounter == 1) {
                username = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
                currentByte = 0;
            }
            else {
                password = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
                currentByte = 0;
            }
        }

        else {
            bytes[currentByte] = b;
            currentByte++;
        }

        if(zeroCounter == 2) {
            isDone  =true;
            return this;
        }

        return null;
    }

    public String getUserName() {
        return username;
    }

    public String getPassWord() {
        return password;
    }

    @Override
    public void process(BidiMessagingProtocolImpl messagingProtocol) {
        messagingProtocol.processMessage(this);
    }

    @Override
    public boolean isDone()
    {
        return isDone;
    }
}
