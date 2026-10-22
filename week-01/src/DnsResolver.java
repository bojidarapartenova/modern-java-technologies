public class DnsResolver {
    private static String nextHost(String[] from, String[] to, String host)
    {
        for(int i=0; i<from.length; i++)
        {
            if(from[i].equals(host))
            {
                return to[i];
            }
        }
        return null;
    }

    public static String resolve(String[] from, String[] to, String start)
    {
        String slow = start;
        String fast = start;
        while(true)
        {
            String nextSlow = nextHost(from, to, slow);

            if(nextSlow == null)
            {
                return slow;
            }
            slow = nextSlow;

            String nextFast = nextHost(from, to, fast);

            if(nextFast == null)
            {
                return fast;
            }
            fast = nextFast;

            String nextFast2 = nextHost(from, to, fast);

            if(nextFast2 == null)
            {
                return fast;
            }
            fast = nextFast2;

            if(slow.equals(fast))
            {
                return null;
            }
        }
    }
}
