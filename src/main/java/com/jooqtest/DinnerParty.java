package com.jooqtest;

import com.jooqtest.model.tables.records.DinnerGuestsRecord;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jooqtest.model.Tables.*;

/**
 * This JOOQ example relies on three tables to be present in a MYSQL database.
 * Initial filling is supplied by the example itself @see initialize(DSLContext databaseContext)
 *
 * Schema name
 * ===========
 * LOOQTEST (
 *      CREATE DATABASE looqtest;
 *      USE looqtest;
 * )
 *
 * Tables
 * ======
 * CREATE TABLE food (
 *      id BIGINT PRIMARY KEY,
 *      kind VARCHAR(255)
 * );
 *
 * CREATE TABLE dinner_guests(
 *      id BIGINT PRIMARY KEY,
 *      name VARCHAR(255)
 * );
 *
 * CREATE TABLE dinner_favorites (
 *      foodid BIGINT NOT NULL,
 *      dinner_guest_id BIGINT NOT NULL,
 *      FOREIGN KEY fk_food(foodid) REFERENCES food(id),
 *      FOREIGN KEY fk_dinner_guests(dinner_guest_id) REFERENCES dinner_guests(id)
 * );
 */
public class DinnerParty {

    public static void main(final String[] args) throws SQLException {
        new DinnerParty().run();
    }

    public void run() throws SQLException {
        // Get connection and initialize DSLContext
        Connection conn = getConnection();
        DSLContext database = DSL.using(conn, SQLDialect.MYSQL);

        // Initialize all tables
        initialize(database);

        // List the entries currently present
        printFavoriteFoodForAllDinnerGuests(database);

        // Use the selectFrom() method:
        DinnerGuestsRecord bert = database.selectFrom(DINNER_GUESTS).where(DINNER_GUESTS.NAME.equal("Bert")).fetchOne();
        // Typesafe field access is now possible:
        System.out.println("ID: " + bert.getId());
        System.out.println("Name: " + bert.getName());

        Result<DinnerGuestsRecord> result = database.selectFrom(DINNER_GUESTS).where(DINNER_GUESTS.ID.in(
                database.selectFrom(DINNER_FAVORITES).where(DINNER_FAVORITES.FOODID.equal(
                                database.selectFrom(FOOD).where(FOOD.KIND.equal("Fruit snack")).fetchOne().getId())
                ).fetch(DINNER_FAVORITES.DINNER_GUEST_ID))).fetch();
        for (DinnerGuestsRecord dinnerGuestsRecord : result) {
            System.out.println(dinnerGuestsRecord.getName() + " likes a Fruit snack");
        }


        SelectConditionStep<Record1<String>> select = database.select(DINNER_GUESTS.NAME).from(DINNER_GUESTS).where(DINNER_GUESTS.ID.in(
                database.select(DINNER_FAVORITES.DINNER_GUEST_ID).from(DINNER_FAVORITES).where(DINNER_FAVORITES.FOODID.equal(
                        database.select(FOOD.ID).from(FOOD).where(FOOD.KIND.equal("Fruit snack"))))
        ));
        String sql = select.getSQL();
        System.out.println("SQL output: " + sql);
        for (Result<Record> stringRecord1 : select.fetchMany()) {
            System.out.println(stringRecord1.field(DINNER_GUESTS.NAME).toString() + " likes a Fruit snack");
        }


        // --------------------------------------------------
        // INSERT a record
        // --------------------------------------------------
        // Suppose Lindsay is starting to love fruit snacks too..
        // --------------------------------------------------
        Long fruitSnackId = database.select(FOOD.ID).from(FOOD).where(FOOD.KIND.equal("Fruit snack")).fetch().getValue(0, FOOD.ID);
        Long dinnerGuestLindsayId = database.select(DINNER_GUESTS.ID).from(DINNER_GUESTS).where(DINNER_GUESTS.NAME.equal("Lindsay")).fetch().getValue(0, DINNER_GUESTS.ID);
        database.insertInto(DINNER_FAVORITES,
                DINNER_FAVORITES.FOODID, DINNER_FAVORITES.DINNER_GUEST_ID)
                .values(fruitSnackId, dinnerGuestLindsayId)
        .execute();

        System.out.println("########################################");
        System.out.println("After INSERT-ing a record:");
        printFavoriteFoodForDinnerGuest(database, "Lindsay");

        // --------------------------------------------------
        // DELETE a record
        // --------------------------------------------------
        // Nope, she just said so, but it's not true
        // --------------------------------------------------
        database.delete(DINNER_FAVORITES)
                .where(DINNER_FAVORITES.FOODID.equal(fruitSnackId))
                .and(DINNER_FAVORITES.DINNER_GUEST_ID.equal(dinnerGuestLindsayId))
        .execute();

        System.out.println("########################################");
        System.out.println("After DELETE-ing a record:");
        printFavoriteFoodForDinnerGuest(database, "Lindsay");

        // --------------------------------------------------
        // UPDATE a record
        // --------------------------------------------------
        // Lindsay just stopped loving candy, now she's all into Indonesian food
        // --------------------------------------------------
        Long candyId = database.select(FOOD.ID).from(FOOD).where(FOOD.KIND.equal("Candy")).fetch().getValue(0, FOOD.ID);
        Long indonesianFoodId = database.select(FOOD.ID).from(FOOD).where(FOOD.KIND.equal("Indonesian food")).fetch().getValue(0, FOOD.ID);
        database.update(DINNER_FAVORITES)
                .set(DINNER_FAVORITES.FOODID, indonesianFoodId)
                .where(DINNER_FAVORITES.FOODID.equal(candyId))
                .and(DINNER_FAVORITES.DINNER_GUEST_ID.equal(dinnerGuestLindsayId))
        .execute();

        System.out.println("########################################");
        System.out.println("After UPDATE-ing a record:");
        printFavoriteFoodForDinnerGuest(database, "Lindsay");

        conn.close();
        System.exit(0);
    }

