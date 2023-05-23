
package EcoAware1;

class Activity {
    private String ActivityType;
    private double distance;
    public Activity(String ActivityType, double distance) {
        this.ActivityType = ActivityType;
        this.distance = distance;
    }
    public String getType() {
        return ActivityType; }
    
    public double getDistance() {
        return distance;}
    
    public double getCarbonEmissions() {
        switch (ActivityType.toLowerCase()) {
            case "walking":
                return distance * 0.0;
            case "biking":
                return distance * 0.01;
            case "driving":
                return distance * 0.4;
            default:
                return 0.0;
        }   
}
}

