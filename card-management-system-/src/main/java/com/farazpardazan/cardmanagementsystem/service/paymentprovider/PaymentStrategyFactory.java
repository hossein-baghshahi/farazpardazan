package com.farazpardazan.cardmanagementsystem.service.paymentprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Hossein Baghshahi
 */
@Component
public class PaymentStrategyFactory {

    private Map<PaymentStrategyName, PaymentProviderStrategy> paymentStrategies;

    @Autowired
    public PaymentStrategyFactory(Set<PaymentProviderStrategy> paymentStrategySet) {
        createPaymentStrategy(paymentStrategySet);
    }

    public PaymentProviderStrategy getAppropriatePaymentProvider(String sourceCardNumber){
        if (sourceCardNumber.startsWith("6037"))
            return paymentStrategies.get(PaymentStrategyName.SAMAN);
        return paymentStrategies.get(PaymentStrategyName.MELLAT);
    }

    private void createPaymentStrategy(Set<PaymentProviderStrategy> paymentStrategySet) {
        paymentStrategies = new HashMap<PaymentStrategyName, PaymentProviderStrategy>();
        paymentStrategySet.forEach(
                strategy -> paymentStrategies.put(strategy.getPaymentStrategyName(), strategy));
    }
}
