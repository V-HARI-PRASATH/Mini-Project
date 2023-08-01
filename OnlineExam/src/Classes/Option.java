package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Option {
    private int id;
    private String opt;
    private boolean value;
    private int questionId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOpt() {
        return opt;
    }
    public void setOpt(String opt) {
        this.opt = opt;
    }
    public boolean isValue() {
        return value;
    }
    public void setValue(boolean value) {
        this.value = value;
    }
    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public Option(int id, String opt, boolean value, int questionId) {
        this.id = id;
        this.opt = opt;
        this.value = value;
        this.questionId = questionId;
    }
    public Option()
    {

    }
    public static void createOption(Option o,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select MAX(id) from options";
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        o.id=rs.getInt("MAX(id)")+1;
        sql="insert into options values("+o.id+",'"+o.opt+"',"+o.value+","+o.questionId+")";
        stmt.executeUpdate(sql);
    }
    public static void deleteOptions(int questionId,Connection con) throws SQLException
    {
        Statement stmt=con.createStatement();
        String sql="delete from options where questionId="+questionId;
        stmt.executeUpdate(sql);
    }
}
