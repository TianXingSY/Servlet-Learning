package com.txsy.txsyweb;

public class Student {
    private String id;  // 改为 String 类型，因为数据库中可能有非数字的 stuNo
    private String name;
    private String birthday;  // 如 "2000-01-01"
    private String pwd;       // 新增：密码
    private String sex;       // 新增：性别，例如 "男" / "女" 或 "M"/"F"
    private String major;     // 新增：专业
    private String hobbies;   // 新增：爱好

    // 构造方法（推荐保留一个完整参数的构造器）
    public Student(String id, String name, String birthday, String pwd, String sex, String major, String hobbies) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.pwd = pwd;
        this.sex = sex;
        this.major = major;
        this.hobbies = hobbies;
    }

    // 如果你还需要旧的三参数构造器（比如只查基本信息），可以保留：
    public Student(String id, String name, String birthday) {
        this(id, name, birthday, "", "", "", ""); // 默认空密码、性别、专业和爱好
    }

    // 五参数构造器（兼容旧代码）
    public Student(String id, String name, String birthday, String pwd, String sex) {
        this(id, name, birthday, pwd, sex, "", ""); // 默认空专业和爱好
    }

    // 六参数构造器（兼容旧代码）
    public Student(String id, String name, String birthday, String pwd, String sex, String major) {
        this(id, name, birthday, pwd, sex, major, ""); // 默认空爱好
    }

    // Getter 方法（必须有）
    public String getId() { return id; }
    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public String getPwd() { return pwd; }
    public String getSex() { return sex; }
    public String getMajor() { return major; }
    public String getHobbies() { return hobbies; }

    // Setter 方法（用于"修改信息"页面回填后更新对象）
    public void setName(String name) { this.name = name; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setSex(String sex) { this.sex = sex; }
    public void setMajor(String major) { this.major = major; }
    public void setHobbies(String hobbies) { this.hobbies = hobbies; }
}