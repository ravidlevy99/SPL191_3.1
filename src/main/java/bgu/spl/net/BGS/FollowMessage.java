package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FollowMessage extends MessageFromClient {

    private short followOrUnfollow, numberOfUsers, usersleft;
    private String[] usernames;
    private boolean passedTwo = false;

    public FollowMessage()
    {
        super();
        numberOfUsers = 0;
        usersleft = 0;
    }

    public Message decodeNextByte(byte b)
    {
        if (currentByte >= bytes.length) {
            bytes = Arrays.copyOf(bytes, currentByte * 2);
        }

        if(b == '\0')
        {
            usernames[numberOfUsers - usersleft] = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
            currentByte = 0;
            usersleft--;
            currentByte = 0;
            if(usersleft == 0)
                return this;
        }

        else {
            if (!passedTwo)
            {
                if (currentByte == 0)
                    followOrUnfollow = (short) b;
                else if (currentByte == 1 | currentByte == 2) {
                    if (currentByte == 1)
                        numberOfUsers += (short) (b & 0xff << 8);
                    else {
                        numberOfUsers += (short) (b & 0xff);
                        usersleft = numberOfUsers;
                        usernames = new String[numberOfUsers];
                        passedTwo = true;
                    }
                }
            }
            bytes[currentByte] = b;
            currentByte++;
        }
        return null;
    }
}
