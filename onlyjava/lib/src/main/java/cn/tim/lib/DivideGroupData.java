package cn.tim.lib;


import java.util.List;

/**
 * rolePlay分组数据，来自Notice
 */
public class DivideGroupData {
    private String type;
    private int groupId;
    private List<Student> list;
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getGroupId() {
        return groupId;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }
    public List<Student> getList() {
        return list;
    }

    public static class Extra {
        private String englishName;
        private String iconUrl;
        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }
        public String getEnglishName() {
            return englishName;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
        public String getIconUrl() {
            return iconUrl;
        }

        @Override
        public String toString() {
            return "Extra{" +
                    "englishName='" + englishName + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    '}';
        }
    }

    public static class Student {

        private long stuId;
        private int gender;
        private int index;
        private Extra extra;
        public void setStuId(long stuId) {
            this.stuId = stuId;
        }
        public long getStuId() {
            return stuId;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
        public int getGender() {
            return gender;
        }

        public void setIndex(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }

        public void setExtra(Extra extra) {
            this.extra = extra;
        }
        public Extra getExtra() {
            return extra;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "stuId=" + stuId +
                    ", gender=" + gender +
                    ", index=" + index +
                    ", extra=" + extra +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DivideGroupData{" +
                "type='" + type + '\'' +
                ", groupId=" + groupId +
                ", list=" + list +
                '}';
    }
}
