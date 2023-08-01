package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Result {
    private int id;
    private int mark;
    private int examId;
    private int participantId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public int getExamId() {
        return examId;
    }
    public void setExamId(int examId) {
        this.examId = examId;
    }
    public int getParticipantId() {
        return participantId;
    }
    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }
    public Result(int id, int mark, int examId, int participantId) {
        this.id = id;
        this.mark = mark;
        this.examId = examId;
        this.participantId = participantId;
    }
    public Result()
    {

    }
    public static void createResult(int score,int examId,int participantId,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select MAX(id) from result";
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        int id=rs.getInt("MAX(id)")+1;
        sql="insert into result values("+id+","+score+","+examId+","+participantId+")";
        stmt.executeUpdate(sql);
    }
}
