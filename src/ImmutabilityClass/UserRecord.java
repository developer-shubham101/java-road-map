package ImmutabilityClass;

// Records can have custom constructors, static methods, and instance methods too:
record UserRecord(int id, String username, String email) {
    // Compact constructor for validation/normalization (runs before field initialization)
    public UserRecord {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if (email != null) {
            email = email.toLowerCase(); // Normalization
        }
    }

    // Static factory method
    public static UserRecord createGuest(int id) {
        return new UserRecord(id, "guest-" + id, null);
    }

    // Additional instance method
    public String getDisplayInfo() {
        return "User #" + id + ": " + username + " (" + (email != null ? email : "No Email") + ")";
    }
}