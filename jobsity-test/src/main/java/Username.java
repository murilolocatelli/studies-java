public class Username {

    public static boolean validate(String username) {
        if (username.length() < 4) {
            return false;
        }

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[a-zA-Z_]");
        java.util.regex.Matcher matcher = pattern.matcher(username);

        return matcher.find();
    }

    public static void main(String[] args) {
        System.out.println(validate("1Mike")); // Valid username
        System.out.println(validate("Mike Standish")); // Invalid username
    }

}