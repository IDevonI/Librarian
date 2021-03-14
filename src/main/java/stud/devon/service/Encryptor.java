package stud.devon;

public class Encryptor {

    public static String encrypt(String n)
    {
        char[] s=n.toCharArray();
        for(int i=0;i<n.length();i++)
        {
            int x1,x2,x3,x4,x32,x41;
            x1=s[i];
            x2=s[i]>>1;
            x3=s[i]>>2;
            x4=s[i]>>3;
            x1=x1&1;
            x2=x2&1;
            x3=x3&1;
            x4=x4&1;
            x32=x3^x2;
            x41=x4^x1;
            x3=(x32<<2)|(x32<<1);
            x4=(x41<<3)|x41;
            s[i]=(char)(s[i]^x3);
            s[i]=(char)(s[i]^x4);
        }
        StringBuilder w= new StringBuilder();
        for (char x:s) {
            w.append(x);
        }
        return w.toString();
    }
}
