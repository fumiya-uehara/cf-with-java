package com.myorg;

import com.github.eladb.dynamotableviewer.TableViewer;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

public class CfWithJavaStack extends Stack {
    public CfWithJavaStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public CfWithJavaStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        // Defines a new lambda resource
        final Function hello = Function.Builder.create(this, "HelloHandler")
                .runtime(Runtime.NODEJS_10_X)
                .code(Code.fromAsset("lambda"))
                .handler("hello.handler")
                .build();

        // Defines our hitcounter resource
        final HitCounter helloWithCounter = new HitCounter(this, "HelloHitCounter", HItCounterProps.builder()
                .downstream(hello)
                .build());

        // Defines an API Gateway REST API resource backed by our "hello" function
        LambdaRestApi.Builder.create(this, "Endpoint")
                .handler(helloWithCounter.getHandler())
                .build();

        // Defines a viewer for the HitCounts table
        TableViewer.Builder.create(this, "ViewerHitCount")
                .title("Hello Hits")
                .table(helloWithCounter.getTable())
                .build();
    }
}
