public class User
{
    public int id;
    public String username;
    public String password;
    public Integer bestTimeInSeconds;

    public String toString()
    {
        return id + ", " + username + ", " + password + ", " + bestTimeInSeconds;
    }

    public int getBestTimeInSeconds(){return bestTimeInSeconds;}
}
