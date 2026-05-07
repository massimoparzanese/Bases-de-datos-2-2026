package unlp.info.bd2.dto;

public class RouteSummaryDTO {
    private String routeName;
    private Long purchaseCount;
    private Double averagePrice;

    public RouteSummaryDTO(String routeName, Long purchaseCount, Double averagePrice) {
        this.routeName = routeName;
        this.purchaseCount = purchaseCount;
        this.averagePrice = averagePrice;
    }

    // Getters
    public String getRouteName() {
        return routeName;
    }

    public Long getPurchaseCount() {
        return purchaseCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    // Setters (opcionales si solo se usa para lectura desde la query, pero convenientes)
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setPurchaseCount(Long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }
}
