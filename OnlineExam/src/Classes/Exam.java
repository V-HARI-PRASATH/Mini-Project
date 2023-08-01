package Classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class Exam
 {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Time duration;
    private int examAdminId;
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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Time getDuration() {
        return duration;
    }
    public void setDuration(Time duration) {
        this.duration = duration;
    }
    public int getExamAdminId() {
        return examAdminId;
    }
    public void setExamAdminId(int examAdminId) {
        this.examAdminId = examAdminId;
    }
    public Exam(int id, String name, Date startDate, Date endDate, Time duration, int examAdminId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.examAdminId = examAdminId;
    }
    public Exam()
    {

    }
    public static List<Exam> getExams(int examAdminId,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select * from exam where examAdminId="+examAdminId;
        ResultSet rs=stmt.executeQuery(sql);
        List<Exam> li=new ArrayList<>();
        while(rs.next())
        {
            Exam e=new Exam(rs.getInt("id"),rs.getString("name"),rs.getDate("startDate"),rs.getDate("endDate"),rs.getTime("duration"),rs.getInt("examAdminId"));
            li.add(e);
        }
        return li;
    }
    public static List<Exam> getExams(Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select * from exam";
        ResultSet rs=stmt.executeQuery(sql);
        List<Exam> li=new ArrayList<>();
        while(rs.next())
        {
            Exam e=new Exam(rs.getInt("id"),rs.getString("name"),rs.getDate("startDate"),rs.getDate("endDate"),rs.getTime("duration"),rs.getInt("examAdminId"));
            li.add(e);
        }
        return li;
    }
    public static void createExam(Exam e,Connection con,List<Question> lq,List<Option> lo) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select MAX(id) from exam";
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        e.id=rs.getInt("MAX(id)")+1;
        sql="insert into exam values("+e.id+",'"+e.name+"','"+e.startDate+"','"+e.endDate+"','"+e.duration+"',"+e.examAdminId+")";
        stmt.executeUpdate(sql);
        for(int i=0;i<lq.size();i++)
        {
            lq.get(i).setExamId(e.id);
            List<Option> ol=new ArrayList<Option>();
            for(int j=0;j<4;j++)
            {
                ol.add(lo.get(4*i+j));
            }
            Question.createQuestion(lq.get(i),ol,con);
        }
    }
    public static void submitExam(int score,int examId,int participantId,Connection con) throws SQLException
    {
        Result.createResult(score,examId,participantId,con);
    }
    public static void deleteExam(int examId,Connection con) throws SQLException
    {
        Question.deleteQuestions(examId,con);
        Statement stmt=con.createStatement();
        String sql="delete from exam where id="+examId;
        stmt.executeUpdate(sql);
        System.out.println("Deleted");
    }
}
