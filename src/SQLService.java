import java.sql.*;
import java.util.ArrayList;

public class SQLService
{
    Connection connection;
    public ArrayList<User> users = new ArrayList<User>();

    public SQLService()
    {
        String url = "jdbc:sqlserver://griffinn.duckdns.org\\SQLEXPRESS:54886;databaseName=MinesweeperLogin";
        String username = "User";
        String password = "asdasd123";

        try
        {
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connected to SQL Server");
        } catch (SQLException e)
        {
            System.out.println("Can't connect to SQL Server");
            e.printStackTrace();
        }

        getAllUsers();
    }
    public void addUser(String username, String password)
    {
        String sqlCommand = "INSERT INTO Account (Id, Username, Password, BestTimeInSeconds ) VALUES " +  "(" + (users.size()+1) +", '"+username +"', '" + password + "', null);";

        try
        {
            PreparedStatement prepsInsertProduct = connection.prepareStatement(sqlCommand);
            prepsInsertProduct.execute();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        getAllUsers();
    }
    public void getAllUsers()
    {
        ArrayList<User> users = new ArrayList<User>();
        User helper = new User();
        String sqlCommand = "SELECT * FROM Account;";
        ResultSet rs = null;
        try
        {
            PreparedStatement prepsInsertProduct = connection.prepareStatement(sqlCommand);
            rs = prepsInsertProduct.executeQuery();
            while (rs.next()) {
                helper.id = Integer.parseInt( rs.getString(1));
                helper.username = rs.getString(2);
                helper.password = rs.getString(3);
                helper.bestTimeInSeconds = rs.getString(4) == null ? null : Integer.parseInt(rs.getString(4));
                users.add(helper);
                helper = new User();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        this.users = users;
    }
    public void updateUser(User currentUser)
    {
        String sqlCommand = "UPDATE Account SET BestTimeInSeconds = " + currentUser.bestTimeInSeconds + " WHERE Id = " + currentUser.id + ";";

        try {
            PreparedStatement prepsInsertProduct = connection.prepareStatement(sqlCommand);
            prepsInsertProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
