import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("Loving #Java and #java at #FMI!")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("No tags here, just vibes")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("#MJT #mjt #MjT are all one tag")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("Adjacent #one#two tags")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("A lone # is not a tag, but #this1 is")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("Trailing hash #")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("   ")
        ));

        System.out.println(Arrays.toString(
                HashtagExtractor.extractHashtags("Happy new year #2024!")
        ));

        System.out.println(DnsResolver.resolve(
                new String[]{"www.fmi.bg", "fmi.uni-sofia.bg", "uni-sofia.bg"},
                new String[]{"fmi.uni-sofia.bg", "uni-sofia.bg", "learn.fmi.bg"},
                "www.fmi.bg"
        ));

        System.out.println(DnsResolver.resolve(
                new String[]{"moodle.bg", "login.moodle.bg"},
                new String[]{"login.moodle.bg", "moodle.bg"},
                "moodle.bg"
        ));

        System.out.println(DnsResolver.resolve(
                new String[]{"localhost"},
                new String[]{"localhost"},
                "localhost"
        ));

        System.out.println(DnsResolver.resolve(
                new String[]{"www.fmi.bg", "fmi.uni-sofia.bg"},
                new String[]{"fmi.uni-sofia.bg", "uni-sofia.bg"},
                "example.com"
        ));
    }
}