    private void printFavoriteFoodForDinnerGuest(DSLContext database, String dinnerGuest) {
        System.out.println("########################################");
        // query food for dinnerGuest
        Result<Record2<Long, String>> result = database
                .select(FOOD.ID, FOOD.KIND)
                .from(FOOD)
                .join(DINNER_FAVORITES).on(DINNER_FAVORITES.FOODID.equal(FOOD.ID))
                .join(DINNER_GUESTS).on(DINNER_FAVORITES.DINNER_GUEST_ID.equal(DINNER_GUESTS.ID))
                .where(DINNER_GUESTS.NAME.equal(dinnerGuest))
                .orderBy(FOOD.KIND.asc(), FOOD.ID.asc())
        .fetch();

        System.out.println("The favorite food of " + dinnerGuest + " is:");
        for (Record2<Long, String> record : result) {
            System.out.println("- " + String.format("%s (food id: %s)", record.getValue(FOOD.KIND), record.getValue(FOOD.ID)));
        }
        System.out.println("########################################\n");
    }

    private void printFavoriteFoodForAllDinnerGuests(DSLContext database) {
        System.out.println("########################################");
        System.out.println("<< Listing all dinner guest favorites >>");
        System.out.println("########################################");
        // query food for dinnerGuest
        Result<Record3<String, Long, String>> result = database
                .select(DINNER_GUESTS.NAME, FOOD.ID, FOOD.KIND)
                .from(FOOD)
                .join(DINNER_FAVORITES).on(DINNER_FAVORITES.FOODID.equal(FOOD.ID))
                .join(DINNER_GUESTS).on(DINNER_FAVORITES.DINNER_GUEST_ID.equal(DINNER_GUESTS.ID))
                .orderBy(DINNER_GUESTS.NAME.asc(), FOOD.KIND.asc(), FOOD.ID.asc())
        .fetch();

        System.out.println(result); // prints out table format
        for (Record3<String, Long, String> record : result) {
            System.out.println("- " + String.format("%s loves %s (food id: %s)", record.getValue(DINNER_GUESTS.NAME), record.getValue(FOOD.KIND), record.getValue(FOOD.ID)));
        }
        System.out.println("########################################\n");
    }

    private void initialize(DSLContext database) {
        // NOTE: Be sure to delete and insert according to the foreign keys defined in the DDL
        database.delete(DINNER_FAVORITES).execute();
        database.delete(DINNER_GUESTS).execute();
        database.delete(FOOD).execute();

        database.insertInto(FOOD, FOOD.ID, FOOD.KIND)
                .values(1L, "Fruit snack")
                .values(2L, "Indonesian food")
                .values(3L, "Candy")
                .values(4L, "Vegetables, potatoes with gravy and a piece of meat")
        .execute();

        database.insertInto(DINNER_GUESTS, DINNER_GUESTS.ID, DINNER_GUESTS.NAME)
                .values(1L, "Bert")
                .values(2L, "Jacky")
                .values(3L, "Lindsay")
                .values(4L, "Suus")
        .execute();

        database.insertInto(DINNER_FAVORITES, DINNER_FAVORITES.DINNER_GUEST_ID, DINNER_FAVORITES.FOODID)
                .values(4L, 1L)  // Suus loves Fruit snacks
                .values(4L, 4L)  // Suus loves Vegetables, potatoes with gravy and a piece of meat
                .values(2L, 1L)  // Jacky loves Fruit snacks
                .values(2L, 2L)  // Jacky loves Fruit snacks
                .values(3L, 3L)  // Lindsay loves candy
                .values(1L, 4L)  // Bert loves Vegetables, potatoes with gravy and a piece of meat
        .execute();
    }

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jooqtest",
                    "jooqtestadmin",
                    "jooqtestadminPassword");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
