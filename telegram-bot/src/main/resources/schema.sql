create schema java_spring_bot;

create table java_spring_bot.competitors
(
id bigserial primary key,
telegram_id bigint not null,
name text,
gender character,
company text,
age integer,
created_at timestamptz default now(),
updated_at timestamptz default now(),
total_result numeric,
competition_type integer default 0
);


create table java_spring_bot.cities
(
id bigserial primary key,
name text,
region_id bigint
);

create table java_spring_bot.companies
(
id bigserial primary key,
name text,
city_id integer
);

create table java_spring_bot.regions
(
id bigserial primary key,
name text
);

create table java_spring_bot.errors
(
id bigserial primary key,
message text,
telegram_id bigint not null,
created_at timestamptz default now()
);

create table java_spring_bot.replies
(
id bigserial primary key,
message text,
telegram_id bigint not null,
created_at timestamptz default now()
);

create table java_spring_bot.results
(
id bigserial primary key,
message text,
telegram_id bigint not null,
created_at timestamptz default now(),
total_result numeric,
last_added_result numeric
);

create table java_spring_bot.users
(
id bigserial primary key,
telegram_id bigint not null,
name text,
full_user_name text,
user_status integer default 0,
created_at timestamptz default now(),
);

Create View java_spring_bot.full_company_table as

select java_spring_bot.companies.name as "Предприятие",
	java_spring_bot.cities.name as "Город",
	java_spring_bot.regions.name as "Регион"

from java_spring_bot.companies,
	java_spring_bot.cities,
	java_spring_bot.regions

where java_spring_bot.companies.city_id = java_spring_bot.cities.id
and java_spring_bot.cities.region_id = java_spring_bot.regions.id

Order by "Регион", "Город", "Предприятие"
