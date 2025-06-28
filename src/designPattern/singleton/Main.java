package designPattern.singleton;

// AppConfiguration.java - Manages global application settings
class AppConfiguration {
    // 1. Private static instance of itself
    private static AppConfiguration instance;

    // Configuration properties
    private String currencySymbol;
    private final double defaultTaxRate;
    private final String paymentGatewayApiKey;

    // 2. Private constructor to prevent instantiation from outside
    private AppConfiguration() {
        // Load configuration from a file, database, or set defaults
        this.currencySymbol = "$";
        this.defaultTaxRate = 0.08; // 8%
        this.paymentGatewayApiKey = "your_api_key_here_loaded_securely";
        System.out.println("AppConfiguration instance created.");
    }

    // 3. Public static method to get the instance
    public static synchronized AppConfiguration getInstance() {
        if (instance == null) {
            instance = new AppConfiguration();
        }
        return instance;
    }

    // Getters for configuration properties
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    // Example: Update a setting (in a real app, this might be more complex)
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public double getDefaultTaxRate() {
        return defaultTaxRate;
    }

    public String getPaymentGatewayApiKey() {
        return paymentGatewayApiKey;
    }
}

// Main.java - How to use it
public class Main {
    public static void main(String[] args) {
        AppConfiguration config1 = AppConfiguration.getInstance();
        AppConfiguration config2 = AppConfiguration.getInstance();

        System.out.println("Config1 Currency: " + config1.getCurrencySymbol());
        System.out.println("Config2 Tax Rate: " + config2.getDefaultTaxRate());

        config1.setCurrencySymbol("€");
        System.out.println("Config2 Currency after change by config1: " + config2.getCurrencySymbol());

        System.out.println("Are config1 and config2 the same instance? " + (config1 == config2));
    }
}