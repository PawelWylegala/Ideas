--liquibase formatted sql

--changeset Pawel:002_1
insert into questions (id, name, category_id) values
	(gen_random_uuid(), 'Dlaczego warto uczyć się programowania', (select id from categories where name = 'Edukacja')),
	(gen_random_uuid(), 'Dlaczego Java jest dobrym językiem na start', (select id from categories where name = 'Edukacja'));

insert into questions (id, name, category_id) values
	(gen_random_uuid(), 'Jakie są najzdrowsze warzywa?', (select id from categories where name = 'Zdrowie'));

insert into answers (id, name, question_id) values
	(gen_random_uuid(), 'Marchewka', (select id from questions where name = 'Jakie są najzdrowsze warzywa?')),
	(gen_random_uuid(), 'Brokuł', (select id from questions where name = 'Jakie są najzdrowsze warzywa?')),
	(gen_random_uuid(), 'Dynia', (select id from questions where name = 'Jakie są najzdrowsze warzywa?')),
	(gen_random_uuid(), 'Groch', (select id from questions where name = 'Jakie są najzdrowsze warzywa?'));

insert into answers (id, name, question_id) values
	(gen_random_uuid(), 'Gdańsk', (select id from questions where name = 'Gdzie najlepiej spędzić wakacje z Polsce')),
	(gen_random_uuid(), 'Bieszczady', (select id from questions where name = 'Gdzie najlepiej spędzić wakacje z Polsce')),
	(gen_random_uuid(), 'Mazury', (select id from questions where name = 'Gdzie najlepiej spędzić wakacje z Polsce'));

