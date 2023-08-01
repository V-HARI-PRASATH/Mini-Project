package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ExamAdmin extends User {
    private int id;
    private String name;
    private int userId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getUserId() {
        return userId;
    }
    public ExamAdmin(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
    public ExamAdmin()
    {

    }
    public static ExamAdmin getExamAdmin(int userId,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select * from exam_admin where userId="+userId;
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        ExamAdmin ea=new ExamAdmin(rs.getInt("id"),rs.getString("name"),rs.getInt("userId"));
        return ea;
    }
}
