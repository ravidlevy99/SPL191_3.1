package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PrivateMessage extends MessageFromClient {

    private String username, content;
    private int zeroCounter;

    public PrivateMessage()
    {
        super();
        username = "";
        content = "";
        zeroCounter = 0;
    }

    @Override
    public Message decodeNextByte(byte b) {
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
                content = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
                currentByte = 0;
            }
        }

        else {
            bytes[currentByte] = b;
            currentByte++;
        }

        if(zeroCounter == 2)
            return this;

        return null;
    }
}