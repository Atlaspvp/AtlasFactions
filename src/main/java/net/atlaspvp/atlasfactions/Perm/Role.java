package net.atlaspvp.atlasfactions.Perm;

public enum Role {
    OWNER("Leader"),
    COLEADER("Coleader"),
    MODERATOR("Mod"),
    MEMBER("Member"),
    RECRUIT("Recruit");

    private final String name;

    Role(String name) {
        this.name = name;
    }


}
