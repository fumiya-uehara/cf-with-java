package com.myorg;

import software.amazon.awscdk.core.App;

public final class CfWithJavaApp {
    public static void main(final String[] args) {
        App app = new App();

        new CfWithJavaStack(app, "CfWithJavaStack");

        app.synth();
    }
}
