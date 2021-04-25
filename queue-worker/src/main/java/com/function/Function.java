package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class Function {
    /**
     * This function will be invoked when a new message is received at the Service
     * Bus Queue.
     */
    @FunctionName("Function")
    public void run(
            @ServiceBusQueueTrigger(name = "sbus", dataType = "binary", queueName = "job_queue", connection = "AzureWebJobsServiceBus") CsvModel message,
            final ExecutionContext context) {
        context.getLogger().info("Java Service Bus Queue trigger function executed.");
        context.getLogger().info(message.toString());
        // Add logic
    }
}
