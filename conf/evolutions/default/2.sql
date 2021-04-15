# --- !Ups
CREATE TABLE "university"("id" SERIAL PRIMARY KEY AUTO_INCREMENT ,"university_name" varchar(200) , "location" varchar(200));
INSERT INTO "university" values (101,'HCU', 'Hyderabad');
INSERT INTO "university" values (102,'JNU', 'Delhi');
INSERT INTO "university" values (103,'DU', 'Delhi');

CREATE TABLE "user_credential"("user_id" varchar(200) PRIMARY KEY ,"first_name" varchar(200), "last_name" varchar(200), "password" varchar(200), "created_date" DATE);
INSERT INTO "university" values ('abc','sourya', 'kumar', '123', '1997-09-09');
# --- !Downs

DROP TABLE "university";
DROP TABLE "user_credential"


