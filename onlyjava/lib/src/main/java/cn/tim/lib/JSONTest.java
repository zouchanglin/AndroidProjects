package cn.tim.lib;

import com.alibaba.fastjson.JSON;

public class JSONTest {
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "  \"type\":\"10146\",\n" +
                "  \"tcpMsgType\":5,\n" +
                "  \"energyType\":3,\n" +
                "  \"planId\":1177003,\n" +
                "  \"interactionId\":\"1817361525975137114\",\n" +
                "  \"groupId\":1,\n" +
                "  \"isRobot\":false,\n" +
                "  \"stuId\":59139,\n" +
                "  \"incrEnergy\":5,\n" +
                "  \"stuTotalEnergy\":5,\n" +
                "  \"groupTotalEnergy\":5,\n" +
                "  \"uuid\":\"12839089038901\"\n" +
                "}";

        MotivateTcpEntity tcpEntity = JSON.parseObject(jsonStr, MotivateTcpEntity.class);
        System.out.println(tcpEntity);
    }
}
