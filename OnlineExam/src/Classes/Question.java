package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Question {
    private int id;
    private String question;
    private int examId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public int getExamId() {
        return examId;
    }
    public void setExamId(int examId) {
        this.examId = examId;
    }
    public Question(int id, String question, int examId) {
        this.id = id;
        this.question = question;
        this.examId = examId;
    }
    public Question()
    {

    }
    public static void createQuestion(Question q,List<Option> ol,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select MAX(id) from question";
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        q.id=rs.getInt("MAX(id)")+1;
        sql="insert into question values("+q.id+",'"+q.question+"',"+q.examId+")";
        stmt.executeUpdate(sql);
        for(Option o:ol)
        {
            o.setQuestionId(q.id);
            Option.createOption(o,con);
        }
    }
    public static void deleteQuestions(int examId,Connection con) throws SQLException
    {

        Statement stmt=con.createStatement();
        String sql="select id from question where examId="+examId;
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next())
        {
            Option.deleteOptions(rs.getInt("id"),con);
        }
        sql="delete from question where examId="+examId;
        stmt.executeUpdate(sql);
    }
}
