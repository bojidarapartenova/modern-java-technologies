import java.util.Arrays;
import java.util.Locale;

public class HashtagExtractor {
    public static String[] extractHashtags(String post)
    {
        if(post == null || post.isEmpty())
        {
            return new String[0];
        }

        post = post.toLowerCase(Locale.ROOT);
        String[] result = new String[post.length()];

        int count=0;
        int i=0;
        while(i<post.length())
        {
            if(post.charAt(i)=='#')
            {
                int start=i+1;
                int end=start;

                while(end<post.length() && Character.isLetterOrDigit(post.charAt(end)))
                {
                    end++;
                }

                if(start<end)
                {
                    String tag = post.substring(start, end);
                    boolean exists=false;

                    for(int j=0; j<count; j++)
                    {
                        if(result[j].equals(tag))
                        {
                            exists=true;
                            break;
                        }
                    }

                    if(!exists)
                    {
                        result[count++]=tag;
                    }
                    i=end;
                }
                else
                {
                    i++;
                }
            }
            else
            {
                i++;
            }
        }
        return Arrays.copyOf(result, count);
    }
}
