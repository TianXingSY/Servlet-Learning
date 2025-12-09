package com.txsy.txsyweb;

public class Student {
    private String id;  // 改为 String 类型，因为数据库中可能有非数字的 stuNo
    private String name;
    private String birthday;  // 如 "2000-01-01"
    private String pwd;       // 新增：密码
    private String sex;       // 新增：性别，例如 "男" / "女" 或 "M"/"F"

    // 构造方法（推荐保留一个完整参数的构造器）
    public Student(String id, String name, String birthday, String pwd, String sex) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.pwd = pwd;
        this.sex = sex;
    }

    // 如果你还需要旧的三参数构造器（比如只查基本信息），可以保留：
    public Student(String id, String name, String birthday) {
        this(id, name, birthday, "", ""); // 默认空密码和性别
    }

    // Getter 方法（必须有）
    public String getId() { return id; }
    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public String getPwd() { return pwd; }
    public String getSex() { return sex; }

    // Setter 方法（用于“修改信息”页面回填后更新对象）
    public void setName(String name) { this.name = name; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setSex(String sex) { this.sex = sex; }
}