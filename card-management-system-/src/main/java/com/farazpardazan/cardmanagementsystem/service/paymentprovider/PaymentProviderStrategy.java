package com.farazpardazan.cardmanagementsystem.service.paymentprovider;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
/*import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;*/

/**
 * @author Hossein Baghshahi
 */
public interface PaymentProviderStrategy {

    public void moneyTransfer(Transfer transfer) throws PaymentProviderException;

    public PaymentStrategyName getPaymentStrategyName();
}
