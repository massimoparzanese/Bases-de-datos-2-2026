package unlp.info.bd2.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class TourGuideUser extends User {

    @Column(nullable = false, length = 100)
    private String education;
    
    @ManyToMany(mappedBy = "tourGuideList")
    private List<Route> routes;

    public TourGuideUser() {
    }

    public TourGuideUser(String username, String password, String name, String email, String education) {
        super(username, password, name, email);
        this.education = education;
    }


    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
