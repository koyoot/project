public class Country {
    private final String countryName;
    private final String regionCountry;
    private final int rank;
    private final float happiness;
    private final float error;
    private final float countryEconomy;
    private final float countryFamily;
    private final float countryHealth;
    private final float countryFreedom;
    private final float countryTrust;
    private final float countryGenerosity;
    private final float dystopia;

    public Country(String countryName, String regionCountry, int rank, float happiness,
        float error, float countryEconomy, float countryFamily, float countryHealth, float countryFreedom,
        float countryTrust, float countryGenerosity, float dystopia) {
            this.countryName = countryName;
            this.regionCountry = regionCountry;
            this.rank = rank;
            this.happiness = happiness;
            this.error = error;
            this.countryEconomy = countryEconomy;
            this.countryFamily = countryFamily;
            this.countryHealth = countryHealth;
            this.countryFreedom = countryFreedom;
            this.countryTrust = countryTrust;
            this.countryGenerosity = countryGenerosity;
            this.dystopia = dystopia;
    }
    
    public String getCountryName() { return countryName; }
    public String getCountryRegion() { return regionCountry; }
    public int getRank() { return rank; }
    public float getHappiness() { return happiness; }
    public float getError() { return error; }
    public float getCountryEconomy() { return countryEconomy; }
    public float getCountryFamily() { return countryFamily; }
    public float getCountryHealth() { return countryHealth; }
    public float getCountryFreedom() { return countryFreedom; }
    public float getCountryTrust() { return countryTrust; }
    public float getCountryGenerosity() { return countryGenerosity; }
    public float getDystopia() { return dystopia; }

    @Override
    public String toString() {
        return String.format("======Данные о стране======:\nНазвание страны: %s\nРегион: %s\nРанг: %d\n" +
                        "Счёт: %f\nОшибка: %f\nЭкономика: %f\nСемья: %f\nЗдоровье: %f\nСвобода: %f\n" +
                        "Доверие: %f\nЩедрость: %f\nАнтиутопия: %f",
                        countryName,
                        regionCountry,
                        rank,
                        happiness,
                        error,
                        countryEconomy,
                        countryFamily,
                        countryHealth,
                        countryFreedom,
                        countryTrust,
                        countryGenerosity,
                        dystopia).toString();
    }
}
