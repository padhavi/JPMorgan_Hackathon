package hk.ust.cse.comp107x.ngoconnect;



public class FormDetails {




    //name and address string
    private String victimname;
    private String victimphno;
    private String victimdob,victimdoc,victimid;
    public FormDetails() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters

    public String getid() {
        return victimid;
    }

    public void setid(String id) {
        this.victimid = id;
    }


    public String getName() {
        return victimname;
    }

    public void setName(String name) {
        this.victimname = name;
    }



    public String getphno() {
        return victimphno;
    }

    public void setphno(String phno) {
        this.victimphno = phno;
    }



    public String getdob() {
        return victimdob;
    }

    public void setdob(String dob) {
        this.victimdob = dob;
    }


    public String getdoc() {
        return victimdoc;
    }

    public void setdoc(String doc) {
        this.victimdoc = doc;
    }





}
