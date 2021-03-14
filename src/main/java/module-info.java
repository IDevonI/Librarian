module stud.devon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.gluonhq.charm.glisten;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.mail;
    requires java.xml.bind;

    opens stud.devon.service to java.activation;
    opens stud.devon.entities to org.hibernate.orm.core;
    opens stud.devon.controllers to javafx.fxml,com.gluonhq.charm.glisten;
    opens stud.devon to javafx.fxml,com.gluonhq.charm.glisten;
    exports stud.devon.service;
    exports stud.devon.entities;
    exports stud.devon.controllers;
    exports stud.devon;
}