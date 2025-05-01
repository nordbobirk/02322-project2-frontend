/**
 * For demonstration purposes and for avoiding some JavaFX warnings, the RoboRally
 * application is now configured as a Java module.
 */
module roborally {

    requires javafx.controls;
    requires org.jetbrains.annotations;
    requires com.google.common;
    requires com.google.gson;
    requires jdk.xml.dom;

    exports yukon;

}