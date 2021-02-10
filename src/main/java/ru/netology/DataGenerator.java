package ru.netology;

import com.github.javafaker.Faker;
import jdk.jfr.ContentType;

import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto generateActiveUser() {
            Faker faker = new Faker(new Locale("rus"));
            return new RegistrationDto (
                    faker.name().username(),
                    faker.internet().password(),
                    "active");
        }

        public static RegistrationDto generateBlockedUser() {
            Faker faker = new Faker(new Locale("rus"));
            return new RegistrationDto(
                    faker.name().username(),
                    faker.internet().password(),
                    "blocked");
        }
    }

    public static class UserGenerator {
        private static RequestSpecification
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static void setUpAll(RegistrationDto  user) {
            given()
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }
    }
}
