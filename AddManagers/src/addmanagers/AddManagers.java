package addmanagers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddManagers
{
    private final String loginFile;
    
    public AddManagers(String loginFile)
    {
        this.loginFile = loginFile;
    }
    
    public void Run()
    {
        try
        {  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto2?serverTimezone=CET&useSSL=false","root","root"))
            {
                InsertIntoDB(con, FindMissingElements(ReadLoginsFromFile(), ReadLoginsFromDB(con)));
                RemoveFromDB(con, FindMissingElements(ReadLoginsFromDB(con), ReadLoginsFromFile()));
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }  
    }
    
    private void InsertIntoDB(Connection con, List<Login> elements)
    {
        for(Login l : elements)
        {
            try
            {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("insert into manager (email, password, username) values (\"" + l.email + "\", \"" + l.password + "\", \"" + l.username + "\")");
                System.out.println("Inserted [" + l + "] into the database");
            }
            catch(SQLException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }
    
    private void RemoveFromDB(Connection con, List<Login> elements)
    {
        for(Login l : elements)
        {
            try
            {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("delete from manager where id = " + l.id);
                System.out.println("Removed [" + l + "] from the database");
            }
            catch(SQLException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }
    
    private <T> List<T> FindMissingElements(List<T> target, List<T> basedOn)
    {
        List<T> missing = new ArrayList<>();
        
        for(T t : target)
            if(!basedOn.contains(t))
                missing.add(t);
        
        return missing;
    }
    
    private List<Login> ReadLoginsFromFile()
    {
        List<Login> logins = new ArrayList<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(loginFile)));
            
            String line;
            while((line = br.readLine()) != null)
            {
                String elements[] = line.split(";");
                logins.add(new Login(elements[0].trim(), elements[1].trim(), PasswordHasher.plainTextToHash(elements[2].trim())));
            }
            
            br.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
        
        return logins;
    }
    
    private List<Login> ReadLoginsFromDB(Connection con)
    {
        List<Login> logins = new ArrayList<>();
        
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from manager");
            while(rs.next())
                logins.add(new Login(rs.getInt(1), rs.getString(4), rs.getString(2), rs.getString(3)));
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e);
        }
        
        return logins;
    }
    
    private class Login
    {
        public final int id;
        public final String username;
        public final String email;
        public final String password;
        
        public Login(String username, String email, String password) {this(-1, username, email, password);}
        public Login(int id, String username, String email, String password)
        {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
        }
        
        @Override
        public String toString()
        {
            return "username: " + username + "\temail: " + email + "\tpassword: " + password;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 47 * hash + Objects.hashCode(this.username);
            hash = 47 * hash + Objects.hashCode(this.email);
            hash = 47 * hash + Objects.hashCode(this.password);
            return hash;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final Login other = (Login) obj;
            if (!Objects.equals(this.username, other.username))
            {
                return false;
            }
            if (!Objects.equals(this.email, other.email))
            {
                return false;
            }
            if (!Objects.equals(this.password, other.password))
            {
                return false;
            }
            return true;
        }
    }
    
    
    
    
    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.out.println("Wrong syntax!\nUse java AddManagers <login file path>");
            System.exit(0);
        }
        
        new AddManagers(args[0]).Run();
    }
}
    