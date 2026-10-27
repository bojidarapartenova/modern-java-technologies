package bg.sofia.uni.fmi.mjt.parking.vehicle;

import java.util.Objects;

public class LicensePlate {

    private final String licensePlate;

    private LicensePlate(String licensePlate)
    {
        this.licensePlate=licensePlate;
    }

    public static LicensePlate parse(String text)
    {
        if (text == null || text.isEmpty()) {
            return null;
        }

        char[] chars=new char[text.length()];
        int length=0;

        for(int i=0; i<text.length(); i++)
        {
            char curr=Character.toUpperCase(text.charAt(i));

            if(curr==' ' || curr=='-')
            {
                continue;
            }

            curr = switch (curr) {
                case 'А' -> 'A';
                case 'В' -> 'B';
                case 'Е' -> 'E';
                case 'К' -> 'K';
                case 'М' -> 'M';
                case 'Н' -> 'H';
                case 'О' -> 'O';
                case 'Р' -> 'P';
                case 'С' -> 'C';
                case 'Т' -> 'T';
                case 'У' -> 'Y';
                case 'Х' -> 'X';
                default -> curr;
            };

            chars[length]=curr;
            length++;
        }

        String plate=new String(chars, 0, length);

        if (length < 6 || length > 8) {
            return null;
        }

        int prefixLen=0;
        while(prefixLen<length && isAllowedLetter(plate.charAt(prefixLen)))
        {
            prefixLen++;
        }

        if(prefixLen<1 || prefixLen>2)
        {
            return null;
        }

        int index=prefixLen;
        int digitCount=0;
        while(index<length && isAllowedDigit(plate.charAt(index)))
        {
            digitCount++;
            index++;
        }

        if(digitCount!=4)
        {
            return null;
        }

        int suffixLen=length-index;
        if(suffixLen<1 || suffixLen>2)
        {
            return null;
        }

        while(index<length)
        {
            if(!isAllowedLetter(plate.charAt(index)))
            {
                return null;
            }
            index++;
        }

        return new LicensePlate(plate);
    }

    private static boolean isAllowedLetter(char c) {
        return c == 'A' || c == 'B' || c == 'E'
                || c == 'K' || c == 'M' || c == 'H'
                || c == 'O' || c == 'P' || c == 'C'
                || c == 'T' || c == 'Y' || c == 'X';
    }

    private static boolean isAllowedDigit(char c){
        return c>='0' && c<='9';
    }

    @Override
    public String toString()
    {
        return licensePlate;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this==obj)
        {
            return true;
        }

        if(!(obj instanceof LicensePlate other))
        {
            return false;
        }

        return licensePlate.equals(other.licensePlate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(licensePlate);
    }
}
