package cn.tim.lib;

/**
 * tcp下发的激励能量和金币的数量
 * IRC消息体与MotivateTcpEntity一致，直接复用即可，后续重构时可考虑改名为MotivateIrcEntity
 */
public class MotivateTcpEntity {
    private int tcpMsgType;  //长连接消息类型（3-游戏进度同步消息，4-游戏结果下发消息，5-能量同步消息，6-金币同步消息）
    private int energyType; // 能量类型（1-战队能量补偿，2-学生在线时长增加能量，3-学生互动题贡献能量）
    private int planId;
    private String interactionId;
    private long groupId;//消息中更新值学生的3v3分队ID
    private int stuId;//更新值的学生ID（能量补偿时无该值）
    private boolean isRobot;//该消息发送时，消息中的groupId是否为机器人战队
    private int incrEnergy;//本条消息增加的能量值 和energyType对应
    private int stuTotalEnergy;//学生当前总能量（能量补偿时无该值）
    private int groupTotalEnergy;//战队当前总能量
    private String uuid;//tcp消息去重复用

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTcpMsgType() {
        return tcpMsgType;
    }

    public void setTcpMsgType(int tcpMsgType) {
        this.tcpMsgType = tcpMsgType;
    }

    public int getEnergyType() {
        return energyType;
    }

    public void setEnergyType(int energyType) {
        this.energyType = energyType;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getIncrEnergy() {
        return incrEnergy;
    }

    public void setIncrEnergy(int incrEnergy) {
        this.incrEnergy = incrEnergy;
    }

    public int getStuTotalEnergy() {
        return stuTotalEnergy;
    }

    public void setStuTotalEnergy(int stuTotalEnergy) {
        this.stuTotalEnergy = stuTotalEnergy;
    }

    public int getGroupTotalEnergy() {
        return groupTotalEnergy;
    }

    public void setGroupTotalEnergy(int groupTotalEnergy) {
        this.groupTotalEnergy = groupTotalEnergy;
    }

    public boolean isRobot() {
        return isRobot;
    }

    public void setRobot(boolean robot) {
        isRobot = robot;
    }

    @Override
    public String toString() {
        return "MotivateTcpEntity{" +
                "tcpMsgType=" + tcpMsgType +
                ", energyType=" + energyType +
                ", planId=" + planId +
                ", interactionId=" + interactionId +
                ", groupId=" + groupId +
                ", stuId=" + stuId +
                ", isRobot=" + isRobot +
                ", incrEnergy=" + incrEnergy +
                ", stuTotalEnergy=" + stuTotalEnergy +
                ", groupTotalEnergy=" + groupTotalEnergy +
                '}';
    }
}
