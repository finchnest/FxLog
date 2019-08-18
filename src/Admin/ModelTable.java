package Admin;


public class ModelTable {
    String fname,lname,username,usertype;
    int account;

    public ModelTable(String username, int account){
        this.username=username;
        this.account=account;
    }
    
    public ModelTable(String fname, String lname, String username,  String usertype, int account) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.usertype = usertype;
        this.account = account;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }
    
    
}
