package com.farazpardazan.cardmanagementsystem.configuration;

/**
 * Class for all constants values.
 *
 * @author Hossein Baghshahi
 */
public final class Constants {

    public static class PaymentProviders {
        public static final String MELLAT_URL = "https://first-payment-provider/payments/transfer";

        public static final String SAMAN_URL = "https://second-payment-provider/cards/pay";
    }

    public static class Kafka {
        public static final String SMS_TOPIC = "farazpardaz-sms-notification";
    }

    public static class URLMapping {
        private static final String API_PREFIX = "/api";

        public static final String CARDS = API_PREFIX + "/cards";

        public static final String TRANSFER = API_PREFIX + "/transfer";

        public static final String CARD_TO_CARD = "/card-to-card";

        public static final String REPORT = "/report";

    }
}
