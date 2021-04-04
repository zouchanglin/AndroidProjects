package cn.tim.lib;

import com.alibaba.fastjson.JSON;

import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCModeParser;
import org.schwering.irc.lib.IRCUser;

import java.io.IOException;

public class MyClass {
    public static void main2(String[] args) {
        String jsonStr = "{\n" +
                "    \"type\":\"10145\",\n" +
                "    \"groupId\":2101,\n" +
                "    \"list\":[\n" +
                "        {\n" +
                "            \"stuId\":59746742,\n" +
                "            \"gender\":0,\n" +
                "            \"index\":1,\n" +
                "            \"extra\":{\n" +
                "                \"englishName\":\"\",\n" +
                "                \"iconUrl\":\"https://t.100tal.com/avatar/%E9%9A%8B%E8%BE%B0%E9%80%B8?0\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"stuId\":61115780,\n" +
                "            \"gender\":3,\n" +
                "            \"index\":2,\n" +
                "            \"extra\":{\n" +
                "                \"englishName\":\"\",\n" +
                "                \"iconUrl\":\"https://t.100tal.com/avatar/%E6%82%A0%E6%82%A0\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        DivideGroupData divideGroupData = JSON.parseObject(jsonStr, DivideGroupData.class);
        System.out.println(divideGroupData);
        System.out.println("--------------------");
        System.out.println(JSON.toJSONString(divideGroupData));

        IRCConnection conn = new IRCConnection(
                "irc.somenetwork.com",
                6667,
                6669,
                null,
                "Foo",
                "Mr. Foobar",
                "foo@bar.com"
        );

        conn.addIRCEventListener(new IRCEventListener() {
            @Override
            public void onRegistered() {
                System.out.println("xxx");
            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onInvite(String s, IRCUser ircUser, String s1) {

            }

            @Override
            public void onJoin(String s, IRCUser ircUser) {

            }

            @Override
            public void onKick(String s, IRCUser ircUser, String s1, String s2) {

            }

            @Override
            public void onMode(String s, IRCUser ircUser, IRCModeParser ircModeParser) {

            }

            @Override
            public void onMode(IRCUser ircUser, String s, String s1) {

            }

            @Override
            public void onNick(IRCUser ircUser, String s) {

            }

            @Override
            public void onNotice(String s, IRCUser ircUser, String s1) {

            }

            @Override
            public void onPart(String s, IRCUser ircUser, String s1) {

            }

            @Override
            public void onPing(String s) {

            }

            @Override
            public void onPrivmsg(String s, IRCUser ircUser, String s1) {

            }

            @Override
            public void onQuit(IRCUser ircUser, String s) {

            }

            @Override
            public void onReply(int i, String s, String s1) {

            }

            @Override
            public void onTopic(String s, IRCUser ircUser, String s1) {

            }

            @Override
            public void unknown(String s, String s1, String s2, String s3) {

            }
        });
        conn.setDaemon(true);
        conn.setColors(false);
        conn.setPong(true);

        try {
            conn.connect(); // Try to connect!!! Don't forget this!!!
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
    }
}