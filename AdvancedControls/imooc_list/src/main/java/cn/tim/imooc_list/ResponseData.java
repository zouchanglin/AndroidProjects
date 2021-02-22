package cn.tim.imooc_list;

public class ResponseData {
    public Integer id;
    public String name;
    public String picSmall;
    public String picBig;
    public String description;
    public Integer learner;

    @Override
    public String toString() {
        return "ResponseData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", picSmall='" + picSmall + '\'' +
                ", picBig='" + picBig + '\'' +
                ", description='" + description + '\'' +
                ", learner=" + learner +
                '}';
    }
}
