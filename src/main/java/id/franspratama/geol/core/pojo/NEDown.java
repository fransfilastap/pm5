package id.franspratama.geol.core.pojo;

public class NEDown {
	
    private String region;
    private int neDown2g;
    private int neDown3g;
    private int totalNe2g;
    private int totalNe3g;
    private double percentageNeDown2g;
    private double percentageNeDown3g;

    public NEDown() {
    }

    public NEDown(String region, int neDown2g, int neDown3g, int totalNe2g, int totalNe3g, double percentageNeDown2g, double percentageNeDown3g) {
        this.region = region;
        this.neDown2g = neDown2g;
        this.neDown3g = neDown3g;
        this.totalNe2g = totalNe2g;
        this.totalNe3g = totalNe3g;
        this.percentageNeDown2g = percentageNeDown2g;
        this.percentageNeDown3g = percentageNeDown3g;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getNeDown2g() {
        return neDown2g;
    }

    public void setNeDown2g(int neDown2g) {
        this.neDown2g = neDown2g;
    }

    public int getNeDown3g() {
        return neDown3g;
    }

    public void setNeDown3g(int neDown3g) {
        this.neDown3g = neDown3g;
    }

    public int getTotalNe2g() {
        return totalNe2g;
    }

    public void setTotalNe2g(int totalNe2g) {
        this.totalNe2g = totalNe2g;
    }

    public int getTotalNe3g() {
        return totalNe3g;
    }

    public void setTotalNe3g(int totalNe3g) {
        this.totalNe3g = totalNe3g;
    }

    public double getPercentageNeDown2g() {
        return percentageNeDown2g;
    }

    public void setPercentageNeDown2g(double percentageNeDown2g) {
        this.percentageNeDown2g = percentageNeDown2g;
    }

    public double getPercentageNeDown3g() {
        return percentageNeDown3g;
    }

    public void setPercentageNeDown3g(double percentageNeDown3g) {
        this.percentageNeDown3g = percentageNeDown3g;
    }
}
