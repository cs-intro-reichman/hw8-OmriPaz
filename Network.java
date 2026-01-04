/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        //// Replace the following statement with your code
        for (User user : this.users) {
            if (user == null) {return null;}
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (this.users.length == userCount) {return false;}
        User res = this.getUser(name);
        if (res == null) {
            User user = new User(name);
            this.users[this.userCount++] = user;
            return true;
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {        
        if (name1 == null | name2 == null) {return false;}
        if (name1.equals(name2)) {return false;}
        boolean found1 = false;
        boolean found2 = false;
        for (User user : this.users) {
            if (user == null) {break;}
            if (user.getName().equals(name1)) {found1 = true;}
            if (user.getName().equals(name2)) {found2 = true;}
        }
        if (!found1 | !found2) {return false;}
        return getUser(name1).addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = this.getUser(name);
        String[] userFollows = user.getfFollows();

        int maxMutual = 0;
        User recommendedFollow = this.users[0];
        for (User nUser : this.users) {
            if (nUser == null) {break;}
            if (nUser.getName().equals(user.getName())) {continue;}
            if (user.follows(nUser.getName())) {continue;} // We cant recommend someone that we user already follows
            int mutual = 0;
            String[] nFollows = nUser.getfFollows();
            for (String userf : userFollows) {
                if (userf == null) {break;}
                for (String nuserf: nFollows) {
                    if (nuserf == null) {break;}
                    if (userf.equals(nuserf)) {mutual++;}
                }
            }
            if (mutual > maxMutual) {
                maxMutual = mutual;
                recommendedFollow = nUser;
            }
        }
        return recommendedFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        String popularUser = "null";
        int maxCount = 0;
        for (User user : this.users) {
            if (user == null) {break;}
            int countFollowers = 0;
            for (User arbUser : this.users) {
                if (arbUser == null) {break;}
                if (user == arbUser) {continue;}
                if (arbUser.follows(user.getName())) {
                    countFollowers++;
                }
            }
            if (countFollowers > maxCount) {
                maxCount = countFollowers;
                popularUser = user.getName();
            }
        }
        return popularUser;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        for (User user : this.users) {
            if (user == null) {break;}
            if (user.getName().equals(name)) {continue;}
            if (user.follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String s = "Network:";
        for (User user : this.users) {
            if (user == null) {break;}
            s += "\n";
            s += user.getName() + " -> ";
            for (String fUser : user.getfFollows()) {
                if (fUser == null) {break;}
                s += fUser + " ";
            }
        }
        return s;
    }
}
