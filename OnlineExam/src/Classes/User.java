package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
abstract class Welcome
{
    abstract void welcome();
}

public class User extends Welcome {
    private int id;
    private String username;
    private String password;
    private String role;
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User()
    {

    }
    public static User login(String uname,String pass,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String q="select * from users where username='"+uname+"' and password='"+pass+"'";
        ResultSet rs=stmt.executeQuery(q);
        if(rs.next())
        {
            User u=new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("role"));
            return u;
        }
        else{
            return null;
        }
    }
    public static void  register(User u,Connection con) throws SQLException
    {
        Statement stmt = con.createStatement();
        String sql="select MAX(id) from users";
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        int id=rs.getInt("max(id)");
        u.id=id+1;
        sql = "INSERT INTO Users VALUES ("+(id+1)+",'"+u.username+"','"+u.password+"','"+u.role+"')";
         stmt.executeUpdate(sql);
         if(u.role=="Participant")
         {
            sql="insert into participants values("+u.id+",'"+u.username+"',"+u.id+")";
            stmt.executeUpdate(sql);
         }
         else
         {
            sql="insert into exam_admin values("+u.id+",'"+u.username+"',"+u.id+")";
            stmt.executeUpdate(sql);
         }
    }
    public void welcome()
    {
        System.out.println("hello");
    }
}
