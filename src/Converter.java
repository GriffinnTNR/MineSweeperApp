import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public static String hashPassword(String passwordToHash)
    {
        String generatedPassword = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(passwordToHash.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
