package unlp.info.bd2.model;


import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class DriverUser extends User {

    @Column(nullable = false, length = 50)
    private String expedient;
    @ManyToMany(mappedBy = "driverList")
    private List<Route> routes;

    public DriverUser() {
    }
    public DriverUser(String username, String password, String name, String email, String expedient) {
        super(username, password, name, email);
        this.expedient = expedient;
    }
    

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRouts(List<Route> routs) {
        this.routes = routs;
    }
}
