package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Participant extends User {
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
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Participant(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
    public Participant()
    {
        
    }
    public static Participant getParticipant(int userId,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select * from participants where userId="+userId;
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        Participant ea=new Participant(rs.getInt("id"),rs.getString("name"),rs.getInt("userId"));
        return ea;
    }
    public void takeExam(int examId,Connection con) throws SQLException
    {
        Scanner inpu=new Scanner(System.in);
        Statement stmt = con.createStatement();
        String sql="select * from exam where id="+examId;
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        Exam e=new Exam(rs.getInt("id"),rs.getString("name"),rs.getDate("startDate"),rs.getDate("endDate"),rs.getTime("duration"),rs.getInt("examAdminId"));
        System.out.println("Take exam "+e.getName());
        System.out.println();
        int score=0;
        int tot=0;
        sql="select * from question where examId="+e.getId();
        rs=stmt.executeQuery(sql);
        List<Question> lq=new ArrayList<Question>();
        while(rs.next())
        {
            tot++;
            Question q=new Question(rs.getInt("id"),rs.getString("question"),rs.getInt("examId"));
            lq.add(q);
        }
        List<Option> lo=new ArrayList<Option>();
        int ind=0;
        for(Question q:lq)
        {
            ind++;
            System.out.println(ind+" :"+q.getQuestion());
            sql="select * from options where questionId="+q.getId();
            rs=stmt.executeQuery(sql);
            int in=0;
            while(rs.next())
            {
                in++;
                Option o=new Option(rs.getInt("id"),rs.getString("opt"),rs.getBoolean("val"),rs.getInt("questionId"));
                lo.add(o);
                System.out.println(in+") "+o.getOpt());
            }
            System.out.println("Answer :");
            int num=inpu.nextInt();
            inpu.nextLine();
            if(lo.get(num-1).isValue())
            {
                score++;
            }
            lo.clear();
        }
        System.out.println(score+"/"+tot);
        Exam.submitExam(score,e.getId(),this.id,con);
        // inpu.close();
    }
    public void viewResult(int examId,Connection con) throws SQLException
    {
        Statement stmt=con.createStatement();
        String sql="select * from result where participantId="+this.id+" and examId="+examId;
        ResultSet rs=stmt.executeQuery(sql);
        int attempt=0;
        while(rs.next())
        {
            attempt++;
            System.out.println("Attempt :"+attempt);
            System.out.println(rs.getInt("mark"));
        }
        if(attempt==0)
        {
            System.out.println("Test Not Attended");
        }
    }
}