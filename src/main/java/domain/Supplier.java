package domain;

public class Supplier {

    private int id;
    private String name, address, postalCode, contactName, email, phoneNo;

    public Supplier(int id, String name, String address, String postalCode, String contactName, String email, String phoneNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.contactName = contactName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setId(int newid) {
        id = newid;
    }

    public void setAttString(String what, String value) {
        switch (what) {
            default:
                break;

            case "name":
                name = value;
                break;

            case "address":
                address = value;
                break;

            case "postalCode":
                postalCode = value;
                break;

            case "contactName":
                contactName = value;
                break;

            case "email":
                email = value;
                break;

            case "phoneNo":
                phoneNo = value;
                break;
        }
    }
}
