
# --- !Ups
CREATE TABLE "student"("id" SERIAL PRIMARY KEY AUTO_INCREMENT ,"name" varchar(200) , "email" varchar(200), "university_id" int, "date_of_birth" date);
INSERT INTO "student" values (1,'Bob', 'bob@xyz.com', 101, '1990-12-12');
INSERT INTO "student" values (2,'Rob', 'rob@xyz.com',102, '1991-11-10');
--INSERT INTO "student" values (3,'sam', 'sam@xyz.com',102, '1991-05-05');


# --- !Downs

DROP TABLE "student";



