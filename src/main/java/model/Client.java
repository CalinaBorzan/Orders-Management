package model;
/**
 * Represents a client in the system.
 * This class encapsulates all the relevant information about a client, including their unique identifier, personal details, and contact information.
 */
public class Client {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private int age;
    private String address;
    /**
     * Constructs a new Client with specified details.
     *
     * @param id The unique identifier for the client.
     * @param lastName The client's last name.
     * @param firstName The client's first name.
     * @param email The client's email address.
     * @param age The client's age.
     * @param address The client's physical address.
     */
    public Client( int id,String lastName,String firstName,String email,int age,String address)
    {
        super();
        this.id=id;
        this.age=age;
        this.lastName=lastName;
        this.firstName=firstName;
        this.email=email;
        this.address=address;

    }
    /**
     * Gets the client's ID.
     *
     * @return the client's ID.
     */
    public int getId() {
        return id;
    }
    /**
     * Gets the client's age.
     *
     * @return the client's age.
     */
    public int getAge() {
        return age;
    }
    /**
     * Sets the client's ID.
     *
     * @param id the new ID for the client.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the client's email address.
     *
     * @return the client's email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Gets the client's first name.
     *
     * @return the client's first name.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Gets the client's last name.
     *
     * @return the client's last name.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Sets the client's age.
     *
     * @param age the new age for the client.
     */
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * Sets the client's email address.
     *
     * @param email the new email address for the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Sets the client's first name.
     *
     * @param firstName the new first name for the client.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Sets the client's last name.
     *
     * @param lastName the new last name for the client.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Gets the client's address.
     *
     * @return the client's address.
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the client's address.
     *
     * @param address the new address for the client.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Returns a string representation of the client, containing all relevant information.
     *
     * @return a string representation of the client.
     */
    @Override
    public String toString() {
        return "Client [id=" + id + ", lastName= " + lastName + "firstName= " + firstName+ ", address= " + address + ", email= " + email + ", age= " + age
                + "]";
    }


}
