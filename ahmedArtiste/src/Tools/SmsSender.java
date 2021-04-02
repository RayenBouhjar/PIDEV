/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import static Tools.SmsSender.ACCOUNT_SID;
import static Tools.SmsSender.AUTH_TOKEN;
import static Tools.SmsSender.*;

import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.net.URI;
import java.util.Arrays;

public class SmsSender {

    /**
     */
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID
            = "AC96dabb181a18cfa3f3174657238592c9";
    public static final String AUTH_TOKEN
            = "47f84ce13b65c8baff4ebce23ee91dcd";

    public void send(String s, String x) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber("+21692805508"), // to
                        new PhoneNumber("+13346058810"), // from
                        "" + s)
                .create();

    }
}
//25061709