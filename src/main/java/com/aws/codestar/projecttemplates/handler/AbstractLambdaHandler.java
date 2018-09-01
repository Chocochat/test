package com.aws.codestar.projecttemplates.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.codestar.projecttemplates.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintViolationException;

import static com.aws.codestar.projecttemplates.util.ErrorMsgTransformer.createError;


@Slf4j
public abstract class AbstractLambdaHandler<I, O> implements RequestHandler<I, O> {

    private static final String PROPERTY_SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    protected final ApplicationContext applicationContext;

    protected AbstractLambdaHandler() {
        final long startTimestamp = System.currentTimeMillis();

        if (null == System.getProperty(PROPERTY_SPRING_PROFILES_ACTIVE)) {
            applicationContext = SpringApplication.run(Application.class);
        } else {
            final String springProfiles = "--spring.profiles.active=" + System.getProperty("spring.profiles.active");
            log.info("starting the spring with profile={}", springProfiles);
            applicationContext = SpringApplication.run(Application.class, springProfiles);
        }
        log.error("Spring container loaded in {} ms", (System.currentTimeMillis() - startTimestamp));
    }

    public O handleRequestWithErrorHandling(I param, Context context) {
        try {
            final long startTimestamp = System.currentTimeMillis();
            O o = doHandleRequest(param, context);
            log.error("Total time taken for the API {} is {} ms", context.getFunctionName(), (System.currentTimeMillis() - startTimestamp));
            return o;
        } catch (ConstraintViolationException e) {
            log.error("ConstraintViolationException {}", e);
            throw new RuntimeException(createError("ConstraintViolationException", HttpStatus.SC_BAD_REQUEST, context.getAwsRequestId(), e.getMessage()));
        } catch (Exception e) {
            throw new RuntimeException(createError("Exception", HttpStatus.SC_INTERNAL_SERVER_ERROR, context.getAwsRequestId(), e.getMessage()));
        }

    }

    protected abstract O doHandleRequest(I param, Context context);

}

