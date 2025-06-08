// Nama File: Rumah.java
public class Rumah {
    private int id;
    private int overallQual;
    private int grLivArea; // Luas lantai di atas permukaan tanah
    private int garageCars;
    private int totalBsmtSF; // Total luas basement
    private int fullBath;
    private int yearBuilt;
    private int salePrice;

    // Constructor
    public Rumah(int id, int overallQual, int grLivArea, int garageCars,
                 int totalBsmtSF, int fullBath, int yearBuilt, int salePrice) {
        this.id = id;
        this.overallQual = overallQual;
        this.grLivArea = grLivArea;
        this.garageCars = garageCars;
        this.totalBsmtSF = totalBsmtSF;
        this.fullBath = fullBath;
        this.yearBuilt = yearBuilt;
        this.salePrice = salePrice;
    }

    // Getter methods
    public int getId() { return id; }
    public int getOverallQual() { return overallQual; }
    public int getGrLivArea() { return grLivArea; }
    public int getGarageCars() { return garageCars; }
    public int getTotalBsmtSF() { return totalBsmtSF; }
    public int getFullBath() { return fullBath; }
    public int getYearBuilt() { return yearBuilt; }
    public int getSalePrice() { return salePrice; }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Kualitas: " + overallQual +
                ", Luas Hidup (sqft): " + grLivArea +
                ", Garasi Mobil: " + garageCars +
                ", Basement (sqft): " + totalBsmtSF +
                ", Kamar Mandi: " + fullBath +
                ", Tahun Bangun: " + yearBuilt +
                ", Harga Jual: $" + salePrice;
    }
}