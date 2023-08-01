import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Classes.Exam;
import Classes.ExamAdmin;
import Classes.Option;
import Classes.Participant;
import Classes.Question;
import Classes.User;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input=new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineexam","root","Hari@2003");

        System.out.println("------------------------------------------------------");
        System.out.println("Welecome to Online Exam Portal");
        System.out.println("To Login Enter :1");
        System.out.println("To Register Enter :2");
        int choice=input.nextInt();
        input.nextLine();
        User u=new User();
        if(choice==2)
        {
            System.out.println("Enter your Username:");
            String username=input.nextLine();
            System.out.println("Enter your Password:");
            String password=input.nextLine();
            System.out.println("Select Role:");
            System.out.println("Participant Enter 1:");
            System.out.println("ExamAdmin Enter 2:");
            String role="";
            int c=0;
            while(true)
            {
                c=input.nextInt();
                input.nextLine();
                if(c==1)
                {
                    role="Participant";
                    break;
                }
                else if(c==2)
                {
                    role="ExamAdmin";
                    break;
                }
                else
                {
                    System.out.println("Enter a valid number");
                }
            }
            u=new User(1,username,password,role);
            User.register(u,con);
        }
        else if(choice==1)
        {
            while(true)
            {
                System.out.println("Enter the username:");
                String username=input.nextLine();
                System.out.println("Enter the password:");
                String password=input.nextLine();
                u=User.login(username,password,con);
                if(u!=null)
                {
                    System.out.println("Welcome "+u.getUsername());
                    break;
                }
                else{
                    System.out.println("Invalid Username");
                }
            }
        }
        System.out.println("------------------------------------------------------");
        u.welcome();
        if(u.getRole().equals("ExamAdmin"))
        {
            ExamAdmin ea=ExamAdmin.getExamAdmin(u.getId(),con);
            while(true)
            {
                System.out.println("------------------------------------------------------");

                System.out.println();
                System.out.println("To View Exams Enter 1:");
                System.out.println("To Create Exam Enter 2:");
                System.out.println("To Edit Exam Enter 3:");
                System.out.println("To Delete Exam Enter 4:");
                System.out.println("To exit Enter 5:");
                int ch=input.nextInt();
                input.nextLine();
                List<Exam> li=Exam.getExams(ea.getId(),con);
                if(ch==1)
                {
                    System.out.println("Your Exams");
                    System.out.println();
                    int ind=1;
                    for(Exam e:li)
                    {
                        System.out.println(ind+":"+e.getName());
                        ind++;
                    }
                }
                else if(ch==2)
                {
                    System.out.println("Creating...");
                    System.out.println();
                    System.out.println("Enter the name of the Exam:");
                    String name=input.nextLine();
                    System.out.println("Enter the start date of the Exam:");
                    String startDates=input. nextLine();
                    Date startDate=Date.valueOf(startDates);
                    System.out.println("Enter the end date of the Exam:");
                    String endDates=input. nextLine();
                    Date endDate=Date.valueOf(endDates);
                    System.out.println("Enter the duration of the Exam:");
                    String durations=input. nextLine();
                    Time duration=Time.valueOf(durations);
                    Exam e=new Exam(1,name,startDate,endDate,duration,ea.getId());
                    System.out.println("Enter the number of questions:");
                    int que=input.nextInt();
                    input.nextLine();
                    List<Question> ql=new ArrayList<Question>();
                    List<Option> ol=new ArrayList<Option>();
                    for(int i=0;i<que;i++)
                    {
                        System.out.println("Enter the "+(i+1)+" question:");
                        String qis=input.nextLine();
                        System.out.println("Enter the  four options one by one");
                        for(int j=0;j<4;j++)
                        {
                            System.out.println("Enter Option "+(j+1));
                            String opt=input.nextLine();
                            Option opto=new Option((i+j),opt,false,i);
                            ol.add(opto);
                        }
                        System.out.println("Enter the sno of the correct Answer :");
                        int k=input.nextInt();
                        input.nextLine();
                        ol.get(4*i+k-1).setValue(true);
                        Question qiso=new Question(i,qis,1);
                        ql.add(qiso);
                    }
                    Exam.createExam(e, con,ql,ol);
                }
                else if(ch==4)
                {
                    System.out.println("Enter The sno of Exam to be Deleted :");
                    int examId=input.nextInt();
                    input.nextLine();
                    Exam.deleteExam(li.get(examId-1).getId(), con);
                    li=Exam.getExams(ea.getId(), con);
                }
                else if(ch==5)
                {
                    System.out.println("Bye "+u.getUsername());
                    break;
                }
                System.out.println("------------------------------------------------------");

            }
        }
        else{
            Participant p=Participant.getParticipant(u.getId(), con);
            while(true)
            {
                System.out.println("------------------------------------------------------");

                System.out.println();
                System.out.println("To view all exams Enter 1:");
                System.out.println("To take exam Enter 2:");
                System.out.println("To view results Enter 3:");
                System.out.println("To exit Enter 4:");
                System.out.println();
                int ch=input.nextInt();
                input.nextLine();
                List<Exam> li=Exam.getExams(con);
                if(ch==1)
                {
                    System.out.println("All Exams");
                    System.out.println();
                    int ind=1;
                    for(Exam e:li)
                    {
                        System.out.println(ind+":"+e.getName());
                        ind++;
                    }
                }
                else if(ch==2)
                {
                    System.out.println("Enter the Exam sno to be taken :");
                    int examId=input.nextInt();
                    input.nextLine();
                    p.takeExam(li.get(examId-1).getId(), con);
                }
                else if(ch==3)
                {
                    System.out.println("Enter the Exam sno to view Result");
                    int examId=input.nextInt();
                    input.nextLine();
                    p.viewResult(li.get(examId-1).getId(),con);
                }
                else if(ch==4)
                {
                    System.out.println("Bye "+u.getUsername());
                    break;
                }
                System.out.println("------------------------------------------------------");
            }
        }
        input.close();
    }
}