package com.realdolmen.rair.domain.entities.user;

import com.realdolmen.rair.data.PasswordManager;
import com.realdolmen.rair.data.dao.Toggle;
import com.realdolmen.rair.domain.Authorizable;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.security.NoSuchAlgorithmException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement
//@DiscriminatorColumn(columnDefinition = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
@DiscriminatorValue("User")
public abstract class User implements Toggle, Authorizable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @XmlTransient
    @JsonIgnore
    private String hash;

    @XmlTransient
    @JsonIgnore
    private String salt;

    private Boolean active = true;

    @Embedded
    private ContactInformation contactInformation;

    private transient PasswordManager pm;

    public User() {

    }

    protected User(String firstName, String lastName, String password, ContactInformation contactInformation) throws NoSuchAlgorithmException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInformation = contactInformation;
        setPassword(password);
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    protected void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        if (pm == null) {
            pm = new PasswordManager();
        }
        if (salt == null) {
            salt = pm.getRandomSalt();
        }
        hash = new String(pm.hashText(salt, password));
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (hash != null ? !hash.equals(user.hash) : user.hash != null) return false;
        if (salt != null ? !salt.equals(user.salt) : user.salt != null) return false;
        if (contactInformation != null ? !contactInformation.equals(user.contactInformation) : user.contactInformation != null)
            return false;
        return pm != null ? pm.equals(user.pm) : user.pm == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (contactInformation != null ? contactInformation.hashCode() : 0);
        result = 31 * result + (pm != null ? pm.hashCode() : 0);
        return result;
    }

    @Override
    public void activate() {
        active = true;
    }

    @Override
    public void deactivate() {
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean flag) {
        active = flag;
    }
}
