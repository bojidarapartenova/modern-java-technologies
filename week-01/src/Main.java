
public class Main {
    public static void main(String[] args) {

        String[] hashtags1 = HashtagExtractor.extractHashtags("Loving #Java and #java at #FMI!");
        String[] hashtags2 = HashtagExtractor.extractHashtags("No tags here, just vibes");
        String[] hashtags3 = HashtagExtractor.extractHashtags("#MJT #mjt #MjT are all one tag");
        String[] hashtags4 = HashtagExtractor.extractHashtags("Adjacent #one#two tags");
        String[] hashtags5 = HashtagExtractor.extractHashtags("A lone # is not a tag, but #this1 is");
        String[] hashtags6 = HashtagExtractor.extractHashtags("Trailing hash #");
        String[] hashtags7 = HashtagExtractor.extractHashtags("   ");
        String[] hashtags8 = HashtagExtractor.extractHashtags("Happy new year #2024!");

        System.out.println(java.util.Arrays.toString(hashtags1));
        System.out.println(java.util.Arrays.toString(hashtags2));
        System.out.println(java.util.Arrays.toString(hashtags3));
        System.out.println(java.util.Arrays.toString(hashtags4));
        System.out.println(java.util.Arrays.toString(hashtags5));
        System.out.println(java.util.Arrays.toString(hashtags6));
        System.out.println(java.util.Arrays.toString(hashtags7));
        System.out.println(java.util.Arrays.toString(hashtags8));
    }
}
