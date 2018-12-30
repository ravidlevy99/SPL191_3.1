package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RegsiterMessage extends MessageFromClient {

    private int zeroCounter;
    private String username, password;

    public RegsiterMessage()
    {
        super();
        zeroCounter = 0;
        username = "";
        password = "";
    }

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

        if(zeroCounter == 2)
            return this;

        return null;
    }

    public MessageFromServer processMessageFromClient(BGSDataBase dataBase) {
        if(dataBase.checkIfAlreadyRegistered(username))
            return new ErrorMessage((short)(1));
        else
        {
            dataBase.registerUser(username , password);
            return new ACKMessage();
        }
    }
}
