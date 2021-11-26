public class Converter
{
    public static String secondsToString(long seconds)
    {
        if(seconds >= 3600)
            return "Don't play this game anymore please!!!";
        else
        {
            long MM = seconds / 60;
            long SS = seconds % 60;
            return String.format("%02d:%02d", MM, SS);
        }
    }
}
