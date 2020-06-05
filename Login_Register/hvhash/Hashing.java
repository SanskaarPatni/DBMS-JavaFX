package hvhash;
public class Hashing 
{
    public static String hash(String st)
    {
        int l=st.length();
        String s="";
        int c=0;
        for(int i=0;i<l;i++)
        {
            char ch=st.charAt(i);
            ch=(char)(ch+c);
            s=s+ch;
            if(c==l%5)
            {
                s=s+"salt";
                c=0;
            }
            c++;
        }
        return s;
    }
}